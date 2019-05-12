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

import BridgePattern.ICanvasDevice;
import BridgePattern.IGameEngine;
import BridgePattern.IStopWatch;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class provides the performance info of Obj Updates and Pairwise collision
 * detection given the number of objects in the system. Every tick it drops and
 * then adds 10% of the objects randomly.
 *
 * @author csc190
 */
public class GameTickEfficiency implements IGameEngine {

    //data members
    protected ArrayList<Sprite> sprites = new ArrayList();
    protected int n; //total number of sprites
    protected int CYCLES = 300; //measure 1000 cycles
    protected IStopWatch timerTotal = null; //total processing time
    protected IStopWatch timerUpdate = null; //time used for updating objects
    protected IStopWatch timerAddDrop = null; //time used for add/drop methods
    protected IStopWatch timerCollision = null; //time used for collision detection
    protected IStopWatch timerRealCost = null; //from frame 1 to frame CYCLES
    protected ICanvasDevice canvas = null;

    //operations
    /**
     * constructor
     *
     * @param n - the number of sprites in the system
     * @param canvas - a canvas device for providing stop watch devices
     */
    public GameTickEfficiency(int n, ICanvasDevice canvas) {
        this.n = n;

        timerTotal = canvas.createStopWatch("total");
        timerUpdate = canvas.createStopWatch("update");
        timerAddDrop = canvas.createStopWatch("add_drop");
        timerCollision = canvas.createStopWatch("collision");
        timerRealCost = canvas.createStopWatch("real_cost");
    }

    @Override
    public void init() {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            Sprite s = new Sprite(rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(50));
            this.sprites.add(s);
        }
    }

    /**
     * Update the location and states of all sprites.
     */
    protected void updateAll() {
        timerUpdate.start();
        for (Sprite s : this.sprites) {
            s.update();
        }
        timerUpdate.stop();
    }

    /**
     * Remove 10% of sprites and add 10% of sprites
     */
    protected void addRemove() {
        timerAddDrop.start();
        //1. remove 10%        
        ArrayList<Sprite> toRemove = new ArrayList();
        Random rand = new Random();
        for (int i = 0; i < n / 10; i++) {
            int idx = rand.nextInt(sprites.size() - 1);
            Sprite s = sprites.get(idx);
            if (!toRemove.contains(s)) {
                toRemove.add(s);
            }
        }
        for (Sprite s : toRemove) {
            sprites.remove(s);
        }

        //2. add 10%
        for (int i = 0; i < n / 10; i++) {
            Sprite s = new Sprite(rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(50));
            sprites.add(s);
        }
        timerAddDrop.stop();
    }

    /**
     * Detect collision
     */
    protected void detectCollision() {
        timerCollision.start();
        int nCollisions = 0;
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                if (sprites.get(i).isCollidingWith(sprites.get(j))) {
                    nCollisions++;
                }
            }
        }
        //System.out.println("nCollisions: " + nCollisions);
        timerCollision.stop();
    }

    protected int tick = 0;

    @Override
    public void onTick() {
        if (tick > CYCLES) {
            return;
        }
        tick++;
        if (tick == 1) {
            timerRealCost.start();
        }
        if (tick == CYCLES) {
            timerRealCost.stop();
        }
        
        timerTotal.start();
        this.detectCollision();
        this.addRemove();
        this.updateAll();
        timerTotal.stop();

        if (tick == CYCLES) {//dump
            System.out.println("--------N: " + this.n + "-all units in millisec------------------");
            System.out.println("Avg Processing time per tick: " + timerTotal.getTotalElapsed() * 1.0 / CYCLES);
            System.out.println("collision detection per tick: " + timerCollision.getTotalElapsed() * 1.0 / CYCLES);
            System.out.println("Add remove per tick: " + timerAddDrop.getTotalElapsed() * 1.0 / CYCLES);
            System.out.println("Update sprites per tick: " + timerUpdate.getTotalElapsed() * 1.0 / CYCLES);
            System.out.println("FPS: " + (CYCLES * 1000.0 / timerRealCost.getTotalElapsed()));
            System.out.println("Real cost per tick: " + timerRealCost.getTotalElapsed() * 1.0 / CYCLES);
        }
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
