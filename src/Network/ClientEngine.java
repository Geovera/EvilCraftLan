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

import BridgePattern.ICanvasDevice;
import BridgePattern.ISoundDevice;
import EvilCraft.ButtonController;
import EvilCraft.GameEngine;
import EvilCraft.Map;
import EvilCraft.MouseEvent;
import EvilCraft.Point;
import EvilCraft.Sprite;
import EvilCraft.StaticObject;
import EvilCraft.Team;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author csc190
 */
public class ClientEngine extends GameEngine{
    
    Connection connection;
    private boolean gameReady;
    private boolean spritesReady;
    
    public ClientEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound, Connection connection) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
        this.connection = connection;
        gameReady =false;
    }
    
    @Override
    public void init(){
            this.connection.connect();
            System.out.println("You are connected to the server!");
            this.connection.setClientEngine(this);
    }
    
    public void initGame(){
        gameReady=true;
        this.map = new Map(mapPath, mainview);
        this.humanController = new ClientButtonController(null,this.buttonCanvas, this.connection);
        this.mainview.setupEventHandler(this);
        this.minimap.setupEventHandler(this);
    }
  
    public void clientGameEnd(String msg){
        this.mainview.drawText(msg, 400, 400, 20);
        this.gameReady=false;

    }
    
    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2){
        Point p1 = this.getGlobalCoordinates(canvas, x1, y1, map);
        Point p2 = this.getGlobalCoordinates(canvas, x2, y2, map);
        //System.out.println("X1: " +x1 + " Y1: " +y1 + " X2: " +x2 + " Y2: "+y2);
        //System.out.println("NX1: " +p1.x + " NY1: " +p1.y + " NX2: " +p2.x + " NY2: "+p2.y);

        connection.dataRegion(p1.x, p1.y, p2.x, p2.y);
    }
    
    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        Point pt = this.getGlobalCoordinates(canvas, x, y, map);
        
        connection.dataRightClick(pt.x, pt.y);
    }
    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        //System.out.println("X1: " +x + " Y1: " +y);
        Point p = this.getGlobalCoordinates(mainview, x, y, map);
        //System.out.println("X2: " +p.x + " Y2: " +p.y);
    }
    
    @Override
    public void onMouseMoved(ICanvasDevice canvas, int x, int y) {
        Point pt = this.getGlobalCoordinates(canvas, x, y, map);
        //ArrayList<Sprite> arrSprites = this.getArrSprites(new Point(pt.x-25, pt.y-25), new Point(pt.x+25, pt.y+25), this.getAITeam(),true);
        this.mouseSprite.handleEvnet(MouseEvent.MouseMove, canvas, x, y, null);
    }

    
    @Override
    public void onTick(){
        if(!gameReady)return;
        //System.out.println(arrMapTiles.size());
        
        
        
        if(!spritesReady) return;
        mainview.clear();
        minimap.clear();
        
        arrMapTiles.stream().forEach(elem -> drawSprite(elem));
        
        arrSprites.stream().forEach(elem -> drawSprite(elem));
        
        this.humanController.onTick();        
        this.mouseSprite.update();
        this.mouseSprite.drawOnMainView(mainview);
        
        spritesReady=false;
    }
    
    public void purchaseAccepted(int cash, String type){
        ((ClientButtonController) humanController).setCash(cash);
        ((ClientButtonController) humanController).setCoolDown(type);
    }
    private void drawSprite(Sprite s){
        s.drawOnMainView(this.mainview);
        s.drawOnMiniMap(this.minimap);
    }
    
    public void changeArrStatic(ArrayList<StaticObject> arr){
        this.arrMapTiles.clear();
        this.arrMapTiles =arr;
        
    }
    public void changeArrSprite(ArrayList<Sprite> arr){
        if(!spritesReady){
            this.arrSprites.clear();
            this.arrSprites =arr;
            spritesReady=true;
        }
        
    }
    
}
