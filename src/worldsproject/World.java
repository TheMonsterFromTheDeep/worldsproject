package worldsproject;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Rand;
import alfredo.geom.Vector;
import alfredo.gfx.EditableSprite;
import alfredo.gfx.Sprite;
import alfredo.gfx.Spriter;
import alfredo.gfx.Texture;
import alfredo.util.F;

public class World extends Component {
    
    public static Entity create() {
        return Entity.insert()
                .chain(new World());
    }
    
    EditableSprite world;
    Sprite light;
    
    char[][] types;
    
    boolean show = true;
    
    public World() {
        world = Spriter.edit(new Vector(128));
        types = new char[128][128];
        light = Spriter.retrieve("/resrc/img/lighting.png").simple();
        
        for(int x = 0; x < 128; ++x) {
            for(int y = 0; y < 128; ++y) {
                types[x][y] = 255;
            }
        }
    }
    
    @Override
    public void ready() {
    }
    
    public Vector getRandom(int type) {
        boolean got = false;
        
        int total = 0;
        
        int random = Rand.i(0, 128 * 128);
        
        Vector current = new Vector(64, 64);
        
        for(int x = 0; x < 128; ++x) {
            for(int y = 0; y < 128; ++y) {
                if(types[x][y] == type) {
                    if(total == random) {
                        return new Vector(x, y).minus(new Vector(64, 64));
                    }
                    ++total;
                    if(random % total == 0) {
                        current.set(x, y);
                    }
                    got = true;
                }
            }
        }
        if(!got) { return Vector.fromDirection(Rand.f(0, 60), Rand.f(0, 360)); }
        return current.minus(new Vector(64, 64));
    }
    
    public Vector getRandom(int a, int b) {
        boolean got = false;
        
        int total = 0;
        
        int random = Rand.i(0, 128 * 128);
        
        Vector current = new Vector(64, 64);
        
        for(int x = 0; x < 128; ++x) {
            for(int y = 0; y < 128; ++y) {
                if(types[x][y] >= a && types[x][y] <= b) {
                    if(total == random) {
                        return new Vector(x, y).minus(new Vector(64, 64));
                    }
                    ++total;
                    if(random % total == 0) {
                        current.set(x, y);
                    }
                    got = true;
                }
            }
        }
        if(!got) { return Vector.fromDirection(Rand.f(0, 60), Rand.f(0, 360)); }
        return current.minus(new Vector(64, 64));
    }
    
    public Vector getRandomNot(int value) {
        boolean got = false;
        
        int total = 0;
        
        int random = Rand.i(0, 128 * 128);
        
        Vector current = new Vector(64, 64);
        
        for(int x = 0; x < 128; ++x) {
            for(int y = 0; y < 128; ++y) {
                if(types[x][y] != value && types[x][y] != 255) {
                    if(total == random) {
                        return new Vector(x, y).minus(new Vector(64, 64));
                    }
                    ++total;
                    if(random % total == 0) {
                        current.set(x, y);
                    }
                    got = true;
                }
            }
        }
        if(!got) { return Vector.fromDirection(Rand.f(0, 60), Rand.f(0, 360)); }
        return current.minus(new Vector(64, 64));
    }
    
    public Vector getRandomNot(int a, int b) {
        boolean got = false;
        
        int total = 0;
        
        int random = Rand.i(0, 128 * 128);
        
        Vector current = new Vector(64, 64);
        
        for(int x = 0; x < 128; ++x) {
            for(int y = 0; y < 128; ++y) {
                if(types[x][y] != a && types[x][y] != b && types[x][y] != 255) {
                    if(total == random) {
                        return new Vector(x, y).minus(new Vector(64, 64));
                    }
                    ++total;
                    if(random % total == 0) {
                        current.set(x, y);
                    }
                    got = true;
                }
            }
        }
        if(!got) { return Vector.fromDirection(Rand.f(0, 60), Rand.f(0, 360)); }
        return current.minus(new Vector(64, 64));
    }
    
    public int type(Vector pos) {
        int x = F.round(pos.x + 60);
        int y = F.round(pos.y + 60);
        if(x < 0 || y < 0 || x > 127 || y > 127) return 255;
        return types[x][y];
    }
    
    public void paint(Vector pos, Texture tex, char type) {
        int radius = 8;
        for(int x = F.floor(pos.x - radius); x <= F.floor(pos.x + radius); ++x) {
            for(int y = F.floor(pos.y - radius); y <= F.floor(pos.y + radius); ++y) {
                if(F.round(F.euler(x - pos.x, y - pos.y)) < radius * radius && F.round(F.euler(x - 64, y - 64)) < 64 * 64) {
                    if(x < 0 || y < 0 || x >= 128 || y >= 128) continue;
                    
                    int rgb = tex.get(x, y);
                    if((rgb & 0xff000000) != 0) {
                        world.set(x, y, rgb);
                    }
                    
                    if(type != 255) {
                        types[x][y] = type;
                    }
                }
            }
        }
    }
    
    @Override
    public void draw(Spriter s) {
        if(show) {
            s.draw(world, parent);   
            s.draw(light, parent.position.plus(new Vector(0.5f)));
        }
    }
}
