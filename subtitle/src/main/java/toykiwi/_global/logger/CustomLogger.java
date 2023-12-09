package toykiwi._global.logger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 프로그램에서 일어나는 외부 환경과의 상호작용들을 로깅하기 위해서
public class CustomLogger {
    public static Logger logger = LoggerFactory.getLogger("toykiwi.subtitle.custom");
    public static Map<CustomLoggerType, String> customLoggerTypeMap = new HashMap<>() {{
        put(CustomLoggerType.ENTER_EXIT, "ENTER/EXIT");
        put(CustomLoggerType.ENTER, "ENTER");
        put(CustomLoggerType.EXIT, "EXIT");
        put(CustomLoggerType.EFFECT, "EFFECT");
    }};


    public static void debug(CustomLoggerType type, String message, String params) {
        logger.debug(CustomLogger.buildNormalMessages(type, message, params));
    }

    public static void debug(CustomLoggerType type, String message) {
        logger.debug(CustomLogger.buildNormalMessages(type, message, "{}"));
    }

    public static void debug(CustomLoggerType type) {
        logger.debug(CustomLogger.buildNormalMessages(type, "", "{}"));
    }

    public static String buildNormalMessages(CustomLoggerType type, String message, String params) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return String.format("[%s] [%s] %s: %s", ste[3].toString(), CustomLogger.customLoggerTypeMap.get(type), message, params);
    }


    public static void error(Exception e, String message, String params) {
        logger.error(CustomLogger.buildErrorMessages(e, message, params));
    }

    public static void error(Exception e, String message) {
        logger.error(CustomLogger.buildErrorMessages(e, message, "{}"));
    }

    public static void error(Exception e) {
        logger.error(CustomLogger.buildErrorMessages(e, "", "{}"));
    }

    public static String buildErrorMessages(Exception e, String message, String params) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return String.format("[%s] [%s] %s: %s", ste[3].toString(), e.getClass().getName(), e.getMessage() + " / " + message, params) + "\n" + e.getStackTrace().toString();
    }
}
