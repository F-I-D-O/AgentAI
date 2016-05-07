/* 
 * AgentAI
 */
package ninja.fido.agentAI.base;

import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 * Universal class for goal order. It's useful when you just want to change agents goal and nothing more.
 * @author F.I.D.O.
 */
public class UniversalGoalOrder extends GoalOrder{
	
	/**
	 * New goal for target.
	 */
	private final Goal goal;

	/**
	 * Constructor.
	 * @param target Target of the order.
	 * @param commandAgent Command agent issuing the order.
	 * @param goal New goal for target.
	 * @throws ChainOfCommandViolationException 
	 */
	public UniversalGoalOrder(Agent target, CommandAgent commandAgent, Goal goal) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.goal = goal;
		goal.setOrder(this);
	}

	@Override
	protected void execute() {
		setGoal(goal);
	}
	
}
