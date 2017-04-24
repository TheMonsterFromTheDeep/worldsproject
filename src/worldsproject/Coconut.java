package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.ParticleSystem;
import alfredo.gfx.Spriter;
import alfredo.util.Curve;

/**
 *
 * @author TheMonsterOfTheDeep
 */
class Coconut extends Component {
    public static void create(Entity start, Entity e) {
        if(e != null)
            Entity.create(start).chain(new Coconut(start, e)).chain(Spriter.retrieve("/resrc/img/drop/coconut.png").simple()).chain(new WorldObject());
    }
    
    Curve curve;
    
    float time = 0;
    
    public Coconut(Entity start, Entity end) {
        curve = new Curve(start.position.copy(), start.position.plus(end.position).times(0.5f).plus(new Vector(0, -15)), end.position.copy());
    }
    
    @Override
    public void tick() {
        time += 0.05f;
        
        if(time >= 1) {
            parent.destroy();
            ParticleSystem.create(parent.position.copy(), Spriter.retrieve("/resrc/img/npc/cocoparticle.png").simple())
                    .alpha(new Vector(0, 1), new Vector(1, 0))
                    .length(5)
                    .lifetime(5);
        }
        
        parent.position.set(curve.evaluate(time));
    }
}
