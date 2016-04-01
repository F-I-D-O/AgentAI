/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import bwapi.PositionOrUnit;
import bwapi.UnitCommand;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GameAgent;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMove extends Activity<GameAgent,Goal>{
	
	private final Position target;
	
	int counter = 0;

	public AttackMove(GameAgent agent, Position target) {
		super(agent);
		this.target = target;
	}

	@Override
	protected void performAction() {
//		counter++;
//		if(agent.getUnit().canMove()){
//			boolean ok = agent.getUnit().move(new Position(0, 0));
//		}
//		boolean ok3 = agent.getUnit().canMove();
//		boolean ok = agent.getUnit().move(new Position(0, 0));
//		boolean ok2 = ok;
		
	}

	@Override
	protected void init() {
		agent.getUnit().attack(target);
		UnitCommand moveCommand = UnitCommand.attack(agent.getUnit(), new PositionOrUnit(target));
		GameAPI.issueCommand(agent, moveCommand);
////		agent.getUnit().move(target);
//		boolean ok3 = agent.getUnit().canMove();
////		boolean ok = agent.getUnit().move(new Position(0, 0));
//		boolean ok2 = ok3;
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
		final AttackMove other = (AttackMove) obj;
		if (!Objects.equals(this.target, other.target)) {
			return false;
		}
		return true;
	}
	
	
	
}
