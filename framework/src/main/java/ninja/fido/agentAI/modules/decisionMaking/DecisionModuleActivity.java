/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.modules.decisionMaking;

import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.Goal;

/**
 *
 * @author F.I.D.O.
 * @param <A> Agent
 * @param <G> Goal
 * @param <AC> Activity
 */
public interface DecisionModuleActivity<A extends Agent, G extends Goal, AC extends Activity> {
	
	public AC create(A agent, G goal);
	
	@Override
	public boolean equals(Object otherActivity);
}
