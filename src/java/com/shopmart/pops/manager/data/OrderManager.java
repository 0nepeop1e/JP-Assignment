package com.shopmart.pops.manager.data;

import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.objects.Order;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class OrderManager extends AbstractManager<Order> {
    @Override
    public Class<Order> getManageType() {
        return null;
    }
}
