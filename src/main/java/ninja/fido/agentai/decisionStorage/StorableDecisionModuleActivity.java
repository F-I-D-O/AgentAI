/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.decisionStorage;

import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 * @param <G>
 * @param <AC>
 */
public interface StorableDecisionModuleActivity<A extends Agent,G extends Goal,AC extends Activity> 
		extends DecisionModuleActivity<A, G, AC>{
	
	public Element getXml(Document document);
	
	public String getId();
}
