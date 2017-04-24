package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class House extends Component {
    public static int count = 0;
    
    public static Entity create(Vector pos) {
        ++count;
        return Entity.create(pos).chain(Spriter.retrieve("/resrc/img/building/house.png").simple()).chain(new House()).chain(new WorldObject());
    }
    
    @Override
    public void destroy() {
        --count;
    }
}
