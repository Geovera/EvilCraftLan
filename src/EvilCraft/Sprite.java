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
import Network.ClientEngine;
import Network.ServerEngine;
import java.util.Random;

/**
 * Base class of all game objects
<<<<<<< HEAD
 *
 * @author csc190
 */
public abstract class Sprite {

    //------- DATA MEMBERS ----------
    protected int x, y, w, h;
    private transient int altitude, blocking_score;
    protected Team team;
    protected transient boolean bDead = false;
    protected transient SpriteInfo attackGoal = null;
    protected transient Point navigationGoal = null;
    private transient int lifepoints;
    private String id;
    protected int degree;
    protected String pic = null;
    
    //default explosion
    protected static String [] arrExplosion= new String [] {
        "resources/images/common/explosion/exp1.png",
         "resources/images/common/explosion/exp2.png",
          "resources/images/common/explosion/exp3.png",
           "resources/images/common/explosion/exp4.png",
            "resources/images/common/explosion/exp5.png",
    };

    //------- OPERATIONS -------------
    public String getID(){
        return id;
    }
    public int getLifepoints() {
        return this.lifepoints;
    }

    public void reduceLifepoints(int offset){
        lifepoints-=offset;
        if(lifepoints<0) lifepoints=0;
        if(lifepoints==0){
            this.startExplode();
        }
    }

    /**
     * Set the long term navigation goal to pt
     *
     * @param pt
     */
    public void setNavigationGoal(Point pt) {
        this.navigationGoal = pt;
    }

    /**
     * Set the PRIORITY attack goal. If sp is in the range, it should be
     * attacked first.
     *
     * @param sp
     */
    public void setAttackGoal(SpriteInfo sp) {
        this.attackGoal = sp;
    }

    public void setDead() {
        this.bDead = true;
    }

