package com.shopmart.pops.dao;

import com.shopmart.pops.dao.annotations.EntityFromTable;
import com.shopmart.pops.dao.annotations.AssignWithField;
import com.shopmart.pops.dao.db.Entry;
import lombok.Getter;
import lombok.Setter;

/**
 * A class which represent entries in users table.
 */
@EntityFromTable("users")
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
