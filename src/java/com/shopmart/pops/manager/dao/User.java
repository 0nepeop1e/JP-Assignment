package com.shopmart.pops.manager.dao;

import com.shopmart.pops.database.annotations.TableEntry;
import com.shopmart.pops.database.annotations.AssignWithField;
import com.shopmart.pops.database.entities.Entry;
import lombok.Getter;
import lombok.Setter;

/**
 * A class which represent entries in users table.
 */
@TableEntry("users")
public class User extends Entry {
    @Getter @AssignWithField
    private int id;
    @Getter @Setter @AssignWithField
    private String username;
    @Getter @AssignWithField("authkey")
    private String authKey;
    @Getter @Setter @AssignWithField
    private String staffId;
    @Getter @Setter @AssignWithField
    private int groupId;
}
