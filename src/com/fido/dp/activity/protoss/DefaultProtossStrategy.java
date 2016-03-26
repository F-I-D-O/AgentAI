/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.protoss;

import com.fido.dp.agent.FullCommander;
import com.fido.dp.agent.unit.Probe;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultProtossStrategy extends CommandActivity<FullCommander, Goal>{
	
	private int targetNumberOfScouts;

	public DefaultProtossStrategy(FullCommander agent) {
		super(agent);
		targetNumberOfScouts = 1;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof DefaultProtossStrategy;
	}
	
	@Override
	public void initialize(Goal goal) {
		targetNumberOfScouts = 1;
	}

	@Override
	protected void performAction() {
		List<Probe> probes = agent.getSubordinateAgents(Probe.class);
		
		agent.detachSubordinateAgents(probes, agent.resourceCommand);
	}

	@Override
	protected void init() {
		List<Probe> probes = agent.getSubordinateAgents(Probe.class);
		
		if(!probes.isEmpty()){           
            for (Probe probe : probes) {
                if(agent.explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachSubordinateAgent(probe, agent.explorationCommand);
                }
            }
        }
	}
	
}
