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
public class Structure extends Component {
    public static Entity create(Vector pos, Sprite appearance, int tag) {
        return Entity.create(pos)
                .chain(new Structure())
                .chain(new WorldObject())
                .chain(appearance)
                .chain(new Health())
                .tag(tag + 1);
    }
    
    public static Entity create(Vector pos, Sprite appearance) {
        return create(pos, appearance, -1);
    }
    
    @Override
    public void destroy() {
        if(parent.tag == TREE) {
            for(int i = 0; i < Rand.i(1, 3); ++i) {
                Drop.create(parent.position.plus(Vector.random(-1, -1, 1, 1)), WOOD, Spriter.retrieve("/resrc/img/drop/wood.png").simple());
            }
        }
        if(parent.tag == PALM) {
            for(int i = 0; i < Rand.i(3, 5); ++i) {
                Drop.create(parent.position.plus(Vector.random(-1, -1, 1, 1)), COCONUT, Spriter.retrieve("/resrc/img/drop/coconut.png").simple());
            }
        }
        if(parent.tag == BOULDER) {
            for(int i = 0; i < Rand.i(1, 3); ++i) {
                Drop.create(parent.position.plus(Vector.random(-1, -1, 1, 1)), ROCK, Spriter.retrieve("/resrc/img/drop/boulder.png").simple());
            }
        }
        if(parent.tag == CACTI) {
            for(int i = 0; i < Rand.i(1, 3); ++i) {
                Drop.create(parent.position.plus(Vector.random(-1, -1, 1, 1)), CACTUS, Spriter.retrieve("/resrc/img/drop/grass.png").simple());
            }
        }
    }
    
    public static int TREE = 1;
    public static int PALM = 2;
    public static int FIR = 3;
    public static int BOULDER = 4;
    public static int SHRUB = 5;
    public static int CACTI = 6;
    
    public static int WOOD = 20;
    public static int COCONUT = 21;
    public static int NEEDLES = 22;
    public static int ROCK = 23;
    public static int CACTUS = 25;
    //3, 4, 5, 6
}
