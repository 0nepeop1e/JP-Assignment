package com.shopmart.pops.manager.scene;

import javafx.scene.Scene;
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

    /**
     * Setup a scene manager for a stage.
     * @param stage the stage
     * @param firstScene scene to start
     */
    public SceneManager(@NonNull Stage stage,
                        @NonNull Scene firstScene){
        this.stage = stage;
        this.scenes = new Stack<>();
        this.changeScene(firstScene);
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
        this.stage.setScene(nextScene);
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