package com.fido.dp.base;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GameAgent;

public abstract class UnitActivity<A extends GameAgent, G extends Goal> extends Activity<A,G> {

    public UnitActivity(A unitAgent) {
        super(unitAgent);
    }

}
