package com.shopmart.pops.manager.data;

import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.objects.Supplier;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public class SupplierManager extends AbstractManager<Supplier> {

    @Override
    public Class<Supplier> getManageType() {
        return Supplier.class;
    }
}
