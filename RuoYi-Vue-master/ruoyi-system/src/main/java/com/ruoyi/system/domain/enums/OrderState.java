package com.ruoyi.system.domain.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author xxl
 * @since 2024/3/13
 */
@Getter
public enum OrderState implements IEnum<String> {
    WAITING("1", BOrderState.WAITING, COrderState.WAITING),
    PAID("2", BOrderState.PAID, COrderState.PAID),
    CANCELLED("3", BOrderState.CANCELLED, COrderState.CANCELLED);

    private final String id;
    private final BOrderState bOrderState;
    private final COrderState cOrderState;

    OrderState(String id, BOrderState bOrderState, COrderState cOrderState) {
        this.id = id;
        this.bOrderState = bOrderState;
        this.cOrderState = cOrderState;
    }

    @Override
    public String getValue() {
        return id;
    }

    public static OrderState getEnum(String id) {
        for (OrderState e : OrderState.values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

    @Getter
    public enum BOrderState{
        WAITING("待付款"),
        PAID("已付款"),
        CANCELLED("已取消");

        private final String desc;
        BOrderState(String desc) {
            this.desc = desc;
        }
    }

    @Getter
    public enum COrderState{
        WAITING("待付款"),
        PAID("已付款"),
        CANCELLED("已取消");

        private final String desc;
        COrderState(String desc) {
            this.desc = desc;
        }
    }
}
