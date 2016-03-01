package com.fido.dp.agent;

import bwapi.Unit;

public abstract class LeafAgent extends Agent {

    protected Unit unit;

    public boolean isIdle() {
        return unit.isIdle();
    }

    public Unit getUnit() {
        return unit;
    }

    public LeafAgent(Unit unit) {
        this.unit = unit;
    }
}
