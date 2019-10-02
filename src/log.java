//Steven Sanchez
//COMP 585
//Due: October 9, 2018
//Project 2 - Find and Replace
//Purpose: TO create a find and replace application

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class log {

    private static final Logger logger = LogManager.getLogger(log.class);

    public void Log(String string, String message){

        if(string.equals("debug"))
        logger.debug(message);

        if(string.equals("info"))
        logger.info(message);

        if(string.equals("warn"))
        logger.warn(message);

        if(string.equals("fatal"))
        logger.fatal(message);

        if(string.equals("error"))
        logger.error(message);

    }

}
