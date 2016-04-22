package com.shopmart.pops.database.entities;

import com.shopmart.pops.database.annotations.AssignWithField;
import com.shopmart.pops.database.annotations.TableEntry;
import lombok.Getter;

/**
 * A class which represent table in sqlite.
 */
@TableEntry("sqlite_master")
public class Table extends Entry {
    @Getter @AssignWithField
    private String name;
    @Getter @AssignWithField
    private String sql;
}
