package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Landing extends Component {
    public static Entity create(Vector where) {
        return Entity.create(where)
                .chain(new Landing())
                .chain(new WorldObject())
                .chain(Spriter.retrieve("/resrc/img/tools/landing.png").simple());
    }
}
