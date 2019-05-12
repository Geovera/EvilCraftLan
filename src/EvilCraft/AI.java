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
package EvilCraft;

import java.util.ArrayList;

/**
 *
 * @author csc190
 */
public class AI {
    //-- data members 
    protected Team team;
    protected ButtonController btnController;
    
    //-- operations
    public AI(Team team, ButtonController btnController){
        this.team = team;
        this.btnController = btnController;
    }
    
    /**
     * Called by GameEngine.onTick() for every 150 ticks.
     * Make decision based on enemy info
     */
    protected int ticks = 0;
    public void update(){
        ticks++;
        //2. make the units
        if(ticks%50==0){
            this.btnController.spawnInfantry();
        }
        if(ticks%50==0){
            this.btnController.spawnTank();
        }
        if(ticks%100==0){
            this.btnController.spawnAircraft();
        }
        
        if(ticks%150!=0){
            return;
        }
        //1. get the data
        GameEngine ge = GameEngine.getInstance();
        TeamInfo enemyTeam = ge.getEnemyTeamInfo(this.team);
        
               
        //3. make half attack goals
        int nxtTarget = 0;
        ArrayList<SpriteInfo> arrEnemy = enemyTeam.getSpritesInfo();
        int half = this.team.getSprites().size()/2;
        for(int i=0; i<half; i++){
            Sprite sp = this.team.getSprites().get(i);
            SpriteInfo target = arrEnemy.get(nxtTarget);
            sp.setNavigationGoal(new Point(target.x, target.y));
            sp.setAttackGoal(target);
            nxtTarget = (nxtTarget+1)%arrEnemy.size();
        }
        
        //4. make rest attack base
        SpriteInfo ebase = enemyTeam.getBaseInfo();
        for(int i=half; i<this.team.getSprites().size(); i++){
            Sprite sp = this.team.getSprites().get(i);
            sp.setNavigationGoal(new Point(ebase.x, ebase.y));
            sp.setAttackGoal(ebase);
        }
    
}
}
