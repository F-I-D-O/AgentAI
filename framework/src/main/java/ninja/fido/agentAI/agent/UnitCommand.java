/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.BaseLocationInfo;
import ninja.fido.agentAI.activity.terran.BBSAttack;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.BBSAttackGoal;
import ninja.fido.agentAI.info.EnemyBasesInfo;
import ninja.fido.agentAI.base.Info;
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
