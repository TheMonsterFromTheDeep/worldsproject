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
public class BuildHouse extends Component {
    HouseTemplate house;
    Entity target;
    
    boolean hasWood = false;
    //boolean getItem = true;
    
    Sprite wood;
    
    int wait = 0;
    
    public BuildHouse() {
        wood = Spriter.retrieve("/resrc/img/drop/wood.png").simple();
        wood.active = false;
    }
    
    @Override
    public void ready() {
        parent.addComponent(wood);
        
        Entity h = parent.closest(HouseTemplate.class);
        if(h == null)
            house = HouseTemplate.create();
        else house = h.getComponent(HouseTemplate.class);
    }
    
    @Override
    public void destroy() {
        parent.removeComponent(wood);
    }
    
    @Override
    public void tick() { 
        NPC npc = parent.getComponent(NPC.class);
        if(npc.hasItem(Structure.WOOD)) {
            if(npc.target(house.getParent().position)) {
                npc.discard();
                if(house.addWood()) {
                    npc.setGoal(null);
                }
            }
        }
        else {
           if(!npc.pursue(Structure.WOOD)) { ++wait; }
           else { wait = 0; }
           
           if(wait + Rand.i(0, 5) > 50) { npc.setGoal(null); }
        }
    }
}
