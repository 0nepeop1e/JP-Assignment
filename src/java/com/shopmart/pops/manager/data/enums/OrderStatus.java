package com.shopmart.pops.manager.data.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public enum OrderStatus {
    Processing,
    Completed;
    public static OrderStatus fromValue(int val){
        Optional<OrderStatus> os = Arrays.stream(OrderStatus.values())
                .filter(s->s.ordinal() == val).findFirst();
        return os.isPresent()?os.get():Processing;
    }
}