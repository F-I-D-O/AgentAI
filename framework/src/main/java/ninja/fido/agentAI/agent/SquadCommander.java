/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.activity.ASAPSquadAttackMove;
import ninja.fido.agentAI.activity.NormalSquadAttackMove;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.ActivityGoal;
import ninja.fido.agentAI.goal.SquadAttackMoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;
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
	protected Activity chooseActivity() {
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
