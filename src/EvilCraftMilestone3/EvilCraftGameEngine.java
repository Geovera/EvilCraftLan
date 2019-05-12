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

import BridgePattern.ICanvasDevice;
import BridgePattern.IGameEngine;
import BridgePattern.ISoundDevice;
import BridgePattern.IStopWatch;
import FXDevices.FXCanvasDevice;

/**
 *
 * @author csc190
 */
public abstract class EvilCraftGameEngine implements IGameEngine {

    //********** DATA MEMBERS ************************
    protected ICanvasDevice canvas = null;
    protected ISoundDevice sound = null;
    private String mapPath; //location of map file
    private IStopWatch stage1Timer; //used for timing stage 1
    private IStopWatch stage2Timer; //used for timing stage 2
    private int tick = 0;
    private int curViewPortX = 0;
    private int curViewPortY = 0;
    private int mapsize = 0;

    //the following are the config parameters for z-shape warlking
    private int STAGE1_TICKS = 900;
    private int[] STAGE2_TICKS = new int[]{1000, 1000, 1000}; //3 legs for 'Z' shape
    private int[] STAGE2_X_SPEED = new int[]{10, -10, 10}; //50 pixels per tick
    private int[] STAGE2_Y_SPEED = new int[]{0, 10, 0};

    //********** METHODS *****************************
    /**
     * Constructor, takes the location of map
     *
     * @param mapPath - path of map, should begin with "resources/"
     * @param canvas - canvas device (view port). size should be 1000x1000
     * @param sound - sound device, cannot be null
     * @param size - the logical size of map size*size (width==height)
     */
    public EvilCraftGameEngine(String mapPath, ICanvasDevice canvas, ISoundDevice sound, int size) {
        this.mapPath = mapPath;
        this.canvas = canvas;
        this.sound = sound;
        this.mapsize = size;
        this.stage1Timer = canvas.createStopWatch("stage1Timer");
        this.stage2Timer = canvas.createStopWatch("stage2Timer");
        this.setViewPort(0, 0);
        //adjust speed based on map size; The original speed is set for 10000x10000 map size
        for (int i = 0; i < 3; i++) {
            STAGE2_X_SPEED[i] = (int) (STAGE2_X_SPEED[i] * size * 1.0 / 10000);
            STAGE2_Y_SPEED[i] = (int) (STAGE2_Y_SPEED[i] * size * 1.0 / 10000);
        }
    }

    /**
     * 
     * @return the viewport canvas device
     */
    public ICanvasDevice getCanvas(){
        return canvas;
    }
    
    /**
     * 
     * @return the sound device
     */
    public ISoundDevice getSound(){
        return sound;
    }
    /**
     * Initialize the game (e.g., loading map and creating moving spriates)
     *
     * @param mapPath - path of the map file
     */
    public abstract void initGame(String mapPath);

    /**
     * Make updates of all game sprites in the game (e.g., moving to new
     * location)
     */
    public abstract void update();

    /**
     * Assumption 1000x1000 view port. Set the left-top corner of the view top
     * to (x,y) in map
     *
     * @param x - x coordinate of the left-top corner of the viewport in map
     * @param y - y coordinate of the left-top corner of the viewport in map
     */
    public abstract void setViewPort(int x, int y);

    @Override
    public final void init() {
        //Don't touch this function.
        this.initGame(this.mapPath);
    }

    /**
     * Two stages: stage 1: stay in top-left corner for 30 seconds (900 ticks).
     * Stage 2: take a z shape walk. Speed: 500 px per tick (60 seconds). It
     * prints the statistics of FPS. Don't change implementation of this method
     */
    @Override
    public final void onTick() {
        int MAX_TICK = STAGE1_TICKS + STAGE2_TICKS[0] + STAGE2_TICKS[1] + STAGE2_TICKS[2];
        if (this.tick > MAX_TICK) {
            return; //do nothing
        }

        //1. control the timer
        if (this.tick == 0) {
            this.stage1Timer.start();
        }
        if (this.tick == STAGE1_TICKS - 1) {
            this.stage1Timer.stop();
        }
        if (this.tick == STAGE1_TICKS) {
            this.stage2Timer.start();
        }
        if (this.tick == MAX_TICK - 1) {
            this.stage2Timer.stop();
        }

        //2. control the movement of view port
        if (this.tick < STAGE1_TICKS) {//stage 1
            //viewport stays at (0,0)
        } else if (this.tick < STAGE1_TICKS + STAGE2_TICKS[0]) {
            this.curViewPortX += STAGE2_X_SPEED[0];
            this.curViewPortY += STAGE2_Y_SPEED[0];
        } else if (this.tick < STAGE1_TICKS + STAGE2_TICKS[0] + STAGE2_TICKS[1]) {
            this.curViewPortX += STAGE2_X_SPEED[1];
            this.curViewPortY += STAGE2_Y_SPEED[1];
        } else if (this.tick < STAGE1_TICKS + STAGE2_TICKS[0] + STAGE2_TICKS[1] + STAGE2_TICKS[2]) {
            this.curViewPortX += STAGE2_X_SPEED[2];
            this.curViewPortY += STAGE2_Y_SPEED[2];
        } else {//it is the SUM of MAX_TICK
            //do nothing, will stop moving
            double fps1 = STAGE1_TICKS * 1000.0 / this.stage1Timer.getTotalElapsed();
            double fps2 = (MAX_TICK - STAGE1_TICKS) * 1000.0 / this.stage2Timer.getTotalElapsed();
            double ps = canvas.getTotalPixsDraw()/1000.0/(this.stage1Timer.getTotalElapsed()+this.stage2Timer.getTotalElapsed());
            double psFrame = canvas.getTotalPixsDraw()/1000000.0/MAX_TICK;
            System.out.println("Stage 1 FPS: " + fps1 + "\nStage 2 FPS: " + fps2 + "\nMillions Pixels Per Second: " + ps + "\nMillion Pixels Per Frame: " + psFrame);
        }
        //calibration
        curViewPortX = curViewPortX<0? 0: curViewPortX;
        curViewPortX = curViewPortX>this.mapsize-1000? this.mapsize-1000: curViewPortX;
        curViewPortY = curViewPortY<0? 0: curViewPortY;
        curViewPortY = curViewPortY>this.mapsize-1000? this.mapsize-1000: curViewPortY;
        this.setViewPort(curViewPortX, curViewPortY);
        //3. update all sprites
        this.update();
        this.tick++;

    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        //do nothing
    }

    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        //do nothing
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        //do nothing
    }

}
