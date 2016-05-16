/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.decisionMaking;

import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;

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
