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
package EvilCraftMilestone1;

import BridgePattern.ICanvasDevice;
import BridgePattern.IGameEngine;
import BridgePattern.IStopWatch;
import java.util.Random;

/**
 * A game to test speed,config params include one picture, and the number of pictures to load per tick. It does not handle mouse events.
 * @author csc190
 */
public class GameFPS implements IGameEngine {
    // ---- DATA MEMBERS ------------
    protected String picPath; //path to the picture to draw
    protected int number; //number of pictures to draw each round
    protected int maxX; //max x coordinate of picture (left-top corner)
    protected int maxY; //max y coordinate of picture (left-top corner)
    protected int size; //picture size in pixel. width equal to height
    protected ICanvasDevice canvas;
    protected IStopWatch timer; //used for calculating FPS. Will dump information at 1000th frame
    protected long ticks = 0;
    protected final static long MEASURE_CYCLES = 100; //measure the performance of the first 1000 ticks
    protected IStopWatch timerOnTick;
    
    // ---- METHODS -----------------
    
    /**
     * Constructor. 
     * @param picPath - relative path of the picture to load
     * @param number - each round, randomly generate the given number of pictures
     * @param maxX - the x coordinate for each pic randomly generated in range [0, maxX]
     * @param maxY - the y coordinate for each pic randomly generated in range [0, maxY]
     * @param size - the size of picture in pixel, we assume it's square.
     * @param canvas - canvas device to draw
     */
    public GameFPS(String picPath, int number, int size, int maxX, int maxY, ICanvasDevice canvas){
        this.picPath = picPath;
        this.number = number;
        this.maxX = maxX;
        this.maxY = maxY;
        this.canvas = canvas;
        this.size = size;
        this.timer = canvas.createStopWatch("FPSWatch");
        this.timerOnTick = canvas.createStopWatch("onTick");
    }

    @Override
    public void init() {
        //do nothing
    }

    @Override
    public void onTick() {
        this.timerOnTick.start();
        this.ticks++;  
        
        if(ticks==1){
            this.timer.start();
        }
        if(ticks==MEASURE_CYCLES+1){
            this.timer.stop();
            long nMS = this.timer.getTotalElapsed();
            double fps = MEASURE_CYCLES*1000.0/nMS;            
            long totalPixels = number * size * size * MEASURE_CYCLES;
            double pps = totalPixels* 1000.0/nMS/1000000.0; //number of millions pixels per second
            System.out.println("=====================================");
            System.out.println("FPS: " + fps + ", totalPixels: " + totalPixels + ", Million Pixels per Second: " + pps);
            System.out.println("onTick time: " + this.timerOnTick.getTotalElapsed() + "ms");
            System.out.println("Total time: " + this.timer.getTotalElapsed() + "ms");
            System.out.println("Pixel Drawtime background: " + (this.timer.getTotalElapsed()-this.timerOnTick.getTotalElapsed())  + "ms");
            System.out.println("=====================================");
        }
        if(ticks>MEASURE_CYCLES+1){
            this.timerOnTick.stop();
            return; //don't do any drawing more
        }
        
        
        //DO THE SHOW of all pictures in one round         
        this.canvas.clear();
        Random rand = new Random();
        for(int i=0; i<this.number; i++){
            int rx = rand.nextInt(this.maxX);
            int ry = rand.nextInt(this.maxY);
            this.canvas.drawImg(this.picPath, rx, ry, size, size, 0);
        }
        this.timerOnTick.stop();
    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
