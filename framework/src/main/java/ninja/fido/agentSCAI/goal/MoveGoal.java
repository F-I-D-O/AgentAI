/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.Position;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class MoveGoal extends Goal {
	
	public static final int DEFAULT_MIN_DISTANCE_FROM_TARGET = 200;
	
	
	
	private final Position targetPosition;
	
	private final int minDistanceFromTarget;

	
	
	
	public Position getTargetPosition() {
		return targetPosition;
	}

	public int getMinDistanceFromTarget() {
		return minDistanceFromTarget;
	}
	
	
	
	

	public MoveGoal(GameAgent target, GoalOrder order, Position targetPosition) {
		super(target, order);
		this.targetPosition = targetPosition;
		minDistanceFromTarget = DEFAULT_MIN_DISTANCE_FROM_TARGET;
	}
	
	public MoveGoal(GameAgent target, GoalOrder order, Position targetPosition, int minDistanceFromTarget) {
		super(target, order);
		this.targetPosition = targetPosition;
		this.minDistanceFromTarget = minDistanceFromTarget;
	}

	@Override
	public boolean isCompleted() {
		return ((GameAgent) agent).getUnit().getPosition().getDistance(targetPosition) <= minDistanceFromTarget;
	}
	
}
