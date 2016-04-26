package com.shopmart.pops.manager.data.objects;

import com.google.gson.JsonObject;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.annotations.Serialize;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public class Supplier extends AbstractEntry {
    @Getter @Setter @Serialize
    private String name = "";
    @Getter @Setter @Serialize
    private String email = "";
    @Getter @Setter @Serialize
    private String phone = "";
    @Getter @Setter @Serialize
    private String fax = "";
}
