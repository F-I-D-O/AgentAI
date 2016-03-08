package com.fido.dp.base;

import com.fido.dp.Log;
import com.fido.dp.action.Action;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

public abstract class Agent {

//    private Action commandedAction;
	
	private Goal goal;

    protected Action chosenAction;
	
	protected boolean assigned;
	
	private final Queue<Command> commandQueue;
	
	

//    public Action getCommandedAction() {
//        return commandedAction;
//    }

//    protected final void setCommandedAction(Action commandedAction) {
//        if (!commandedAction.equals(this.commandedAction)) {
//            this.commandedAction = commandedAction;
//            Log.log(this, Level.FINE, " {0}: Commanded action: {1}", this.getClass(), commandedAction.getClass());
//        }
//    }
	
	

	public final <T extends Goal> T getGoal() {
		return (T) goal;
	}

	public final boolean IsAssigned() {
		return assigned;
	}

	public final void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	
	
	
	public Agent() {
		assigned = false;
		commandQueue = new ArrayDeque<>();
	}
	
	

//    public void start() {
//        System.out.println("Agent started: " + getClass());
//    }

    public final void run() {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		acceptCommand();
		routine();
        if (chosenAction == null) {
            chosenAction = chooseAction();
        }
        if (chosenAction != null) {
            Log.log(this, Level.FINE, "{0}: Chosen action: {1}", this.getClass(), chosenAction.getClass());
            chosenAction.run();
        }
        Log.log(this, Level.FINE, "{0}: Agent run ended", this.getClass());
    }

    protected abstract Action chooseAction();

    public final void onActionFinish() {
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
//        run(); not needet, because frame rate is high enough
    }

    public final void onActionFailed(String reason) {
        Log.log(this, Level.WARNING, "Action {0} failed: {1}", chosenAction, reason);
        run();
    }
	
	protected void routine() {
		
	}

	

	final void addToCommandQueue(Command command) {
		commandQueue.add(command);
	}

	
	
	final void setGoal(Goal goal){
		this.goal = goal;
	}
	
	private final void acceptCommand() {
		Command command;
		while(!commandQueue.isEmpty()){
			command = commandQueue.poll();
			command.execute();
		}
	}
}
