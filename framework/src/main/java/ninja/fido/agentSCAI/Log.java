/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger,
 * @author david_000
 */
public class Log {
	
	/**
	 * Java logger.
	 */
	private static Logger logger;
	
	
	
	
	/**
	 * Inits logger.
	 * @param logLevel Java log level.
	 */
	public static void init(Level logLevel){

		logger = Logger.getLogger("AgentAI Logger");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		// conslole log settings
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.WARNING);
		consoleHandler.setFormatter(new LogFormater());
		logger.addHandler(consoleHandler);
		
		try {  
			// file log settings
			FileHandler fileHandler = new FileHandler("log.txt");
			fileHandler.setLevel(logLevel);
			fileHandler.setFormatter(new LogFormater());
			logger.addHandler(fileHandler);
		} catch (IOException ex) {
			Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Log message with no params.
	 * @param caller Caller of the method.
	 * @param level Java log level.
	 * @param message Log message.
	 */
    public static void log(Object caller, Level level, String message){ 
		logger.log(level, message);
    }
    
	/**
	 * Log message.
	 * @param caller Caller of the method.
	 * @param level Java log level.
	 * @param message Log message.
	 * @param params Message parameters.
	 */
    public static void log(Object caller, Level level, String message, Object... params){
		logger.log(level, message, params);
    }
}
