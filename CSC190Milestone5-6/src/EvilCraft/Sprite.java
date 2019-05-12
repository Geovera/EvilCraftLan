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
 * Base class of all game objects
 * @author csc190
 */
public abstract class Sprite {
    //------- DATA MEMBERS ----------
    protected int x, y, w, h;
    protected Team team;
    protected boolean bDead = false;
    
    //------- OPERATIONS -------------
    public void setDead(){
        this.bDead = true;
    }
    
    public boolean isDead(){
        return this.bDead;
    }
    
    protected void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Sprite(Team team, int x, int y, int w, int h){
        this.team = team;
        this.x = x;
        this.y = y;
        this.w= w;
        this.h = h;
    }
    
    public Team getTeam() {
        return this.team;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    /**
     * update its own data attributes
     */
    public abstract void update();
    
    /**
     * Draw itself on main view, mostly like pictures
     * @param mainview - canvas device
     */
    public abstract void drawOnMainView(ICanvasDevice mainview);
    
    /**
     * Draw itself on mini map, most likely colored squares
     * @param minimap - canvas device
     */
    public abstract void drawOnMiniMap(ICanvasDevice minimap);
}
