package com.ruoyi.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.param.FlowerTutorialParam;
import com.ruoyi.system.mapper.FlowerTutorialMapper;
import com.ruoyi.system.service.impl.FIleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Boolean saveTutorial(FlowerTutorialParam param, String strUserId) {
        FlowerTutorial flowerTutorial = new FlowerTutorial();
        BeanUtil.copyProperties(param,flowerTutorial);
        String fileStr = listToString(param.getFileId());
        flowerTutorial.setUserId(strUserId);
        flowerTutorial.setFileId(fileStr);
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
}
