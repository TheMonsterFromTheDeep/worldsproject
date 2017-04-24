package alfredo.phx;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Scene;
import alfredo.geom.Vector;
import java.util.ArrayList;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Physics {
    public static final Vector gravity = new Vector(0, 9.807f);
    
    public static void tick() {           
        for(Entity e : Entity.all(Body.class)) {
            e.getComponent(Body.class).acceleration.set(gravity);
        }
        
        Scene.getCurrent().tick();
        
        for(Entity e : Entity.all()) {
            for (Component c : e.getComponents()) {
                c.tick();
            }
        }
    }
}
