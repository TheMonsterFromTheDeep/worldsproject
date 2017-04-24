package worldsproject;

import alfredo.Component;
import alfredo.Debug;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.gfx.Texture;
import alfredo.inpt.Button;
import alfredo.inpt.Cursor;
import alfredo.inpt.Key;
import alfredo.inpt.Mouse;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Brush extends Component {
    public static Entity create() {
        return Entity.create()
                .chain(new Brush());
    }
    
    public Editor editor;
    Cursor pointer;
    
    Button paint;
    
    Texture[] surfaces;
    
    Texture current;
    int currentIndex = 0;
    boolean enabled = true;
    
    Sprite cursor;
    
    public Brush() {
        paint = Button.create(Mouse.LMB);
        
        surfaces = Spriter.textureAll("/resrc/img/surface/",
            "magma.png",
            "rock.png",
            "dirt.png",
            "grass.png",
            "water.png",
            "sand.png",
            "flowers.png",
            "shells.png",
            "pebbles.png",
            "leaf.png",
            "fissure.png",
            "leafpad.png"
        );
        
        current = surfaces[0];
        
        cursor = Spriter.retrieve("/resrc/img/circletool.png").ui();
    }
    
    @Override
    public void ready() {
        parent.addComponent(cursor);
        
        pointer = Cursor.create(Mouse.pointer).moved((Vector pos) -> {
            parent.position.set(pos);
        });
    }
    
    public void paintDefault() {
        for(int x = 0; x <= 128; x += 8) {
            for(int y = 0; y <= 128; y += 8) {
                editor.current.paint(new Vector(x, y), surfaces[0], (char)0);
            }
        }
    }
    
    public void load(int index) {
        if(index >= surfaces.length) return;
        current = surfaces[index];
        currentIndex = index;
    }
    
    @Override
    public void tick() {
        cursor.active = enabled;
        
        if(paint.isPressed()) {
            if(!enabled) return;
            char type = (char) (currentIndex);
            if(type > 5) { type = 255; }
            editor.current.paint(pointer.getPosition().minus(editor.current.getParent().position).plus(new Vector(64, 64)), current, type);
        }
    }
}
