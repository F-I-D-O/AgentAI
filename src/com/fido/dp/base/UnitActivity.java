package com.fido.dp.base;

public abstract class UnitActivity<A extends GameAgent, G extends Goal> extends Activity<A,G> {

	public UnitActivity() {
	}
	
    public UnitActivity(A unitAgent) {
        super(unitAgent);
    }
	
	public UnitActivity(A agent, G goal) {
		super(agent, goal);
	}

}
