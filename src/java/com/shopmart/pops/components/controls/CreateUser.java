package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.User;
import com.shopmart.pops.manager.data.objects.builder.UserBuilder;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class CreateUser extends UserForm {
    private UserBuilder builder;

    public CreateUser(UserBuilder builder) {
        super(new User());
        this.builder = builder;
        usernameField.setText("");
        passwordButton.setText("Set Password");
        staffIdField.setText("");
        staffNameField.setText("");
        accessLevelField.setValue(AccessLevel.Staff);
    }
    @Override
    protected void setPassword(){
        super.setPassword();
        if(this.password.isPresent())
            this.passwordButton.setText("Change Password");
    }
    @Override
    public boolean apply(){
        if(!this.validate()) return false;
        if(!password.isPresent()) return false;
        builder.setUsername(this.getUsername());
        builder.setPassword(this.password.get());
        builder.setStaffId(this.getStaffId());
        builder.setStaffName(this.getStaffName());
        builder.setAccessLevel(this.getAccessLevel());
        builder.addToManager(POPS.getDataManager().getUserManager());
        POPS.getDataManager().saveTo(POPS.dataPath);
        return true;
    }
}
