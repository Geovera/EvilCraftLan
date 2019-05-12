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

import BridgePattern.ICanvasDevice;
import BridgePattern.ISoundDevice;
import java.util.ArrayList;

/**
 * Main class for Milestone 3.
 *
 * @author csc190
 */
public class GameEngineMS3 extends EvilCraftGameEngine {

    //**************** DATA MEMBERS *******************
    protected Picture[][] map;
    protected int size;
    protected int viewportX = 0, viewportY = 0;
    protected ArrayList<Picture> arrPics = new ArrayList();
    protected ArrayList<IGameObject> arrGameObjects = new ArrayList();
    protected static GameEngineMS3 inst;
    
    public static GameEngineMS3 getInstance(){
        return inst;
    }
    
    public GameEngineMS3(String mapPath, ICanvasDevice canvas, ISoundDevice sound, int size) {
        super(mapPath, canvas, sound, size);
        this.size = size;
        inst = this;

    }
    
    public void registerGameObj(IGameObject obj){
        this.arrGameObjects.add(obj);   
        for(Picture pic: obj.getPictures()){
            this.addPic(pic);
        }
    }
    
    public void removeGameObj(IGameObject obj){
        this.arrGameObjects.remove(obj);
        for(Picture pic: obj.getPictures()){
            this.removePic(pic);
        }
    }
    
    public void addPic(Picture pic){
        this.arrPics.add(pic);
    }
    
    public void removePic(Picture pic){
        this.arrPics.remove(pic);
    }
    
    protected void updateAllGameObjects(){
        int size = this.arrGameObjects.size();
        for(int i=0; i<size; i++){
            IGameObject obj = this.arrGameObjects.get(i);
            obj.update();
        }
    }
    
    protected void drawAllPics(){
        for(Picture pic: this.arrPics){
            canvas.drawImg(pic.getImg(), pic.getX()-this.viewportX, pic.getY()-this.viewportY,
                    pic.getSize(), pic.getSize(), pic.getDegree());
        }
    }

    @Override
    public void initGame(String mapPath) {
        //1. load map
        int mapunits = size / 100;
        map = new Picture[mapunits][mapunits];

        String sc = canvas.readFile(mapPath);
        String[] lines = sc.split("\n");
        for (int i = 0; i < mapunits && i < lines.length; i++) {
            String line = lines[i];
            String[] arrNames = line.split(" +");
            for (int j = 0; j < mapunits && j < arrNames.length; j++) {
                String name = arrNames[j];
                String path = "resources/images/common/" + name + ".png";
                map[i][j] = new Picture(path, j * 100, i * 100, 100);
            }
        }
        
        //2. add tanks
        for(int i=0; i<50; i++){
            for(int j=0; j<20; j++){
                Tank t1 = new Tank(i*100, j*100);
                this.registerGameObj(t1);
            }
        }
        
        
        //3. play sound
        this.getSound().playRepeated("resources/sound/drama.wav");

        
    }

    protected void drawMap() {
        int bp1 = 1;
        int mini = this.viewportY/100-1;
        int maxi = (this.viewportY/100+11);
        int minj = this.viewportX/100-1;
        int maxj = (this.viewportX/100+11);
        mini = mini<0? 0: mini;
        maxi = maxi>=map.length? map.length: maxi;
        minj= minj<0? 0: minj;
        maxj = maxj>=map.length? map.length: maxj;
        
        mini = 0;
        maxi = map.length;
        minj = 0;
        maxj = map.length;
        for (int i = mini; i < maxj; i++) {
            for (int j = minj; j < maxj; j++) {
                Picture tile = map[i][j];
                int x = tile.x - this.viewportX;
                int y = tile.y - this.viewportY;
                String imgPath = tile.getImg();
                this.canvas.drawImg(imgPath, x, y, 100, 100, 0);
            }
        }
        int bp = 100;
    }

    protected void removeDead(){
        ArrayList<IGameObject> arrDead = new ArrayList();
        for(IGameObject obj: this.arrGameObjects){
            if(obj.isDead()) arrDead.add(obj);
        }
        for(IGameObject obj: arrDead){
            ArrayList<Picture> arrPics = obj.getPictures();
            for(Picture p: arrPics){
                this.arrPics.remove(p);
            }
            this.arrGameObjects.remove(obj);
        }
    }
    @Override
    public void update() {
        this.canvas.clear();
        this.drawMap();
        this.updateAllGameObjects();
        this.drawAllPics();
        this.removeDead();
    }

    @Override
    public void setViewPort(int x, int y) {
        this.viewportX = x;
        this.viewportY = y;
    }

    @Override
    public void onMouseMoved(ICanvasDevice canvas, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
