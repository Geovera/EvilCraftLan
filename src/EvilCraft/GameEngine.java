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
import java.util.HashMap;

/**
 *
 * @author csc190
 */
public class GameEngine implements IGameEngine {

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
    protected ArrayList<Sprite> arrSelected = null; //set by Drag operations and released by left click
    protected MouseSprite mouseSprite;
    protected AI ai;
    protected boolean[][] taken;
    protected ButtonController aiButtonController= null;
//---------------- OPERATIONS ------------------
    /**
     * Constructor.
     * An evil craft game engine has 3 canvases: main view, mini map and a panel for manufacturing units
=======
    protected ArrayList<Sprite> arrSelected = null; //set by Drag operations and released by left click
    protected MouseSprite mouseSprite;
//---------------- OPERATIONS ------------------

    /**
     * Constructor. An evil craft game engine has 3 canvases: main view, mini
     * map and a panel for manufacturing units
     *
>>>>>>> origin2/NEW_MODULE_D
     * @param mapPath
     * @param mainview
     * @param minimap
     * @param factoryPanel
     * @param sound
     */
    public GameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {

        this.mapPath = mapPath;
        this.mainview = mainview;
        this.minimap = minimap;
        this.buttonCanvas = factoryPanel;
        this.soundDevice = soundDevice;
        //this.loadGameMap(mapPath);
        this.mouseSprite = new MouseSprite(this.mainview, this.minimap);
        
        
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
    
    public static GameEngine getInstance() {
        return ge_instance;
    }

    @Override
    public void init() {
        //DON'T KILL THE following line
        //set up the ButtonController
        Team t1 = new Team(10000, "Human");
        Team t2 = new Team(10000, "AI");
        this.arrTeams.add(t1);
        this.arrTeams.add(t2);
        loadGameMap(this.mapPath);
        humanController = new ButtonController(this.arrTeams.get(0), this.buttonCanvas);
        this.mainview.setupEventHandler(this);
        //set up the ButtonController        
        this.mainview.setupEventHandler(this);
        this.minimap.setupEventHandler(this);
        for(Sprite sp: this.arrMapTiles){
            sp.drawOnMiniMap(minimap);
        }
        this.createBackground();
        ge_instance  = this;
        
        
    }

    @Override
    public void onTick() {

        this.mainview.clear();
        this.minimap.clear();
        for(int i=0; i<this.arrMapTiles.size(); i++){
            this.arrMapTiles.get(i).drawOnMainView(mainview);
        }
        this.drawBackgroundOfMiniMap();
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
        this.mouseSprite.update();
        this.mouseSprite.drawOnMainView(mainview);

        for (int i = 0; i < this.arrSprites.size(); i++) {
            Sprite sp = this.arrSprites.get(i);
            if (sp instanceof ArmyUnit) {
                ((ArmyUnit) sp).setFireAction();
            }
            sp.update();
            sp.drawOnMainView(mainview);
        }
        //2. collect the dead
        ArrayList<Sprite> arrDead= new ArrayList<Sprite>();
        for(Sprite sp: this.arrSprites){
            if(sp.isDead()){
                arrDead.add(sp);
            }
        }
        for(Sprite sp: arrDead){
            this.arrSprites.remove(sp);
        }

    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {


        Point pt = this.getGlobalCoordinates(canvas, x, y, map);
        Point pt1 = new Point(pt.x-25, pt.y-25);
        Point pt2 = new Point(pt.x+25, pt.y+25);
        ArrayList<Sprite> targets = this.getArrSprites(pt1, pt2, this.getAITeam());
        Sprite target = targets==null || targets.size()==0? null: targets.get(0);
        if(this.arrSelected!=null && this.arrSelected.size()>0){
            for(Sprite sprite: this.arrSelected){
                sprite.setNavigationGoal(pt);
                sprite.setAttackGoal(target.getSpriteInfo());
            }
        }
        this.mouseSprite.handleEvnet(MouseEvent.RightClick, canvas, x, y, this.arrSelected);        
        ge_instance  = this;
        this.mouseSprite = new MouseSprite(mainview, minimap);
        this.mainview.setupEventHandler(this);
        this.loadGameMap(this.mapPath);
        this.aiButtonController = new ButtonController(this.getAITeam(), null); //no display device
        this.ai = new AI(this.getAITeam(), this.aiButtonController);
        //DON'T KILL THE ABOVE LINE
    }

    protected int ticks = 0;


    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        this.arrSelected = null;
        if(canvas==this.minimap){
            Point pt = this.getGlobalCoordinates(canvas, x, y, map);
            this.mainview.setViewPort(pt.x-mainview.getWidth()/2, pt.y-mainview.getHeight()/2);
        }
        this.mouseSprite.handleEvnet(MouseEvent.LeftClick, canvas, x, y, null);
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        Point pt1 = this.getGlobalCoordinates(canvas, x1, y1, map);
        Point pt2 = this.getGlobalCoordinates(canvas, x2, y2, map);
        this.arrSelected = this.getArrSprites(pt1, pt2, this.getPlayerTeam());

    }
    
    /**
     * Load map tils and load them into the arrMapTiles of the GameEngine.
     * Also create the Map object
     * @param mapPath 
     */
    public void loadGameMap(String mapPath){
        this.map = new Map(mapPath, mainview);
        for(int i=0; i<map.getNumCols(); i++){
            for(int j=0; j<map.getNumCols(); j++){
                String tile = map.getMapTile(i, j);
                StaticObject so = null;
                if(tile.equals("b1")){
                    so = new Base(this.getPlayerTeam(), j*100, i*100, 100, 100, "b1");
                    this.getPlayerTeam().setBase((Base)so);
                }else if(tile.equals("b2")){
                    so = new Base(this.getAITeam(), j*100, i*100, 100, 100, "b2");
                    this.getAITeam().setBase((Base)so);
                }
                if(so!=null)
                    this.arrMapTiles.add(so);
            }
        }
    }
    
    /**
     * Return the left top corner of a free space close to (x,y)
     * The requested free space's dimension is (w,h)
=======

    }

    /**
     * Return the left top corner of a free space close to (x,y) The requested
     * free space's dimension is (w,h)
     *
>>>>>>> origin2/NEW_MODULE_D
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
        return null;
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
    /**
     * Translates the (x1,y1) in canvas into the coordinates in Map
     * @param canvas
     * @param x1
     * @param y1
     * @return 
     */
    public Point getGlobalCoordinates(ICanvasDevice canvas, int x1, int y1, Map map){
        int cw = canvas.getWidth();
        int ch = canvas.getHeight();
        int mw = map.getNumCols()*100;
        int mh = map.getNumRows()*100;
        int x2 = canvas==this.mainview? canvas.getX()+x1: canvas.getX() + x1 * mw/cw;
        int y2 = canvas==this.mainview? canvas.getY() + y1: canvas.getY() + y1*mh/ch;
        return new Point(x2,y2);
    }
    
    /**
     * Return the new left-top corner of mainview so that center point is now
     * located at the center of the mainview
     * @param center
     * @param mainview
     * @return 
     */
    public Point getNewLeftTopCoordinates(Point center, ICanvasDevice mainview){
        Point nl = new Point(center.x- mainview.getWidth()/2, center.y - mainview.getHeight()/2);
        return nl;
    }
    /**
     * 
     * @return human player team (by default it's arrTeams[0])
     */
    public Team getPlayerTeam(){
        return this.arrTeams.get(0);
    
    }
    
    /**
     * 
     * @return computer team (by default it's arrTeams[1])
     */
    public Team getAITeam(){
         return this.arrTeams.get(1);
    }
    
    protected boolean isCollide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){
       int xmax = Integer.max(x1, x2);
       int xmin = Integer.min(x1+w1-1, x2+w2-1);
       int ymax = Integer.max(y1, y2);
       int ymin = Integer.min(y1 + h1-1, y2 + h2-1);
       return xmin>=xmax && ymin>=ymax;
    }
    /**
     * Get the units (including base but not map tiles) in between pt1 and pt2 that
     * belongs to the given team. If team is null, then both team's units will be 
     * included. Suggestion: use some advanced data storage to guarantee quick response!
     * @param pt1
     * @param pt2
     * @param team
     * @return 
     */
    public ArrayList<Sprite> getArrSprites(Point pt1, Point pt2, Team team){
         //slow version
         
         ArrayList<Sprite> ret = new ArrayList<Sprite>();
         for(int i=0; i<this.arrSprites.size(); i++){
             Sprite s = arrSprites.get(i);
             if(isCollide(pt1.x, pt1.y, pt2.x-pt1.x, pt2.y-pt1.y, s.getX(), s.getY(), s.getW(), s.getH())){
                 ret.add(s);
             }
         }
         return ret;
    }

