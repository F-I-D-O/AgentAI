/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo.activity.protoss;

import bwapi.Color;
import bwapi.Position;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.unit.HighTemplar;
import ninja.fido.agentAI.agent.unit.Zealot;
import ninja.fido.agentAI.base.CommandActivity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.Order;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.order.MoveOrder;
import ninja.fido.agentAI.base.UniversalGoalOrder;
import java.util.ArrayList;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author F.I.D.O.
 */
public class FormationTestSquadFormation extends CommandActivity<SquadCommander, Goal>{
	
	private ArrayList<Position> zealotPositions;
	
	private boolean squadInFormation;
	
	private int numberOfZealotsInFormation;
	
	private ArrayList<Zealot> zealotsOnPosition;
	
	private boolean templarOnPosition;
	
	private Vector2D direction;

	public FormationTestSquadFormation(SquadCommander agent) {
		super(agent);
		squadInFormation = false;
		numberOfZealotsInFormation = 0;
		zealotsOnPosition = new ArrayList<>();
		templarOnPosition = true;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof FormationTestSquadFormation;
	}

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		List<Zealot> zealots = agent.getCommandedAgents(Zealot.class);
		HighTemplar highTemplar = agent.getCommandedAgent(HighTemplar.class);
		
		
		if(squadInFormation){
			if(GameAPI.getFrameCount() % 250 == 0){
				direction = new Vector2D(Math.random(), Math.random());
			}
			if(templarOnPosition){
				zealotsOnPosition = new ArrayList<>();
				
				Position highTemplarNewPosition = transferPosition(highTemplar.getUnit().getPosition(), direction);
				new UniversalGoalOrder(highTemplar, agent, new MoveGoal(highTemplar, null, 
							highTemplarNewPosition, 5)).issueOrder();
				
				computeZealotPositions(zealots, highTemplarNewPosition, direction);
				int i = 0;
				for (Zealot zealot : zealots) {
					if(!zealotsOnPosition.contains(zealot)){
						new UniversalGoalOrder(zealot, agent, new MoveGoal(zealot, null, zealotPositions.get(i), 5)).issueOrder();
					}
					GameAPI.getGame().drawCircleMap(zealotPositions.get(i), 5, Color.Blue, true);
					i++;
				}
				
				templarOnPosition = false;
			}
			
			
//			Vector2D direction = new Vector2D(1, 0);
//			if(GameAPI.getFrameCount() % 5 == 0){
//				for(int i = 0; i < zealotPositions.size(); i++){
//					zealotPositions.set(i, transferPosition(zealotPositions.get(i), direction));
//					new UniversalGoalOrder(zealots.get(i), agent, new MoveGoal(zealots.get(i), null, 
//							zealotPositions.get(i), 5)).issueOrder();
//				}
//				Position highTemplarNewPosition = transferPosition(highTemplar.getUnit().getPosition(), direction);
//				new UniversalGoalOrder(highTemplar, agent, new MoveGoal(highTemplar, null, 
//							highTemplarNewPosition, 5)).issueOrder();
//			}
		}
		else{
			computeZealotPositions(zealots, highTemplar.getUnit().getPosition(), direction);

			int i = 0;
			for (Zealot zealot : zealots) {
				if(!zealotsOnPosition.contains(zealot)){
					new UniversalGoalOrder(zealot, agent, 
							new MoveGoal(zealot, null, zealotPositions.get(i), 5)).issueOrder();
				}
				GameAPI.getGame().drawCircleMap(zealotPositions.get(i), 5, Color.Blue, true);
				i++;
			}
		}
	}

	@Override
	protected void init() {
		direction = new Vector2D(1, 0);
	}
	
	private void computeZealotPositions(List<Zealot> zealots, Position centerPosition, Vector2D direction){
		zealotPositions = new ArrayList<>();
		
		int count = 0;
		double angleBetweenUnits = Math.PI / (zealots.size() - 1);
		double length = (zealots.size() - 1) * 50;
		double radius = length / Math.PI;
		for (Zealot zealot : zealots) {
			
			double angleToRotate = Math.PI / 2 - count * angleBetweenUnits;
			double dirX = direction.getX() * Math.cos(angleToRotate) - direction.getY() * Math.sin(angleToRotate);
			double dirY = direction.getX() * Math.sin(angleToRotate) + direction.getY() * Math.cos(angleToRotate);
			Vector2D dirVector = new Vector2D(dirX, dirY);
			Vector2D finalVector = dirVector.normalize().scalarMultiply(radius);
			Position position = new Position(centerPosition.getX() + (int) finalVector.getX(),
					centerPosition.getY() + (int) finalVector.getY());
			zealotPositions.add(position);
			count++;
		}
	}

	@Override
	protected void handleCompletedOrder(Order order) {
		if(order instanceof MoveOrder || order instanceof UniversalGoalOrder){
			if(order.getTarget() instanceof Zealot){
				zealotsOnPosition.add(order.getTarget());
				if(!squadInFormation){
					numberOfZealotsInFormation++;
					List<Zealot> zealots = agent.getCommandedAgents(Zealot.class);
					if(numberOfZealotsInFormation > 5 && numberOfZealotsInFormation == zealots.size()){
						squadInFormation = true;
					}
				}
			}
			if(order.getTarget() instanceof HighTemplar){
				templarOnPosition = true;
			}
		}
	}

	private Position transferPosition(Position oldPosition, Vector2D direction) {
		int length = 10;
		direction = direction.scalarMultiply(length);
		int newX = oldPosition.getX() + (int) direction.getX();
		int newY = oldPosition.getY() + (int) direction.getY();
		return new Position(newX, newY);
	}
	
	
}
