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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csc190
 */
public class Connection {
    
    public boolean connectionStatus;
    
    public Client client;
    
    public void connect(){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Socket dataSocket = new Socket(RefStrings.SERVER_IP, RefStrings.DATA_PORT);
                    Socket echoSocket = new Socket(RefStrings.SERVER_IP, RefStrings.ECHO_PORT);
                    
                    PrintWriter dataOut = new PrintWriter(dataSocket.getOutputStream());
                    Scanner dataIn = new Scanner(dataSocket.getInputStream());
                    
                    PrintWriter echoOut = new PrintWriter(echoSocket.getOutputStream());
                    Scanner echoIn = new Scanner(echoSocket.getInputStream());
                    
                    client = new Client(dataSocket, echoSocket, dataOut, dataIn, echoOut, echoIn);
                    
                    connectionStatus = true;
                    goOnline();

                } catch (Exception ex) {
                    System.out.print("Connection Error");
                    System.exit(0);
                }
            }
        });
        t.start();
    }
    
    private void goOnline(){
        if(connectionStatus){
            register();
            echoListen();
        }
    }
    
    private void register(){
        Random rand = new Random();
        client.dataOut.println(rand.nextInt(1000));
        client.dataOut.flush();
    }
    
    public void echoPrint(){
        client.dataOut.println(RefStrings.CMD_MADRE);
        client.dataOut.flush();
    }
    
    private void echoListen(){
        Thread t = new Thread(new Runnable(){
           @Override
           public void run(){
               try{
                   while(true){
                       String cmd = client.echoIn.nextLine();
                       if(cmd.equals(RefStrings.CMD_UPDATESPRITES)){
                           System.out.print("Funciona casi");
                       }else if(cmd.equals(RefStrings.CMD_REGISTERPLAYER)){
                           System.out.println("Un boludo entro!");
                       }
                   }
               }
               catch(Exception e){
                   e.printStackTrace();;
               }
               finally{
                   
               }
           }
        });
        t.start();
    }
    
}
