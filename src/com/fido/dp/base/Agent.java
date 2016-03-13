package com.fido.dp.base;

import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.Supply;
import com.fido.dp.request.Request;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

public abstract class Agent {

    private CommandAgent commandAgent;
	
	private Goal goal;

    protected Action chosenAction;
	
	protected boolean assigned;
	
	private final Queue<Order> commandQueue;
	
	private final Supply minerals;
    
    private final Supply gas;
	
	private int receivedMineralsTotal;
	
	private int receivedGasTotal;
	
	protected final Queue<Request> requests;
	

	
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

	public int getReceivedMineralsTotal() {
		return receivedMineralsTotal;
	}

	public int getReceivedGasTotal() {
		return receivedGasTotal;
	}
	
	void setCommandAgent(CommandAgent commandAgent){
		this.commandAgent = commandAgent;
	}
	
	public CommandAgent geCommandAgent(){
		return commandAgent;
	}
	
	
	
	public Agent() {
		assigned = false;
		commandQueue = new ArrayDeque<>();
		minerals = new Supply(GameAPI.getCommander(), Material.MINERALS, 0);
		gas = new Supply(GameAPI.getCommander(), Material.GAS, 0);
		receivedMineralsTotal = 0;
		receivedMineralsTotal = 0;
		requests = new ArrayDeque<>();
	}
	
	

//    public void start() {
//        System.out.println("Agent started: " + getClass());
//    }

    public final void run() {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		acceptCommands();
		handleRequests();
		routine();
		Action newAction = chooseAction();
		
		// if chosen action changed, save it to chosenAction property
        if (chosenAction == null || !chosenAction.equals(newAction)) {
            chosenAction = newAction;
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

	

	final void addToCommandQueue(Order command) {
		commandQueue.add(command);
	}

	
	
	final void setGoal(Goal goal){
		this.goal = goal;
		Log.log(this, Level.INFO, "{0}: new goal set: {1}", this.getClass(), goal.getClass());
	}
	
	private final void acceptCommands() {
		Order command;
		while(!commandQueue.isEmpty()){
			command = commandQueue.poll();
			command.execute();
			Log.log(this, Level.INFO, "{0}: command accepted: {1}", this.getClass(), command.getClass());
		}
	}
	
	public final void receiveSupply(Supply supply){
        if(supply.getMaterial() == Material.GAS){
            gas.merge(supply);
			receivedGasTotal += supply.getAmount();
        }
        else{
            minerals.merge(supply);
			receivedMineralsTotal += supply.getAmount();
        }
    }
	
	public int getOwnedGas(){
        return gas.getAmount();
    } 
    
    public int getOwnedMinerals(){
        return minerals.getAmount();
    }
	
	public void giveSupply(Agent receiver, Material material, int amount){
		if(material == Material.GAS){
			receiver.receiveSupply(gas.split(amount));
		}
		else{
			receiver.receiveSupply(minerals.split(amount));
		}
	}
	
	protected final void spendSupply(Material material, int amount){
		Supply supply;
		if(material == Material.GAS){
			supply = gas.split(amount);
		}
		else{
			supply = minerals.split(amount);
		}
		supply.spend(amount);
	}
	
	public void queRequest(Request request){
		requests.add(request);
	}
    
	private final void handleRequests(){
		Request request;
		while((request = requests.poll()) != null){
			handleRequest(request);
			chosenAction.handleRequest(request);
		}
	}

	protected void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}
}
