package com.ruoyi.system.domain.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author xxl
 * @since 2024/2/27
 */
@Data
public class Entity implements Serializable {

    private static final long serialVersionUID = -1284347847778673827L;

    @JsonView({IGNORE.class})
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @JsonView({IGNORE.class})
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建用户ID
     */
    @JsonView({IGNORE.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新用户ID
     */
    @JsonView({IGNORE.class})
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    public interface INSERT {}
    public interface IGNORE {}
    public interface UPDATE {}
}
