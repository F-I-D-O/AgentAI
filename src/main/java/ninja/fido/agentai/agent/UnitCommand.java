/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.activity.terran.BBSAttack;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.BBSAttackGoal;
import ninja.fido.agentai.info.EnemyBasesInfo;
import ninja.fido.agentai.base.Info;
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
