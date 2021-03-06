package com.shopmart.pops.components.dialogs;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.CredentialForm;
import com.shopmart.pops.manager.resource.ResourceManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * A dialog which prompt for credential.
 */
public class CredentialDialog extends Alert {

    private CredentialForm credential;
    @Getter
    private boolean validated;
    @Getter
    private Optional<Alert> error = Optional.empty();

    /**
     * A credential dialog with a pre-set username.
     * @param username the username
     */
    public CredentialDialog(String username){
        super(AlertType.NONE);
        validated = false;
        credential = new CredentialForm();
        credential.setUsername(username);
        this.getDialogPane().setContent(credential);
        this.getButtonTypes().clear();
        this.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button ok = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Submit");
        ok.setOnAction(e->onOk());
        ImageView icon = new ImageView(ResourceManager.getImage("img/login.png"));
        icon.setViewport(new Rectangle2D(0,0,64,64));
        this.setGraphic(icon);
        this.setTitle("Credential Required");
        this.setHeaderText("The system need your credential.");
    }

    private void onOk() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Login");
        String username = credential.getUsername();
        String password = credential.getPassword();
        if(Pattern.matches("^\\s*$", username)){
            a.setHeaderText("Invalid Username!");
            a.setContentText("Please enter a valid username.");
            this.error = Optional.of(a);
            return;
        }
        if(Pattern.matches("^\\s*$", password)){
            a.setHeaderText("Invalid Password!");
            a.setContentText("Please enter a valid password.");
            this.error = Optional.of(a);
            return;
        }
        if(POPS.getDataManager().getUserManager().validate(username, password)) {
            validated = true;
            return;
        }
        a.setHeaderText("Failed to Login!");
        a.setContentText("Incorrect username or password.");
        a.showAndWait();
        credential.setPassword("");
    }

    /**
     * Just a credential dialog.
     */
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