    @Override
    public void onMouseMoved(ICanvasDevice canvas, int x, int y) {
        Point pt = this.getGlobalCoordinates(canvas, x, y, map);
        ArrayList<Sprite> arrSprites = this.getArrSprites(new Point(pt.x-25, pt.y-25), new Point(pt.x+25, pt.y+25), this.getAITeam());
        this.mouseSprite.handleEvnet(MouseEvent.MouseMove, canvas, x, y, this.arrSprites);
        
    }
    
    /**
     * Create a background WritableImage for the MiniMap.
     * Implementation idea: draw all maptiles as colored squares on the mini maps
     * canvas and take a snapshot and save it as a WritableImage. Later you can simply
     * draw that image in the minimap's canvas.
     * Note: call canvas.takeSnapshot function.
     */
    public void createBackground(){
        this.minimap.takeSnapshot("miniview_snapshot");

    }
    
    /**
     * Take the previously saved snapshot of Minimap background and draw it.
     */
    public void drawBackgroundOfMiniMap(){
        this.minimap.clear();
        this.minimap.drawImg("miniview_snapshot", 0, 0, minimap.getWidth(), minimap.getHeight(), 0);
    }
    
    /**
     * Return the team info of the opponent team
     * @param myteam
     * @return 
     */
    public TeamInfo getEnemyTeamInfo(Team myteam){
        if(myteam==this.arrTeams.get(0)){
            return this.arrTeams.get(1).getTeamInfo();
        }else{
            return this.arrTeams.get(0).getTeamInfo();
        }
    }
    /**
     * Approve if to allow propser to move to rectangle. Lefttop and width and
     * height are provided Algorithm: call getArrSprites to get all colliding
     * with rectangle, and then get the altitude and blocking score to decide.
     *
>>>>>>> origin2/NEW_MODULE_D
     * @param proposer
     * @param lefttop_corner
     * @param w
     * @param y
     * @return 
     */
    public boolean approveNextMove(Sprite proposer, Point lefttop_corner, int width, int height){
        ArrayList<Sprite> arr = this.getArrSprites(lefttop_corner, new Point(lefttop_corner.x+width, lefttop_corner.y+height), null);
        for(Sprite sp: arr){
            if(sp!=proposer){
                if(sp.getAltitude()==proposer.getAltitude()){
                    if(sp.getBlockingScore()>=proposer.getBlockingScore()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    HashMap<Point,int[][]> mapstore = new HashMap();
    /**
     * The int[][] cost matrix is generated in onRightClick() and saved into a hashmap.
     * Now simply retrieve it from hashmap
     * @param dest
     * @return 
     */
    public int [][] getBFSMap(Point dest){
        if(!mapstore.containsKey(dest)){
            int [][] costMatrix = this.map.generateBFSMap(dest);
            mapstore.put(dest, costMatrix);
        }
        int [][] cost = this.mapstore.get(dest);
        return cost;
    }
    /**
     * Check sequence diagram (2) in D1 (1) check if projectile has requested
     * damage before (2) if not, use getArrSprites to get the list of victimes
     * (3) call reducelifepoints for victimes
     *
     * @param projectile
     */
    public void requestDamage(Projectile projectile) {
        if (projectile.hasRequestedDamage()) {
            return;
        }
        projectile.setHasRequestedDamage();
        int range = getDamageRange(projectile);
        int points = getDamagePoints(projectile);
        Point pt1 = new Point(projectile.getX() - range, projectile.getY() - range);
        Point pt2 = new Point(projectile.getX() + range, projectile.getY() + range);
        Team enemy = projectile.team == this.getAITeam() ? this.getPlayerTeam() : this.getAITeam();
        ArrayList<Sprite> arr = this.getArrSprites(pt1, pt2, enemy);
        for (int i = 0; i < arr.size(); i++) {
            Sprite sp = arr.get(i);
            if(projectile instanceof Bomb){
                if(sp instanceof Airplane){
                    continue; //Bomb cannot hurt Airplane
                }
            }
            if(projectile instanceof Shell){
                if(sp instanceof Airplane){
                    continue; //Shell cannot hurt Airplane, but bullet can
                }
            }
            sp.reduceLifepoints(points);
        }

    }

    protected int getDamageRange(Projectile p) {
        if (p instanceof Bullet) {
            return 20;
        } else if (p instanceof Shell) {
            return 50;
        } else if (p instanceof Bomb) {
            return 50;
        } else {
            return 0;
        }
    }

    protected int getDamagePoints(Projectile p) {
        if (p instanceof Bullet) {
            return 20;
        } else if (p instanceof Shell) {
            return 50;
        } else if (p instanceof Bomb) {
            return 100;
        } else {
            return 0;
        }
    }
}
