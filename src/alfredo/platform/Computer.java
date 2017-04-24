package alfredo.platform;

import alfredo.Debug;
import alfredo.Game;
import alfredo.Scene;
import alfredo.gfx.Spriter;
import alfredo.inpt.Key;
import alfredo.inpt.Mouse;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a device that can be accessed through AWT.
 * @author TheMonsterOfTheDeep
 */
public class Computer implements Game.Platform {

    private JFrame frame;
    private JPanel panel;
    
    //private Canvas canvas;
    private AWTSpriter spriter;
    
    @Override
    public Spriter create(int width, int height) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.spriter = new AWTSpriter(width, height);
        
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                Scene.getCurrent().render(spriter);
                
                g.drawImage(spriter.buffer, 0, 0, null);
                
                repaint();
            }
        };
        
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                for(int i = 0; i < Key.keys.length; ++i) {
                    if(i == e.getKeyCode()) {
                        Key.keys[i].press();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for(int i = 0; i < Key.keys.length; ++i) {
                    if(i == e.getKeyCode()) {
                        Key.keys[i].release();
                    }
                }
            }
            
            @Override public void keyTyped(KeyEvent e) { }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                (button == MouseEvent.BUTTON1 ? Mouse.LMB :      //I think this is probably cleaner than a switch
                 button == MouseEvent.BUTTON2 ? Mouse.MMB : Mouse.RMB).press();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int button = e.getButton();
                (button == MouseEvent.BUTTON1 ? Mouse.LMB :
                 button == MouseEvent.BUTTON2 ? Mouse.MMB : Mouse.RMB).release();
            }

            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Mouse.pointer.set(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Mouse.pointer.set(e.getX(), e.getY());
            }
        });
        
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                spriter.resize(panel.getWidth(), panel.getHeight());
            }
        });
        
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel);
        frame.pack();
        
        return spriter;
    }
    
    @Override
    public void title(String title) {
        frame.setTitle(title);
    }

    @Override
    public void size(float width, float height) {
        panel.setPreferredSize(new Dimension((int)width, (int)height));
        spriter.resize((int)width, (int)height);
        frame.pack();
    }

    @Override
    public boolean setIcon(String path) {
        try {
            BufferedImage icon = AWTSpriter.load(path);
            frame.setIconImage(icon);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public void run() {
        frame.setVisible(true);
    }

    @Override
    public void saveScreenshot(String path) {
        try {
            ImageIO.write(spriter.buffer, "PNG", new File(path + Game.getTick() + ".png"));
        } catch (IOException ex) {
            System.err.println("Could not save screenshot: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public Debug.Logger createLogger() {
        return null; //This will keep the standard logger
    }
}
