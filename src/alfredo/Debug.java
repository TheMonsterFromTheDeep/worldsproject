package alfredo;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Debug {
    public static interface Logger {
        void info(String msg);
        void log(String msg);
        void warn(String msg);
        void error(String msg);
    }
    
    public static Logger createStandardLogger() {
        return new Debug.Logger() {
            @Override
            public void info(String msg) {
                System.out.println(msg);
            }

            @Override
            public void log(String msg) {
                System.out.println(msg);
            }
            
            @Override
            public void warn(String msg) {
                System.err.println(msg);
            }

            @Override
            public void error(String msg) {
                System.err.println(msg);
            }
        };
    }
    
    private static Logger logger = createStandardLogger();
    
    public static void setLogger(Logger l) {
        if(l != null) {
            logger = l;
        }
    }
    
    public static void info(String msg) { logger.info("[INFO] " + msg); }
    public static void log(String msg) { logger.log("[DEBUG] " + msg); }
    public static void warn(String msg) { logger.log("[WARN]" + msg); }
    public static void error(String msg) { logger.error("[ERROR] " + msg); }
}
