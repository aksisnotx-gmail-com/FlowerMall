package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 留言表
 * @author xxl
 * @TableName sys_leave_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_file")
@Data
public class OrderInfo extends Entity implements Serializable {
    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 文件保存地址
     */
    private String userId;

    @TableField(exist = false)
    private String suffix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}