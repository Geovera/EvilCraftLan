/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvilCraftMilestone1;

import BridgePattern.IGameEngine;
import FXDevices.FXCanvasDevice;
import FXDevices.FXSoundDevice;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
 * @author csc190
 */
public class MainApp extends Application {
    // ----------- DATA MEMBERS -------------------
    /**
     * global animation timer for firing timer tick pulses. 
     */
    protected AnimationTimer animTimer = null; 
    //-------------ABOVE ARE DATA MEMBERS ---------
    
    //-------------METHODS ---------------------------
    
    /**
     * Get the current animation timer
     * @return the current value of protected data member animTimer
     */
    protected AnimationTimer getAnimTimer(){
        return this.animTimer;
    }
    
    /**
    * Set the animation timer of main app
    * @param timer 
    */
    protected void setAnimationTimer(AnimationTimer timer){
        this.animTimer = timer;
    }
    
    protected void createButton(String btnTitle, IGameEngine gameEngine, VBox vbox){
       //1. create button and append to VBox
       Button btn = new Button(btnTitle);
       vbox.getChildren().add(btn);
       
       //2. set up the click event
       btn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event) {
               //1. stop existing animation if it exists
               AnimationTimer timer = getAnimTimer();
               if(timer!=null) timer.stop();
               
               //2. start new timer pulses and the new game logic using gameEngine
               gameEngine.init();
               timer = new AnimationTimer(){
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
     * Set up the UI layout: a main canvas of 1000x1000 and a button deck of 200x1000
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
        FXSoundDevice fxSound = new FXSoundDevice();
        GameTestRotate gRotate = new GameTestRotate(fxCanvas);
        GameFPS gFPS1 = new GameFPS("resources/images/pic1.png", 1, 50, 1000, 1000, fxCanvas);
        GameFPS gFPS100 = new GameFPS("resources/images/pic1.png", 100, 50, 1000, 1000, fxCanvas);
        GameFPS gFPS200 = new GameFPS("resources/images/pic1.png", 200, 50, 1000, 1000, fxCanvas);
        GameFPS gFPS1000 = new GameFPS("resources/images/pic1.png", 1000, 50, 1000, 1000, fxCanvas);
        GameFPS gFPS2000 = new GameFPS("resources/images/pic1.png", 2000, 50, 1000, 1000, fxCanvas);
        GameFPS gFPS2000Sparse = new GameFPS("resources/images/pic1.png", 2000, 50, 10000, 10000, fxCanvas);
        GameFPS gLargePic = new GameFPS("resources/images/large.png", 20, 500, 500 , 500, fxCanvas);
        GameTestEvents gMouseEvents = new GameTestEvents(fxCanvas);
        GameTestSound gTestSound = new GameTestSound(fxSound);
        
        createButton("Test Sound", gTestSound, vbox);
        createButton("Test Rotate", gRotate, vbox);
        createButton("Test Mouse", gMouseEvents, vbox);
        createButton("Test FPS 1 Pics", gFPS1, vbox);
        createButton("Test FPS 100 Pics", gFPS100, vbox);
        createButton("Test FPS 200 Pics", gFPS200, vbox);
        createButton("Test FPS 1000 Pics", gFPS1000, vbox);
        createButton("Test FPS 2000 Pics", gFPS2000, vbox);
        createButton("Test FPS 2000 Pics Sparse", gFPS2000Sparse, vbox);
         createButton("Test BigPic Equiv 2000", gLargePic, vbox);
        
        
        //3. First layer HBox (horitontal box) of Canvas and VBox
        HBox hbox = new HBox();
        hbox.getChildren().add(canvas);
        hbox.getChildren().add(vbox);
        Scene scene = new Scene(hbox, 1200, 1000);
        
        primaryStage.setTitle("EvilCraft Milestone 1");
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
