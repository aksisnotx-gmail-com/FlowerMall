package com.ruoyi.web.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 系统的相关配置
 *
 * @author xxl
 * @since 2024/2/23
 */
@Configuration
@MapperScan("**.mapper")
public class MybatisPlusConfiguration implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("createUser",SecurityUtils.getLoginUser().getUser().getUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("updateUser",SecurityUtils.getLoginUser().getUser().getUserId());
    }
}
