package alfredo.inpt;

import alfredo.geom.Vector;
import java.util.ArrayList;

/**
 * A Cursor represents a control that refers to a single point -
 * so, the mouse.
 * 
 * May also be used for scroll wheels.
 * @author TheMonsterOfTheDeep
 */
public class Cursor {
    public static interface Action {
        void moved(Vector to);
    }
    
    public static class Source {
        private final ArrayList<Cursor> cursors;
        
        public Source() {
            cursors = new ArrayList();
        }
        
        public void set(float x, float y) {
            for(Cursor c : cursors) {
                c.move(x, y);
            }
        }
        
        private void add(Cursor c) {
            cursors.add(c);
        }
        
        private void remove(Cursor c) {
            cursors.remove(c);
        }
    }
    
    private final Vector position;
    private Source device = null;
    
    private final ArrayList<Action> actions = new ArrayList();
    
    private void move(float x, float y) {
        position.set(x, y);
        for(Action act : actions) {
            act.moved(new Vector(x, y));
        }
    }
    
    public Vector getPosition() {
        return position.copy(); //This irratingly creates an extra pointer and everything
                                //But it's the only real way to guarantee safety
    }
    
    public float getX() {
        return position.x;
    }
    
    public float getY() {
        return position.y;
    }
    
    public Cursor moved(Action act) {
        actions.add(act);
        return this;
    }
    
    private Cursor() {
        position = new Vector();
    }
    
    public static Cursor create() {
        return new Cursor();
    }
    
    public static Cursor create(Cursor.Source d) {
        Cursor c = new Cursor();
        return c.setDevice(d);
    }
    
    public Cursor setDevice(Cursor.Source d) {
        if(d == null) {
            System.out.println("Warning: Attempting to assign Cursor null device");
            return this;
        }
        if(device != null) device.remove(this);
        device = d;
        device.add(this);
        return this;
    }
}
