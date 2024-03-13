package com.ruoyi.system.domain.param;

import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.system.domain.common.Entity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 留言表
 * @author xxl
 * @TableName sys_leave_message
 */
@Data
public class FlowerTutorialParam  implements Serializable {

    private static final long serialVersionUID = 4984579341897490260L;

    @JsonView({Entity.UPDATE.class})
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 教程内容
     */
    @NotBlank(message = "教程内容不能为空",groups = {Entity.INSERT.class, Entity.UPDATE.class})
    @JsonView({Entity.INSERT.class, Entity.UPDATE.class})
    private String textDesc;

    /**
     * 文件ID,如果有不为空,如果是多个文件用逗号隔开
     */
    @JsonView({Entity.INSERT.class, Entity.UPDATE.class})
    private String fileId;
}