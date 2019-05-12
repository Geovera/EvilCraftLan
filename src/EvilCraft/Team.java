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
    
    private int getPrice(String sprite){
        if(sprite.equals("TANK")){
            return 500;
        }else if(sprite.equals("INFANTRY")){
            return 100;
        }else if(sprite.equals("PLANE")){
            return 1000;
        }
        return 100000000;
    }

    public boolean PurchaseSprite(String spriteName){
        if(this.cash<this.getPrice(spriteName)){
            return false;
        }
        this.cash-= this.getPrice(spriteName);
        return true;
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
    
    
    public ArrayList<Sprite> getSprites(){       
        return this.arrSprites;   
    }
    
    protected TeamInfo getTeamInfo(){
        SpriteInfo binfo = this.getBase().getSpriteInfo();
        ArrayList<SpriteInfo> arrInfo = new ArrayList<SpriteInfo>();
        for(int i=0; i<this.arrSprites.size(); i++){
            arrInfo.add(this.arrSprites.get(i).getSpriteInfo());
        }
        TeamInfo ti = new TeamInfo(binfo, arrInfo);
        return ti;
    }
}
