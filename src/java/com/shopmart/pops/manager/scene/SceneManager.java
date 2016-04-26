package com.shopmart.pops.manager.scene;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lombok.*;

import java.util.Stack;

/**
 * A Class for manage Scene.
 */
public class SceneManager {

    private Stack<Scene> scenes;
    @Getter private Scene currentScene;
    @Getter private Stage stage;

    private double hBorder;
    private double vBorder;

    /**
     * Setup a scene manager for a stage.
     * @param stage the stage
     * @param firstScene scene to start
     */
    public SceneManager(@NonNull Stage stage,
                        @NonNull Scene firstScene){
        this.scenes = new Stack<>();
        this.stage = stage;
        this.changeScene(firstScene);
        this.stage.show();
    }

    /**
     * Get the previous scene in stack.
     * @return the previous scene
     */
    public Scene getPreviousScene(){
        return this.scenes.lastElement();
    }

    /**
     * Swap the current scene with next scene
     * without stacking the current scene.
     * @param nextScene the next scene
     */
    public void changeScene(@NonNull Scene nextScene){
        this.currentScene = nextScene;
        this.stage.hide();
        this.stage.setScene(nextScene);
        this.stage.show();
    }

    /**
     * Stack the scene and change to next scene.
     * @param nextScene the next scene
     */
    public void nextScene(@NonNull Scene nextScene){
        this.scenes.push(this.currentScene);
        this.changeScene(nextScene);
    }

    /**
     * Go back to the previous scene in stack.
     */
    public void prevScene(){
        if(this.scenes.size() > 0)
            this.changeScene(this.scenes.pop());
        else this.stage.close();
    }
}