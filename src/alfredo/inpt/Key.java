package alfredo.inpt;

import java.awt.event.KeyEvent;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Key {        
    public static final Button.Source[] keys = new Button.Source[256];
    
    static {
        for(int i = 0; i < keys.length; ++i) {
            keys[i] = new Button.Source();
        }
    }
    
    public static final Button.Source SHIFT = keys[KeyEvent.VK_SHIFT];
    public static final Button.Source A = keys[KeyEvent.VK_A];
    public static final Button.Source B = keys[KeyEvent.VK_B];
    public static final Button.Source C = keys[KeyEvent.VK_C];
    public static final Button.Source D = keys[KeyEvent.VK_D];
    public static final Button.Source E = keys[KeyEvent.VK_E];
    public static final Button.Source F = keys[KeyEvent.VK_F];
    public static final Button.Source G = keys[KeyEvent.VK_G];
    public static final Button.Source H = keys[KeyEvent.VK_H];
    public static final Button.Source I = keys[KeyEvent.VK_I];
    public static final Button.Source J = keys[KeyEvent.VK_J];
    public static final Button.Source K = keys[KeyEvent.VK_K];
    public static final Button.Source L = keys[KeyEvent.VK_L];
    public static final Button.Source M = keys[KeyEvent.VK_M];
    public static final Button.Source N = keys[KeyEvent.VK_N];
    public static final Button.Source O = keys[KeyEvent.VK_O];
    public static final Button.Source P = keys[KeyEvent.VK_P];
    public static final Button.Source Q = keys[KeyEvent.VK_Q];
    public static final Button.Source R = keys[KeyEvent.VK_R];
    public static final Button.Source S = keys[KeyEvent.VK_S];
    public static final Button.Source T = keys[KeyEvent.VK_T];
    public static final Button.Source U = keys[KeyEvent.VK_U];
    public static final Button.Source V = keys[KeyEvent.VK_V];
    public static final Button.Source W = keys[KeyEvent.VK_W];
    public static final Button.Source X = keys[KeyEvent.VK_X];
    public static final Button.Source Y = keys[KeyEvent.VK_Y];
    public static final Button.Source Z = keys[KeyEvent.VK_Z];
    public static final Button.Source SPACE = keys[KeyEvent.VK_SPACE];
    public static final Button.Source ROW_0 = keys[KeyEvent.VK_0];
    public static final Button.Source ROW_1 = keys[KeyEvent.VK_1];
    public static final Button.Source ROW_2 = keys[KeyEvent.VK_2];
    public static final Button.Source ROW_3 = keys[KeyEvent.VK_3];
    public static final Button.Source ROW_4 = keys[KeyEvent.VK_4];
    public static final Button.Source ROW_5 = keys[KeyEvent.VK_5];
    public static final Button.Source ROW_6 = keys[KeyEvent.VK_6];
    public static final Button.Source ROW_7 = keys[KeyEvent.VK_7];
    public static final Button.Source ROW_8 = keys[KeyEvent.VK_8];
    public static final Button.Source ROW_9 = keys[KeyEvent.VK_9];
    public static final Button.Source PAD_0 = keys[KeyEvent.VK_NUMPAD0];   
    public static final Button.Source PAD_1 = keys[KeyEvent.VK_NUMPAD1];
    public static final Button.Source PAD_2 = keys[KeyEvent.VK_NUMPAD2];
    public static final Button.Source PAD_3 = keys[KeyEvent.VK_NUMPAD3];
    public static final Button.Source PAD_4 = keys[KeyEvent.VK_NUMPAD4];
    public static final Button.Source PAD_5 = keys[KeyEvent.VK_NUMPAD5];
    public static final Button.Source PAD_6 = keys[KeyEvent.VK_NUMPAD6];
    public static final Button.Source PAD_7 = keys[KeyEvent.VK_NUMPAD7];
    public static final Button.Source PAD_8 = keys[KeyEvent.VK_NUMPAD8];
    public static final Button.Source PAD_9 = keys[KeyEvent.VK_NUMPAD9];
}
