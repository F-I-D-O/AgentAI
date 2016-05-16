/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI;


import java.util.ArrayList;


/**
 * Game result.
 * @author F.I.D.O.
 */
public class GameResult {
	
	/**
	 * Determines if the game was victory or defeat.
	 */
	private boolean win;
	
	/**
	 * Game score
	 */
	private int score;
	
	/**
	 * List of decision module settings per unit.
	 */
	private ArrayList<UnitDecisionSetting> unitDecisionSettings;

	
	
	
	/**
	 * Returns true if game was victory, false otherwise.
	 * @return Returns true if game was victory, false otherwise.
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * Sets the victory flag.
	 * @param win True for victory, false otherwise.
	 */
	public void setWin(boolean win) {
		this.win = win;
	}

	/**
	 * Returns game score.
	 * @return Returns game score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the game score.
	 * @param score Game score.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns decision module settings for agents.
	 * @return 
	 */
	public ArrayList<UnitDecisionSetting> getUnitDecisionSettings() {
		return unitDecisionSettings;
	}

	/**
	 * Sets the the decision module settings for agents.
	 * @param unitDecisionSettings 
	 */
	public void setUnitDecisionSettings(ArrayList<UnitDecisionSetting> unitDecisionSettings) {
		this.unitDecisionSettings = unitDecisionSettings;
	}
	
	

	
	/**
	 * Constructor.
	 * @param win Determines if the game was victory or defeat.
	 * @param score Game score.
	 * @param unitDecisionSettings Decision module settings for agents.
	 */
	public GameResult(boolean win, int score, ArrayList<UnitDecisionSetting> unitDecisionSettings) {
		this.win = win;
		this.score = score;
		this.unitDecisionSettings = unitDecisionSettings;
	}
	
	
}
