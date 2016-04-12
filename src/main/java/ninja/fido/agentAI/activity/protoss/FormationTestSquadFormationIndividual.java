/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.activity.protoss;

import bwapi.Position;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.unit.HighTemplar;
import ninja.fido.agentAI.agent.unit.Zealot;
import ninja.fido.agentAI.base.CommandActivity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.Info;
import ninja.fido.agentAI.base.UniversalGoalOrder;
import ninja.fido.agentAI.goal.GroupGuardGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.info.GuardOnPositionInfo;
import java.util.ArrayList;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author F.I.D.O.
 */
public class FormationTestSquadFormationIndividual extends CommandActivity<SquadCommander, Goal>{
	
	private ArrayList<Zealot> zealotsOnPosition;
	
	private boolean zealotsOrdered;
	
	private Vector2D direction;
	

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
		ArrayList<Zealot> zealots = agent.getCommandedAgents(Zealot.class);
		HighTemplar highTemplar = agent.getCommandedAgent(HighTemplar.class);
		
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
	
}
