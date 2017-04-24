package worldsproject;

import alfredo.Component;
import alfredo.Debug;
import alfredo.Entity;
import alfredo.Game;
import alfredo.Rand;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.util.Curve;
import alfredo.util.F;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class NPC extends Component {
    
    Component goal = null;
    
    Sprite appearance;
    
    Curve curve = null;
    float time = 1;
    float speed = 0;
    
    static int count = 0;
    
    Sprite want = null;
    Sprite bubble;
    
    public int item = -1;
    Sprite itemApp = null;
    
    int itemPursue = -1;
    
    boolean getItem = false;
    
    Entity target;
    
    String[] needs = new String[] {
        "tree",
        "palm",
        "pine",
        "boulder",
        "shrubbery",
        "grass"
    };
    
    public static void create(Vector pos) {
        Entity.create(pos).chain(new NPC()).chain(new WorldObject()).chain(new Health());
        ++count;
    }
    
    void setGoal(Component c) {
        curve = null;
        want = null;
        
        if(goal != null) parent.removeComponent(goal);
        goal = c;
        
        if(c != null) parent.addComponent(c);
        
        if(c == null) {
            target(Entity.first(World.class).getComponent(World.class).getRandomNot(0));
            speed /= 3;
        }
    }
    
    public NPC() {
    }
    
    @Override
    public void ready() {
        appearance = Spriter.retrieve("/resrc/img/placeable/npc.png").simple();
        bubble = Spriter.retrieve("/resrc/img/npc/bubble.png").simple();
        appearance.alpha = 0;
        
        parent.addComponent(appearance);
    }
    
    Vector getRandPos() {
        Vector pos = parent.position.plus(Vector.fromDirection(Rand.f(0, 20), Rand.f(0, 360)));
        if(F.euler(pos) > F.sq(60)) {
            pos = Vector.fromDirection(60, pos.getDirection());
        }
        return pos;
    }
    
    @Override
    public void tick() {
        if(cooldown > 0) { --cooldown; }
        
        if(appearance.alpha < 1) {
            appearance.alpha += 0.05f;
            if(appearance.alpha >= 1) {
                appearance.alpha = 1;
                setGoal(null);
            }
            return;
        }
        
        if(curve != null) {
            if(time < 1) {
                parent.position.set(curve.evaluate(time));
                time += speed;
            }
            else {
                if(getItem) {
                    itemApp = target.getComponent(Sprite.class);
                    item = target.tag;
                    target.destroy();
                    target = null;
                    getItem = false;
                }
                else if(attack) {
                    attack(target);
                }
            }
        }

        if(goal == null) {
            if(time < 1) {
                return;
            }

            switch(Rand.i(0, 3)) {
                case 0:
                    setGoal(new BuildA("/resrc/img/building/statue", new int[] { 4, 4, 4 }));
                    return;
                case 1:
                    setGoal(new BuildA("/resrc/img/building/barracks", new int[] { 1, 4, 4, 4 }));
                    return;
                case 2:
                    if(parent.firstOther(NPC.class) != null) {
                        setGoal(new ThrowCoconut());
                        return;
                    }
                case 3:
                    if(House.count <= NPC.count / 3) {
                        setGoal(new BuildHouse());
                        return;
                    }
            }

            setGoal(null);
        }
    }
    
    @Override
    public void destroy() {
        --count;
    }
    
    public void bubble(Sprite what) {
        want = what;
    }
    
    int cooldownTime = 20;
    int cooldown = 0;
    int damage = 2;
    
    public void attack(Entity e) {
        if(e == null) { return; }
        if(cooldown == 0) {
            Health h = e.getComponent(Health.class);
            if(h != null) {
                h.health -= damage;
                cooldown = cooldownTime;
            }
        }
    }
    
    boolean attack = false;
    
    public boolean pursue(int item) {
        if(hasItem(item)) {
            return true;
        }
        
        drop();
        if(pickup(item)) {
            return true;
        }
        
        Entity e = parent.closest(item - 19);
        if(e == null) {
            bubble(Spriter.retrieve("/resrc/img/want/" + needs[item - 20] + ".png").simple());
            return false;
        }
        else if(target != e) {
            bubble(null);
            target = e;
            target(e.position);
            attack = true;
            getItem = false;
        }
        return true;
    }
    
    public boolean target(Vector position) {
        if(curve != null)
            if(curve.controlPoints[1].equal(position)) { return time >= 1; }
        curve = new Curve(parent.position.copy(), position.copy());
        speed = position.minus(parent.position).getMagnitude();
        if(speed > 1) { speed = 1 / speed; }
        time = 0;
        attack = false;
        getItem = false;
        return false;
    }
    
    public void discard() {
        item = -1;
        itemApp = null;
    }
    
    public void drop() {
        if(itemApp != null)
            Drop.create(parent.position, item, itemApp);
        discard();
    }
    
    public boolean hasItem(int item) {
        return this.item == item;
    }
    
    public boolean pickup(int item) {
        drop();
        
        Entity e = parent.closest(item);
        if(e == null) {
            return false;
        }
        else if(target != e) {
            this.target = e;
            target(target.position); 
            getItem = true;
        }
        return true;
    }
    
    @Override
    public void draw(Spriter s) {
        if(want != null) {
            s.draw(bubble, parent.position.plus(new Vector(8, -8)));
            s.draw(want, parent.position.plus(new Vector(8, -8)));
        }
        if(itemApp != null) {
             s.draw(itemApp, parent.position.plus(Vector.fromDirection(2, (float)parent.direction - 45)), parent.direction, 1);
        }
    }
}
