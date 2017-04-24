package worldsproject;

import alfredo.Component;
import alfredo.Debug;
import alfredo.Entity;
import alfredo.Game;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.inpt.Button;
import alfredo.inpt.Cursor;
import alfredo.inpt.Key;
import alfredo.inpt.Mouse;
import alfredo.util.F;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Structurer extends Component {
    public static Entity create() {
        return Entity.create()
                .chain(new Structurer());
    }
    
    Cursor mouse;
    Button create;
    
    public Editor editor;
    
    Sprite[] structures;
    
    Sprite current;
    
    Sprite cursor;
    
    boolean enabled = false;
    private int currentIndex = 0;
    
    public Structurer() {
        mouse = Cursor.create(Mouse.pointer);

        structures = Spriter.retrieveAll("/resrc/img/placeable/",
            "tree.png",
            "palm.png",
            "pine.png",
            "boulder.png",
            "shrubbery.png",
            "grass.png",
            "npc.png",
            "pig.png",
            "cow.png",
            "fish.png",
            "horse.png",
            "shark.png"
        );
        
        current = structures[0];
        
        create = Button.create(Mouse.LMB).press(() -> {
            if(enabled) {
                if(F.euler(parent.position.minus(Editor.current.getParent().position)) <= F.sq(64)) {
                    if(currentIndex < 6) {
                        Structure.create(parent.position, current.copy(), currentIndex);
                    }
                    else {
                        switch(currentIndex - 6) {
                            case 0:
                                NPC.create(parent.position);
                                break;
                            case 1:
                                Animal.create("pig", parent.position, 0.25f);
                                break;
                            case 2:
                                Animal.create("cow", parent.position, 0.26f);
                                break;
                            case 3:
                                Fish.create("fish", parent.position, 0.30f);
                                break;
                            case 4:
                                Animal.create("horse", parent.position, 1.1f);
                                break;
                            case 5:
                                Fish.create("shark", parent.position, 1.05f);
                                break;
                        }
                    }
                }
            }
        });
        
        cursor = Spriter.retrieve("/resrc/img/circletool.png").simple();
    }
    
    @Override
    public void ready() {
    }
    
    public void load(int index) {
        if(index >= structures.length) return;
        current = structures[index];
        currentIndex = index;
    }
    
    @Override
    public void tick() {
        parent.position.set(mouse.getPosition());

        parent.direction = parent.position.minus(editor.current.getParent().position).getDirection() + 90;
    }
    
    @Override
    public void ui(Spriter s) {
        if(!enabled) return;
        
        if(F.euler(parent.position.minus(Editor.current.getParent().position)) <= F.sq(64)) {
            current.alpha = 0.5f;
            s.draw(current, parent);
        }
        else {
            s.drawDir(cursor, parent, 0);
        }
    }
}
