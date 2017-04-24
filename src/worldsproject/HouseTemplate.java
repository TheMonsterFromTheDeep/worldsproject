package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Rand;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
class HouseTemplate extends Component {
    public static HouseTemplate create() {
        return Entity.create().chain(new WorldObject()).addComponent(new HouseTemplate());
    }
    
    public int wood = 0;
    
    Sprite stage1;
    Sprite stage2;
    
    @Override
    public void ready() {
        stage1 = Spriter.retrieve("/resrc/img/building/house1.png").simple();
        stage2 = Spriter.retrieve("/resrc/img/building/house2.png").simple();
        
        stage1.active = stage2.active = false;
        
        parent.chain(stage1).chain(stage2);
        
        parent.position.set(Entity.first(World.class).getComponent(World.class).getRandom(1, 3));//Vector.fromDirection(Rand.f(0, 60), Rand.f(0, 360)));
    }
    
    public boolean addWood() {
        ++wood;
        if(wood == 1) {
            stage1.active = true;
        }
        if(wood == 2) {
            stage1.active = false;
            stage2.active = true;
        }
        if(wood == 3) {
            House.create(parent.position);
            parent.destroy();
            return true;
        }
        
        return false;
    }
}
