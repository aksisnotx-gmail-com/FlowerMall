package com.ruoyi.system.domain.param;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.system.domain.common.Entity;
import com.ruoyi.system.domain.enums.OrderState;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xxl
 * @since 2024/3/13
 */
@Data
public class OrderParam implements Serializable {

    private static final long serialVersionUID = -3785509502467623982L;

    /**
     * ID
     */
    @JsonView({Entity.UPDATE.class})
    @NotBlank(message = "ID不能为空",groups = Entity.UPDATE.class)
    private String id;

    /**
     * 发货地址
     */
    @JsonView({Entity.INSERT.class})
    @NotBlank(message = "配送地址不能为空",groups = Entity.INSERT.class)
    private String deliveryAddress;

    /**
     * 订单总价
     */
    @JsonView({Entity.INSERT.class})
    private Double lumpSum;

    /**
     * 商品ID
     */
    @JsonView({Entity.INSERT.class})
    @NotEmpty(message = "商品ID不能为空",groups = Entity.INSERT.class)
    private List<String> productId;
}
