/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.protoss;

import ninja.fido.agentSCAI.agent.FullCommander;
import ninja.fido.agentSCAI.agent.unit.Probe;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultProtossStrategy extends CommandActivity<FullCommander, Goal, DefaultProtossStrategy> 
		implements DecisionModuleActivity<FullCommander, Goal, DefaultProtossStrategy>{
	
	private int targetNumberOfScouts;

	
	
	
	public DefaultProtossStrategy() {
	}

	public DefaultProtossStrategy(FullCommander agent) {
		super(agent);
		targetNumberOfScouts = 1;
	}	
	
	

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof DefaultProtossStrategy;
	}

	@Override
	protected void performAction() {
		List<Probe> probes = getCommandedAgents(Probe.class);
		
		detachCommandedAgents(probes, agent.resourceCommand);
	}

	@Override
	protected void init() {
		List<Probe> probes = getCommandedAgents(Probe.class);
		
		if(!probes.isEmpty()){           
            for (Probe probe : probes) {
                if(agent.explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    detachCommandedAgent(probe, agent.explorationCommand);
                }
            }
        }
	}

	@Override
	public DefaultProtossStrategy create(FullCommander agent, Goal goal) {
		return new DefaultProtossStrategy(agent);
	}
	
}
