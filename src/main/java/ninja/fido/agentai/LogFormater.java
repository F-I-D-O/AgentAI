/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author F.I.D.O.
 */
public class LogFormater extends Formatter{
	
	private static final String lineSeparator = System.getProperty("line.separator");

	@Override
	public String format(LogRecord record) {
		StringBuilder output = new StringBuilder()
			.append("[")
			.append(record.getLevel())
			.append("]: ")
			.append(formatMessage(record))
			.append(lineSeparator);
		return output.toString();	
	}
	
}
