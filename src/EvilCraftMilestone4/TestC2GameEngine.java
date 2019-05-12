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
import EvilCraft.Point;
import EvilCraft.Tank;
import EvilCraft.Team;

/**
 *
 * @author csc190
 */
public class TestC2GameEngine extends GameEngine{
    
    public TestC2GameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
    }
    
    @Override
    public void init(){
        super.init();
        Team myteam = this.getPlayerTeam();
        Team aiteam = this.getAITeam();
        Tank t1 = new Tank(myteam, 100, 100, 50, 50);
        Tank t2 = new Tank(aiteam, 300, 100, 50, 50);
        myteam.addSprite(t1);
        aiteam.addSprite(t2);
        this.addSprite(t1);
        this.addSprite(t2);
        t1.setNavigationGoal(new Point(t2.getX(), t2.getY()));
    }
    
}
