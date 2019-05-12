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

/**
 * Simply represents (x,y)
 * @author csc190
 */
public class Point {
    public int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object others){
        if(!(others instanceof Point)){
            return false;
        }
        Point pother = (Point) others;
        return pother.x==x && pother.y==y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.x;
        hash = 41 * hash + this.y;
        return hash;
    }
}
