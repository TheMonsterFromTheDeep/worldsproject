package worldsproject;

import alfredo.Component;
import alfredo.Debug;
import alfredo.Entity;
import alfredo.Rand;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.util.Curve;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class BuildA extends Component {
    Entity target;
    
    boolean holding = false;
    
    int wait = 0;
    
    int[] materials;
    boolean[] done;
    
    int current = 0;
    
    Vector where;
    String path;
    
    Entity partial;
    
    public BuildA(String path, int[] materials) {
        this.path = path;
        this.materials = materials;
        
        this.where = Entity.first(World.class).getComponent(World.class).getRandomNot(0, 4);
        
        done = new boolean[materials.length];
        for(int i = 0; i < done.length; ++i) {
            done[i] = false;
        }
    }
    
    @Override
    public void ready() {
        //parent.addComponent(wood);
    }
    
    @Override
    public void destroy() {
        //parent.removeComponent(wood);
    }
    
    @Override
    public void tick() { 
        NPC npc = parent.getComponent(NPC.class);
        if(npc.hasItem(materials[current] + 19)) {
            
            if(npc.target(where)) {
                npc.discard();
                
                boolean complete = true;
                int count = 0;
                
                done[current] = true;
                
                for(boolean b : done) {
                    complete = complete && b;
                    if(b) ++count;
                }
                
                if(partial != null) partial.destroy();
                
                if(complete) {
                    Structure.create(where, Spriter.retrieve(path + ".png").simple());
                }
                else {
                    partial = Structure.create(where, Spriter.retrieve(path + count + ".png").simple());
                }
                
                ++current;
                
                if(complete) {
                    npc.setGoal(null);
                }
            }
        }
        else {
           if(!npc.pursue(materials[current] + 19)) { ++wait; }
           else { wait = 0; }
           
           if(wait + Rand.i(0, 5) > 50) { npc.setGoal(null); }
        }
    }
}
