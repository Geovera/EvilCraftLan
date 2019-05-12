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
 * StopWatch device. May be system implementation specific. Provide functions for measuring
 * elapsed time.
 * @author csc190
 */
public interface IStopWatch {
   
    
    /**
     * Start ticking. That is, no two start() should be called in sequence.
     */
    public void start();
    
    /**
     * Stop ticking. Start() and stop() should always be called in pairs. That is, no two stop() should be called in sequence.
     */
    public void stop();
    
   
    /**
     * Get the elapsed time for the LATEST pair of start() and stop()
     * @return the number of milliseconds elapsed for the last pair of start() and stop()
     */
    public long getElapsed();
    
    /**
     * Get the total sum of of elapsed time for ALL pairs of start() and stop()
     * @return number of milliseconds
     */
    public long getTotalElapsed();
    
    /**
     * Reset the total to 0.
     */
    public void clear();
    
    /**
     * Get the name of stop watch
     */
    public String getName();
}
