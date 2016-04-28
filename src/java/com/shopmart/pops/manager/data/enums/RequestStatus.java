package com.shopmart.pops.manager.data.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public enum RequestStatus {
    Invalid,
    Created,
    Pending,
    Approved,
    Rejected;
    public static RequestStatus fromValue(int val){
        Optional<RequestStatus> os = Arrays.stream(RequestStatus.values())
                .filter(s->s.ordinal() == val).findFirst();
        return os.isPresent()?os.get():Invalid;
    }
}
