/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.protoss;

import ninja.fido.agentai.agent.FullCommander;
import ninja.fido.agentai.agent.unit.Probe;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.modules.decisionMaking.DecisionModuleActivity;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultProtossStrategy extends CommandActivity<FullCommander, Goal> 
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
		List<Probe> probes = agent.getCommandedAgents(Probe.class);
		
		agent.detachCommandedAgents(probes, agent.resourceCommand);
	}

	@Override
	protected void init() {
		List<Probe> probes = agent.getCommandedAgents(Probe.class);
		
		if(!probes.isEmpty()){           
            for (Probe probe : probes) {
                if(agent.explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachCommandedAgent(probe, agent.explorationCommand);
                }
            }
        }
	}

	@Override
	public DefaultProtossStrategy create(FullCommander agent, Goal goal) {
		return new DefaultProtossStrategy(agent);
	}
	
}
