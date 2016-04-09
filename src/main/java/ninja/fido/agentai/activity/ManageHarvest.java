/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity;

import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.agent.ResourceCommand;
import ninja.fido.agentai.agent.unit.SCV;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.order.HarvestMineralsOrder;
import ninja.fido.agentai.goal.HarvestMineralsGoal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author david_000
 * @param <A>
 */
public class ManageHarvest<A extends ResourceCommand> extends CommandActivity<A,Goal>{
	
	private final double mineralShare;

    public ManageHarvest(A agent, double mineralShare) {
        super(agent);
        this.mineralShare = mineralShare;
    }

    @Override
    public void performAction() {
		List<SCV> scvs = getAgent().getCommandedAgents(SCV.class);
        for (SCV scv : scvs) {
			if(!(scv.getGoal() instanceof HarvestMineralsGoal)){
				new HarvestMineralsOrder(scv, this.getAgent()).issueOrder();
			}
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ManageHarvest other = (ManageHarvest) obj;
        if (!Objects.equals(this.mineralShare, other.mineralShare)) {
            return false;
        }
        return true;
    }

	@Override
	protected void init() {
		
	}
    
}
