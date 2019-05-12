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
package EvilCraftMilestone2;

import java.util.Random;

/**
 * A game sprite
 * @author csc190
 */
public class Sprite {
    //data members
    protected int x; //x coordinate of left-top corder
    protected int y;
    protected int size;
    
    //operations
    /**
     * 
     * @param x - x coordinate of left-top corder
     * @param y - y coordinate of left-top corder
     * @param size - in pixels
     */
    public Sprite(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    /**
     * To test if (x,y) is in square at (rx,ry) with the given size
     * @param x - x coordinate of the point
     * @param y - y coordinate of the point
     * @param rx - x coordinate of the left-top corner of square
     * @param ry - y coordinate of the right-top corner of square
     * @param size - size of square
     * @return 
     */
    protected boolean isPointInSquare(int x, int y, int rx, int ry, int size){
        return x>=rx && x<=rx+size && y>=ry && y<=ry+size;
    }
    
    /**
     * to tell if two sprites collide with each other
     * @param other - cannt be null
     * @return 
     */
    public boolean isCollidingWith(Sprite other){
        return isPointInSquare(this.x, this.y, other.x, other.y, other.size) ||
                isPointInSquare(this.x + this.size, this.y, other.x, other.y, other.size) ||
                isPointInSquare(this.x, this.y + this.size, other.x, other.y, other.size) ||
                isPointInSquare(this.x+size, this.y+size, other.x, other.y, other.size)||
                isPointInSquare(other.x, other.y, this.x, this.y, this.size);
    }
    
    /**
     * Update its position every tick.
     */
    public void update(){
        Random rand = new Random();
        this.x += rand.nextInt(10);
        this.y += rand.nextInt(5);
    }
}
