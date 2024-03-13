package com.ruoyi.web.controller.file;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.File;
import com.ruoyi.system.service.impl.FIleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 *
 * @author xxl
 * @since 2024/3/13
 */
@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
@Validated
@RequiredArgsConstructor
public class FileController {

    private final FIleServiceImpl fileService;

    @PostMapping("upload")
    @ApiOperation("上传文件支持JPEG,FLAC,PNG,GIF,MP4,AVI,FLV,MKV,MOV,WMV,WEBM")
    public R<String> upload(@RequestBody MultipartFile file) {
        return R.ok(fileService.saveFile(file));
    }

    @GetMapping("download/{fileId}")
    @ApiOperation("下载文件")
    public  R<File> download(@PathVariable("fileId") String fileId) {
        return R.ok(fileService.getById(fileId));
    }
}
