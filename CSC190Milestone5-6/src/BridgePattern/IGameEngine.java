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
package BridgePattern;

/**
 * An abstract game engine. It should support several event handler function for
 * the events fired by the canvas devices
 * @author csc190
 */
public interface IGameEngine {
    /**
     * Initialization. maybe used to load game sprites.
     */
    public void init();
    
    /**
     * Will be expected every tick. (e.g., 30 ticks per second for 30 FPS). Perform operations
     * such as update all sprites and redraw them.
     */
    public void onTick();
    
    /**
     * Handles the mouse right button click. This operation may be substituted by finger ops on mobile devices.
     * Note that game engine needs to keep track of canvas size and perform translation of coordinates (relative to map)
     * @param canvas - the canvas device which raises the event
     * @param x - x coordinate of the event IN the canvas device
     * @param y - y coordinate of the event IN the canvas device
     */
    public void onRightClick(ICanvasDevice canvas, int x, int y);
    
    
    /**
     * Handles the mouse left button click. This operation may be substituted by finger ops on mobile devices.
     * Note that game engine needs to keep track of canvas size and perform translation of coordinates (relative to map)
     * @param canvas - the canvas device which raises the event
     * @param x - x coordinate of the event IN the canvas device
     * @param y - y coordinate of the event IN the canvas device
     */
    public void onLeftClick(ICanvasDevice canvas, int x, int y);
    
    
    /**
     * Handles the mouse drag and then release event. This operation may be substituted by finger ops on mobile devices.
     * Note that game engine needs to keep track of canvas size and perform translation of coordinates (relative to map)
     * @param canvas - the canvas device which raises the event
     * @param x1 - x coordinate of the DRAG_START event IN the canvas device. 
     * @param y1 - y coordinate of the DRAG_START event IN the canvas device
     * @param x2 - x coordinate of the DRAG_END event IN the canvas device. 
     * @param y2 - y coordinate of the DRAG_END event IN the canvas device
     */
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2);
    
}
