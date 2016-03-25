package com.fido.dp.action;

import com.fido.dp.base.Action;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;

public abstract class UnitAction<A extends UnitAgent, G extends Goal> extends Action<A,G> {

    public UnitAction(A unitAgent) {
        super(unitAgent);
    }

}
