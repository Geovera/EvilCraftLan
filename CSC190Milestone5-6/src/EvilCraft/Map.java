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

import FXDevices.FXCanvasDevice;

/**
 * Map will be later used to provide routing information
 * @author csc190
 */
public class Map {
    // --- DATA MEMBERS -------
    protected String [][] arrTiles;
    
    //--- OPERATIONS ----
    public Map(String mapPath){
        FXCanvasDevice canvas = new FXCanvasDevice(null);
        String sAll = canvas.readFile(mapPath);
        String [] arrLines = sAll.split("\n");
        arrTiles = new String [arrLines.length][];
        for(int i=0; i<arrTiles.length; i++){
            arrTiles[i] = arrLines[i].split(" ");
        }
    }
    
    public int getNumRows(){
        return arrTiles.length;
    }
    
    public int getNumCols(){
        return arrTiles[0].length;
    }
    
    /**
     * Get the maptile symbol at the location. Row and col are "logical" index of tiles.
     * @param row
     * @param col
     * @return 
     */
    public String getMapTile(int row, int col){
        return arrTiles[row][col];
    }
    
    /**
     * Given the symbol tell if it's an obstacle for ground units.
     * @param maptile
     * @return 
     */
    protected static final String obstacles [] = {"t1", "b1", "b2"}; 
    public boolean isObstacle(String maptile){
        for(String s: obstacles){
            if(s.equals(maptile)){
                return true;
            }
        }
        return false;
    }
}
