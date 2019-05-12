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
import java.util.LinkedList;
import java.util.Queue;
import javafx.util.Pair;

/**
 * Map will be later used to provide routing information
 * @author csc190
 */
public class Map {
    protected String [][] tiles;
    protected int [][] map;
    /**
     * constructor
     * @param mapPath
     * @param canvas - can be used to readFile()
     */
    public Map(String mapPath, ICanvasDevice canvas){
        String sall = canvas.readFile(mapPath);
        String [] sLines = sall.split("\n");
        this.tiles = new String[sLines.length][];
        this.map = new int [sLines.length][];
        for(int i=0; i<sLines.length; i++){
            String [] words = sLines[i].split(" ");
            this.tiles[i] = words;
            this.map[i] = new int [words.length];
            for(int j=0; j<words.length; j++){
                this.map[i][j] = isObstacle(words[j])? 1: 0;
            }
        }
    }
    
    public int getNumRows(){
        return this.tiles.length;
    }
    
    public int getNumCols(){
        return this.tiles[0].length;
    }
    
    /**
     * Get the maptile symbol at the location. Row and col are "logical" index of tiles.
     * @param row
     * @param col
     * @return 
     */
    public String getMapTile(int row, int col){
        return this.tiles[row][col];
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
    
    /**
     * Generate the 2d cost matrix based on the map, for pt as destination.
     * @param pt
     * @return 
     */
    public int [][] generateBFSMap(Point pt){
        int rowTarget = pt.y/100;
        int colTarget = pt.x/100; //assumption maptiles are 100x100
         /**
         * FAST Version. BFS Search (given all neighbor distance is 1). Idea:
         * keep a queue of to be processed and keep track of the "visited" state
         * of all cells - never process the same cell again.
         */
         int[][] res = new int[map.length][map[0].length]; //result to return
        boolean[][] visited = new boolean[map.length][map[0].length]; //whether visited
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                res[i][j] = i == rowTarget && j == colTarget ? 0 : Integer.MAX_VALUE;
                visited[i][j] = map[i][j] == 0 ? false : true; //don't visit blocks
            }
        }

        Queue<Pair<Integer, Integer>> queue = new LinkedList();
        queue.add(new Pair(rowTarget, colTarget));
        visited[rowTarget][colTarget] = true;
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.remove();
            int x = pair.getKey();
            int y = pair.getValue();

            //update each of its neighbors
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    int nbX = x + xOffset;
                    int nbY = y + yOffset;
                    if (nbX < 0 || nbY < 0 || nbX >= map.length || nbY >= map[0].length) {
                        continue; //out of range
                    }
                    if (xOffset == 0 && yOffset == 0) {
                        continue;
                    }
                    //NOW valid indexes
                    if (map[nbX][nbY] == 0 && !visited[nbX][nbY]) {//not blocks
                        visited[nbX][nbY] = true;
                        //THE CHECK IS TO AVOID CUTTING CORNER OF DIAGNAL MOVEMENT!
                        int newval = map[x][y+yOffset]==0 && map[x+xOffset][y]==0? res[x][y] + 1: res[x][y]+2; 
                        res[nbX][nbY] = newval;
                        queue.add(new Pair(nbX, nbY));

                    }
                }
            }

        }

        return res;
    }
}
