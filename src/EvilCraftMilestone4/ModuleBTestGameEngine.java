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
import EvilCraft.GameEngine;
import EvilCraft.Infantry;
import EvilCraft.Tank;
import EvilCraft.Point;
import EvilCraft.Tank;
import EvilCraft.Team;

/**
 *
 * @author csc190
 */
public class ModuleBTestGameEngine extends GameEngine{
    
    public ModuleBTestGameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
    }
    
    @Override
    public void init(){
        super.init();
        
        Tank mytank1 = new Tank(this.getPlayerTeam(), 300, 300, 50, 50);
        Tank mytank2 = new Tank(this.getPlayerTeam(), 1800, 1800, 50, 50);
        this.addSprite(mytank1);
        this.addSprite(mytank2);
        this.getPlayerTeam().addSprite(mytank1);
        this.getPlayerTeam().addSprite(mytank2);
        
        Infantry i1 = new Infantry(this.getAITeam(), 300, 500, 25, 25);
        Infantry i2 = new Infantry(this.getAITeam(), 400, 500, 25, 25);
        this.addSprite(i1);
        this.addSprite(i2);
        this.getAITeam().addSprite(i1);
        this.getAITeam().addSprite(i2);
        
        
    }
}
