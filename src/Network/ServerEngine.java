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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author csc190
 */
public class ServerEngine {
    
    public static ServerSocket dataServerSocket;
    public static ServerSocket echoServerSocket;
    
    public static boolean firstPlayer;
    
    public volatile static ArrayList<Client> clients;
    
    public static void main(String[] args){
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
    
    public static void registerPlayer(Client client){
        clients.add(client);
    }
    public static void deregisterPlayer(Client client){
        clients.remove(client);
    }
}
