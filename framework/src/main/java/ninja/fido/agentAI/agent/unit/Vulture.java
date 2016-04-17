/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import ninja.fido.agentAI.base.GameAgent;
import bwapi.Unit;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;

/**
 *
 * @author david_000
 */
public class Vulture extends GameAgent {

    public Vulture(Unit unit) {
        super(unit);
    }

    @Override
    protected Activity chooseActivity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	protected Goal getDefaultGoal() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    
}
