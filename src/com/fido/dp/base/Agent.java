package com.fido.dp.base;

import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.Log;
import com.fido.dp.ResourceType;
import com.fido.dp.NoActionChosenException;
import com.fido.dp.Resource;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.info.Info;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Level;

public abstract class Agent {

    private CommandAgent commandAgent;
	
	private Goal goal;

    protected Activity chosenAction;
	
	protected boolean assigned;
	
	private final Queue<Order> commandQueue;
	
	private final Resource minerals;
    
    private final Resource gas;
	
	private final Resource supply;
	
	private int receivedMineralsTotal;
	
	private int receivedGasTotal;
	
	private final HashMap<DecisionTablesMapKey,DecisionTable> decisionTablesMap;
	
	protected DecisionTablesMapKey referenceKey;
	
	protected final Queue<Info> infoQue;
	
	protected boolean reasoningOn;
	
	private boolean goalChanged;
	
	private boolean initialized;
	
	private final ArrayList<Request> sendRequests;
	
	
	

	

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
	
	public CommandAgent getCommandAgent(){
		return commandAgent;
	}
	
	protected final void addToDecisionTablesMap(DecisionTablesMapKey key, DecisionTable map){
		decisionTablesMap.put(key, map);
	}
	
	
	
	public Agent() {
		assigned = false;
		commandQueue = new ArrayDeque<>();
		minerals = new Resource(GameAPI.getCommander(), ResourceType.MINERALS, 0);
		gas = new Resource(GameAPI.getCommander(), ResourceType.GAS, 0);
		supply = new Resource(GameAPI.getCommander(), ResourceType.SUPPLY, 0);
		receivedMineralsTotal = 0;
		receivedMineralsTotal = 0;
		infoQue = new ArrayDeque<>();
		goal = getDefaultGoal();
		reasoningOn = false;
		decisionTablesMap = new HashMap<>();
		goalChanged = true;
		initialized = false;
		sendRequests = new ArrayList<>();
	}
	
	
	

    public final void run() throws Exception {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		if(!initialized){
			initialize();
			initialized = true;
		}
		
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

    public final void onActivityFinish(Activity activity) {
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
//        run(); not needet, because frame rate is high enough
    }

    public final void onActionFailed(String reason) {
        Log.log(this, Level.WARNING, "Action {0} failed: {1}", chosenAction, reason);
//        run();
    }
	
	
	public final void receiveResource(Resource resource){
		switch(resource.getResourceType()){
			case GAS:
				gas.merge(resource);
				receivedGasTotal += resource.getAmount();
				break;
			case MINERALS:
				minerals.merge(resource);
				receivedMineralsTotal += resource.getAmount();
				break;
			case SUPPLY:
				supply.merge(resource);
				break;
		}
    }
	
	public int getOwnedGas(){
        return gas.getAmount();
    } 
    
    public int getOwnedMinerals(){
        return minerals.getAmount();
    }
	
	public int getOwnedSupply(){
        return supply.getAmount();
    }
	
	public void giveSupply(Agent receiver, ResourceType material, int amount){
		if(material == ResourceType.GAS){
			receiver.receiveResource(gas.split(amount));
		}
		else{
			receiver.receiveResource(minerals.split(amount));
		}
	}
	
	public final void queInfo(Info info) {
		infoQue.add(info);
	}
	
	public int getMissingMinerals(int neededAmount){
		int difference = neededAmount - getOwnedMinerals();
		return difference > 0 ? difference : 0;
	}
	
	public int getMissingGas(int neededAmount){
		int difference = neededAmount - getOwnedGas();
		return difference > 0 ? difference : 0;
	}
	
	public boolean requestSended(Request request){
		return sendRequests.contains(request);
	}
	
	void addSendedRequest(Request request){
		sendRequests.add(request);
	}

//	public Agent(Queue<Order> commandQueue, Resource minerals, Resource gas, HashMap<DecisionTablesMapKey, DecisionTable> decisionTablesMap, Queue<Info> infoQue, ArrayList<Request> sendRequests) {
//		this.commandQueue = commandQueue;
//		this.minerals = minerals;
//		this.gas = gas;
//		this.decisionTablesMap = decisionTablesMap;
//		this.infoQue = infoQue;
//		this.sendRequests = sendRequests;
//	}
	
	
	protected abstract Activity chooseAction();
	
	protected void routine() {
		
	}

	protected final void spendSupply(ResourceType material, int amount){
		Resource resource = null;
		switch(material){
			case GAS:
				resource = gas.split(amount);
				break;
			case MINERALS:
				resource = minerals.split(amount);
				break;
			case SUPPLY:
				resource = supply.split(amount);
				break;
		}
		resource.spend(amount);
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

	protected void initialize() {
		
	}




}
