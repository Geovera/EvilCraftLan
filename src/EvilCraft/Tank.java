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
import java.util.ArrayList;

public class Tank extends ArmyUnit {

    protected int gun_degree = 0;
    transient String body_pic;
    String gun_pic;

    public Tank(Team team, int x, int y, int w, int h) {
        super(team, x, y, w, h, 300, 0, 2);
        String team_name = team == GameEngine.getInstance().getPlayerTeam() ? "team_red" : "team_yellow";
        this.pic = "resources/images/" + team_name + "/tank/body.png";
        gun_pic = "resources/images/" + team_name + "/tank/gun.png";
        this.speed=5;
        int k = 0;
    }
    public Tank(Team team, int x, int y, int w, int h, String pic, String gun_pic, int degree, int gun_degree) {
        super(team, x, y, w, h, 300, degree, 2);
        this.pic = pic;
        this.gun_pic = gun_pic;
        this.gun_degree=gun_degree;
        int k = 0;
        this.idx_explode=-1;
    }


    @Override
    public void drawOnMainView(ICanvasDevice mainview) {
        //System.out.println(this.pic);
        if (this.pic != null && this.idx_explode==-1) {
            //System.out.println(this.pic);
            mainview.drawImg(this.pic, this.getX(), this.getY(), this.getW(), this.getH(), degree);
            mainview.drawImg(gun_pic, this.getX(), this.getY(), this.getW(), this.getH(), gun_degree);
            
        }
    }

    @Override
    public void drawOnMiniMap(ICanvasDevice minimap) {
        int mw = GameEngine.getInstance().map.getNumRows()*100;
        int vw = minimap.getWidth();
        String color = this.team.name.indexOf("Human")>=0? "#FF0000": "#0000FF";
        minimap.drawRectangle(x*vw/mw, y*vw/mw, w*vw/mw, h*vw/mw, color);
    }

    @Override
    public Point getNextMove() {
        return this.defaultGetNextMove(5);
    }

    @Override
    public boolean isFacing(Point pt) {
        return this.defaultIsFacing(degree, pt);
    }

    @Override
    public void adjustBodyHeading(Point pt) {
        float targetDegree = this.getAngle(new Point(this.getX(), this.getY()), pt);
        int iTargetDegree = (int) targetDegree;
        int diff = (iTargetDegree-this.degree+360)%360;
        if (diff > 180) {
            //turn left
            diff = diff - 180;
            int offset = diff < 10 ? diff : 10;
            this.degree -= offset;
        } else {
            int offset = diff < 10 ? diff : 10;
            this.degree += offset;
        }
        this.degree = (this.degree+360)%360;
    }

    @Override
    public void resetCoolRate() {
        this.setCoolTicksNeeded(60);
    }

    @Override
    public SpriteInfo getFiringGoal() {
        //1. get the enemy in range
        ArrayList<Sprite> ar = this.getEnemyInRange(100);
        for (int i = 0; i < ar.size(); i++) {
            SpriteInfo si = ar.get(i).getSpriteInfo();
            if (si == this.attackGoal) {
                return si;
            }
        }
        for (int i = 0; i < ar.size(); i++) {
            SpriteInfo si = ar.get(i).getSpriteInfo();
             if (si.type == SpriteInfo.TYPE.TANK) {
                return si;
            }
            if (si.type == SpriteInfo.TYPE.INFANTRY) {
                return si;
            }
           
            if (si.type == SpriteInfo.TYPE.BASE) {
                return si;
            }
        }

        return null;
    }

    @Override
    public boolean isGunFacing(Point goal) {
        int degree = MyMath.getDegree(this.getX(), this.getY(), goal.x, goal.y);
        int diff = degree - this.gun_degree;
        return diff * diff <= 10;
    }

    @Override
    public void adjustGunHeading(Point goal) {
        int maxAdjust = 2; //5 degrees
        int degree = MyMath.getDegree(this.getX(), this.getY(), goal.x, goal.y);
        int diff = degree - this.gun_degree;
        diff = (diff + 360) % 360;
        if (diff > 180) {
            //rotate left
            diff = diff - 180;
            int toTurn = diff <= maxAdjust ? diff : maxAdjust;
            this.gun_degree -= toTurn;
        } else {
            int toTurn = diff <= maxAdjust ? diff : maxAdjust;
            this.gun_degree += toTurn;
        }
        this.gun_degree = (this.gun_degree + 360) % 360;
    }

    @Override
    public void fireAt(Point pt) {
        Shell shell = new Shell(this.team, this.getX() + this.getW() / 2, this.getY() + this.getH() / 2, 10, 10, 100000, pt.x, pt.y);
        GameEngine ge = GameEngine.getInstance();
        ge.addSprite(shell);
    }

}
