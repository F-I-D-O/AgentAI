/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.protoss;

import bwapi.Position;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.agent.unit.HighTemplar;
import ninja.fido.agentSCAI.agent.unit.Zealot;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.base.UniversalGoalOrder;
import ninja.fido.agentSCAI.demo.goal.GroupGuardGoal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.info.GuardOnPositionInfo;
import java.util.ArrayList;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author F.I.D.O.
 */
public class FormationTestSquadFormationIndividual 
		extends CommandActivity<SquadCommander,Goal,FormationTestSquadFormationIndividual>{
	
	private ArrayList<Zealot> zealotsOnPosition;
	
	private boolean zealotsOrdered;
	
	private Vector2D direction;

	
	
	
	public FormationTestSquadFormationIndividual() {
	}
	
	public FormationTestSquadFormationIndividual(SquadCommander agent) {
		super(agent);
		zealotsOnPosition = new ArrayList<>();
		zealotsOrdered = false; 
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof FormationTestSquadFormationIndividual;
	}

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		ArrayList<Zealot> zealots = getCommandedAgents(Zealot.class);
		HighTemplar highTemplar = getCommandedAgent(HighTemplar.class);
		
		if(!zealotsOrdered && zealots.size() > 8){
			for (Zealot zealot : zealots) {
				new UniversalGoalOrder(
						zealot, agent, new GroupGuardGoal(zealot, null, highTemplar, zealots)).issueOrder();
			}
			zealotsOrdered = true;
		}
		if(GameAPI.getFrameCount() % 350 == 0){
			direction = new Vector2D(Math.random(), Math.random());
		}
		if(zealotsOnPosition.size() >= zealots.size()){
			Position highTemplarNewPosition = transferPosition(highTemplar.getUnit().getPosition(), direction);
			new UniversalGoalOrder(highTemplar, agent, new MoveGoal(highTemplar, null, 
							highTemplarNewPosition, 5)).issueOrder();
			zealotsOnPosition = new ArrayList<>();
		}
	}

	@Override
	protected void init() {
		direction = new Vector2D(1, 0);
	}

	@Override
	protected void processInfo(Info info) {
		if(info instanceof GuardOnPositionInfo){
			zealotsOnPosition.add((Zealot) info.getSender());
		}
	}
	
	private Position transferPosition(Position oldPosition, Vector2D direction) {
		int length = 20;
		direction = direction.scalarMultiply(length);
		int newX = oldPosition.getX() + (int) direction.getX();
		int newY = oldPosition.getY() + (int) direction.getY();
		return new Position(newX, newY);
	}

	@Override
	public FormationTestSquadFormationIndividual create(SquadCommander agent, Goal goal) {
		return new FormationTestSquadFormationIndividual(agent);
	}
	
}
