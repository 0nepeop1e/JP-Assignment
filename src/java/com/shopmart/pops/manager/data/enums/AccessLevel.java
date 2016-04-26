package com.shopmart.pops.manager.data.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public enum AccessLevel {
    SuperUser,
    Administrator,
    Manager,
    Staff,
    Terminated,
    NoAccess;

    public static AccessLevel fromValue(int value){
        Optional<AccessLevel> oa = Arrays.stream(AccessLevel.values())
                .filter(a->a.ordinal() == value).findFirst();
        return oa.isPresent() ? oa.get() : NoAccess;
    }

    public boolean enoughFor(AccessLevel other){
        return this.ordinal() <= other.ordinal();
    }

    @Override
    public String toString() {
        if(this == SuperUser) return "Super User";
        if(this == NoAccess) return "No Access";
        return super.toString();
    }
}
