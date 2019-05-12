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
import java.util.concurrent.TimeUnit;

/**
 *
 * @author csc190
 */
public class ClientEngine {
    
    Connection connection;
    
    public ClientEngine(Connection connection){
        this.connection = connection;
        update();
    }
    
    private void update(){
        while(true){
            try{
                TimeUnit.SECONDS.sleep(10);
            }
            catch(InterruptedException e){
                System.out.println("Yo no fue");
            }
            connection.echoPrint();
            
        }
    }
    
}
