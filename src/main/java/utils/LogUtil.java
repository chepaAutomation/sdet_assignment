package utils;

import org.apache.log4j.Logger;

public class LogUtil {

    final static Logger logger = Logger.getLogger(LogUtil.class);

    private LogUtil() {
    }

    public static Logger getLogger() {
        return logger;
    }

}
