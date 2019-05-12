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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author csc190
 */
public class Client {
    public Team team;
    
    public Socket dataSocket;
    public Socket echoSocket;
    
    public BufferedReader dataIn;
    public OutputStreamWriter dataOut;
    
    public BufferedReader echoIn;
    public OutputStreamWriter echoOut;
    
    public Client(Socket dataSocket, Socket echoSocket, OutputStreamWriter dataOut, BufferedReader dataIn, OutputStreamWriter echoOut, BufferedReader echoIn){
        this.dataSocket = dataSocket;
        this.echoSocket = echoSocket;
        this.dataOut = dataOut;
        this.dataIn = dataIn;
        this.echoOut= echoOut;
        this.echoIn = echoIn;
        this.team = null;
    }
    
    public void close(){
        try{
        dataIn.close();
        dataOut.close();
        echoIn.close();
        echoOut.close();
        echoSocket.close();
        }catch(IOException e){}
    }
}
