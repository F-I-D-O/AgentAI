package com.fido.dp.agent;

import com.fido.dp.action.Action;
import com.fido.dp.action.BBSStrategy;

public class Commander extends CommandAgent {

    public Commander() {
    }

    @Override
    protected Action chooseAction() {
        return new BBSStrategy(this);
    }
}
