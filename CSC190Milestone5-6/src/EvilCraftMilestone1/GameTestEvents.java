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
 * A simple game engine that shows the mouse events information. It simply prints out the location of all events
 * @author csc190
 */
public class GameTestEvents implements IGameEngine{
    //data members
    protected ICanvasDevice canvasDevice;
    
    public GameTestEvents(ICanvasDevice canvas){
        this.canvasDevice = canvas;
        this.canvasDevice.setupEventHandler(this);
    }

    @Override
    public void init() {
        //do nothing
    }

    @Override
    public void onTick() {
        //do nothing
    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        System.out.println("Right Click on (" + x + ", " + y + ")");
    }

    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        System.out.println("Left Click on (" + x + ", " + y + ")");
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        System.out.println("Region Selected from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
    }
    
}
