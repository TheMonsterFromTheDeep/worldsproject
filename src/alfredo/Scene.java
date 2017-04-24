package alfredo;

import alfredo.gfx.Spriter;
import alfredo.phx.Physics;
import java.util.ArrayList;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Scene {
    
    private static ArrayList<Scene> scenes;
    private static Scene current = null;
    
    protected int bgcolor;
    
    public static <T extends Scene> void open(Class<T> sceneClass) {
        if(scenes == null) {
            scenes = new ArrayList();
        }
        else {
            for(Scene s : scenes) {
                if(s.getClass() == sceneClass) {
                    current = s;
                    s.opened();
                    return;
                }
            }
        }
        try {
            current = sceneClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Error setting scene: " + ex);
        }
        scenes.add(current);
        current.setup();
        current.opened();
    }
    
    public static <T extends Scene> T get(Class<T> sceneClass) {
        if(scenes == null) {
            return null;
        }
        for(Scene s : scenes) {
            if(s.getClass() == sceneClass) {
                return (T) s;
            }
        }
        return null;
    }
    
    public static Scene getCurrent() { return current; }
    
    public Scene() { }
    
    public void render(Spriter s) {
        s.clear();
        s.fill(bgcolor, -Camera.getMain().position.x, -Camera.getMain().position.y, Camera.getMain().getViewport());
        
        backdrop(s);
        
        for(Entity e : Entity.all()) {
            for(Component co : e.getComponents()) {
                co.draw(s);
            }
        }
        
        for(Entity e : Entity.all()) {
            for(Component co : e.getComponents()) {
                co.ui(s);
            }
        }
        
        draw(s);
    }
    
    public void loop() {
        Physics.tick();
    }
    
    protected void setup() { }
    protected void opened() { }
    public void tick() { }
    public void backdrop(Spriter s) { }
    public void draw(Spriter s) { }
}
