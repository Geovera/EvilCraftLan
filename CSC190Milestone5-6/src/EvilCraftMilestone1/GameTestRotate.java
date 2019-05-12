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

/**
 * A simple game that display an image and rotates it. No handling of mouse events.
 * @author csc190
 */
public class GameTestRotate implements IGameEngine{
    // ---- DATA MEMBERS -----
    protected ICanvasDevice canvas = null;
    protected int degrees = 0; //degrees to rotate
    
    // ---- METHODS -----------
    
    public GameTestRotate(ICanvasDevice canvas){
        this.canvas = canvas;
    }

    @Override
    public void init() {
        this.degrees = 0;
    }

    @Override
    public void onTick() {
        this.degrees += 1;
        this.canvas.clear();
        this.canvas.drawImg("resources/images/pic1.png", 200, 200, 50, 50, this.degrees); //draw p1.png at (200,200). size: 50, rotate clockwise for degrees
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
