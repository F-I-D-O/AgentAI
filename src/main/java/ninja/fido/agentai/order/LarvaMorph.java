/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import ninja.fido.agentai.Log;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.agent.unit.Larva;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GoalOrder;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaMorph extends GoalOrder{
	
	private final Larva.MorphOption morphOption;

	public LarvaMorph(Larva target, CommandAgent commandAgent, Larva.MorphOption morphOption) {
		super(target, commandAgent);
		this.morphOption = morphOption;
	}

	@Override
	protected void execute() {
		Larva larva = getTarget();
		try {
			larva.morph(morphOption);
		} 
		catch (ResourceDeficiencyException ex) {
			 Log.log(this, Level.SEVERE, "{1}: Don't have enough supply to morph - missing {1} (requested amount: {2}, "
					 + "current amount: {3}",
                    ex.getSpender(), ex.getResourceType(), ex.getAmount(), ex.getCurrentAmount());
			 ex.printStackTrace();
		}
	}
	
}
