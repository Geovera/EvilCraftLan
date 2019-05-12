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
package EvilCraft;

import BridgePattern.ICanvasDevice;
import BridgePattern.IGameEngine;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the Palette of buttons for creating units
 * @author csc190
 */
public class ButtonController implements IGameEngine{
    //---- DATA MEMBERS ------------------
    protected ArrayList<ShopButton> arrButtons;
    protected Team myteam;
    protected ICanvasDevice canvas;
    //---- OPERATIONS --------------------

    /**
     * Create 3 buttons and set up the team and canvas for future use
     * @param team
     * @param canvas 
     */
    public ButtonController(Team team, ICanvasDevice canvas){
        this.myteam = team;
        this.canvas = canvas;
        String [] arrTypes = new String [] {ShopButton.INFANTRY, ShopButton.TANK, ShopButton.PLANE};
        String basePath = "resources/images/common/";
        String [] paths = new String []{
            basePath + "infantry_btn.png",
            basePath + "tank_btn.png", 
            basePath + "plane_btn.png"
        };
        this.arrButtons = new ArrayList<ShopButton>();
        for(int i=0; i<arrTypes.length; i++){
            int y = 100*(i+1);
            ShopButton button = new ShopButton(myteam, arrTypes[i], 100, paths[i], 0, y, 200, 100);
            this.arrButtons.add(button);
        }
        this.canvas.setupEventHandler(this);
    }
    
    @Override
    public void init() {
       
    }

    /**
     * For each button, update() them and drawThem. Draw the text for remaining cash as well
     */
    @Override
    public void onTick() {
        //1. draw the bank account
        String sCash = "$" + this.myteam.getCash();
        this.canvas.drawText(sCash, 10, 0, 20);
        
        //2. draw the buttons
        for(int i=0; i<this.arrButtons.size(); i++){
            ShopButton btn = this.arrButtons.get(i);
            btn.update();
            btn.draw(canvas);
        }
    }

    @Override
    public void onRightClick(ICanvasDevice canvas, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y) {
        if(y>=100){
            int idx = y/100-1;
            if(idx>=0 && idx<=2){
                ShopButton btn = this.arrButtons.get(idx);
                btn.onClick();
            }
        }
    }

    @Override
    public void onRegionSelected(ICanvasDevice canvas, int x1, int y1, int x2, int y2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
