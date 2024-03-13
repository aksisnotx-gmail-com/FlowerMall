package com.ruoyi.web.controller.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.common.Entity;
import com.ruoyi.system.domain.param.OrderParam;
import com.ruoyi.system.service.impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 订单接口
 *
 * @author xxl
 * @since 2024/3/13
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderServiceImpl orderService;
    @PostMapping("/pay")
    @ApiOperation("下单")
    public R<Boolean> placeAnOrder(@RequestBody @JsonView(Entity.INSERT.class) @Validated(Entity.INSERT.class) OrderParam order) {
        return R.ok(orderService.pay(order, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @PostMapping("/continue/pay")
    @ApiOperation("付款")
    public R<Boolean> continuePay(@RequestBody @JsonView(Entity.UPDATE.class) @Validated(Entity.UPDATE.class) OrderParam order) {
        return R.ok(orderService.continuePay(order, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/cancel/{orderId}")
    @ApiOperation("取消订单")
    public R<Boolean> cancel(@PathVariable("orderId") String orderId) {
        return R.ok(orderService.cancel(orderId, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/get")
    @ApiOperation("获取个人所有订单")
    public R<Page<Order>> get() {
        return R.ok(orderService.getOrders(SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/get/{orderId}")
    @ApiOperation("获取订单信息")
    public R<Order> get(@PathVariable("orderId") String orderId) {
        return R.ok(orderService.getOrderInfo(orderId));
    }

    @GetMapping("/admin/getAll")
    @ApiOperation("管理员获取所有订单")
    @PreAuthorize("@ss.hasRole('admin')")
    public R<Page<Order>> getAll() {
        return R.ok(orderService.getAllOrders());
    }

    @GetMapping("/admin/delete/{orderId}")
    @ApiOperation("管理员删除订单信息")
    @PreAuthorize("@ss.hasRole('admin')")
    public R<Boolean> adminDeleteOrder(@PathVariable("orderId") String orderId) {
        return R.ok(orderService.adminDeleteOrder(orderId));
    }

    @GetMapping("/delete/{orderId}")
    @ApiOperation("删除订单信息")
    public R<Boolean> deleteOrder(@PathVariable("orderId") String orderId) {
        return R.ok(orderService.deleteOrder(orderId,SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }
}
