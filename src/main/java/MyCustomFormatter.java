import java.util.logging.*;

public class MyCustomFormatter {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(MyCustomFormatter.class.getName());
		logger.setUseParentHandlers(false);
		PatternFormatter formatter = new PatternFormatter();
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(formatter);
		logger.addHandler(handler);
		logger.info(" Example of creating custom formatter.");
		logger.warning(" A warning message.");
		logger.severe(" A severe message.");
	}
}


