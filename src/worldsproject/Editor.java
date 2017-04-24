package worldsproject;

import alfredo.Debug;
import alfredo.Entity;
import alfredo.Game;
import alfredo.Scene;
import alfredo.Sound;
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
public class Editor extends Scene {
    public static World current = null;
    
    Brush brush;
    Structurer structure;
    Eraser eraser;
    
    int selected = 0;
    
    Vector selectedPos = new Vector(-109, -79);
    
    Sprite bg;
    Sprite select;
    
    Cursor pointer;
    Button click;
    
    boolean active = false;
    
    Sound music;
    
    @Override
    protected void setup() {
        brush = Brush.create().getComponent(Brush.class);
        brush.editor = this;
        structure = Structurer.create().getComponent(Structurer.class);
        structure.editor = this;
        eraser = Eraser.create().getComponent(Eraser.class);
        
        bg = Spriter.retrieve("/resrc/img/editor.png").simple();
        select = Spriter.retrieve("/resrc/img/select.png").simple();
        
        music = Sound.load("/resrc/snd/music.wav");
        music.loop();
        
        pointer = Cursor.create(Mouse.pointer);
        click = Button.create(Mouse.LMB).press(() -> {
            //if(!active) return;
            
            int newSelect = 0;
            for(int y = 0; y < 3; ++y) {
                for(int x = 0; x < 2; ++x) {
                    Vector pos = new Vector(-109 + x * 21, -79 + y * 21);
                    if(select.contains(pointer.getPosition(), pos)) {
                        selected = newSelect;
                        selectedPos = pos;
                    }
                    ++newSelect;
                }
            }
            
            for(int y = 0; y < 3; ++y) {
                for(int x = 0; x < 2; ++x) {
                    Vector pos = new Vector(-109 + x * 21, 37 + y * 21);
                    if(select.contains(pointer.getPosition(), pos)) {
                        selected = newSelect;
                        selectedPos = pos;
                    }
                    ++newSelect;
                }
            }
            
            for(int y = 0; y < 3; ++y) {
                for(int x = 0; x < 2; ++x) {
                    Vector pos = new Vector(88 + x * 21, -79 + y * 21);
                    if(select.contains(pointer.getPosition(), pos)) {
                        selected = newSelect;
                        selectedPos = pos;
                    }
                    ++newSelect;
                }
            }
            
            for(int y = 0; y < 3; ++y) {
                for(int x = 0; x < 2; ++x) {
                    Vector pos = new Vector(88 + x * 21, 37 + y * 21);
                    if(select.contains(pointer.getPosition(), pos)) {
                        selected = newSelect;
                        selectedPos = pos;
                    }
                    ++newSelect;
                }
            }
            
            if(select.contains(pointer.getPosition(), new Vector(109, -16))) {
                selected = 100;
                selectedPos = new Vector(109, -16);
                brush.enabled = false;
                structure.enabled = false;
                eraser.enabled = true;
            }
            
            //if(select.contains(pointer.getPosition(), new Vector(109, 16))) {
                //NPC.create(current);
                //return;
            //}
            
            int bucket = selected / 6;
            if(bucket == 0 || bucket == 1) {
                brush.enabled = true;
                structure.enabled = false;
                eraser.enabled = false;
                brush.load(selected);
                
            }
            if(bucket == 2 || bucket == 3) {
                brush.enabled = false;
                structure.enabled = true;
                eraser.enabled = false;
                structure.load(selected - 12);
                
            }
        });
    }
    
    @Override
    public void opened() {
        for(Entity e : Entity.all(World.class)) {
            e.getComponent(World.class).show = false;
        }
        current.show = true;
        active = true;
        
        brush.enabled = true;
        structure.enabled = false;
        eraser.enabled = false;
        
        selected = 0;
        selectedPos = new Vector(-109, -79);
        
        brush.load(selected);
        
        beginRecord = Button.create(Key.R).press(() -> {
            save = true;
        });
    }
    
    public static void edit(Entity world) {
        world.position.set(0, 0);
        current = world.getComponent(World.class);
        
        Scene.open(Editor.class);
    }
    
    public static void start(Entity world) {
        edit(world);
        Scene.get(Editor.class).brush.paintDefault();
    }
    
    @Override
    public void backdrop(Spriter s) {
        s.draw(bg, 0, 0);
    }
    
    @Override
    public void draw(Spriter s) {
        s.draw(select, selectedPos);
    }
    
    boolean save = false;
    Button beginRecord;
    
    @Override
    public void tick() {
        if(save) {
            //Game.saveScreenshot("npc/out");
        }
        
        Entity.sort((Entity a, Entity b) -> {
            WorldObject wa = a.getComponent(WorldObject.class);
            WorldObject wb = b.getComponent(WorldObject.class);
            if(wa == null && wb != null) {
                return -1;
            }
            if(wa != null && wb == null) {
                return 1;
            }
            return (int)(Math.round(F.euler(b.position) - F.euler(a.position)));
        });
    }
}
