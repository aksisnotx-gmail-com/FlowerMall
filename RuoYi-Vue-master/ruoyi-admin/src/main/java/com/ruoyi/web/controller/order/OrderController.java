package com.ruoyi.web.controller.order;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysProductInfo;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单接口
 *
 * @author xxl
 * @since 2024/3/13
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @PostMapping("/pay")
    public R<Boolean> pay(@RequestBody SysProductInfo order) {
        return null;
    }
}
