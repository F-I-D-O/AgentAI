/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.decisionMaking.DecisionTable;
import ninja.fido.agentai.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.WaitGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class Overlord extends GameAgent{

	public Overlord(Unit unit) {
		super(unit);
		
		reasoningOn = true;
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
