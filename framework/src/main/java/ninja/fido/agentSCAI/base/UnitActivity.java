/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base;

/**
 * Game unit or building activity.
 * @author david
 * @param <A> Agent type.
 * @param <G> Goal type.
 * @param <AC> Unit activity type.
 */
public abstract class UnitActivity<A extends GameAgent, G extends Goal, AC extends UnitActivity> 
		extends Activity<A,G,AC> {

	/**
	 * Empty constructor.
	 */
	public UnitActivity() {
	}
	
	/**
	 * Constructor.
	 * @param unitAgent Agent.
	 */
    public UnitActivity(A unitAgent) {
        super(unitAgent);
    }


}
