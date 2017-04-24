package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.geom.Vector;
import alfredo.gfx.Sprite;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Drop extends Component {
    public static void create(Vector pos, int tag, Sprite kind) {
        Entity.create(pos).tag(tag).chain(kind).chain(new WorldObject());
    }
}
