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

import EvilCraft.Team;

/**
 *
 * @author csc190
 */
class Handler {
    
    private Client client;
    
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
                String cmd = client.dataIn.nextLine();        
                if(cmd.equals(RefStrings.CMD_MADRE)){
                    System.out.println(cmd);
                }
            }
        }catch(Exception e){}
        finally{
            disconnect();
        }
    }
    
    public boolean register(){
        String num = client.dataIn.nextLine();
        Team team;
        
        if(!ServerEngine.firstPlayer){
            team = new Team(10000, "Human");
            ServerEngine.firstPlayer=true;
        }
        else
            team = new Team(10000, "Persona");
        
        client.team = team;
        ServerEngine.registerPlayer(client);
        
        for(Client other : ServerEngine.clients){
            if(!other.team.getName().equals(client.team.getName()))
            {
                other.echoOut.println(RefStrings.CMD_REGISTERPLAYER);
                other.echoOut.flush();
            }
        }
        
        return true;
    }
    
    public void disconnect(){
        try{
            for(Client other : ServerEngine.clients){
                if(!other.team.getName().equals(client.team.getName())){
                    other.echoOut.println("Se fue a la mrd" + client.team.getName());
                }
            }
            client.close();
            
        }catch(Exception e){}
        finally{
            System.out.println(client.team.getName() +" has disconnected");
            
            ServerEngine.deregisterPlayer(client);
        }
    }
    
}
