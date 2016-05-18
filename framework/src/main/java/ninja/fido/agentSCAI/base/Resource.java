/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base;

import java.util.logging.Level;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;

/**
 * Represents game resourse ie Supply, Mineral or Gas.
 * @author david_000
 */
public final class Resource {
	
	/**
	 * Agent who owns this resource.
	 */
	private final Agent agent;
    
	/**
	 * Reference to commander.
	 */
    private final Commander commander;
    
	/**
	 * Resource type.
	 */
    private final ResourceType resourceType;
    
	/**
	 * Amount of the resource.
	 */
    private int amount;
    
    
    

	/**
	 * Returns resource type.
	 * @return Returns resource type.
	 */
    final ResourceType getResourceType() {
        return resourceType;
    }

	/**
	 * Returns resource amount.
	 * @return Returns resource amount.
	 */
    final int getAmount() {
        return amount;
    }

    
    
    
	/**
	 * Constructor.
	 * @param agent Agent who owns this resource.
	 * @param commander Reference to commander
	 * @param resourceType Resource type.
	 * @param amount Amount of the resource.
	 */
    Resource(Agent agent, Commander commander, ResourceType resourceType, int amount) {
        this.commander = commander;
        this.resourceType = resourceType;
        this.amount = amount;
		this.agent = agent;
    }
    
    
    
    
	/**
	 * Merge this resource with another resource of the same type.
	 * @param resource Resource of the same tzpe as this.
	 */
    final void merge(Resource resource){
        if(resourceType != resource.getResourceType()){
            Log.log(this, Level.SEVERE, "Cannot merge {0} to {1}", resource.getResourceType(), resourceType);
        }
        else{
            amount += resource.getAmount();
        }
    }
    
	/**
	 * Split this resource.
	 * @param amount Amount to split.
	 * @return Returns new resource of the same type.
	 * @throws ResourceDeficiencyException If the agent does not own enought resources to split.
	 */
    final Resource split(int amount) throws ResourceDeficiencyException{
        if(amount > this.amount){
//            Log.log(this, Level.SEVERE, "Don't have enough supply to split - requested amount: {0}, current amount: {1}",
//                    amount, this.amount);
            throw new ResourceDeficiencyException(agent, resourceType, amount, this.amount);
        }
        
        this.amount -= amount;
        return new Resource(agent, commander, resourceType, amount);
    }
    
	/**
	 * Spends this resource.
	 */
    final void spend(){
        commander.removeReservedResource(this);
    }
    
}
