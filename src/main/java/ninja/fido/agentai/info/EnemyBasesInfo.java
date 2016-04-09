/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.info;

import ninja.fido.agentai.base.Info;
import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.base.Agent;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBasesInfo extends Info{
	
	private final ArrayList<BaseLocationInfo> enemyBases;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	
	
	public EnemyBasesInfo(Agent recipient, Agent sender, ArrayList<BaseLocationInfo> enemyBases) {
		super(recipient, sender);
		this.enemyBases = enemyBases;
	}
	
}
