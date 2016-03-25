package com.fido.dp.base;

import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.NoActionChosenException;
import com.fido.dp.Supply;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.info.Info;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Level;

public abstract class Agent {

    private CommandAgent commandAgent;
	
	private Goal goal;

    protected Activity chosenAction;
	
	protected boolean assigned;
	
	private final Queue<Order> commandQueue;
	
	private final Supply minerals;
    
    private final Supply gas;
	
	private int receivedMineralsTotal;
	
	private int receivedGasTotal;
	
	private final HashMap<DecisionTablesMapKey,DecisionTable> decisionTablesMap;
	
	protected DecisionTablesMapKey referenceKey;
	
	protected final Queue<Info> infoQue;
	
	protected boolean reasoningOn;
	
	private boolean goalChanged;
	
	
	

	

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
	
	protected final void addToDecisionTablesMap(DecisionTablesMapKey key, DecisionTable map){
		decisionTablesMap.put(key, map);
	}
	
	
	
	public Agent() {
		assigned = false;
		commandQueue = new ArrayDeque<>();
		minerals = new Supply(GameAPI.getCommander(), Material.MINERALS, 0);
		gas = new Supply(GameAPI.getCommander(), Material.GAS, 0);
		receivedMineralsTotal = 0;
		receivedMineralsTotal = 0;
		infoQue = new ArrayDeque<>();
		goal = getDefaultGoal();
		reasoningOn = false;
		decisionTablesMap = new HashMap<>();
		goalChanged = true;
	}
	
	
	

    public final void run() throws Exception {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		acceptCommands();
		processInfoQue();
		routine();
		
		Activity newAction;
		if(goalChanged){
			if(reasoningOn){
				newAction = decide();
			}
			else{
				newAction = chooseAction();
			}
			goalChanged = false;
			
			// if chosen action changed, save it to chosenAction property
			if (chosenAction == null || !chosenAction.equals(newAction)) {
				chosenAction = newAction;
			}
		}
		
		if(goal.isCompleted()){
			if(goal.isOrdered()){
				goal.reportCompleted();
			}
			goal = getDefaultGoal();
			goalChanged = true;
		}
		
        if (chosenAction == null) {
            throw new NoActionChosenException(this.getClass(), goal, commandAgent.getClass());
        }
		
		Log.log(this, Level.FINE, "{0}: Chosen action: {1}", this.getClass(), chosenAction.getClass());
		chosenAction.run();
		
        Log.log(this, Level.FINE, "{0}: Agent run ended", this.getClass());
    }

    public final void onActionFinish() {
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
//        run(); not needet, because frame rate is high enough
    }

    public final void onActionFailed(String reason) {
        Log.log(this, Level.WARNING, "Action {0} failed: {1}", chosenAction, reason);
//        run();
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
	
	public final void queInfo(Info info) {
		infoQue.add(info);
	}
	
	
	protected abstract Activity chooseAction();
	
	protected void routine() {
		
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
	
	protected void processInfo(Info info) {
		Log.log(this, Level.FINE, "{0}: info received: {1}", this.getClass(), info.getClass());
	}
	
	protected abstract Goal getDefaultGoal();
	
	
	final void addToCommandQueue(Order command) {
		commandQueue.add(command);
	}
	
	final void setGoal(Goal goal){
		this.goal = goal;
		goalChanged = true;
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
	
	private final void processInfoQue(){
		Info info;
		while((info = infoQue.poll()) != null){
			processInfo(info);
			chosenAction.processInfo(info);
		}
	}

	protected Activity decide() {
		DecisionTablesMapKey key = DecisionTablesMapKey.createKeyBasedOnCurrentState(this, referenceKey);
		Activity chosenAction = decisionTablesMap.get(key).chooseAction();
		chosenAction.initialize(goal);
		return chosenAction;
	}




}
