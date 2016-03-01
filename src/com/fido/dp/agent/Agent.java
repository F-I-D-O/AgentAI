package com.fido.dp.agent;

import com.fido.dp.Log;
import com.fido.dp.action.Action;
import java.util.logging.Level;

public abstract class Agent {

    private Action commandedAction;

    protected Action chosenAction;

    public Action getCommandedAction() {
        return commandedAction;
    }

    protected final void setCommandedAction(Action commandedAction) {
        if (!commandedAction.equals(this.commandedAction)) {
            this.commandedAction = commandedAction;
            Log.log(this, Level.FINE, " {0}: Commanded action: {1}", this.getClass(), commandedAction.getClass());
        }
    }

    public void start() {
        System.out.println("Agent started: " + getClass());
    }

    public final void run() {
        Log.log(this, Level.FINE, " {0}: Agent run started", this.getClass());
        if (chosenAction == null) {
            chosenAction = chooseAction();
        }
        if (chosenAction != null) {
            Log.log(this, Level.FINE, " {0}: Chosen action: {1}", this.getClass(), chosenAction.getClass());
            chosenAction.run();
        }
        Log.log(this, Level.FINE, " {0}: Agent run ended", this.getClass());
    }

    protected abstract Action chooseAction();

    public final void onActionFinish() {
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
        run();
    }

    public final void onActionFailed(String reason) {
        Log.log(this, Level.WARNING, "Action {0} failed: {1}", chosenAction, reason);
        run();
    }
}
