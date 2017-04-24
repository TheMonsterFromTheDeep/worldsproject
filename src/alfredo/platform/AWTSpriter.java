package alfredo.platform;

import alfredo.Camera;
import alfredo.geom.Vector;
import alfredo.gfx.EditableSprite;
import alfredo.gfx.Sprite;
import alfredo.util.IndexedStringDict;
import alfredo.gfx.SpriteSource;
import alfredo.gfx.Spriter;
import alfredo.gfx.Texture;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public final class AWTSpriter extends Spriter {

    private final IndexedStringDict<BufferedImage> dict;
    
    public BufferedImage buffer = null;
    private Graphics2D graphics = null;
    
    private void createBuffer(int width, int height) {
        if(width <= 0) { width = 1; }
        if(height <= 0) { height = 1; }
        
        if(buffer != null) {
            buffer.flush();
        }
        
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Shape oldClip = null;
        
        if(graphics != null) {
            oldClip = graphics.getClip();
            graphics.dispose();
        }
        
        graphics = buffer.createGraphics();
        
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(oldClip != null) {
            graphics.setClip(oldClip);
        }
    }
    
    public AWTSpriter(int width, int height) {
        dict = new IndexedStringDict();
        
        createBuffer(width, height);
    }
    
    @Override
    protected void onResize(int width, int height) {
        createBuffer(width, height);
    }
    
    public static BufferedImage load(String path) throws Exception {
        return ImageIO.read(AWTSpriter.class.getResourceAsStream(path));
    }
    
    private static BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(AWTSpriter.class.getResourceAsStream(path));
        } catch (Exception ex) {
            error(path, ex);
            return null;
        }
    }
    
    @Override
    protected SpriteSource getSource(String path) {
        //I literally created the Or class just so this line is nice and expressive\
        int index = dict.indexOf(path).or(() -> dict.add(path, loadSprite(path)));
        return new SpriteSource(index, dict.getAlways(index).getWidth(), dict.getAlways(index).getHeight());
    }
    
    @Override
    protected void drawImpl(Sprite s, float x, float y, float inScale, double angle, float opacity) {     
        BufferedImage image = dict.getAlways(s.getIndex());

        if(image == null) { return; }
        
        float scale = inScale;
        
        float w = image.getWidth() / 2f;
        float h = image.getHeight() / 2f;
        
        Vector pivot = s.getPivot();
        
        AffineTransform transform = new AffineTransform(); 

        transform.translate(x, y);
        transform.rotate(Math.toRadians(angle), 0, 0);
        
        transform.scale(scale, scale);
        transform.translate(-w - pivot.x, -h - pivot.y);
        
        Composite old = graphics.getComposite();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        graphics.drawImage(image, transform, null);
        graphics.setComposite(old);
    }
    
    @Override
    protected void fillImpl(int color, float x, float y, float width, float height) {       
        AffineTransform old = graphics.getTransform();
        
        float scale = Camera.getMain().getScale();
        
        graphics.translate(x, y);
        graphics.scale(width * scale, height * scale);
        
        graphics.setColor(new Color(color, true));
        graphics.fillRect(0, 0, 1, 1);
        
        graphics.setTransform(old);
    }

    @Override
    public void clip(int x, int y, int width, int height) {
        graphics.setClip(x, y, width, height);
    }

    @Override
    public void clear() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    @Override
    protected EditableSprite getEditable(String path) {
        int index = dict.add(path, loadSprite(path));
        return new EditableSprite(index, new Vector(), dict.getAlways(index).getWidth(), dict.getAlways(index).getHeight()) {
            @Override
            public void set(int x, int y, int rgb) {
                dict.getAlways(getIndex()).setRGB(x, y, rgb);
            }
        };
    }
    
    @Override
    protected EditableSprite getEditable(Vector size) {
        return new EditableSprite(dict.add("", new BufferedImage((int)size.x, (int)size.y, BufferedImage.TYPE_INT_ARGB)), new Vector(), (int)size.x, (int)size.y) {
            @Override
            public void set(int x, int y, int rgb) {
                dict.getAlways(getIndex()).setRGB(x, y, rgb);
            }
        };
    }
    
    @Override
    protected Texture getTexture(String path) {
        int index = dict.indexOf(path).or(() -> dict.add(path, loadSprite(path)));
        return new Texture(index, new Vector(), dict.getAlways(index).getWidth(), dict.getAlways(index).getHeight()) {
            @Override
            public int get(int x, int y) {
                BufferedImage image = dict.getAlways(getIndex());
                return image.getRGB(x % image.getWidth(), y % image.getHeight());
            }
        };
    }
}
