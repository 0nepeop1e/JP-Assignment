package com.shopmart.pops.manager.data.entries;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.annotations.Serialize;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public class Item extends AbstractEntry {

    @Getter @Setter @Serialize
    private String barcode = "";

    @Getter @Setter @Serialize
    private String name = "";

    @Getter @Setter @Serialize
    private String unit = "Box";

    @Getter @Setter @Serialize
    private String remarks = "";

    @Setter @Serialize
    private int supplier = 0;

    public Supplier getSupplier(){
        if(this.dataManager == null)
            return POPS.getDataManager().getSupplierManager().getById(supplier);
        return this.dataManager
                .getSupplierManager()
                .getById(supplier);
    }
}
