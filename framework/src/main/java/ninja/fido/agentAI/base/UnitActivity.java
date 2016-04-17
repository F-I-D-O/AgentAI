package ninja.fido.agentAI.base;

public abstract class UnitActivity<A extends GameAgent, G extends Goal, AC extends UnitActivity> 
		extends Activity<A,G,AC> {

	public UnitActivity() {
	}
	
    public UnitActivity(A unitAgent) {
        super(unitAgent);
    }


}
