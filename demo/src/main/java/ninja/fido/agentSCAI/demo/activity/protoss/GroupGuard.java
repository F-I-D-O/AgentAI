/* 
 * AgentAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.protoss;

import bwapi.Position;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.agent.unit.Zealot;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.UnitActivity;
import ninja.fido.agentSCAI.demo.goal.GroupGuardGoal;
import ninja.fido.agentAI.base.Info;
import ninja.fido.agentAI.info.GuardOnPositionInfo;
import ninja.fido.agentAI.info.PositionChosenInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author F.I.D.O.
 */
public class GroupGuard extends UnitActivity<Zealot,GroupGuardGoal,GroupGuard>{
	
	private final UnitAgent vip;
	
	private final ArrayList<Zealot> guards;
	
	private ArrayList<Position> zealotPositions;
	
	private ArrayList<Position> chosenPositions;
	
	private ArrayList<Zealot> zealotsOnMove;
	
	private Vector2D direction;
	
	private boolean onMove;
	
	private boolean inFormation;
	
	private Position lastVipPosition;
	
	private int formationPosition;

	
	
	public GroupGuard() {
		this.vip = null;
		this.guards = null;
	}
	
	

	public GroupGuard(Zealot unitAgent, GroupGuardGoal goal) {
		super(unitAgent);
		this.vip = goal.getVip();
		this.guards = goal.getGuards();
		onMove = false;
		inFormation = false;
		chosenPositions = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GroupGuard other = (GroupGuard) obj;
		if (!Objects.equals(this.vip, other.vip)) {
			return false;
		}
		return true;
	}

	

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		if(zealotPositions == null 
				|| (!vip.getUnit().getPosition().equals(lastVipPosition) && GameAPI.getFrameCount() % 10 == 0)){
			computeZealotPositions(guards, vip.getUnit().getPosition(), direction);
			onMove = false;
			zealotsOnMove = new ArrayList<>();
			
			if(lastVipPosition != null && !vip.getUnit().getPosition().equals(lastVipPosition)){
				direction = new Vector2D(vip.getUnit().getPosition().getX() - lastVipPosition.getX(), 
						vip.getUnit().getPosition().getY() - lastVipPosition.getY());
			}
			lastVipPosition = vip.getUnit().getPosition();
		}
		if(!onMove){
			Zealot chosenZealot = null;
			if(!inFormation){
				for (Zealot guard : guards) {
					if(!zealotsOnMove.contains(guard)){
						if(chosenZealot == null || chosenZealot.getUnit().getDistance(vip.getUnit())
								< guard.getUnit().getDistance(vip.getUnit())){
							chosenZealot = guard;
						}
					}
				}
				zealotsOnMove.add(chosenZealot);
			}
			if(inFormation || chosenZealot == agent){
				Position chosenPosition = null;
				if(inFormation){
					chosenPosition = zealotPositions.get(formationPosition);
				}
				else{
					int positionIndex = 0;
					for (Position position : zealotPositions) {
						if(!chosenPositions.contains(position) && (chosenPosition == null 
								|| chosenPosition.getDistance(agent.getUnit())
										> position.getDistance(agent.getUnit()))){
							chosenPosition = position;
							formationPosition = positionIndex;
						}
						positionIndex++;
					}
				}
				runChildActivity(new Move(agent, chosenPosition, 5));
				
				for (Zealot guard : guards) {
					if(guard != agent){
						new PositionChosenInfo(guard, agent, chosenPosition).send();
					}
				}
				
				chosenPositions.add(chosenPosition);
				onMove = true;
				inFormation = true;
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
	protected void processInfo(Info info) {
		if(info instanceof PositionChosenInfo && !inFormation){
			if(zealotPositions == null){
				info.send();
			}
			else{
				PositionChosenInfo positionChosenInfo = (PositionChosenInfo) info;
				chosenPositions.add(positionChosenInfo.getChosenPosition());
				zealotsOnMove.add((Zealot) info.getSender());
			}
		}
	}

	@Override
	protected void onChildActivityFinish(Activity activity) {
		if(lastVipPosition == null || vip.getUnit().getPosition().equals(lastVipPosition)){
			new GuardOnPositionInfo(agent.getCommandAgent(), agent).send();
		}
	}

	@Override
	public GroupGuard create(Zealot agent, GroupGuardGoal goal) {
		return new GroupGuard(agent, goal);
	}
	
	
	
	
}
