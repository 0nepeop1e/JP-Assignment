package com.shopmart.pops.components.dialogs;

import com.shopmart.pops.components.controls.CredentialForm;
import com.shopmart.pops.manager.resource.ResourceManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * A dialog which prompt for credential.
 */
public class CredentialDialog extends Alert {

    private CredentialForm credential;

    public CredentialDialog(String username){
        super(AlertType.NONE);
        credential = new CredentialForm();
        credential.setUsername(username);
        this.getDialogPane().setContent(credential);
        this.getButtonTypes().clear();
        this.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ImageView icon = new ImageView(ResourceManager.getImage("img/login.png"));
        icon.setViewport(new Rectangle2D(0,0,64,64));
        this.setGraphic(icon);
        this.setTitle("Credential Required");
        this.setHeaderText("The system need your credential.");
    }

    public CredentialDialog(){
        this("");
    }

    /**
     * The username field is locked or not.
     * @return locked?
     */
    public boolean isUsernameLocked(){
        return credential.getUsernameField().isDisabled();
    }

    /**
     * Set the username field is locked or not
     * @param lock locked?
     */
    public void setUsernameLocked(boolean lock){
        credential.getUsernameField().setDisable(lock);
    }

    /**
     * Get the username entered in this credential dialog.
     * @return the username
     */
    public String getUsername(){
        return this.credential.getUsername();
    }

    /**
     * Set the username entered in this credential dialog.
     * @param username the username
     */
    public void setUsername(String username){
        this.credential.setUsername(username);
    }

    /**
     * Get the password entered in this credential dialog.
     * @return the password
     */
    public String getPassword(){
        return this.credential.getPassword();
    }

    /**
     * Set the password entered in this credential dialog.
     * @param password the password
     */
    public void setPassword(String password){
        this.credential.setPassword(password);
    }

}
