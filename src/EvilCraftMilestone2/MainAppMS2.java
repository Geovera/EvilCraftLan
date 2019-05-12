/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvilCraftMilestone1;

import BridgePattern.IGameEngine;
import EvilCraftMilestone2.GameTickEfficiency;
import FXDevices.FXCanvasDevice;
import FXDevices.FXSoundDevice;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX App class and it provides the UI.
 *
 * @author csc190
 */
public class MainAppMS2 extends Application {

    // ----------- DATA MEMBERS -------------------
    /**
     * global animation timer for firing timer tick pulses.
     */
    protected AnimationTimer animTimer = null;
    //-------------ABOVE ARE DATA MEMBERS ---------

    //-------------METHODS ---------------------------
    /**
     * Get the current animation timer
     *
     * @return the current value of protected data member animTimer
     */
    protected AnimationTimer getAnimTimer() {
        return this.animTimer;
    }

    /**
     * Set the animation timer of main app
     *
     * @param timer
     */
    protected void setAnimationTimer(AnimationTimer timer) {
        this.animTimer = timer;
    }

    protected void createButton(String btnTitle, IGameEngine gameEngine, VBox vbox) {
        //1. create button and append to VBox
        Button btn = new Button(btnTitle);
        vbox.getChildren().add(btn);

        //2. set up the click event
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //1. stop existing animation if it exists
                AnimationTimer timer = getAnimTimer();
                if (timer != null) {
                    timer.stop();
                }

                //2. start new timer pulses and the new game logic using gameEngine
                gameEngine.init();
                timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {//handle every tick
                        gameEngine.onTick();
                    }

                };
                setAnimationTimer(timer);
                timer.start();

            }

        });
    }

    /**
     * Set up the UI layout: a main canvas of 1000x1000 and a button deck of
     * 200x1000
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        //1. Create the Canvas 1000x1000
        Canvas canvas = new Canvas(1000, 1000);

        //2. Create the VBox (right banner) 200x100 and Add Buttons
        VBox vbox = new VBox();
        vbox.setPrefSize(200, 1000);
        FXCanvasDevice fxCanvas = new FXCanvasDevice(canvas);
        GameTickEfficiency g100 = new GameTickEfficiency(100, fxCanvas);
        GameTickEfficiency g200 = new GameTickEfficiency(200, fxCanvas);
        GameTickEfficiency g400 = new GameTickEfficiency(400, fxCanvas);
        GameTickEfficiency g800 = new GameTickEfficiency(800, fxCanvas);
        GameTickEfficiency g1600 = new GameTickEfficiency(1600, fxCanvas);
        GameTickEfficiency g3200 = new GameTickEfficiency(3200, fxCanvas);

        createButton("100 sprites", g100, vbox);
        createButton("200 sprites", g200, vbox);
        createButton("400 sprites", g400, vbox);
        createButton("800 sprites", g800, vbox);
        createButton("1600 sprites", g1600, vbox);
        createButton("3200 sprites", g3200, vbox);
        

        //3. First layer HBox (horitontal box) of Canvas and VBox
        HBox hbox = new HBox();
        hbox.getChildren().add(canvas);
        hbox.getChildren().add(vbox);
        Scene scene = new Scene(hbox, 1200, 1000);

        primaryStage.setTitle("EvilCraft Milestone 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
