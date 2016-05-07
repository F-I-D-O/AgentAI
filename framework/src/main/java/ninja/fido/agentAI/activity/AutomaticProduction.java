/* 
 * AgentAI
 */
package ninja.fido.agentAI.activity;

import ninja.fido.agentAI.base.UnitActivity;
import bwapi.UnitType;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.agent.unit.Barracks;
import ninja.fido.agentAI.base.Goal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProduction extends UnitActivity<Barracks,Goal,AutomaticProduction>{
	
	private UnitType unitType;
	
	
	

	public AutomaticProduction(UnitType automaticProductionType) {
		this.unitType = automaticProductionType;
	}

	public AutomaticProduction(Barracks unitAgent, UnitType automaticProductionType) {
		super(unitAgent);
		this.unitType = automaticProductionType;
	}

	@Override
	protected void performAction() {
		try {
			agent.automaticProduction();
		} catch (ResourceDeficiencyException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void init() {
		agent.setAutomaticProductionUnitType(unitType);
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
		final AutomaticProduction other = (AutomaticProduction) obj;
		if (!Objects.equals(this.unitType, other.unitType)) {
			return false;
		}
		return true;
	}

	@Override
	public AutomaticProduction create(Barracks agent, Goal goal) {
		return new AutomaticProduction(agent, unitType);
	}
	
}
