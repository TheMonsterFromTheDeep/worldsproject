package alfredo.inpt;

import alfredo.Camera;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Mouse {
    public static class Pointer extends Cursor.Source {
        @Override
        public void set(float x, float y) {
            super.set(Camera.getMain().windowX(x), Camera.getMain().windowY(y));
        }
    }
    
    public static final Button.Source LMB = new Button.Source();
    public static final Button.Source MMB = new Button.Source();
    public static final Button.Source RMB = new Button.Source();
    public static final Pointer pointer = new Pointer();
}
