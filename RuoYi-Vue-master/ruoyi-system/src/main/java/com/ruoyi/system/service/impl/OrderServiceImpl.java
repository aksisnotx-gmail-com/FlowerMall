package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysProductInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.CommonPageRequestUtils;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.enums.OrderState;
import com.ruoyi.system.domain.param.OrderParam;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.service.ISysProductInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xxl
 * @since 2024/3/13
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> {

    private final ISysProductInfoService service;

    public Boolean pay(OrderParam order, String userId) {
        Order orderEntity = new Order();
        BeanUtil.copyProperties(order,orderEntity);
        orderEntity.setUserId(userId);
        orderEntity.setOrderState(OrderState.WAITING);
        orderEntity.setProductId(JSONUtil.toJsonStr(order.getProductId()));
        return this.save(orderEntity);
    }

    public Boolean continuePay(OrderParam order, String userId) {
        Order orderEntity = checkUser(check(order.getId()), userId);
        orderEntity.setDeliveryAddress(order.getDeliveryAddress());
        Assert.isTrue(orderEntity.getOrderState().equals(OrderState.WAITING),"当前状态不允许付款");
        orderEntity.setOrderState(OrderState.PAID);
        return this.updateById(orderEntity);
    }

    public Boolean cancel(String orderId, String userId) {
        Order orderEntity = checkUser(check(orderId), userId);
        if (!OrderState.PAID.equals(orderEntity.getOrderState())) {
            throw new GlobalException("只有未付款的订单才能取消");
        }
        orderEntity.setOrderState(OrderState.CANCELLED);
        return this.updateById(orderEntity);
    }

    public CommonPageRequestUtils.PageVO<Order> getOrders(String userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        return getPage(wrapper);
    }

    public CommonPageRequestUtils.PageVO<Order> getAllOrders() {
        return getPage(null);
    }

    public Boolean adminDeleteOrder(String orderId) {
        Order check = check(orderId);
        Assert.isTrue(!OrderState.WAITING.equals(check.getOrderState()),"当前订单状态不允许删除");
        return this.removeById(orderId);
    }

    public Boolean deleteOrder(String orderId, String userId) {
        Order order = checkUser(check(orderId), userId);
        Assert.isTrue(!OrderState.WAITING.equals(order.getOrderState()),"当前订单状态不允许删除");
        return this.removeById(orderId);
    }

    private Order check(String orderId) {
        Order byId = this.getById(orderId);
        Assert.notNull(byId,"订单不存在");
        return byId;
    }

    private Order checkUser(Order order, String userId) {
        Assert.isTrue(order.getUserId().equals(userId),"当前订单不属于当前用户");
        return order;
    }

    private CommonPageRequestUtils.PageVO<Order> getPage(LambdaQueryWrapper<Order> wrapper) {
        CommonPageRequestUtils.PageVO<Order> page;
        if (!Objects.isNull(wrapper)) {
            page = CommonPageRequestUtils.getPageVO(this, wrapper);
        }else {
            page = CommonPageRequestUtils.getPageVO(this, null);
        }

        page.getRecords().forEach(t -> {
            List<SysProductInfo> products = JSONUtil.toList(t.getProductId(), String.class).stream().map(service::selectSysProductInfoById).collect(Collectors.toList());
            t.setProducts(products);
        });

        return page;
    }

    public Order getOrderInfo(String orderId) {
        Order byId = this.getById(orderId);
        List<SysProductInfo> products = JSONUtil.toList(byId.getProductId(), String.class).stream().map(service::selectSysProductInfoById).collect(Collectors.toList());
        byId.setProducts(products);
        return byId;
    }


}
