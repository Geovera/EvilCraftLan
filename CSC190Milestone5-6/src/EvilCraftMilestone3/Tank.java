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
import java.util.Random;

/**
 *
 * @author csc190
 */
public class Tank implements IGameObject {
    //------------ * data members ***************
    protected Picture picBody;
    protected Picture picGun;
    protected int x, y;
    protected int body_degree = 0;
    protected int gun_degree = 0;
            
    //------------ OPERATIONS -----------------
    public Tank(int x, int y){
        this.x = x;
        this.y = y;
        this.picBody = new Picture("resources/images/team_red/tank/body.png", x, y, 50);
        this.picGun = new Picture("resources/images/team_red/tank/gun.png", x, y, 50);
    }   

    @Override
    public ArrayList<Picture> getPictures() {
        ArrayList<Picture> arr = new ArrayList();
        arr.add(this.picBody);
        arr.add(this.picGun);
        return arr;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    protected void fire(){
        int ys = (int) ((int)10*Math.sin(gun_degree));
        int xs = (int) ((int)10*Math.cos(gun_degree));
        Bullet b1 = new Bullet(x, y, xs, ys);
        GameEngineMS3 ge = GameEngineMS3.getInstance();
        ge.registerGameObj(b1);
        ge.getSound().playOnce("resources/sound/ok.wav");
    }
    protected int tick = 0;
    @Override
    public void update() {
        tick++;
        this.gun_degree++;
        this.picBody.setDegree(this.body_degree);
        this.picGun.setDegree(this.gun_degree);
        this.picBody.setX(x);
        this.picBody.setY(y);
        this.picGun.setX(x);
        this.picGun.setY(y);
        if(tick%30*2==0){
            Random rand = new Random();
            if(rand.nextInt()%5==0){
                this.fire();
            }
        }
    }
    
}
