/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.Position;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.agent.Scout;
import ninja.fido.agentSCAI.agent.ExplorationCommand;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.order.ExploreBaseLocationOrder;
import ninja.fido.agentSCAI.order.MoveOrder;
import java.util.ArrayList;
import java.util.logging.Level;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author david_000
 * @param <A> Agent
 */
public class StrategicExploration<A extends ExplorationCommand> extends CommandActivity<A,Goal,StrategicExploration> {
    
    private final ArrayList<Scout> scouts;

	
	
	
	public StrategicExploration() {
		this.scouts = null;
	}

    public StrategicExploration(A agent) {
        super(agent);
        scouts = new ArrayList<>();
       
    }

    @Override
    public void performAction() throws ChainOfCommandViolationException {
//        Log.log(this, Level.FINE, "{0}:{1} Number of subordinate Agents: {2}", this.getClass(), getAgent().getClass(),
//                ((CommandAgent) getAgent()).getCommandedAgents().size());
		for (Agent commandedAgent :  getCommandedAgents()) {
			if(commandedAgent instanceof Scout && !scouts.contains(commandedAgent)){
				scouts.add((Scout) commandedAgent);
			}
        }
        
        Log.log(this, Level.FINE, "{0}: Number of scouts: {1}", this, scouts.size());
		
        for (Scout scout : scouts) {
			if(!isCommandedAgentOccupied((Agent) scout)){
				Position target = null;
				for (BaseLocationInfo baseInfo : agent.getBaseLocations()) {
					if(baseInfo.isStartLocation() && !baseInfo.isExpplored() && !baseInfo.isExplorationInProgress() 
							&& !baseInfo.isOurBase()){
						target = baseInfo.getPosition(); 
						baseInfo.setExplorationInProgress(true);
						new ExploreBaseLocationOrder((Agent) scout, this.getAgent(), target).issueOrder();
						break;
					}
				}
				
				// if there are no unexplored bases
				if(target == null && scout.getPosition().getDistance(GameAPI.getStartBasePosition()) 
						> Move.DEFAULT_MAX_DISTANCE_FROM_TARGET){
					new MoveOrder((UnitAgent) scout, agent, GameAPI.getStartBasePosition()).issueOrder();
				}
			}
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StrategicExploration other = (StrategicExploration) obj;
        return true;
    }

	@Override
	protected void init() {
		
	}

	@Override
	public StrategicExploration create(A agent, Goal goal) {
		return new StrategicExploration(agent);
	}
    
    
    
}
