package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Game;
import alfredo.geom.Vector;
import alfredo.gfx.Spriter;
import alfredo.util.Curve;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Animal extends Component {
    public static void create(String name, Vector pos, float speed) {
        Entity.create(pos).chain(new Health()).chain(new Animal(speed)).chain(new WorldObject()).chain(Spriter.retrieve("/resrc/img/placeable/" + name + ".png").simple());
    }
    
    Curve curve;

    float time = 0;
    float speed = 0;
    
    float baseSpeed;
    
    public Animal(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
    
    void target() {
        Vector pos = Entity.first(World.class).getComponent(World.class).getRandom(3);
        curve = new Curve(parent.position.copy(), pos);
        speed = baseSpeed / pos.minus(parent.position).getMagnitude();
        time = 0;
    }
    
    @Override
    public void ready() {
        target();
        
    }
    
    @Override
    public void tick() {
        time += speed;
        parent.position.set(curve.evaluate(time));
        
        if(time > 1) {
            target();
        }
    }
}
