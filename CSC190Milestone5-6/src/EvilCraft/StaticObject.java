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
public class StaticObject extends Sprite{
    protected String picname;
    protected String maptileName;

    
    public StaticObject(Team team, int x, int y, int w, int h, String maptileName) {
        super(team, x, y, w, h);
        this.maptileName = maptileName;
        this.picname = "resources/images/common/" + maptileName + ".png";
    }

    @Override
    public void update() {
        //do nothing
    }

    @Override
    public void drawOnMainView(ICanvasDevice mainview) {
        mainview.drawImg(picname, x, y, w, h, 0);
    }

    @Override
    public void drawOnMiniMap(ICanvasDevice minimap) {
        //do nothing
    }
    
}
