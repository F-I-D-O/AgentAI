/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.zerg;

import com.fido.dp.agent.unit.Drone;
import com.fido.dp.agent.ZergCommander;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategy extends CommandActivity<ZergCommander, Goal>{
	
	private int targetNumberOfScouts;

	public OutbreakStrategy(ZergCommander agent) {
		super(agent);
		targetNumberOfScouts = 1;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == OutOfMemoryError.class;
	}

	@Override
	public void initialize(Goal goal) {
		targetNumberOfScouts = 1;
	}
	
	

	@Override
	protected void performAction() {
		List<Larva> larvas = agent.getSubordinateAgents(Larva.class);
		List<Drone> drones = agent.getSubordinateAgents(Drone.class);
		
		agent.detachSubordinateAgents(drones, agent.resourceCommand);
		agent.detachSubordinateAgents(larvas, agent.larvaCommand);
	}

	@Override
	protected void init() {
		List<Drone> drones = agent.getSubordinateAgents(Drone.class);
		
		if(!drones.isEmpty()){           
            for (Drone drone : drones) {
                if(agent.explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachSubordinateAgent(drone, agent.explorationCommand);
                }
            }
        }
	}
	
}
