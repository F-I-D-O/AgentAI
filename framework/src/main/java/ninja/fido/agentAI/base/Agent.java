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
import ninja.fido.agentAI.base.exception.SimpleDecisionException;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 * Agent class.
 * @author F.I.D.O.
 */
public abstract class Agent {
	
	/**
	 * Current chosen activity.
	 */
    Activity chosenAction;

	
	/**
	 * Command agent.
	 */
    private CommandAgent commandAgent;
	
	/**
	 * Current goal.
	 */
	private Goal goal;

	/**
	 * This property determines whether agent is assigned to some quest. This value is in responsibility of his 
	 * command agent. Framework change this property only when agent is detached (it' changed to false in this case)
	 */
	private boolean assigned;
	
	/**
	 * Queue of orders from command agent.
	 */
	private final Queue<Order> orderQueue;
	
	/**
	 * Minerals.
	 */
	private final Resource minerals;
    
	/**
	 * Gas.
	 */
    private final Resource gas;
	
	/**
	 * Supply
	 */
	private final Resource supply;
	
	/**
	 * Total amount of minerals rceived.
	 */
	private int receivedMineralsTotal;
	
	/**
	 * Total amount of gas received.
	 */
	private int receivedGasTotal;
	
	/**
	 * This maps goals to activities. It's used for simple goal -> activity decisions.
	 */
	private Map<Class<? extends Goal>,Activity> goalActivityMap;
	
	/**
	 * Map for decisions. Maps {@link DecisionTablesMapKey} to  {@link DecisionTable}.
	 */
	private Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap;
	
	/**
	 * Reference key for decision table map. It's used for creating key that represents current state if needed.
	 */
	private DecisionTablesMapKey referenceKey;
	
	/**
	 * Queue of incomming informations.
	 */
	private final Queue<Info> infoQue;
	
	/**
	 * Determines if {@link DecisionModule} is on and this agent type is registered in it.
	 */
	private boolean decisionMakingOn;
	
	/**
	 * Determines if goal has been changed recently.
	 */
	private boolean goalChanged;
	
	/**
	 * Determines if agent has been initialized.
	 */
	private boolean initialized;
	
	/**
	 * List of all request that has been sent by this agent.
	 */
	private final ArrayList<Request> sentRequests;

	
	
	
	/**
	 * Goal getter.
	 * @return Returns current goal.
	 */
	public final Goal getGoal() {
		return goal;
	}

	/**
	 * Determines whether agent is assigned to some quest. This value is in responsibility of his 
	 * command agent. Framework change this property only when agent is detached (it' changed to false in this case)
	 * @return true if agent is assigned to some quest, false otherwise.
	 */
	public final boolean IsAssigned() {
		return assigned;
	}

	/**
	 * Set assigned to new value. This method should be used by this agent's command agent only.
	 * @param assigned New assigned valu.
	 */
	public final void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	/**
	 * Total amount of minerals received this game.
	 * @return Returns total amount of minerals rceived.
	 */
	public int getReceivedMineralsTotal() {
		return receivedMineralsTotal;
	}

	/**
	 * Total amount of gas received in this game.
	 * @return Returns total amount of gas received.
	 */
	public int getReceivedGasTotal() {
		return receivedGasTotal;
	}
	
	/**
	 * Command agent.
	 * @return Returns current command agent.
	 */
	public CommandAgent getCommandAgent(){
		return commandAgent;
	}
	
	/**
	 * Decision tables map.
	 * @return Returns decision tables map.
	 */
	public Map<DecisionTablesMapKey, DecisionTable> getDecisionTablesMap() {
		return decisionTablesMap;
	}
	
	
	/**
	 * Setts new command agent.
	 * @param commandAgent new command agent.
	 */
	void setCommandAgent(CommandAgent commandAgent){
		this.commandAgent = commandAgent;
	}

	
	
	
	/**
	 * Constructor.
	 * @throws EmptyDecisionTableMapException 
	 */
	public Agent() throws EmptyDecisionTableMapException {
		
		// in case that this agent instance is created only for registration, we do not have to initialize any property.
		if(!GameAPI.ready()){
			orderQueue = null;
			minerals = null;
			gas = null;
			supply = null;
			infoQue = null;
			sentRequests = null;
			return;
		}
		
		assigned = false;
		decisionMakingOn = false;
		goalChanged = true;
		initialized = false;
		orderQueue = new ArrayDeque<>();
		infoQue = new ArrayDeque<>();
		sentRequests = new ArrayList<>();
		
		// resources onInitialize
		minerals = new Resource(this, GameAPI.getCommander(), ResourceType.MINERALS, 0);
		gas = new Resource(this, GameAPI.getCommander(), ResourceType.GAS, 0);
		supply = new Resource(this, GameAPI.getCommander(), ResourceType.SUPPLY, 0);
		receivedMineralsTotal = 0;
		receivedMineralsTotal = 0;
		
		// decision making
		if(GameAPI.isDecisionMakingOn(this)){
			decisionTablesMap = GameAPI.getDecisionTablesMap(getClass());
			setReferenceKey();
			decisionMakingOn = true;
		}
		
		// simple decisions
		goalActivityMap = GameAPI.getSimpleDecisionsMap(this.getClass());
		if(goalActivityMap == null){
			goalActivityMap = getDefaultGoalActivityMap();
		}
	}
	
