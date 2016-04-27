package com.shopmart.pops;

import com.shopmart.pops.manager.data.DataManager;
import com.shopmart.pops.manager.scene.SceneManager;
import com.shopmart.pops.components.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class POPS extends Application {

    public final static String dataPath = "data.json";

    @Getter private static SceneManager sceneManager;
    @Getter private static DataManager dataManager;

    @Override
    public void start(Stage stage) throws Exception {
        if (Files.exists(Paths.get(dataPath)))
            dataManager = DataManager.loadFrom(dataPath);
        else
            dataManager = new DataManager();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Shopmart Sdn. Bhd.");
        sceneManager = new SceneManager(stage, new LoginScene());
    }
    public static void main(String args[]){
        launch(args);
    }
}
