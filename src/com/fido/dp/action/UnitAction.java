package com.fido.dp.action;

import com.fido.dp.base.Action;
import com.fido.dp.base.UnitAgent;

public abstract class UnitAction<T extends UnitAgent> extends Action<T> {

    public UnitAction(T unitAgent) {
        super(unitAgent);
    }

}