	/**
	 * Rerurns default decision tables map.
	 * @return Rerurns default decision tables map.
	 */
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		return null;
	}
	
	/**
	 * Returns default goal activitz map.
	 * @return Returns default goal activitz map.
	 */
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		return new HashMap<>();
	}
	
	
	/**
	 * Called when current activity finises.
	 * @param activity Activity that just finished.
	 */
	protected void onActivityFinish(Activity activity) {
        Log.log(this, Level.FINE, "Activity finished: {0}", chosenAction);
//        run(); not needet, because frame rate is high enough
    }
	
	/**
	 * Called when current activity fails.
	 * @param activity Activity that just failed.
	 * @param reason Reason of the failure.
	 */
	protected void onActionFailed(Activity activity, String reason) {
        Log.log(this, Level.SEVERE, "Action {0} failed: {1}", chosenAction, reason);
//        run();
    }
	
	/**
	 * Returns amount of owned gas.
	 * @return Amount of owned gas.
	 */
	protected int getOwnedGas(){
        return gas.getAmount();
    } 
    
	/**
	 * Returns amount of owned minerals.
	 * @return Amount of owned minerals.
	 */
    protected int getOwnedMinerals(){
        return minerals.getAmount();
    }
	
	/**
	 * Returns amount of owned supply.
	 * @return Amount of owned supply.
	 */
	protected int getOwnedSupply(){
        return supply.getAmount();
    }
	
	/**
	 * Gives resource to oher agent.
	 * @param receiver Receiver of the recource.
	 * @param material Material.
	 * @param amount Resource amount.
	 * @throws ResourceDeficiencyException 
	 */
	protected void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException{
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
	
	/**
	 * Return minerals missing to specified amount.
	 * @param neededAmount Needen amount of minerals.
	 * @return Amount of mineraal missing to needed amount.
	 */
	protected int getMissingMinerals(int neededAmount){
		int difference = neededAmount - getOwnedMinerals();
		return difference > 0 ? difference : 0;
	}
	
	/**
	 * Return gas missing to specified amount.
	 * @param neededAmount Needen amount of gas.
	 * @return Amount of gas missing to needed amount.
	 */
	protected int getMissingGas(int neededAmount){
		int difference = neededAmount - getOwnedGas();
		return difference > 0 ? difference : 0;
	}
	
	/**
	 * Determines whether certain request was sended. Note that you have to override equals in your reques if you want
	 * to use this.
	 * @param request Request to test.
	 * @return Returns true if this request has been sended before or not.
	 */
	protected boolean requestSended(Request request){
		return sentRequests.contains(request);
	}
	
	/**
	 * Runs every frame. Agent per frame logic belongs here, if there is some.
	 */
	protected void routine() {		
	}
	
	/**
	 * Spends resource. This should be called only after api call whitch results in spending supply.
	 * @param material Material.
	 * @param amount Resource amount.
	 * @throws ResourceDeficiencyException 
	 */
	protected final void spendResource(ResourceType material, int amount) throws ResourceDeficiencyException{
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
	
	/**
	 * Processes one info object.
	 * @param info Info object.
	 */
	protected void processInfo(Info info) {
		Log.log(this, Level.FINE, "{0}: info received: {1}", this.getClass(), info.getClass());
	}
	
	/**
	 * Returns defaul goal for this type.
	 * @return Returns defaul goal for this type.
	 */
	protected abstract Goal getDefaultGoal();
	
	/**
	 * In this function agent make decision what to do next.
	 * @return Chosen action.
	 * @throws CannotDecideException 
	 */
	protected Activity decide() throws CannotDecideException {
		DecisionTablesMapKey key = DecisionTablesMapKey.createKeyBasedOnCurrentState(this, referenceKey);
		DecisionTable decisionTable = decisionTablesMap.get(key);
		if(decisionTable == null){
			throw new CannotDecideException(this, key);
		}
		return decisionTable.chooseAction().create(this, goal);
	}
	
	/**
	 * This function is called in the first frame.
	 */
	protected void onInitialize() {
		 
	}
	
	
	/**
	 * Main method, runs every frame.
	 * @throws CannotDecideException
	 * @throws NoActionChosenException
	 * @throws ChainOfCommandViolationException
	 * @throws SimpleDecisionException 
	 */
    final void run() throws CannotDecideException, NoActionChosenException, ChainOfCommandViolationException, 
			SimpleDecisionException {
        Log.log(this, Level.FINE, "{0}: Agent run started", this.getClass());
		if(!initialized){
			init();
			initialized = true;
		}
		
		acceptCommands();
		
		if(goalChanged){
			Activity newAction;
			
			// decision making
			if(decisionMakingOn){
				newAction = decide();
			}
			// simple decisions
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
			if(this instanceof Commander){
				throw new NoActionChosenException(this.getClass(), goal, null);
			}
			else{
				throw new NoActionChosenException(this.getClass(), goal, commandAgent.getClass());
			}
        }
		Log.log(this, Level.FINE, "{0}: Goal: {1}", this.getClass(), goal.getClass());
		Log.log(this, Level.FINE, "{0}: Chosen action: {1}", this.getClass(), chosenAction.getClass());
		
		// run action logic
		chosenAction.run();
		
        Log.log(this, Level.FINE, "{0}: Agent run ended", this.getClass());
    }

	/**
	 * Called internaly when another agent gives resource to this agent.
	 * @param resource Resource given.
	 */
	final void receiveResource(Resource resource){
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
	
	/**
	 * Queue information from other agent.
	 * @param info Info object.
	 */
	final void queInfo(Info info) {
		infoQue.add(info);
	}
	
	/**
	 * Adds new request to request que.
	 * @param request 
	 */
	final void addSendedRequest(Request request){
		sentRequests.add(request);
	}

	/**
	 * Adds new order to order queue
	 * @param order Incoming order.
	 */
	final void addToCommandQueue(Order order) {
		orderQueue.add(order);
	}
	
	/**
	 * Set new goal.
	 * @param goal New goal.
	 */
	final void setGoal(Goal goal){
		this.goal = goal;
		goalChanged = true;
		Log.log(this, Level.INFO, "{0}: new goal set: {1}", this.getClass(), goal.getClass());
	}
	
	
	/**
	 * Choose the activity to perform.
	 * @return Chosen aativity.
	 * @throws SimpleDecisionException 
	 */
	private Activity chooseActivity() throws SimpleDecisionException{
		Activity template = goalActivityMap.get(goal.getClass());
		if(template == null){
			if(this instanceof Commander){
				throw new SimpleDecisionException(this.getClass(), goal, null);
			}
			else{
				throw new SimpleDecisionException(this.getClass(), goal, commandAgent.getClass());
			}
		}
		return template.create(this, goal);
	}
	
	/**
	 * Accepts all commands in queue.
	 */
	private final void acceptCommands() {
		Order command;
		while(!orderQueue.isEmpty()){
			command = orderQueue.poll();
			command.execute();
			Log.log(this, Level.INFO, "{0}: command accepted: {1}", this.getClass(), command.getClass());
		}
	}
	
	/**
	 * Procces all informations in info queue.
	 */
	private final void processInfoQue(){
		Info info;
		int infoCount = infoQue.size();
		for (int i = 0; i < infoCount; i++) {
			info = infoQue.poll();
			processInfo(info);
			chosenAction.processInfo(info);
		}
	}

	/**
	 * Sets the reference key to decision table.
	 * @throws EmptyDecisionTableMapException 
	 */
	private void setReferenceKey() throws EmptyDecisionTableMapException {
		if(decisionTablesMap.isEmpty()){
			throw new EmptyDecisionTableMapException(this.getClass());
		}
		for (Map.Entry<DecisionTablesMapKey, DecisionTable> entry : decisionTablesMap.entrySet()) {
			referenceKey = entry.getKey();
			break;
		}
	}

	/**
	 * Intitilaize the agent
	 */
	private void init() {
		onInitialize();
		goal = getDefaultGoal();
	}




}
