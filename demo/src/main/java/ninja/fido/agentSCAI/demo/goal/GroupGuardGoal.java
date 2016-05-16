/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.agent.unit.Zealot;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class GroupGuardGoal extends Goal
{
	
	private final UnitAgent vip;
	
	private final ArrayList<Zealot> guards;

	public UnitAgent getVip() {
		return vip;
	}

	public ArrayList<Zealot> getGuards() {
		return guards;
	}
	
	
	
	
	

	public GroupGuardGoal(Zealot agent, GoalOrder order, UnitAgent vip, ArrayList<Zealot> guards) {
		super(agent, order);
		this.vip = vip;
		this.guards = guards;
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
