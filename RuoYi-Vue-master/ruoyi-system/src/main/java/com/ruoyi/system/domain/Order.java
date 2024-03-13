package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.entity.SysProductInfo;
import com.ruoyi.system.domain.common.Entity;
import com.ruoyi.system.domain.enums.OrderState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 留言表
 * @author xxl
 * @TableName sys_leave_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_order")
@Data
public class Order extends Entity implements Serializable {
    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 文件保存地址
     */
    private String userId;

    /**
     * 订单状态
     */
    private OrderState orderState;

    /**
     * 发货地址
     */
    private String deliveryAddress;

    /**
     * 订单总价
     */
    private Boolean lumpSum;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品信息
     */
    @TableField(exist = false)
    private List<SysProductInfo> products;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}