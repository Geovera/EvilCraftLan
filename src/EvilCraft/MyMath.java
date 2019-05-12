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
 * Calculate angles
 * @author csc190
 */
public class MyMath {
    public static int getDegree(int x1, int y1, int x2, int y2){
        if(y2==y1){
            return x1==x2?0: (x1<x2?90: 270);
        }
        double degree = Math.toDegrees(Math.atan2(x2-x1, y1-y2));
        
        int id = (int) degree;
        return (id+360)%360;
    }
    
    public static int dist(int x1, int y1, int x2, int y2){
        int d1 = x2-x1;
        int d2 = y2-y2;
        double dist = Math.sqrt(d1*d1+d2*d2);
        return (int) dist;
    }
    
    public static boolean isCollide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){
        //calculate the intersection
       int xmax = Integer.max(x1, x2);
       int xmin = Integer.min(x1+w1-1, x2+w2-1);
       int ymax = Integer.max(y1, y2);
       int ymin = Integer.min(y1 + h1-1, y2 + h2-1);
       return xmin>=xmax && ymin>=ymax;
    }
}
