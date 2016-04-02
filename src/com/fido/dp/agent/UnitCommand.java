/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.BaseLocationInfo;
import com.fido.dp.activity.terran.BBSAttack;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.BBSAttackGoal;
import com.fido.dp.info.EnemyBasesInfo;
import com.fido.dp.base.Info;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class UnitCommand extends CommandAgent {
	
	private ArrayList<BaseLocationInfo> enemyBases;

	
	
	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	

	public UnitCommand() {
		this.enemyBases = new ArrayList<>();
	}
	
	

    @Override
    protected Activity chooseAction() {
        if(getGoal() instanceof BBSAttackGoal){
			return new BBSAttack(this);
		}
		return null;
    }

	@Override
	protected void processInfo(Info info) {
		if(info instanceof EnemyBasesInfo){
			enemyBases = ((EnemyBasesInfo) info).getEnemyBases();
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return new BBSAttackGoal(this, null);
	}
	
	
    
}
