/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.base;

/**
 *
 * @author F.I.D.O.
 */
public interface GameApiModule {
	
	public void beforeGameStart();

	public void onGameEnd(boolean winner, int score);
}
