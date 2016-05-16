/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.agent.unit.Larva;
import ninja.fido.agentSCAI.base.CommandAgent;
import java.util.logging.Level;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaMorph extends GoalOrder<Larva>{
	
	private final Larva.MorphOption morphOption;

	public LarvaMorph(Larva target, CommandAgent commandAgent, Larva.MorphOption morphOption)
			throws ChainOfCommandViolationException {
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
