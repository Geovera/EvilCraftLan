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

/**
 * Represent the object of Mouse. Display mouse shapes at different situation
 *
 * @author csc190
 */
public class MouseSprite extends Sprite {

    int x = -1, y = -1; //this is the logical map coordinates
    int clientx = -1, clienty = -1;

    @Override
    public void drawOnMainView(ICanvasDevice mainview) {
        if (pic!=null){
            mainview.drawImg(pic, x, y, w, h, 0);
        }
    }

    @Override
    public void drawOnMiniMap(ICanvasDevice minimap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    enum MODE {
        NONE, ARROW_LEFT, ARROW_RIGHT, ARROW_TOP, ARROW_BOTTOM, MOVE, ATTACK
    };
    protected MODE mode;
    ArrayList<Sprite> arrTargets = null;
    protected String picbase = "resources/images/common/";
    protected String pic = null;
    protected ICanvasDevice mainview;
    protected ICanvasDevice minimap;

    public MouseSprite(ICanvasDevice mainview, ICanvasDevice minimap) {
        super(null, 0, 0, 0, 0,1,0,0);
        this.mainview = mainview;
        this.minimap = minimap;
    }

    public void handleEvnet(MouseEvent eventType, ICanvasDevice canvas, int x, int y, ArrayList<Sprite> arrSprites) {
        GameEngine ge = GameEngine.getInstance();
        if (eventType == MouseEvent.LeftClick) {
            this.arrTargets = null;
            this.mode = MODE.NONE;
        } else if (eventType == MouseEvent.RightClick) {
            this.arrTargets = arrSprites;
        } else if (eventType == MouseEvent.MouseMove && canvas == ge.mainview) {

            Point mappoint = ge.getGlobalCoordinates(canvas, x, y, ge.map);
            this.x = mappoint.x;
            this.y = mappoint.y;
            this.clientx = x;
            this.clienty = y;
            if (x >= 0 && x <= 20) {
                this.mode = MODE.ARROW_LEFT;
            } else if (x > canvas.getWidth() - 50) {
                this.mode = MODE.ARROW_RIGHT;
            } else if (y >= 0 && y <= 20) {
                this.mode = MODE.ARROW_TOP;
            } else if (y > canvas.getHeight() - 50) {
                this.mode = MODE.ARROW_BOTTOM;
            } else if (ge.arrSelected != null && ge.arrSelected.size() > 0) {
                if (arrSprites != null && arrSprites.size() > 0) {
                    this.mode = MODE.ATTACK;
                } else {
                    this.mode = MODE.MOVE;
                }

            } else {
                this.mode = MODE.NONE;
            }
        } else {

        }
    }
    /**
     * Return first enemy sprite which is in the previous MouseMoved event
     *
     * @return
     */
    public Sprite getAttackGoal() {
        if (this.arrTargets != null && this.arrTargets.size() > 0) {
            return this.arrTargets.get(0);
        } else {
            return null;
        }
    }

    protected int ticks = 0;

    @Override
    public void update() {
        ticks++;
        GameEngine ge = GameEngine.getInstance();
        if (this.clientx != -1) {
            Point mappoint = ge.getGlobalCoordinates(mainview, this.clientx, this.clienty, ge.map);

            this.x = mappoint.x;
            this.y = mappoint.y;
        }
        if (this.mode == MODE.ARROW_BOTTOM) {
            pic = picbase + "down.png";
            this.mainview.setViewPort(mainview.getX(), mainview.getY() + 2);
        } else if (this.mode == MODE.ARROW_LEFT) {
            pic = picbase + "left.png";
            this.mainview.setViewPort(mainview.getX() - 2, mainview.getY());
        } else if (this.mode == MODE.ARROW_RIGHT) {
            pic = picbase + "right.png";
            this.mainview.setViewPort(mainview.getX() + 2, mainview.getY());
        } else if (this.mode == MODE.ARROW_TOP) {
            pic = picbase + "up.png";
            this.mainview.setViewPort(mainview.getX(), mainview.getY() - 2);
        } else if (this.mode == MODE.MOVE) {
            pic = ticks / 10 % 2 == 0 ? picbase + "move.png" : null;
        } else if (this.mode == MODE.ATTACK) {
            pic = ticks / 10 % 2 == 0 ? picbase + "attack.png" : null;
        } else {
            pic = null;
        }
    }
}