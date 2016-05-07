/* 
 * AgentAI
 */
package ninja.fido.agentAI.modules.decisionMaking;

import ninja.fido.agentAI.base.Agent;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionTablesMapKey {

	public static DecisionTablesMapKey createKeyBasedOnCurrentState(Agent agent, DecisionTablesMapKey referenceKey) {
		DecisionTablesMapKey key = new DecisionTablesMapKey();
		for (Entry<Class<DecisionTablesMapParameter>,DecisionTablesMapParameter> entry : 
				referenceKey.getKeyParametrs().entrySet()) {
			key.addParameter(entry.getValue().getCurrentParameter(agent));
		}
		return key;
	}
	
	
	
	
	
	private HashMap<Class<DecisionTablesMapParameter>,DecisionTablesMapParameter> keyParametrs;

	public HashMap<Class<DecisionTablesMapParameter>, DecisionTablesMapParameter> getKeyParametrs() {
		return keyParametrs;
	}

	public void setKeyParametrs(HashMap<Class<DecisionTablesMapParameter>, DecisionTablesMapParameter> keyParametrs) {
		this.keyParametrs = keyParametrs;
	}
	
	
	

	public DecisionTablesMapKey() {
		this.keyParametrs = new HashMap<>();
	}
	
	

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DecisionTablesMapKey other = (DecisionTablesMapKey) obj;
		if (!Objects.equals(this.keyParametrs, other.keyParametrs)) {
			return false;
		}
		return true;
	}
	
	public void addParameter(DecisionTablesMapParameter parametr){
		keyParametrs.put((Class<DecisionTablesMapParameter>) parametr.getClass(), parametr);
	}

	@Override
	public String toString() {
		String[] content = new String[keyParametrs.size()];
		int i = 0;
		for (Entry<Class<DecisionTablesMapParameter>, DecisionTablesMapParameter> entry : keyParametrs.entrySet()) {
			content[i] += entry.getValue().toString();
			i++;
		}
		
		return "{" + String.join(", ", content) + '}';
	}
	
	

}
