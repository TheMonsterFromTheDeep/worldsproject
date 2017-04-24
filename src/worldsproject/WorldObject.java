package worldsproject;

import alfredo.Component;
import alfredo.Entity;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class WorldObject extends Component {
    World world;
   
    
    public WorldObject(World w) {
        this.world = w;
    }
    
    public WorldObject() {
        this(Entity.first(World.class).getComponent(World.class));
    }
    
    public void tick() {
        parent.direction = parent.position.minus(world.getParent().position).getDirection() + 90;
    }
}
