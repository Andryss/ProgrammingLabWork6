package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServerController {

    private static final Logger logger = LogManager.getLogger();

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message, Throwable error) {
        logger.error(message,error);
    }

}
