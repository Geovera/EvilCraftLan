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

import java.util.ArrayList;

/**
 * Manages account information of a team
 * @author csc190
 */
public class Team {
    // ---- DATA MEMBERS ---------
    protected int cash;
    protected String name;
    protected Base base;
    protected ArrayList<Sprite> arrSprites = new ArrayList<Sprite>();
    // ---- OPERATIONS
    public Team(int cash, String name){
        this.cash = cash;
        this.name = name;
    }
    
    public Base getBase(){
        return base;
    }
    
    public void setBase(Base base){
        this.base = base;
    }
    
    /**
     * Return false if cannot afford. Otherwise, adjust the cash correspondingly
     * @param spriteName
     * @return 
     */
    protected static String [] units = new String [] {ShopButton.INFANTRY, ShopButton.TANK, ShopButton.PLANE};
    protected int [] costs = new int [] {100, 500, 1000};
    public boolean PurchaseSprite(String spriteName){
        for(int i=0; i<3; i++){
            if(units[i].equals(spriteName)){
                if(this.cash>=costs[i]){
                    this.cash-= costs[i];
                    return true;
                }
            }
        }
        return false;
        
    }
    
    public int getCash(){
        return this.cash;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void addSprite(Sprite sprite){
        this.arrSprites.add(sprite);
    }
}