    final protected void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected float getAngle(Point me, Point target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - me.y, target.x - me.x));
       angle += 90.0; //cause we use y-axis clockwise
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Get next move (for next tick), seek approval from game engine, and turn
     * body if necessary. NOTE this implementation only addresses C3.2 BUT NOT
     * C3.3 You got to check C3.3.2 Sequence diagram, which shows how to decide
     * next move based on navigation map.
     */
    final protected void setNextMove() {
        Point pt = this.getNextMove(); //virtual function
        GameEngine ge = GameEngine.getInstance();
        if (ge.approveNextMove(this, pt, this.w, this.h)) {
            if (!this.isFacing(pt)) {
                this.adjustBodyHeading(pt);
            } else {
                this.setPos(pt.x, pt.y);
            }
        }
    }
    
    @Override
    public String toString(){
        return team.getName() + "X: " + x;
    }

    public Sprite(Team team, int x, int y, int w, int h, int lifepoints, int altitude, int block_score){
        this.team = team;
        this.x = x;
        this.y = y;
        this.w= w;
        this.h = h;
        this.lifepoints = lifepoints;
        this.altitude = altitude;
        this.blocking_score = blocking_score;
        Random rand = new Random();
        this.id = String.valueOf(rand.nextInt())+"_" + String.valueOf(rand.nextInt());
    }

    //------- OPERATIONS -------------
    
    
    public boolean isDead(){
        return this.bDead;
    }

    public Team getTeam() {
        return this.team;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public int getBlockingScore() {
        return this.blocking_score;
    }
    
    
    
    public abstract void update();


    /**
     * Draw itself on main view, mostly like pictures
     *
     * @param mainview - canvas device
     */
    public abstract void drawOnMainView(ICanvasDevice mainview);

    /**
     * Draw itself on mini map, most likely colored squares
     *
     * @param minimap - canvas device
     */
    public abstract void drawOnMiniMap(ICanvasDevice minimap);

    /**
     * To be implemented by all sprites.
     *
     * @return
     */
    public abstract Point getNextMove();

    /**
     * if the body of the sprite is heading to pt
     *
     * @param pt
     * @return
     */
    public abstract boolean isFacing(Point pt);

    /**
     * Adjust the body heading so that it is pointing to pt. Note that for Tank,
     * multiple calls of adjustBodyHeading may be needed.
     *
     * @param pt
     * @return
     */
    public abstract void adjustBodyHeading(Point pt);

    protected boolean isClose(Point pt) {
        if (pt == null) {
            return true;
        }
        int xd = pt.x - this.getX();
        int yd = pt.y - this.getY();
        return xd * xd + yd * yd <= 20;
    }

    protected Point defaultGetNextMove(int speed) {
        Point dft = new Point(this.getX(), this.getY());
        if (this.navigationGoal == null || isClose(this.navigationGoal)) {
            return dft;
        }
        //1. get the nav map
        int[][] costMap = GameEngine.getInstance().getBFSMap(navigationGoal);

        //2. get the immediate destination
        Map map = GameEngine.getInstance().map;
        int row = this.getY() / 100;
        int col = this.getX() / 100;
        int bestx = -1;
        int besty = -1;
        int bestCost = costMap[row][col];
        for (int xdiff = -1; xdiff <= 1; xdiff++) {
            for (int ydiff = -1; ydiff <= 1; ydiff++) {
                int nx = row + xdiff;
                int ny = col + ydiff;
                if (nx >= 0 && nx < costMap.length && ny >= 0 && ny < costMap[0].length) {
                    int newcost = costMap[nx][ny];
                    if (newcost < costMap[row][col]) {
                        bestCost = newcost;
                        bestx = nx;
                        besty = ny;
                    }
                }
            }
        }
        if (bestx != -1) {
            dft = new Point(besty * 100, bestx * 100);
        } else {
            return dft;
        }

        //3. calculate the next point
        int diffx = dft.x + 50 - x; //avoid corner cut
        int diffy = dft.y + 50 - y;
        double total = Math.sqrt(diffx * diffx + diffy * diffy);
        double time = total / speed;
        double offx = (diffx * 1.0 / time);
        double offy = (diffy * 1.0 / time);
        Point pt = new Point(this.getX() + (int) offx, this.getY() + (int) offy);
        return pt;

    }
    
    protected boolean defaultIsFacing(int degree, Point pt){
        float targetDegree = this.getAngle(new Point(this.getX(), this.getY()), pt);
        int iTargetDegree = (int) targetDegree;
        int diff = degree>iTargetDegree?degree-iTargetDegree: iTargetDegree-degree;
        diff = diff>180? diff-180: diff;
        return diff<5;
    }
    
    public SpriteInfo getSpriteInfo(){
        SpriteInfo.TYPE tp;
        if(this instanceof Tank){
            tp = SpriteInfo.TYPE.TANK;
        }else if(this instanceof Base){
            tp = SpriteInfo.TYPE.BASE;  
        }else if(this instanceof Infantry){
            tp = SpriteInfo.TYPE.INFANTRY;  
        }else if(this instanceof Airplane){
            tp = SpriteInfo.TYPE.PLANE;  
        }else if(this instanceof Bullet){
            tp = SpriteInfo.TYPE.BULLET;  
        }else if(this instanceof Shell){
            tp = SpriteInfo.TYPE.SHELL;  
        }else if(this instanceof Rocket){
            tp = SpriteInfo.TYPE.ROCKET;  
        }else{
            tp = SpriteInfo.TYPE.NONE;
        }
        SpriteInfo si = new SpriteInfo(tp, this.getX(), this.getY(), this.getLifepoints(), this.id);
        return si;
    }
    
    protected int idx_explode=-1;
    protected void startExplode(){
        if(this.idx_explode<0){
             this.idx_explode = 0;
        }
    }
    protected int ticks = 0;
    protected void explode_ifenabled(){
        if(idx_explode==-1) return;
        ticks++;
        
        if(idx_explode==arrExplosion.length-1){
            this.setDead();
            GameEngine ge = GameEngine.getInstance();
            if(ge instanceof ServerEngine)((ServerEngine)ge).removeSprite(this);
            //if(this instanceof Tank);
            return;
        }
        
        //updating the picture to show
        this.pic = arrExplosion[idx_explode];
        if(ticks%5==0){
            idx_explode = idx_explode<arrExplosion.length-1? idx_explode+1: idx_explode;
        }
    }
}