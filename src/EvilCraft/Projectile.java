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

/**
 *
 * @author csc190
 */
public abstract class Projectile extends Sprite{
    //-----------DATA OPERATIONS ------------
    protected int destx, desty; //destination coordinates, should explode_ifenabled there
    private boolean bHasRequestedDamage = false;
    
    //similar explosion, will be set later
   
    protected int offX = 0;
    protected int offY = 0;
    protected int degree = 0;
    protected int travel_tick = 0;
    
    
    protected int id_exp = -1;
    //----------------------------------------
    
    protected void setTravel(int speed){
        int degree = MyMath.getDegree(this.getX(), this.getY(), destx, desty);
        this.offX = (int) (speed*1.0*Math.sin(Math.toRadians(degree)));
        this.offY = 0-(int) (speed*1.0*Math.cos(Math.toRadians(degree)));
        this.travel_tick = MyMath.dist(this.getX(), this.getY(), destx, desty);
        
        travel_tick/= speed;
        travel_tick++;
    }
    public Projectile(Team team, int x, int y, int w, int h, int lifepoints, int altitude, int block_score, int destx, int desty) {
        super(team, x, y, w, h, lifepoints, altitude, block_score);
        this.destx = destx;
        this.desty = desty;
    }
    
    public boolean hasRequestedDamage(){
        return this.bHasRequestedDamage;
    }
    
    public void setHasRequestedDamage(){
        this.bHasRequestedDamage = true;
    }
    
    public void update(){
        
      
        if(this.travel_tick>0){
            this.setPos(this.getX()+offX, this.getY()+offY);
        }
        if(this.travel_tick==0){
            this.startExplode();
            this.applyDamage();
        }
        this.travel_tick--;
        this.explode_ifenabled();
        
    }
    
   
    /**
     * If (x,y) is close to (destx,desty) return true;
     * @return 
     */
    public boolean isTimeToExplode(){
        int dist = MyMath.dist(this.getX(), this.getY(), destx, desty);
        if(this.travel_tick<=0 || dist<10){
            return true;
        }else{
            return false;
        }
    }
    
    public void drawOnMainView(ICanvasDevice canvas){
        if(this.pic==null) return;
        canvas.drawImg(this.pic, this.getX(), this.getY(), this.getW(), this.getH(), 0);
    }
    
    /**
     * Read about sequence diagram #2 in D1 wiki pages.
     * (1) check if isTimeToExplode()
     * (2) If yes, submit request to GameEngine.requestDamage(this)
     */
    public void applyDamage(){
        if(!this.isTimeToExplode()) return;
        GameEngine ge = GameEngine.getInstance();
        ge.requestDamage(this);
    }
    
    
    

  
}
