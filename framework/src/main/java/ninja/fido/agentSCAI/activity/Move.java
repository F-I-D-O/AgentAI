/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.activity;

import ninja.fido.agentSCAI.base.UnitActivity;
import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.goal.MoveGoal;
import java.util.Objects;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class Move extends UnitActivity<UnitAgent,MoveGoal,Move>
		implements DecisionModuleActivity<UnitAgent, MoveGoal, Activity>{
	
	public static final int DEFAULT_MAX_DISTANCE_FROM_TARGET = 200;
    
    private Position target;
    
    private int maxDistanceFromTarget;
    
    private boolean onMove;

    
    
    
    public Position getTarget() {
        return target;
    }

	
	
	
	
	public Move() {
	}

    public Move(UnitAgent unitAgent, Position target) {
        super(unitAgent);
        this.target = target;
        maxDistanceFromTarget = DEFAULT_MAX_DISTANCE_FROM_TARGET;
        onMove = false;
    }
	
	 public Move(UnitAgent unitAgent, Position target, int maxDistanceFromTarget) {
        super(unitAgent);
        this.target = target;
        this.maxDistanceFromTarget = maxDistanceFromTarget;
        onMove = false;
    }

	public Move(UnitAgent agent, MoveGoal goal) {
		super(agent);
		this.maxDistanceFromTarget = goal.getMinDistanceFromTarget();
		this.target = goal.getTargetPosition();
	}
	 
	 

    @Override
    public void performAction() {
        Unit unit = agent.getUnit();
		if(onMove){
			if(agent.isIdle()){
				fail("Agent stuck");
				if(GameAPI.getFrameCount() % (int) (Math.random() * 4 + 2) == 0){
					onMove = false;
				}
			}
		}
        else{
            if(!target.isValid()){
                fail("Invalid target");
            }
			if(!unit.hasPath(target)){
				fail("Unit cannot reach target");
			}
			
            Log.log(this, Level.FINE, "{0}: {1} On position: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), agent.getUnit().getPosition());
            Log.log(this, Level.FINE, "{0}: {1} target position: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), target);
//            unit.move(target);
			agent.move(target);
            
            onMove = true;
        }
        if(unit.getPosition().getDistance(target) <= maxDistanceFromTarget){
            Log.log(this, Level.FINE, "{0}: {1} Reached target: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), target);
            unit.stop();
            finish();
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
        final Move other = (Move) obj;
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxDistanceFromTarget) != Double.doubleToLongBits(other.maxDistanceFromTarget)) {
            return false;
        }
        return true;
    }
	
	

	@Override
	protected void init() {
		
	}

	@Override
	public Move create(UnitAgent agent, MoveGoal goal) {
		return new Move(agent, goal);
	}
    
    
    
}
