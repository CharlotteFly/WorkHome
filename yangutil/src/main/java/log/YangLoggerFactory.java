package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by hwyang on 2015/1/23.
 */
public class YangLoggerFactory {
    public static final String lineSeparator = System.getProperty("line.separator");
    private static Logger globalLogger = Logger.getGlobal();

    public static void setGlobalLogger(Logger logger) {
        globalLogger = logger;
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getSimpleName());
    }

    public static Logger getGlobalLogger() {
        return globalLogger;
    }

    public static Logger getFileLogger(Class<?> clazz, File file) {
        Logger logger = getLogger(clazz);
        try {
            Handler handler = new FileHandler(file.getAbsolutePath());
            Formatter formatter = new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + lineSeparator;
                }
            };
            handler.setLevel(Level.ALL);
            handler.setFormatter(formatter);
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return logger;
    }

}
