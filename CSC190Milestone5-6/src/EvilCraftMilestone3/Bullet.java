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
package EvilCraftMilestone3;

import java.util.ArrayList;

/**
 *
 * @author csc190
 */
public class Bullet implements IGameObject {

    protected int x, y, xspeed, yspeed;
    protected Picture pic;
    protected int counter = 0;

    public Bullet(int x, int y, int xspeed, int yspeed) {
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.pic = new Picture("resources/images/team_red/tank/bullet.png", x, y, 10);

    }

    @Override
    public ArrayList<Picture> getPictures() {
        ArrayList<Picture> arr = new ArrayList();
        arr.add(this.pic);
        return arr;
    }

    @Override
    public boolean isDead() {
        return counter>150; //will fly for 5 seconds
    }

    @Override
    public void update() {
        counter++;
        this.x+= this.xspeed;
        this.y+= this.yspeed;
        this.pic.setX(x);
        this.pic.setY(y);
        
    }

}
