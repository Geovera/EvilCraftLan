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

import EvilCraft.Infantry;
import EvilCraft.Sprite;
import EvilCraft.StaticObject;
import EvilCraft.Tank;
import EvilCraft.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
    private ClientEngine ce;
    Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Sprite.class, new InterfaceAdapter()).create();
    Type listOfSprite = new TypeToken<List<Sprite>>(){}.getType();
    Type listOfStatic = new TypeToken<List<StaticObject>>(){}.getType();

    public void setClientEngine(ClientEngine ce){
        this.ce = ce;
    }
    
    public Client client;

    public void connect() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket dataSocket = new Socket(RefStrings.SERVER_IP, RefStrings.DATA_PORT);
                    Socket echoSocket = new Socket(RefStrings.SERVER_IP, RefStrings.ECHO_PORT);

                    OutputStreamWriter dataOut = new OutputStreamWriter(dataSocket.getOutputStream(), StandardCharsets.UTF_8);
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));

                    OutputStreamWriter echoOut = new OutputStreamWriter(echoSocket.getOutputStream(), StandardCharsets.UTF_8);
                    BufferedReader echoIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

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

    private void goOnline() {
        if (connectionStatus) {
            register();
            echoListen();
        }
    }

    private void register() {
        try {
            client.dataOut.write("PLEASE\r\n");
            client.dataOut.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void echoPrint() {
        try {
            client.dataOut.write(RefStrings.CMD_MADRE);
            client.dataOut.flush();
        } catch (Exception e) {
            System.out.println("Couldn't echo Print");
        }
    }

    private void echoListen() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String cmd = client.echoIn.readLine();
                        if (cmd.equals(RefStrings.CMD_UPDATESPRITES)) {
                            //System.out.print("Funciona casi");
                        } else if (cmd.equals(RefStrings.CMD_REGISTERPLAYER)) {
                            System.out.println("Un boludo entro!");
                        }else if (cmd.equals(RefStrings.CMD_DEREGISTERPLAYER)){
                            System.out.println(client.echoIn.readLine());
                        }else if(cmd.equals(RefStrings.CMD_STARTMAP)){
                            System.out.println("yes");
                            String json = (client.echoIn.readLine().toString());
                            //System.out.println(json);
                            ArrayList<StaticObject> jt = gson.fromJson(json, listOfStatic);
                            ce.changeArrStatic(jt);
                            ce.initGame();
                            
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();;
                } finally {

                }
            }
        });
        t.start();
    }

}
