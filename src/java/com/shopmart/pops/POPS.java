package com.shopmart.pops;

import com.shopmart.pops.manager.scene.SceneManager;
import com.shopmart.pops.components.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

public class POPS extends Application {

    @Getter private static SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle("Shopmart Sdn. Bhd.");
        sceneManager = new SceneManager(stage, new LoginScene());
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
