package alfredo;

import alfredo.gfx.Spriter;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Component {
    protected Entity parent;
    
    public Component() {
        
    }
    
    public Entity getParent() { return parent; }
    
    public void ready() { }
    public void tick() { }
    public void draw(Spriter s) { }
    public void ui(Spriter s) { }
    
    public void destroy() {}
}
