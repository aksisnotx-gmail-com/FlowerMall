package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.LeaveMessage;
import com.ruoyi.system.mapper.LeaveMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxl
 * @since 2024/3/12
 */
@Service
public class LeaveMessageServiceImpl extends ServiceImpl<LeaveMessageMapper, LeaveMessage> {
    public boolean saveLeaveMessage(LeaveMessage message) {
        return this.save(message);
    }

    public Boolean deleteMessage(String messageId) {
        return this.removeById(messageId);
    }
}
