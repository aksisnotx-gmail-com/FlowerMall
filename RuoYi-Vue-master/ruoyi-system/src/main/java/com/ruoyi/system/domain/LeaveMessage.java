package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.system.domain.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 留言表
 * @author xxl
 * @TableName sys_leave_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_leave_message")
@Data
public class LeaveMessage extends Entity implements Serializable {
    /**
     * ID
     */
    @TableId
    @JsonView({IGNORE.class})
    private String id;

    /**
     * 留言信息
     */
    @JsonView({INSERT.class})
    @NotBlank(message = "信息内容不能为空",groups = INSERT.class)
    private String message;

    /**
     * 用户ID
     */
    @JsonView({IGNORE.class})
    private String userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}