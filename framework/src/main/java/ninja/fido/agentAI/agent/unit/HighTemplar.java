/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class HighTemplar extends UnitAgent{

	public HighTemplar(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());
		defaultActivityMap.put(MoveGoal.class, new Move());

		return defaultActivityMap;
	}
	

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
