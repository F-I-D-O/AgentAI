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
	
	public void onRun();

	public void onEnd(boolean winner, int score);

	public void onStart(int gameCount);
}
