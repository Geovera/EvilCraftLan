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
 *
 * @author csc190
 */
public class Bomb extends Projectile{

    public Bomb(Team team, int x, int y, int w, int h, int lifepoints, int altitude, int block_score, int destx, int desty) {
        super(team, x, y, w, h, lifepoints, altitude, block_score, destx, desty);
        this.setTravel(10);
         this.pic = "resources/images/common/bomb.png";
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void drawOnMainView(ICanvasDevice mainview) {
        super.drawOnMainView(mainview);
    }

    @Override
    public void drawOnMiniMap(ICanvasDevice minimap) {
        return;
     }

    @Override
    public Point getNextMove() {
        return null;}

    @Override
    public boolean isFacing(Point pt) {
        return true;}

    @Override
    public void adjustBodyHeading(Point pt) {
        return;}
    
}
