package ninja.fido.agentAI;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author F.I.D.O.
 */
public class GameResult {
	private boolean win;
	
	private int score;
	
	private ArrayList<UnitDecisionSetting> unitDecisionSettings;

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<UnitDecisionSetting> getUnitDecisionSettings() {
		return unitDecisionSettings;
	}

	public void setUnitDecisionSettings(ArrayList<UnitDecisionSetting> unitDecisionSettings) {
		this.unitDecisionSettings = unitDecisionSettings;
	}
	
	

	public GameResult(boolean win, int score, ArrayList<UnitDecisionSetting> unitDecisionSettings) {
		this.win = win;
		this.score = score;
		this.unitDecisionSettings = unitDecisionSettings;
	}
	
	
}
