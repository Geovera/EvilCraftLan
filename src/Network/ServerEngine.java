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
import EvilCraft.GameEngine;
import EvilCraft.Sprite;
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
    
    public static boolean firstPlayer;
    
    public volatile static ArrayList<Client> clients;
    
    private ArrayList<Sprite> delSpr;
    private ArrayList<Sprite> addSpr;
    
    public static ServerEngine getServerInstance(){return se_instance;}
    
    public ServerEngine(String mapPath, ICanvasDevice mainview, ICanvasDevice minimap, ICanvasDevice factoryPanel, ISoundDevice sound) {
        super(mapPath, mainview, minimap, factoryPanel, sound);
        gameReady=false;
        delSpr = new ArrayList<Sprite>();
        addSpr = new ArrayList<Sprite>();
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
        gameReady =true;
        Handler.echoMap(arrMapTiles);
    }
    
    @Override
    public void onTick(){
        if(!gameReady)
            return;
        
        arrSprites.stream().forEach(elem -> elem.update());
        
        for(Sprite s : delSpr){
            arrSprites.remove(s);
        }
        for(Sprite s : addSpr){
            arrSprites.add(s);
        }
        
        Handler.updateSprites();
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
    }

    
}
