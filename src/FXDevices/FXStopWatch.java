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
package FXDevices;

import BridgePattern.IStopWatch;

/**
 * FX implementation of IStopWatch
 * @author csc190
 */
public class FXStopWatch implements IStopWatch{
    //--- DATA MEMBERS -------------
    protected String name;
    
    protected long start=0;
    protected long end=0;
    protected long total = 0;
    protected long diff = 0;
    
    // ----------------- METHODS ----------------------------
    
    //--- OPERATOINS----------------
    public FXStopWatch(String name){
        this.name = name;
    }

    @Override
    public void start() {
        this.start= System.currentTimeMillis();
    }

    @Override
    public void stop() {
        this.end = System.currentTimeMillis();
        if(this.start!=0){
            this.diff = end-start;
            this.total += diff;
        }
        this.start = 0;
        this.end = 0;
    }

    @Override
    public long getElapsed() {
        return this.diff;
    }

    @Override
    public long getTotalElapsed() {
        return this.total;
    }

    @Override
    public void clear() {
        this.start = 0;
        this.end = 0;
        this.total = 0;
    }

    @Override
    public String getName() {
       return this.name;
    }
    
}
