/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionStorage;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionMaking.DecisionModuleActivity;
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
