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
import EvilCraft.Airplane;
import EvilCraft.Base;
import EvilCraft.GameEngine;
import EvilCraft.Infantry;
import EvilCraft.Point;
import EvilCraft.Tank;
import EvilCraft.Team;

/**
 *
 * @author csc190
 */
public class TestD3GameEngine extends GameEngine {

    public TestD3GameEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
    }

    @Override
    public void init() {
        /**
         * Goal:
         * (1) show tank can hurt infantry and tank but not plane
         * (2) tank will not hurt units out of range
         * (3) tank will not hurt its own team
         */
        super.init();
        Team myteam = this.getPlayerTeam();
        Team aiteam = this.getAITeam();
        Base b1 = new Base(myteam, 0, 0, 50, 50, "b1");
        Base b2 = new Base(aiteam, 300, 300, 50, 50, "b2");

        myteam.setBase(b1);
        aiteam.setBase(b2);

        Tank t11 = new Tank(myteam, 100, 100, 30, 30);
       
        
       Infantry i21 = new Infantry(aiteam, 80, 120, 25, 25);
        Infantry i22 = new Infantry(aiteam, 250, 220, 25, 25); 
        Infantry i23 = new Infantry(aiteam, 250, 300, 25, 25);
        Infantry i24 = new Infantry(aiteam, 225, 280, 25, 25);
        Infantry i25= new Infantry(aiteam, 325, 350, 25, 25);
       
        Airplane a21 = new Airplane(aiteam, 400, 150, 80, 80);
        
        myteam.addSprite(t11);
        
       
        aiteam.addSprite(i21);
        aiteam.addSprite(i22);
        aiteam.addSprite(i23);
        aiteam.addSprite(i24);
        aiteam.addSprite(i25);
       
        aiteam.addSprite(a21);
        
        this.addSprite(t11);
        
        
        this.addSprite(i21);
        this.addSprite(i22);
         this.addSprite(i23);
          this.addSprite(i24);
           this.addSprite(i25);
        this.addSprite(a21);
        
        this.addSprite(b1);
        this.addSprite(b2);
        
        t11.setNavigationGoal(new Point(b2.getX()-50, b2.getY()-50));
        
        
        


    }

}
