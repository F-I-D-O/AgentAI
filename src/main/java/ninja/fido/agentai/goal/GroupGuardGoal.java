/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.goal;

import ninja.fido.agentai.agent.unit.UnitAgent;
import ninja.fido.agentai.agent.unit.Zealot;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GoalOrder;
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
