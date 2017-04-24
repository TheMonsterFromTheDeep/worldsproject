package alfredo.gfx;

import alfredo.Component;
import alfredo.geom.Vector;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Sprite extends Component {  
    private final int index;
    public Vector pivot;
    
    public float alpha = 1;
    
    public boolean active = true;
    
    private int width, height;
    
    Sprite(int index, Vector pivot, int width, int height) {
        this.index = index;
        this.pivot = pivot;
        this.width = width;
        this.height = height;
    }
    
    public int getIndex() { return index; }
    public Vector getPivot() { return pivot; }
    
    @Override
    public void draw(Spriter s) {
        if(active) {
            s.draw(this, parent.position.x, parent.position.y, parent.direction);
        }
    }
    
    public Sprite copy() {
        return new Sprite(index, pivot.copy(), width, height);
    }
    
    public boolean contains(Vector point) {
        return point.x >= parent.position.x && point.x <= parent.position.x + width &&
               point.y >= parent.position.y && point.y <= parent.position.y + height;
    }
    
    public boolean contains(Vector point, Vector center) {
        return point.x >= center.x - width / 2 && point.x <= center.x + width / 2 &&
               point.y >= center.y  - height / 2&& point.y <= center.y + height / 2;
    }
}
