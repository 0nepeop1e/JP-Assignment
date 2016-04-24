package com.shopmart.pops.database.entities;

import com.shopmart.pops.database.annotations.AssignWithField;
import lombok.Getter;

/**
 * Created by 0nepeop1e on 4/23/16.
 */
public class Column extends Entry {
    @Getter @AssignWithField
    private String name;
    @Getter @AssignWithField
    private String type;
    @Getter @AssignWithField("dflt_value")
    private Object defVal;
    @Getter @AssignWithField("notnull")
    private boolean notNull;
    @Getter @AssignWithField("pk")
    private boolean primaryKey;

}
