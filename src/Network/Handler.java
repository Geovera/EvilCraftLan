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

import EvilCraft.Sprite;
import EvilCraft.StaticObject;
import EvilCraft.Tank;
import EvilCraft.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author csc190
 */
public class Handler {
    
    private Client client;
    static ArrayJsonConverter converter = new ArrayJsonConverter();

    
    public Handler(Client client){
        this.client=client;
    }
    
    public void handle(){
        Thread t= new Thread(new Runnable(){
            @Override
            public void run(){
                handleUpdate();
            }
        });
        t.start();
    }
    
    private void handleUpdate(){
        try{
            while(true){
                String cmd = client.dataIn.readLine();      
                cmd = cmd==null?"":cmd;
                if(cmd.equals(RefStrings.CMD_MADRE)){
                    System.out.println(cmd);
                }else if(cmd.equals(RefStrings.CMD_BUTTONCLICK)){
                    int x = client.dataIn.read();
                    int y = client.dataIn.read();
                    ServerEngine.getServerInstance().purchaseRequest(client.team, x, y);
                }else if(cmd.equals(RefStrings.CMD_REGION)){
                    int x1 = client.dataIn.read();
                    int y1 = client.dataIn.read();
                    int x2 = client.dataIn.read();
                    int y2 = client.dataIn.read();
                    ServerEngine.getServerInstance().regionSelect(client.team, x1, y1, x2, y2);
                }else if(cmd.equals(RefStrings.CMD_RIGHTCLICK)){
                    int x = client.dataIn.read();
                    int y = client.dataIn.read();
                    ServerEngine.getServerInstance().rightClick(client.team, x, y);
                }
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            disconnect();
        }
    }
    
    public boolean register(){

        try{
        String num = client.dataIn.readLine();

        }catch(Exception e){System.out.println("Fuck you");
        e.printStackTrace();}
        
        Team team;
        if(!ServerEngine.firstPlayer){
            team = new Team(10000, "Human");
            ServerEngine.firstPlayer=true;
        }
        else
            team = new Team(10000, "Persona");
        
        client.team = team;

        ServerEngine.registerPlayer(client);
        
        try{
            for(Client other : ServerEngine.clients){
                if(!other.team.getName().equals(client.team.getName()))
                {
                    other.echoOut.write(RefStrings.CMD_REGISTERPLAYER + "\r\n");
                    other.echoOut.flush();
                }
            }
        }catch(Exception e){
            System.out.println("Couldn't post players");
        }
        
        return true;
    }
    
    public void disconnect(){
        try{
            for(Client other : ServerEngine.clients){
                if(!other.team.getName().equals(client.team.getName())){
                    other.echoOut.write(RefStrings.CMD_DEREGISTERPLAYER+ "\r\n");
                    other.echoOut.write("Se fue a la mrd: " + client.team.getName()+"\r\n");
                    other.echoOut.flush();
                }
            }
            client.close();
            
        }catch(Exception e){}
        finally{
            System.out.println(client.team.getName() +" has disconnected");
            
            ServerEngine.deregisterPlayer(client);
        }
    }
    
    public static void echoMap(ArrayList<StaticObject> map){
        try{
            String json =converter.convertToJson(map, ArrayJsonConverter.ArrayType.STATIC);
            for(Client cl : ServerEngine.clients){
                cl.echoOut.write(RefStrings.CMD_STARTMAP + "\r\n");
                cl.echoOut.write(json+"\r\n");
                cl.echoOut.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void purchaseAccept(String teamName, String type, int nowCash){
        try{
            Client cl = teamName.equals("Human") ? ServerEngine.clients.get(0) : ServerEngine.clients.get(1);
            cl.echoOut.write(RefStrings.CMD_PURCHASEACCEPTED+ "\r\n");
            cl.echoOut.write(nowCash);
            cl.echoOut.write(type + "\r\n");
            cl.echoOut.flush();
        }catch(Exception e){}
    }
    
    public static void gameFinish(Client cl, String won){
        try{
            cl.echoOut.write(RefStrings.CMD_GAMEFINISH + "\r\n");
            cl.echoOut.write(won + "\r\n");
            cl.echoOut.flush();
        }catch(Exception e){}
    }
    
    public static void updateSprites(ArrayList<Sprite> arrSprite){
        try{
            String json =converter.convertToJson(arrSprite, ArrayJsonConverter.ArrayType.SPRITE);
            //System.out.println(json);
            for(Client cl : ServerEngine.clients){
                cl.echoOut.write(RefStrings.CMD_UPDATESPRITES + "\r\n");
                cl.echoOut.write(json+"\r\n");
                cl.echoOut.flush();
            }
        }catch(Exception e){
            //e.printStackTrace();
            //System.exit(1);
        }
    }
    
    
}
