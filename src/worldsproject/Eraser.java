package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.inpt.Button;
import alfredo.inpt.Cursor;
import alfredo.inpt.Mouse;
import alfredo.util.F;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Eraser extends Component {
    public static Entity create() {
        return Entity.create().chain(new Eraser());
    }
    
    Sprite cursor;
    
    Cursor pointer;
    Button click;
    public boolean enabled = false;

    private Eraser() {
        cursor = Spriter.retrieve("/resrc/img/eraser.png").simple();
        
        pointer = Cursor.create(Mouse.pointer).moved((Vector v) -> {
            parent.position.set(v);
        });
        
        click = Button.create(Mouse.LMB);
    }
    
    @Override
    public void tick() {
        if(enabled && click.isPressed()) {
            for(Entity e : Entity.all(WorldObject.class)) {
                if(F.euler(parent.position.minus(e.position)) <= F.sq(5)) {
                    e.destroy();
                }
            }
        }
    }
    
    @Override
    public void ui(Spriter s) {
        if(enabled) {
            cursor.alpha = click.isPressed() ? 1 : 0.5f;
            s.draw(cursor, parent);
        }
    }
}
