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
package EvilCraftMilestone4;

import BridgePattern.ICanvasDevice;
import BridgePattern.ISoundDevice;
import EvilCraft.Base;
import EvilCraft.GameEngine;
import EvilCraft.Team;

/**
 *
 * @author csc190
 */
public class TestCase3GameEngine extends GameEngine {
    
    public TestCase3GameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
    }
    
    protected int ticks = 0;
    @Override
    public void onTick(){
        ticks++;
        if(ticks==100){
            Team team = this.arrTeams.get(1);
            Base base = team.getBase();
            base.setDead();
        }
        super.onTick();
        
    }
    
}
