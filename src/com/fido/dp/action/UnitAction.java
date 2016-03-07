package com.fido.dp.action;

import com.fido.dp.base.Agent;
import com.fido.dp.base.LeafAgent;

public abstract class UnitAction extends Action {

    public LeafAgent getUnitAgent() {
        return (LeafAgent) agent;
    }

    public UnitAction(LeafAgent unitAgent) {
        super(unitAgent);
    }

}
