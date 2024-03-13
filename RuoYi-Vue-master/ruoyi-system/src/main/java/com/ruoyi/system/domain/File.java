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
@TableName(value ="sys_file")
@Data
public class File extends Entity implements Serializable {
    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 文件保存地址
     */
    private String path;

    @TableField(exist = false)
    private String suffix;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}