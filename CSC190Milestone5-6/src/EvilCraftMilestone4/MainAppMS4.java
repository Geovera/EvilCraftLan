/*
 * Copyright (C) 2019 csc190
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package EvilCraftMilestone4;


import EvilCraftMilestone3.*;
import BridgePattern.IGameEngine;
import EvilCraft.GameEngine;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main JavaFX App class and it provides the UI.
 *
 * @author csc190
 */
public class MainAppMS4 extends Application {

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
     * Set up the UI layout: a main canvasMainView of 1000x1000 and a button deck of
 200x1000
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        //1. Create the Canvas 1200x1000
        Canvas canvasMainView = new Canvas(1000, 1000);
        
        //2. Create Vbox (it has a mini map 200x200 (canvasMainView) and factory panel 200x800 (canvasMainView)
        VBox vboxRight = new VBox();
        vboxRight.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Canvas canvasMiniMap = new Canvas(200,200);
        FXCanvasDevice fxMiniMap = new FXCanvasDevice(canvasMainView);
        Canvas canvasFactory = new Canvas(200, 800);
        FXCanvasDevice fxFactoryPanel = new FXCanvasDevice(canvasFactory);
        canvasMiniMap.getGraphicsContext2D().strokeText("MiniMap", 20, 100);
        canvasFactory.getGraphicsContext2D().strokeText("FACTORY PANEL", 20, 100);
        vboxRight.getChildren().add(canvasMiniMap);
        vboxRight.getChildren().add(canvasFactory);
                
        //3. Create the VBox (right banner) 200x100 and Add Buttons
        VBox vboxTestButtons = new VBox();
        vboxTestButtons.setPrefSize(200, 1000);
        vboxTestButtons.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        FXCanvasDevice fxMainView = new FXCanvasDevice(canvasMainView);
        FXSoundDevice fxSound = new FXSoundDevice();
        
        //4. Create Test Scenario Buttons
        GameEngine g1 = new GameEngine("resources/map/small.txt", fxMainView, fxMiniMap, fxFactoryPanel, fxSound);
        
        GameEngine g2 = new GameEngine("resources/map/small.txt", fxMainView, fxMiniMap, fxFactoryPanel, fxSound);
        TestCase3GameEngine g3 = new TestCase3GameEngine("resources/map/small.txt", fxMainView, fxMiniMap, fxFactoryPanel, fxSound);
         createButton("Test1", g1, vboxTestButtons);
        createButton("Test2", g2, vboxTestButtons);
        createButton("Test3", g3, vboxTestButtons);
        
       
        

        //3. First layer HBox (horitontal box) of Canvas and VBox
        HBox hbox = new HBox();
        hbox.getChildren().add(canvasMainView);
        hbox.getChildren().add(vboxRight);
        hbox.getChildren().add(vboxTestButtons);
        Scene scene = new Scene(hbox, 1400, 1000);

        primaryStage.setTitle("EvilCraft Milestone 4");
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
