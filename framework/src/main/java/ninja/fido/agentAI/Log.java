/* 
 * AgentAI
 */
package ninja.fido.agentAI;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david_000
 */
public class Log {
	
	private static Logger logger;
	
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
	
    public static void log(Object object, Level level, String message){ 
		logger.log(level, message);
    }
    
    public static void log(Object object, Level level, String message, Object... params){
		logger.log(level, message, params);
    }
}
