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
package Network;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author csc190
 */
public class Main_Server extends Application{

    
    protected AnimationTimer animTimer = null;
    
    protected AnimationTimer getAnimTimer() {
        return this.animTimer;
    }
    
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
    @Override
    public void start(Stage primaryStage) throws Exception {
        //1. Create the Canvas 1200x1000
        Canvas canvasMainView = new Canvas(1000, 1000);
        
        //2. Create Vbox (it has a mini map 200x200 (canvasMainView) and factory panel 200x800 (canvasMainView)
        VBox vboxRight = new VBox();
        vboxRight.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Canvas canvasMiniMap = new Canvas(200,200);
        FXCanvasDevice fxMiniMap = new FXCanvasDevice(canvasMiniMap);
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
        ServerEngine g10 = new ServerEngine("resources/map/small.txt", fxMainView, fxMiniMap, fxFactoryPanel, fxSound);
        createButton("StartServer", g10, vboxTestButtons);
        
        HBox hbox = new HBox();

        hbox.getChildren().add(vboxTestButtons);
        Scene scene = new Scene(hbox, 200, 200);

        primaryStage.setTitle("EvilCraft Milestone LAN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
