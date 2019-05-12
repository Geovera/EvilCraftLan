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
 * A canvas device provides a variety of draw functions and will call its controller on mouse operations.
 * Only supports .png files
 * @author csc190
 */
public interface ICanvasDevice {
    /**
     * Draw a given image at the specified location. X axis is from left to right and Y axis is from top to bottom. 
     * @param imgPath - relative path of the image (.png file), e.g., "resources/images/pic1.png"
     * @param x - x coordinate
     * @param y - y coordinate
     * @param width - width in pixel
     * @param height - height in pixel
     * @param degree - 0 degree is reverse Y axis (going up). Rotate clock wise
     */
    public void drawImg(String imgPath, int x, int y, int width, int height, int degree);
    
    
    /**
     * Clear the entire canvas.
     */
    public void clear();
    
    /**
     * Get the width of canvas device in pixels
     * @return non-negative integer
     */
    public int getWidth();
    
    /**
     * Get the height of canvas device in pixels
     * @return non-negative integer
     */
    public int getHeight();
    
    /**
     * Create the named stop watch for measuring performance
     * @param name - name of the stop watch
     * @return non-null instance of a stop watch object
     */
    public IStopWatch createStopWatch(String name);
    
    /**
     * Set up the event handling so that mouse events could be passed to the game engine.
     * Note: RegionSelect event should not trigger redundant mouse click events
     * @param gameEngine - a non-null instance of IGameEngine
     */
    public void setupEventHandler(IGameEngine gameEngine);
    
    
    /**
     * Read the entire file and return the entire content as a string. It will not throw
     * exception. If file not found, return empty string.
     * @param filepath - a relative path started with "resources/"
     * @return the entire file contents. If file can't be opened, return empty string. 
     */
    public String readFile(String filepath);
    
    /**
     * Return the total number of pixels drawn so far
     * @return total number of pixels drawn so far
     */
    public long getTotalPixsDraw();
    
    /**
     * Set the left top corner to (x,y) in map
     * @param x
     * @param y 
     */
    public void setViewPort(int x, int y);
    
    /**
     * Draw the given message with the font size
     * @param msg
     * @param x
     * @param y
     * @param fontsize 
     */
    public  void drawText(String msg, int x, int y, int fontsize);
    
    /**
     * Draw a line from (x1,y1) to (x2,y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2 
     */
    public  void drawLine(int x1, int y1, int x2, int y2);
    
}
