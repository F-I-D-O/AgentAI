/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base;

/**
 * Request class.
 * @author F.I.D.O.
 */
public abstract class Request {
	
	/**
	 * Recipient of the request.
	 */
	private final CommandAgent recipient;
	
	/**
	 * Request sender.
	 */
	private final Agent sender;

	
	
	/**
	 * Request sender getter.
	 * @return Request sender.
	 */
	public Agent getSender() {
		return sender;
	}
	
	
	/**
	 * Constructor.
	 * @param recipient Recipient of the request.
	 * @param sender Request sender.
	 */
	public Request(CommandAgent recipient, Agent sender) {
		this.recipient = recipient;
		this.sender = sender;
	}
	
	/**
	 * Sends the request to the target.
	 */
	public final void send(){
		recipient.queRequest(this);
		sender.addSendedRequest(this);
	}
}
