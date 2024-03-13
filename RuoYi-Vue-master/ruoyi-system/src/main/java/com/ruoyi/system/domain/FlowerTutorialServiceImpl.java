package com.ruoyi.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.file.FileUtils;
import com.ruoyi.common.utils.CommonPageRequestUtils;
import com.ruoyi.system.domain.param.FlowerTutorialParam;
import com.ruoyi.system.mapper.FlowerTutorialMapper;
import com.ruoyi.system.service.impl.FIleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xxl
 * @since 2024/3/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowerTutorialServiceImpl extends ServiceImpl<FlowerTutorialMapper,FlowerTutorial> {

    private final FIleServiceImpl fileService;

    public static final String VIDEO_PICTURES = "/video_Pictures/";

    private final HttpServletRequest request;

    private static final String FILE_LINK = "/file/download/";

    public Boolean saveTutorial(FlowerTutorialParam param, String strUserId) {
        FlowerTutorial flowerTutorial = new FlowerTutorial();
        BeanUtil.copyProperties(param,flowerTutorial);
        flowerTutorial.setUserId(strUserId);
        return this.save(flowerTutorial);
    }

    public Boolean modifyTutorial(FlowerTutorialParam param, String strUserId) {
        FlowerTutorial entity = this.getById(param.getId());
        Assert.notNull(entity, "该教程不存在");
        Assert.isTrue(entity.getUserId().equals(strUserId), "该教程不属于当前用户不可以修改");
        BeanUtil.copyProperties(param,entity);
        return this.updateById(entity);
    }

    public Boolean adminDeleteTutorial(String tutorialId) {
        FlowerTutorial entity = this.getById(tutorialId);
        Assert.notNull(entity, "该教程不存在");
        return  this.removeById(tutorialId) && fileService.removeByIds(stringToList(entity.getFileId()));
    }

    public Boolean deleteTutorial(String tutorialId,String strUserId) {
        FlowerTutorial entity = this.getById(tutorialId);
        Assert.notNull(entity, "该教程不存在");
        Assert.isTrue(entity.getUserId().equals(strUserId), "该教程不属于当前用户不可以修改");
        return this.removeById(tutorialId) && fileService.removeByIds(stringToList(entity.getFileId()));
    }


    private String listToString(List<String> list){
        if(Objects.isNull(list) || list.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int count = list.size();
        for(String str : list){
            sb.append(str);
            if (--count != 0) {
                sb.append(StrUtil.COMMA);
            }
        }
        return sb.toString();
    }

    private List<String> stringToList(String str){
        if(StrUtil.isBlank(str)){
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(StrUtil.COMMA)).collect(Collectors.toList());
    }


    public Page<FlowerTutorial> pageOwner(String strUserId) {
        LambdaQueryWrapper<FlowerTutorial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowerTutorial::getUserId, strUserId);
        //转化
        return getPage(wrapper);
    }

    public Page<FlowerTutorial> pageAll() {
        return getPage(null);
    }

    private Page<FlowerTutorial> getPage(LambdaQueryWrapper<FlowerTutorial> wrapper) {
        Page<FlowerTutorial> page;
        if (!Objects.isNull(wrapper)) {
            page = this.page(CommonPageRequestUtils.defaultPage(), wrapper);
        }else {
            page = this.page(CommonPageRequestUtils.defaultPage());
        }

        page.getRecords().forEach(t -> {
            File file = fileService.getById(t.getFileId());
            String path = file.getPath();
            Assert.notNull(file,"ID为: "+t.getFileId()+"文件不存在");
            file.setPath(getUrl(request).concat(FILE_LINK).concat(file.getId()));
            file.setSuffix(FileUtils.getFileSuffix(path));
            t.setFile(file);
        });
        return page;
    }

    private String getUrl(HttpServletRequest request) {
        // 获取协议（如http或https）
        String scheme = request.getScheme();
        // 获取服务器名称（如localhost或具体域名）
        String serverName = request.getServerName();
        // 获取服务器端口号
        int serverPort = request.getServerPort();
        // 获取请求URI（如/myendpoint）
        String requestURI = request.getRequestURI();

        // 组合成完整的请求URL
        String url = scheme + "://" + serverName + ":" + serverPort ;

        return url;
    }
}
