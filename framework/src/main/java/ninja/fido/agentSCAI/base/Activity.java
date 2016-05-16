/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base;

import ninja.fido.agentSCAI.Log;
import java.util.logging.Level;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 * This class represent agent's activity. 
 * @author F.I.D.O.
 * @param <A> Agent type
 * @param <G> Goal type
 * @param <AC> Activity type
 */
public abstract class Activity<A extends Agent,G extends Goal, AC extends Activity> {
    
	/**
	 * This property reflects if this activity has already been initialized.
	 */
    private boolean isInitialized;
	
	/**
	 * Agent who performs this activity.
	 */
	protected final A agent;
    
	/**
	 * Parent activity.
	 */
    private Activity parrentActivity;
    
	/**
	 * Child activity.
	 */
    private Activity childActivity;
    
	/**
	 * Agent getter.
	 * @return Returns agent who performs this activity.
	 */
	public final A getAgent() {
		return agent;
	}

	
	
	
	/**
	 * Empty constructor. It should be only use to register the activity somewhere. Agent musn't ever choose activity
	 * created by this constructor!
	 */
	public Activity() {
		agent = null;
	}

	/**
	 * Standard constructor
	 * @param agent Agent who performs this activity.
	 */
	public Activity(A agent) {
		this.agent = agent;
	}

	
	
	
	@Override
	public abstract boolean equals(Object obj);

	/**
	 * Factory method, creates instance of itself. It's called on instance created by empty constructor and it 
	 * should return new instance created by standard constructor.
	 * @param agent Agent who performs this activity.
	 * @param goal Agent's current goal.
	 * @return Returns new instance of itself.
	 */
	public abstract AC create(A agent, G goal);
	
	
	/**
	 * Action logic. This method is called every frame.
	 * @throws ChainOfCommandViolationException 
	 */
	protected abstract void performAction() throws ChainOfCommandViolationException;
	
	/**
	 * Acitvity initializatin. This method is called on first frame.
	 * @throws ChainOfCommandViolationException 
	 */
	protected abstract void init() throws ChainOfCommandViolationException;
	
	/**
	 * This method finishes activity and calls parent activity if it exists or agent if it is not. If your activity 
	 * should be finished, this is the rigt place.
	 */
	protected final void finish(){
        if(parrentActivity != null){
            parrentActivity.onChildActivityFinish(this);
			parrentActivity.childActivity = null;
        }
        else{
            agent.onActivityFinish(this);
        }
    }
	
	/**
	 * If something gets wrong in your activity that cannot be fixed, it should call this method.
	 * @param reason Reason of the failure
	 */
	protected final void fail(String reason){
        agent.onActionFailed(this, reason);
    }
	
	/**
	 * This method is called when child activity is finished.
	 * @param activity Child activity.
	 */
	protected void onChildActivityFinish(Activity activity) {
        Log.log(this, Level.FINE, "{0}: Child action finished: {1}", this.getClass(), childActivity);
//        performAction(); not needet, because frame rate is high enough
    }
	
	/**
	 * Runs child activity.
	 * @param childActivity Child activity. 
	 */
	protected final void runChildActivity(Activity childActivity){
		try {
			if(!childActivity.equals(this.childActivity)){
				Log.log(this, Level.FINE, "{0}: Child action replaced. Old: {1}, new: {2}", this.getClass(),
						this.childActivity, childActivity.getClass());
				this.childActivity = childActivity;
				childActivity.parrentActivity = this;
			}
			Log.log(this, Level.FINE, "{0}: Running child action: {1}", this.getClass(), childActivity);
			this.childActivity.run();
		} catch (ChainOfCommandViolationException ex) {
			ex.printStackTrace();
			Log.log(this, Level.WARNING, "Running child activity resulted in ChainOfCommandViolationException."
					+ "Probably it's beacause chain of command changed while running this activity."
					+ " Agent: {2}, Activity {0}, Child Activity {1}, Command Agent: {3}", this.getClass(), 
					childActivity, agent.getClass(), agent.getCommandAgent().getClass());
		}
    }
	
	/**
	 * Called by agent's info queue when it is processed.
	 * @param info Info object.
	 */
	protected void processInfo(Info info) {
		Log.log(this, Level.FINE, "{0}: Info received: {1}", this.getClass(), info.getClass());
	}
	
	/**
	 * Called by agent when new agent is added under it's direct command.
	 * @param commandedAgent New commanded agent.
	 */
	protected void onCommandedAgentAdded(Agent commandedAgent) {
		Log.log(this, Level.FINE, "{0}: Commanded agent added: {1}", this.getClass(), commandedAgent.getClass());
	}
	
	/**
	 * Returns Amount of gas owned by agent who runs this action.
	 * @return Returns amount of gas owned by agent who runs this action.
	 */
	protected int getOwnedGas(){
        return agent.getOwnedGas();
    } 
    
	/**
	 * Returns Amount of mineral owned by agent who runs this action.
	 * @return Returns amount of mineral owned by agent who runs this action.
	 */
    protected int getOwnedMinerals(){
        return agent.getOwnedMinerals();
    }
	
	/**
	 * Returns Amount of supply owned by agent who runs this action.
	 * @return Returns amount of supply owned by agent who runs this action.
	 */
	protected int getOwnedSupply(){
        return agent.getOwnedSupply();
    }
	
	/**
	 * Gives resource to oher agent.
	 * @param receiver Receiver of the recource.
	 * @param material Material.
	 * @param amount Resource amount.
	 * @throws ResourceDeficiencyException 
	 */
	protected void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException{
		agent.giveResource(receiver, material, amount);
	}

	
	
	/**
	 * Main activity method. This method is called automaticaly by framework.
	 * @throws ChainOfCommandViolationException 
	 */   
    final void run() throws ChainOfCommandViolationException{
        Log.log(this, Level.FINE, "{0}: run() START", this.getClass());
        if(childActivity != null){
            childActivity.run();
        }
        else{
            if(!isInitialized){
                init();
				isInitialized = true;
            }
            performAction();
        }
        Log.log(this, Level.FINE, "{0}: run() END", this.getClass());
    }
}
