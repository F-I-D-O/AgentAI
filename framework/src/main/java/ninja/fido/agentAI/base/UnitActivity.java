package ninja.fido.agentAI.base;

public abstract class UnitActivity<A extends GameAgent, G extends Goal> extends Activity<A,G> {

	public UnitActivity() {
	}
	
    public UnitActivity(A unitAgent) {
        super(unitAgent);
    }


}
