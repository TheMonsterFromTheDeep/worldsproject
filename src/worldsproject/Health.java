package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Health extends Component {
    public float health = 10;
    
    Sprite bar;
    
    @Override
    public void ready() {
        bar = Spriter.retrieve("/resrc/img/health.png").simple();
    }
    
    @Override
    public void ui(Spriter s) {
        if(health < 10) {
            s.draw(bar, parent.position.plus(new Vector(0, -5)));
            s.fill(0xff0000, parent.position.plus(new Vector(-5, -6)), health, 1);
        }
    }
    
    @Override
    public void tick() {
        if(parent.tag != 4) {
            if(Entity.first(World.class).getComponent(World.class).type(parent.position) == 0) {
                health -= 0.05f;
            }
        }
        
        if(health <= 0) {
            parent.destroy();
        }
    }
}
