package alfredo.inpt;

import alfredo.Debug;
import alfredo.Scene;
import java.util.ArrayList;

/**
 * A Button represents a control that has two states - this
 * mostly means either a key on a keyboard or a mouse button.
 * @author TheMonsterOfTheDeep
 */
public final class Button {
    public static interface Action {
        void perform();
    }
    
    public static class Source {
        private final ArrayList<Button> buttons;
        
        public Source() {
            buttons = new ArrayList();
        }
        
        public void press() {
            for(Button b : buttons) {
                 /* Keyboards will repeatedly claim to be pressed - we only want to activate *
                  * event once until it is released and pressed again                        */
                if(!b.pressed) {
                    b.performPress();
                }
            }
        }
        
        public void release() {
            for(Button b : buttons) {
                if(b.pressed) {
                    b.performRelease();
                }
            }
        }
        
        private void add(Button b) {
            buttons.add(b);
        }
        
        private void remove(Button b) {
            buttons.remove(b);
        }
    }
    
    private static class SpecificAction {
        Action action;
        Scene scene;
        
        public SpecificAction(Action action) {
            this.action = action;
            this.scene = Scene.getCurrent();
        }
        
        void perform() {
            if(Scene.getCurrent() == scene) {
                action.perform();
            }
        }
    }
    
    private Source device = null;
    private boolean pressed = false;
    
    private final ArrayList<SpecificAction> downActions = new ArrayList();
    private final ArrayList<SpecificAction> upActions = new ArrayList();
    
    private void performPress() {
        pressed = true;
        for(SpecificAction act : downActions) {
            act.perform();
        }
    }
    
    private void performRelease() {
        pressed = false;
        for(SpecificAction act : upActions) {
            act.perform();
        }
    }
    
    public boolean isPressed() {
        return pressed;
    }
    
    public Button press(Action act) {
        downActions.add(new SpecificAction(act));
        return this;
    }
    
    public Button release(Action act) {
        upActions.add(new SpecificAction(act));
        return this;
    }
    
    private Button() { }
    
    public static Button create() {
        return new Button();
    }
    
    public static Button create(Source d) {
        Button b = new Button();
        return b.setDevice(d);
    }
    
    public Button setDevice(Source d) {
        if(d == null) {
            System.out.println("Warning: Attempting to assign Button null device");
            return this;
        }
        if(device != null) device.remove(this);
        device = d;
        device.add(this);
        return this;
    }
}
