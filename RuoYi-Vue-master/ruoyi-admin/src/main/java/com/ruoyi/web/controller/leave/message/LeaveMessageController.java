package com.ruoyi.web.controller.leave.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.CommonPageRequestUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.FlowerTutorial;
import com.ruoyi.system.domain.LeaveMessage;
import com.ruoyi.system.domain.common.Entity;
import com.ruoyi.system.service.impl.LeaveMessageServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xxl
 * @since 2024/3/12
 */
@Api(tags = "留言接口")
@RestController
@RequestMapping("/leave/message")
@RequiredArgsConstructor
@Validated
public class LeaveMessageController {

    private final LeaveMessageServiceImpl messageService;

    @PostMapping("release")
    @ApiOperation("发布留言")
    public R<Boolean> release(@RequestBody
                             @JsonView(Entity.INSERT.class)
                             @Validated(Entity.INSERT.class) LeaveMessage message) {
        message.setUserId(SecurityUtils.getLoginUser().getUser().getStrUserId());
        return R.ok(messageService.saveLeaveMessage(message));
    }

    @GetMapping("getMessage")
    @ApiOperation("分页获取自己留言")
    public R<CommonPageRequestUtils.PageVO<LeaveMessage>> get() {
        LambdaQueryWrapper<LeaveMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveMessage::getUserId, SecurityUtils.getLoginUser().getUser().getStrUserId());
        return R.ok(CommonPageRequestUtils.getPageVO(messageService, wrapper));
    }
    @GetMapping("getAllMessage")
    @ApiOperation("分页获取所有的留言")
    public R<CommonPageRequestUtils.PageVO<LeaveMessage>> getAll() {
        return R.ok(CommonPageRequestUtils.getPageVO(messageService, null));
    }

    @GetMapping("delete/{messageId}")
    @ApiOperation("删除留言")
    public R<Boolean> deleteMessage(@PathVariable("messageId") String messageId) {
        return R.ok(messageService.deleteMessage(messageId));
    }

}
