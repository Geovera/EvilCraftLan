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

/**
 * Represents a maptile
 * @author csc190
 */

public class StaticObject extends Sprite {
    protected transient String tile;

    public StaticObject(Team team, int x, int y, int w, int h, String maptile, int lifepoints) {
        super(team, x, y, w, h, lifepoints, 0, 0);
        this.tile = maptile;
        this.pic = "resources/images/common/" + maptile + ".png";
    }
    
    public StaticObject(Team team, int x, int y, int w, int h, String pic){
        super(team, x, y, w, h, 1, 0,0);
        this.pic = "resources/images/common/"+pic + ".png";
    }

    @Override
    public void update() {
        this.explode_ifenabled();
    }

    @Override
    public void drawOnMainView(ICanvasDevice mainview) {
        if(this.pic!=null){
            mainview.drawImg(this.pic,this.getX(), this.getY(), this.getW(), this.getH(), 0);
        }
    }

    @Override
    public void drawOnMiniMap(ICanvasDevice minimap) {

        int mw = GameEngine.getInstance().map.getNumRows()*100;
        int vw = 200;
        //if(this.team==null);
        //String color = this.team.name.indexOf("Human") >= 0 ? "#FF0000" : "#0000FF";
        minimap.drawImg(this.pic, this.getX()*200/mw, this.getY()*200/mw, this.getW()*200/mw+1, this.getH()*200/mw+1, 0);
    }

    @Override
    public Point getNextMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFacing(Point pt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adjustBodyHeading(Point pt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
