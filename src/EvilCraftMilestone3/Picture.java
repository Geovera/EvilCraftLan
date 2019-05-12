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
package EvilCraftMilestone3;

/**
 *
 * @author csc190
 */
public class Picture {

    //* data members *
    protected String filepath;
    protected int x, y;
    protected int size;
    protected int degree;

    //*********** operations ***************
    public Picture(String filepath, int x, int y, int size) {
        this.filepath = filepath;
        this.x = x;
        this.y = y;
        this.size = size;
    }

   
    public int getX() {
        return this.x;
    }

    
    public int getY() {
        return this.y;
    }

    
    public int getSize() {
        return this.size;
    }

    
    public String getImg() {
        return this.filepath;
    }

   
    public int getDegree() {
        return this.degree;
    }
    
    public void setDegree(int d){
        this.degree = d;
    }
    
    public void setPath(String path){
        this.filepath = path;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }

}
