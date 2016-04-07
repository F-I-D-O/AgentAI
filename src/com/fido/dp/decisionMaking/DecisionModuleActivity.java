/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 * @param <G>
 * @param <AC>
 */
public interface DecisionModuleActivity<A extends Agent, G extends Goal, AC extends Activity> {
	
	public AC create(A agent, G goal);
	
	@Override
	public boolean equals(Object otherActivity);
}
