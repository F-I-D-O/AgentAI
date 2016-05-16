/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentSCAI.agent.unit.Marine;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.modules.decisionStorage.StorableDecisionModuleActivity;
import ninja.fido.agentSCAI.goal.SquadAttackMoveGoal;
import ninja.fido.agentSCAI.order.AttackMoveOrder;
import java.util.List;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 */
public class ASAPSquadAttackMove extends SquadAttackMove<ASAPSquadAttackMove>
		implements StorableDecisionModuleActivity<SquadCommander, SquadAttackMoveGoal, ASAPSquadAttackMove>{

	public ASAPSquadAttackMove() {
	}

	public ASAPSquadAttackMove(SquadCommander agent, SquadAttackMoveGoal goal) {
		super(agent, goal);
	}
	
	
	
	public ASAPSquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent, attackTarget);
	}
	
	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		List<Marine> marines = getCommandedAgents(Marine.class);
		Unit unit;
		for (Marine marine : marines) {
			if(marine.isIdle()){
				new AttackMoveOrder(marine, agent, attackTarget).issueOrder();
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("ASAPSquadAttackMove");
		
		return parameter;
	}

	@Override
	public String getId() {
		return "ASAPSquadAttackMove";
	}

	@Override
	public ASAPSquadAttackMove create(SquadCommander agent, SquadAttackMoveGoal goal) {
		return new ASAPSquadAttackMove(agent, goal);
	}

	@Override
	public StorableDecisionModuleActivity getFromXml(Element activityElement) {
		return new ASAPSquadAttackMove();
	}
	
	
}
