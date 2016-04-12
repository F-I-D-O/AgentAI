/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import ninja.fido.agentai.activity.ASAPSquadAttackMove;
import ninja.fido.agentai.activity.NormalSquadAttackMove;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.modules.decisionMaking.DecisionTable;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.modules.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.ActivityGoal;
import ninja.fido.agentai.goal.SquadAttackMoveGoal;
import ninja.fido.agentai.goal.WaitGoal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class SquadCommander extends CommandAgent {

	public SquadCommander() {
		
	}
	
	

	@Override
	protected Activity chooseAction() {
		if(getGoal() instanceof SquadAttackMoveGoal){
			SquadAttackMoveGoal goal = getGoal();
			return new ASAPSquadAttackMove(this, goal.getAttackTarget());
		}
		else if(getGoal() instanceof WaitGoal){
			return new Wait(this);
		}
		
		// to remove later
		else if(getGoal() instanceof ActivityGoal){
			ActivityGoal activityGoal = getGoal();
			return activityGoal.getActivity();
		}
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(0.2, new ASAPSquadAttackMove());
		actionMap.put(1.0, new NormalSquadAttackMove());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(SquadAttackMoveGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
}
