package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Rand;
import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class ThrowCoconut extends Component {
    
    int wait = 0;
    
    Entity target;
    
    @Override
    public void tick() { 
        NPC npc = parent.getComponent(NPC.class);
        if(npc.hasItem(Structure.COCONUT)) {
            npc.discard();
            Coconut.create(parent, parent.firstOther(NPC.class));
            npc.setGoal(null);
        }
        else {
            if(!npc.pursue(Structure.COCONUT)) { ++wait; }
            else { wait = 0; }
           
            if(wait + Rand.i(0, 5) > 50) { npc.setGoal(null); }
        }
        
        /*if(target == null) {
            target = parent.closest(2);
            if(target == null) {
                ++wait;
                parent.getComponent(NPC.class).bubble(Spriter.retrieve("/resrc/img/want/palm.png").simple());
                if(wait + Rand.i(0, 5) > 50) {
                    parent.getComponent(NPC.class).setGoal(null);
                }
                return;
            }

            wait = 0;

            parent.getComponent(NPC.class).bubble(null);
            parent.getComponent(NPC.class).target(target.position);
        }
        
        if(!target.isAlive()) {
            parent.getComponent(NPC.class).setGoal(null);
            return;
        }
        
        if(parent.getComponent(NPC.class).time > 1) {
            target.destroy();
            Coconut.create(parent, parent.firstOther(NPC.class));
        }*/
    }
}
