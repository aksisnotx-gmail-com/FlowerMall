package com.ruoyi.system.service.impl;

import cn.hutool.core.io.FileMagicNumber;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.file.FileUtils;
import com.ruoyi.system.domain.File;
import com.ruoyi.system.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static cn.hutool.core.io.FileMagicNumber.*;

/**
 * @author xxl
 * @since 2024/3/13
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FIleServiceImpl extends ServiceImpl<FileMapper, File> {

    @Value("${file.save.path}")
    private String filePath;

    private final HttpServletResponse response;

    public String saveFile(MultipartFile file) {
        String fileName = null;
        String downloadPath = null;
        String uuid = null;
        try {
            //保存歌曲文件
            fileName = file.getOriginalFilename();

            //检查文件格式格式
            check(fileName,JPEG,FLAC,PNG,GIF,MP4,AVI,FLV,MKV,MOV,WMV,WEBM);

            String path = String.format("%s%s", filePath,fileName);
            downloadPath = FileUtils.upload(file.getInputStream(),path);
            File fileEntity = new File();
            fileEntity.setPath(downloadPath);
            this.save(fileEntity);
            uuid = fileEntity.getId();
        } catch (IOException e) {
            log.error("SongInformationServiceImpl.save抛出异常",e);
            throw new GlobalException("文件保存失败，文件名" + fileName);
        }
        return uuid;
    }

    private void check(String songName, FileMagicNumber...type)  {
        boolean match = Arrays.stream(type).anyMatch(t -> t.getExtension().equalsIgnoreCase(FileUtils.getFileSuffix(songName)));
        Assert.isTrue(match,"不支持的文件格式 = "+ FileUtils.getFileSuffix(songName));
    }

    public void dowload(String id)  {
        File fileEntity = this.getById(id);
        Assert.notNull(fileEntity,() -> new GlobalException("文件不存在"));
        FileUtils.webDownload(fileEntity.getPath(),response,FileUtils.getFileName(fileEntity.getPath()));
    }
}


