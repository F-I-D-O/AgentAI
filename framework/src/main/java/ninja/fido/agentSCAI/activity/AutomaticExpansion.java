/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.ExpansionCommand;
import ninja.fido.agentSCAI.agent.FullCommander;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Order;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.goal.AutomaticExpansionGoal;
import ninja.fido.agentSCAI.request.ExpansionInfoRequest;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticExpansion extends CommandActivity<ExpansionCommand,AutomaticExpansionGoal,AutomaticExpansion> 
		implements DecisionModuleActivity<ExpansionCommand, AutomaticExpansionGoal, AutomaticExpansion>{
	
	private UnitType expansionBuildingType;
	
	private boolean expansionRequestSended;

	
	
	
	public AutomaticExpansion() {
	}

	public AutomaticExpansion(ExpansionCommand agent, UnitType expansionBuilding) {
		super(agent);
		this.expansionBuildingType = expansionBuilding;
		expansionRequestSended = false;
	}

	public AutomaticExpansion(ExpansionCommand agent, AutomaticExpansionGoal goal) {
		super(agent);
		this.expansionBuildingType = goal.getExpansionBuildingType();
		expansionRequestSended = false;
	}
	
	
	

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof AutomaticExpansion;
	}

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		Worker worker;
		if(agent.getNextExpansionPosition() == null && !expansionRequestSended){
			new ExpansionInfoRequest(((FullCommander) GameAPI.getCommander()).explorationCommand, agent).send();
			expansionRequestSended = true;
		}
		else if(agent.getNextExpansionPosition() != null && (worker = agent.getWorker()) != null){
			agent.startExpansion(expansionBuildingType, worker);
			expansionRequestSended = false;
		}
	}

	@Override
	protected void init() {
		
	}
	
	

	@Override
	protected void handleCompletedOrder(Order order) {
		
	}

	@Override
	public AutomaticExpansion create(ExpansionCommand agent, AutomaticExpansionGoal goal) {
		return new AutomaticExpansion(agent, goal);
	}
	
	
}
