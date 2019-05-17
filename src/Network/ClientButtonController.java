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
package Network;

import BridgePattern.ICanvasDevice;
import EvilCraft.ButtonController;
import EvilCraft.ShopButton;
import EvilCraft.Team;

/**
 *
 * @author csc190
 */
public class ClientButtonController extends ButtonController{
    
    Connection connection;
    private int cash = 10000;
    
    public ClientButtonController(Team team, ICanvasDevice canvas, Connection connection) {
        super(team, canvas,40);
        this.connection = connection;
    }
    
    @Override
    public void onLeftClick(ICanvasDevice canvas, int x, int y){
        connection.dataButtonClick(x, y);
    }
    @Override
    public void onTick() {
        //1. draw the bank account
        String sCash = "$" + cash;
        this.canvas.drawText(sCash, 10, 50, 20);
        
        //2. draw the buttons
        for(int i=0; i<this.arrButtons.size(); i++){
            ShopButton btn = this.arrButtons.get(i);
            btn.update();
            btn.draw(canvas);
        }
    }
    
    public void setCash(int cash){
        this.cash = cash;
    }
    public void setCoolDown(String type){
        if(type.equals(ShopButton.INFANTRY))
            this.arrButtons.get(0).startTimer();
        else if(type.equals(ShopButton.TANK))
            this.arrButtons.get(1).startTimer();
        else if(type.equals(ShopButton.PLANE))
            this.arrButtons.get(2).startTimer();
    }
    
}
