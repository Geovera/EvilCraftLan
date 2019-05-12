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
import EvilCraft.GameEngine;
import EvilCraft.Map;
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
    
    public ClientEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound, Connection connection) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
        this.connection = connection;
        gameReady =false;
    }
    
    @Override
    public void init(){
            this.connection.connect();
            this.connection.setClientEngine(this);
    }
    
    public void initGame(){
        gameReady=true;
        this.map = new Map(mapPath, mainview);
    }
    
    @Override
    public void onTick(){
        if(!gameReady)return;
        //System.out.println(arrMapTiles.size());
        
        mainview.clear();
        minimap.clear();
        
        
        arrMapTiles.stream().forEach(elem -> drawSprite(elem));
    }
    
    private void drawSprite(Sprite s){
        s.drawOnMainView(this.mainview);
        s.drawOnMiniMap(this.minimap);
    }
    
    public void changeArrStatic(ArrayList<StaticObject> arr){
        this.arrMapTiles.clear();
        this.arrMapTiles =arr;
        
    }
    
}
