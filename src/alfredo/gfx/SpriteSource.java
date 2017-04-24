package alfredo.gfx;

import alfredo.geom.Vector;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class SpriteSource {
    private final int index;
    private final int width, height;
    
    public SpriteSource(int index, int width, int height) {
        this.index = index;
        this.width = width;
        this.height = height;
    }
    
    public Sprite simple() {
        return new Sprite(index, new Vector(), width, height);
    }
    
    public Sprite ui() {
        return new UISprite(index, new Vector(), width, height);
    }
}
