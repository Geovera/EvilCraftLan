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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author csc190
 */
public class Dispatcher implements Runnable{
    
    private Socket dataSocket;
    private Socket echoSocket;
    
    private BufferedReader dataIn;
    private OutputStreamWriter dataOut;
    
    private BufferedReader echoIn;
    private OutputStreamWriter echoOut;
    
    private Client client;

    @Override
    public void run() {
        dispatch();
    }
    
    private void dispatch(){
        while(true){
            if(listen()){
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        Handler handle = new Handler(client);
                        if(handle.register())
                            handle.handle();
                        else
                            handle.disconnect();
                    }
                });
                t.start();
            }
        }
    }
    
    private boolean listen(){
        try{
            dataSocket = ServerEngine.dataServerSocket.accept();
            echoSocket = ServerEngine.echoServerSocket.accept();
            
            dataOut = new OutputStreamWriter(dataSocket.getOutputStream(), StandardCharsets.UTF_8);
            dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            
            echoOut = new OutputStreamWriter(echoSocket.getOutputStream(), StandardCharsets.UTF_8);
            echoIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            
            client = new Client(dataSocket, echoSocket, dataOut, dataIn, echoOut, echoIn);
            return true;
        }catch(IOException e){
            System.out.println("Client not accepted.");
            return false;
        }
    }
    
}
