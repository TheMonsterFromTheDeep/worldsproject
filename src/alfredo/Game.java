package alfredo;

import alfredo.gfx.Spriter;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Game {
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
    
    private static Platform gamePlatform = null;
    
    public static interface Platform {
        Spriter create(int width, int height);
        void title(String title);
        void size(float width, float height);
        boolean setIcon(String path);
        void run();
        void saveScreenshot(String path);
        Debug.Logger createLogger();
    }
    
    private static long tick = 0;
    private static Timer timer = null;
    
    public static <T extends Platform> void platform(Class<T> platformClass) {
        if(gamePlatform != null) {
            throw new IllegalStateException("Game platform already established!");
        }
        
        try {
            gamePlatform = platformClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) { }
        
        Spriter.setSpriter(gamePlatform.create(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        Debug.setLogger(gamePlatform.createLogger());
        
        timer = new Timer(33, (ActionEvent e) -> {
            Scene.getCurrent().loop();
            
            ++tick;
        });
    }
    
    private static void testPlatform() {
        if(gamePlatform == null) {
            throw new IllegalStateException("Cannot perform action until Game.platform() has been called!");
        }
    }
    
    public static void title(String title) {
        testPlatform();
        gamePlatform.title(title);
    }
    
    public static void size(float width, float height) {
        testPlatform();
        gamePlatform.size(width, height);
    }
    
    public static void play() {        
        /* TODO: Reimplement this
        BufferedImage icon;
        try {
            icon = ImageGraphic.read("/resrc/img/icon.png");
            frame.setIconImage(icon);
        } catch (Exception ex) {
            System.out.println("No icon image found (resrc/img/icon.png)");
        }*/
        
        testPlatform();
        
        if(!gamePlatform.setIcon("/resrc/img/icon.png")) {
            Debug.info("No icon image found (resrc/img/icon.png)");
        }
        
        gamePlatform.run();
        timer.start();
    }
    
    public static void setDelay(int ms) {
        timer.setDelay(ms);
    }
    
    public static int getDelay() {
        return timer.getDelay();
    }
    
    public static void saveScreenshot(String path) {
        testPlatform();
        gamePlatform.saveScreenshot(path);
    }
    
    public static long getTick() {
        return tick;
    }
    
    static void updateCamera() {
        testPlatform();
    }
}
