package ninja.fido.agentAI.base;

import ninja.fido.agentAI.modules.decisionMaking.CannotDecideException;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.NoActionChosenException;
import ninja.fido.agentAI.Resource;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

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
	
	private Map<Class<? extends Goal>,? extends Activity> goalActivityMap;
	
	private Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap;
	
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

	public Map<DecisionTablesMapKey, DecisionTable> getDecisionTablesMap() {
		return decisionTablesMap;
	}
	
	
	
	
	
	
	public Agent() {
		assigned = false;
		reasoningOn = false;
		goalChanged = true;
		initialized = false;
		commandQueue = new ArrayDeque<>();
		infoQue = new ArrayDeque<>();
		sendRequests = new ArrayList<>();
		
		// resources init
		minerals = new Resource(this, GameAPI.getCommander(), ResourceType.MINERALS, 0);
		gas = new Resource(this, GameAPI.getCommander(), ResourceType.GAS, 0);
		supply = new Resource(this, GameAPI.getCommander(), ResourceType.SUPPLY, 0);
		receivedMineralsTotal = 0;
		receivedMineralsTotal = 0;
		
		// decision making
		if(GameAPI.isDecisionMakingOn(this)){
			decisionTablesMap = GameAPI.getDecisionTablesMap(getClass());
			setReferenceKey();
			reasoningOn = true;
		}
		goalActivityMap = getDefaultGoalActivityMap();
	}
	
	
	

    public final void run() throws CannotDecideException, NoActionChosenException, ChainOfCommandViolationException {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		if(!initialized){
			init();
			goal = getDefaultGoal();
			initialized = true;
		}
		
		acceptCommands();
		
		Activity newAction;
		if(goalChanged){
			if(reasoningOn){
				newAction = decide();
			}
			else{
				newAction = chooseActivity();
			}
			goalChanged = false;
			
			// if chosen action changed, save it to chosenAction property
			if (chosenAction == null || !chosenAction.equals(newAction)) {
				chosenAction = newAction;
			}
		}
		
		routine();
		processInfoQue();
		
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
		Log.log(this, Level.FINE, "{0}: Goal: {1}", this.getClass(), goal.getClass());
		Log.log(this, Level.FINE, "{0}: Chosen action: {1}", this.getClass(), chosenAction.getClass());
		chosenAction.run();
		
        Log.log(this, Level.FINE, "{0}: Agent run ended", this.getClass());
    }

    public final void onActivityFinish(Activity activity) {
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
//        run(); not needet, because frame rate is high enough
    }

    public final void onActionFailed(String reason) {
        Log.log(this, Level.SEVERE, "Action {0} failed: {1}", chosenAction, reason);
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
	
	public void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException{
		switch(material){
			case GAS:
				receiver.receiveResource(gas.split(amount));
				break;
			case MINERALS:
				receiver.receiveResource(minerals.split(amount));
				break;
			case SUPPLY:
				receiver.receiveResource(supply.split(amount));
				break;
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
	
	
	protected Activity chooseActivity(){
		Activity template = goalActivityMap.get(goal.getClass());
		return template.create(this, goal);
	}
	
	protected void routine() {
		
	}

	protected final void spendSupply(ResourceType material, int amount) throws ResourceDeficiencyException{
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
		int infoCount = infoQue.size();
		for (int i = 0; i < infoCount; i++) {
			info = infoQue.poll();
			processInfo(info);
			chosenAction.processInfo(info);
		}
	}

	protected Activity decide() throws CannotDecideException {
		DecisionTablesMapKey key = DecisionTablesMapKey.createKeyBasedOnCurrentState(this, referenceKey);
		DecisionTable decisionTable = decisionTablesMap.get(key);
		if(decisionTable == null){
			throw new CannotDecideException(this, key);
		}
//		Activity chosenAction = decisionTable.chooseAction();
//		chosenAction.init(this, goal);
//		return chosenAction;
		return decisionTable.chooseAction().create(this, goal);
	}

	protected void init() {
		 
	}

	private void setReferenceKey() {
		for (Map.Entry<DecisionTablesMapKey, DecisionTable> entry : decisionTablesMap.entrySet()) {
			referenceKey = entry.getKey();
			break;
		}
	}

	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		return null;
	}

	protected Map<Class<? extends Goal>, ? extends Activity> getDefaultGoalActivityMap() {
		return new HashMap<>();
	}




}
