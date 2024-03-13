package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.system.domain.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 留言表
 * @author xxl
 * @TableName sys_leave_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_flowers_tutorial")
@Data
public class FlowerTutorial extends Entity implements Serializable {
    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 教程内容
     */
    private String textDesc;

    /**
     * 文件ID,如果有不为空,如果是多个文件用逗号隔开
     */
    private String fileId;

    /**
     * 用户ID
     */
    private String userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}