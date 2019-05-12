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
package EvilCraft;

import BridgePattern.ICanvasDevice;
import BridgePattern.IGameEngine;
import BridgePattern.ISoundDevice;
import java.util.ArrayList;

/**
 *
 * @author csc190
 */
public class GameEngine implements IGameEngine{
    // -------------- DATA MEBERS ------------------
    protected String mapPath;
    protected Map map;
    protected ICanvasDevice mainview;
    protected ICanvasDevice minimap;
    protected ISoundDevice soundDevice;
    protected ICanvasDevice buttonCanvas;
    protected static GameEngine ge_instance = null;
    protected ArrayList<Sprite> arrSprites = new ArrayList<Sprite>(); //moving objects
    protected ArrayList<StaticObject> arrMapTiles = new ArrayList<StaticObject>();
    protected ArrayList<Team> arrTeams = new ArrayList<Team>();
    protected ButtonController humanController;
    
    protected boolean [][] taken;
    //---------------- OPERATIONS ------------------
    /**
     * Constructor.
     * An evil craft game engine has 3 canvases: main view, mini map and a panel for manufacturing units
     * @param mapPath
     * @param mainview
     * @param minimap
     * @param factoryPanel
     * @param sound 
     */
    public GameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound){
        this.mapPath = mapPath;
        this.mainview = mainview;
        this.minimap = minimap;
        this.buttonCanvas = factoryPanel;
        this.soundDevice = soundDevice;
        Team team1 = new Team(10000, "Human");
        Team team2 = new Team(10000, "AI");
        this.arrTeams.add(team1);
        this.arrTeams.add(team2);
        this.loadGameMap(mapPath);
        
        
        
        //TEMPORARY
        taken = new boolean [20][];
        for(int i=0; i<20; i++){
            taken[i] = new boolean [20];
            for(int j=0; j<20; j++){
                taken[i][j] = false;
            }
        }
        //TEMPORARY REMOVE ABOVE
        ge_instance = this;
    }
    
    public static GameEngine getInstance(){
        
        return ge_instance;
    }

    @Override
    public void init() {
        //DON'T KILL THE following line
        //set up the ButtonController
        humanController = new ButtonController(this.arrTeams.get(0), this.buttonCanvas);
        this.mainview.setupEventHandler(this);
        ge_instance  = this;
        
        //DON'T KILL THE ABOVE LINE
    }

    @Override
    public void onTick() {
        for(int i=0; i<this.arrMapTiles.size(); i++){
            this.arrMapTiles.get(i).drawOnMainView(mainview);
        }
        for(int i=0; i<this.arrSprites.size(); i++){
            this.arrSprites.get(i).update();
            this.arrSprites.get(i).drawOnMainView(mainview);
            this.arrSprites.get(i).drawOnMiniMap(minimap);
        }
        Team winner = this.CheckWinner();
        if(winner!=null){
            this.endGame(winner);
        }
        
        this.humanController.onTick();
    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        
    }

    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        
    }
    
    /**
     * Load map tils and load them into the arrMapTiles of the GameEngine.
     * Also create the Map object
     * @param mapPath 
     */
    public void loadGameMap(String mapPath){
        this.mapPath = mapPath;
        this.map = new Map(mapPath);
        for(int i=0; i<map.getNumRows(); i++){
            for(int j=0; j<map.getNumCols(); j++){
                String maptile = this.map.getMapTile(i, j);
               
                if(maptile.equals("b1")){
                    Base b1 = new Base(this.arrTeams.get(0), j*100, i*100, 100, 100, maptile);
                    this.arrTeams.get(0).setBase(b1);
                    this.arrMapTiles.add(b1);
                }else if(maptile.equals("b2")){
                    Base b2 = new Base(this.arrTeams.get(1), j*100, i*100, 100, 100, maptile);
                    this.arrTeams.get(1).setBase(b2);
                    this.arrMapTiles.add(b2);
                }else{
                    StaticObject s1 = new StaticObject(null, j*100, i*100, 100, 100, maptile);
                     this.arrMapTiles.add(s1);
                }
                
            }
        }
    }
    
    /**
     * Return the left top corner of a free space close to (x,y)
     * The requested free space's dimension is (w,h)
     * @param x
     * @param y
     * @param w
     * @param h
     * @return 
     */
    
    public Point getFreeSpace(int x, int y, int w, int h){
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                if(!taken[i][j]){
                    int x1 = x+50*j;
                    int y1 = y + 50*i;
                    if(x1>=0 && x1<this.map.getNumCols()*100 && y1>=0 && y1<this.map.getNumRows()*100){
                        taken[i][j] = true;
                        return new Point(x1, y1);
                    }
                }
            }
        }
        throw new UnsupportedOperationException("not implemented yet!");
    }
    
    public void addSprite(Sprite s){
        this.arrSprites.add(s);
    }
    
    public void removeSprite(Sprite s){
        this.arrSprites.remove(s);
    }
    
    /**
     * return null if no winner
     * @return 
     */
    public Team CheckWinner(){
        for(int i=0; i<=1; i++){
            Team team = this.arrTeams.get(i);
            if(team.getBase().isDead()){
                return this.arrTeams.get(1-i);
            }
        }
        return null;
    }
    
    /**
     * Display the message correspondingly
     * @param winner 
     */
    public void endGame(Team winner){
        String msg = winner.getName().equals("Human")? "You Win!": "You Lose";
        this.mainview.drawText(msg, 400, 400, 20);
    }
    
}
