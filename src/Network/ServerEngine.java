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
package Network;

import BridgePattern.ICanvasDevice;
import BridgePattern.ISoundDevice;
import EvilCraft.AI;
import EvilCraft.ArmyUnit;
import EvilCraft.Base;
import EvilCraft.ButtonController;
import EvilCraft.GameEngine;
import EvilCraft.MouseEvent;
import EvilCraft.MouseSprite;
import EvilCraft.Point;
import EvilCraft.Sprite;
import EvilCraft.Tank;
import EvilCraft.Team;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author csc190
 */
public class ServerEngine extends GameEngine{
    
    public static ServerSocket dataServerSocket;
    public static ServerSocket echoServerSocket;
    private static ServerEngine se_instance = null;
    public static boolean gameReady;
    private boolean hasAdded;
    
    public static boolean firstPlayer;
    
    private ButtonController humanController2;
    private ArrayList<Sprite> arrSelected2;
    
    public volatile static ArrayList<Client> clients;
    
    private ArrayList<Sprite> delSpr;
    private ArrayList<Sprite> addSpr;
    
    public static ServerEngine getServerInstance(){return se_instance;}
    
    public ServerEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
        gameReady=false;
        hasAdded =false;
        delSpr = new ArrayList<Sprite>();
        addSpr = new ArrayList<Sprite>();
        arrSelected2 = new ArrayList<Sprite>();
        se_instance=this;
    }
    
    @Override
    public void init(){
        
        try{
            Process p = Runtime.getRuntime().exec("sudo ifconfig eth0 " + RefStrings.SERVER_IP);
        }catch(IOException e){
            System.out.print("Failed to set IP Address!");
            System.exit(0);
        }
        firstPlayer =false;
        clients = new ArrayList<Client>();
        
        startServers(10);
        
        Thread t1 = new Thread(new Dispatcher());
        
        t1.start();
    }
    
    public void initGame(){
        loadGameMap(this.mapPath);
        Handler.echoMap(arrMapTiles);
        
        Team human1 = this.getPlayerTeam();
        Team human2 = this.getAITeam();
        
        /*Sprite tank1 = new Tank(human1,100,100,50,50);
        Sprite tank2 = new Tank(human2,200,200,50,50);
        human1.addSprite(tank1);
        human2.addSprite(tank2);*/
        
        Base b1 = new Base(human1,100,100,100,100,null);
        Base b2 = new Base(human2,800,800,100,100,null);
        human1.setBase(b1);
        human2.setBase(b2);
        this.addSprite(b1);
        this.addSprite(b2);


        
        humanController = new ServerButtonController(human1, null);
        humanController2 = new ServerButtonController(human2, null);
        //tank1.setNavigationGoal(new Point(500,500));
        //tank1.setAttackGoal(tank2.getSpriteInfo());
        //this.addSprite(tank1);
        //this.addSprite(tank2);
        gameReady =true;

    }
    
    @Override
    public void onTick(){
        if(!gameReady)
            return;
        
        arrSprites.stream().forEach(elem -> updateSprite(elem));
        
        for(Sprite s : delSpr){
            arrSprites.remove(s);
        }
        if(!hasAdded && addSpr.size()>0){
            for(Sprite s : addSpr){
                arrSprites.add(s);
            }
            hasAdded=true;
        }
        Team winner = this.CheckWinner();
        if(winner!=null)
            this.endGame(winner);
        if(humanController!=null){
            humanController.onTick();
            humanController2.onTick();
        }
        delSpr.clear();
        if(hasAdded){
            addSpr.clear();
            hasAdded=false;
        }
        
        Handler.updateSprites(this.arrSprites);
    }
    
    @Override
    public void endGame(Team winner){
        if(this.getPlayerTeam().equals(winner)){
            Handler.gameFinish(this.clients.get(0), "You Won");
            Handler.gameFinish(this.clients.get(1), "You Lost");
        }
        else if(this.getAITeam().equals(winner)){
            Handler.gameFinish(this.clients.get(1), "You Won");
            Handler.gameFinish(this.clients.get(0), "You Lost");
        }
        this.gameReady=false;

    }
    
    private void updateSprite(Sprite s){
            s.update();
    }
    
    @Override
    public void addSprite(Sprite s){
        addSpr.add(s);
    }
    @Override
    public void removeSprite(Sprite s){
        delSpr.add(s);
    }
    
    private static boolean startServers(int counter){
        try{
            dataServerSocket = new ServerSocket(RefStrings.DATA_PORT);
            System.out.println("----- Positions Server started on port " + RefStrings.DATA_PORT + " -----");
            echoServerSocket = new ServerSocket(RefStrings.ECHO_PORT);
            System.out.println("----- Echo Positions Server started on port " + RefStrings.ECHO_PORT + " -----");
            return true;
        }catch(IOException e){
            while(counter !=0){
                System.out.println("----- Could not start servers on ports. -----");
                counter -=1;
                
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch(InterruptedException e1){}
                
                startServers(counter);
            }
            System.out.println("----- Could not start server. Please restart. -----");
            System.exit(0);
        }
        return false;
    }
    
    @Override
    public Team getPlayerTeam(){
        return clients.get(0).team;
    }
    
    @Override
    public Team getAITeam(){
        return clients.get(1).team;
    }
    
    public void purchaseRequest(Team t, int x, int y){
        if(t.getName().equals(this.getPlayerTeam().getName()))
            humanController.onLeftClick(null, x, y);
        else
            humanController2.onLeftClick(null, x, y);
    }
    
    public void regionSelect(Team t, int x1, int y1, int x2, int y2){
        Point p1 = new Point(x1,y1);
        Point p2 = new Point(x2,y2);
        
        if(t.equals(this.getPlayerTeam())){
            this.arrSelected= this.getArrSprites(p1, p2, this.getPlayerTeam(), true);
        }
        else{
            this.arrSelected2= this.getArrSprites(p1, p2, this.getAITeam(), true);

        }
    }
    
    public void rightClick(Team t, int x, int y){
        Point pt = new Point(x,y);
        Point pt1 = new Point(pt.x-25, pt.y-25);
        Point pt2 = new Point(pt.x+25, pt.y+25);
        //System.out.println("X: " +x +" Y: " + y);
        ArrayList<Sprite> targets = this.getArrSprites(pt1, pt2, t, false);
        Sprite target = targets==null || targets.size()==0? null: targets.get(0);
        ArrayList<Sprite> sel = t.equals(getPlayerTeam()) ? arrSelected : arrSelected2;
        //System.out.println("Size: " + sel.size());

        if(target!=null){
            for(Sprite sprite: sel){
                sprite.setNavigationGoal(pt);
                sprite.setAttackGoal(target.getSpriteInfo());
            }
        }else{
            for(Sprite sprite: sel){
                sprite.setNavigationGoal(pt);
            }
        }
        //this.mouseSprite.handleEvnet(MouseEvent.RightClick, canvas, x, y, this.arrSelected);        
        //ge_instance  = this;
        //this.mouseSprite = new MouseSprite(mainview, minimap);
        //this.mainview.setupEventHandler(this);
        this.loadGameMap(this.mapPath);
        //this.aiButtonController = new ButtonController(this.getAITeam(), null); //no display device
        //this.ai = new AI(this.getAITeam(), this.aiButtonController);
    }
    
    public Team CheckWinner(){
        for(int i=0; i<=1; i++){
            Team team = this.clients.get(i).team;
            if(team.getBase().isDead()){
                return this.clients.get(1-i).team;
            }
        }
        return null;
    }
    
    public static void registerPlayer(Client client){
        if(clients.size()>=2){
            System.out.println("Game is full");
            return;
        }
        clients.add(client);
        
        System.out.println(client.team.getName() + " has joined.");
        if(clients.size()==2)
            ServerEngine.getServerInstance().initGame();
    }
    public static void deregisterPlayer(Client client){
        clients.remove(client);
        if(clients.size()<2){
            ServerEngine.getServerInstance().gameReady=false;
        }
    }

    
}
