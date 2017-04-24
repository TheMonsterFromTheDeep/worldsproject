package worldsproject;

import alfredo.Camera;
import alfredo.Game;
import alfredo.Scene;
import alfredo.platform.Computer;

public class Main {
    public static void main(String[] args) {
        Game.platform(Computer.class);
        
        Camera.setMain(new Camera.Fit(240, 180));
        Camera.getMain().position.add(Camera.getMain().getViewport().times(0.5f));
        
        Editor.start(World.create());
        
        Game.title("Worlds Project");
        Game.size(480 * 2, 360 * 2);
        Game.play();
    }
}
