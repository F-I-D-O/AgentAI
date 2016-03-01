package com.fido.dp.action;

import com.fido.dp.agent.Agent;
import com.fido.dp.agent.LeafAgent;

public abstract class UnitAction extends Action {

    public LeafAgent getUnitAgent() {
        return (LeafAgent) agent;
    }

    public UnitAction(LeafAgent unitAgent) {
        super(unitAgent);
    }

}
