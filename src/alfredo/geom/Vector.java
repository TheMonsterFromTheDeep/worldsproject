package alfredo.geom;

import alfredo.Rand;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Vector {
    public float x;
    public float y;
    
    private boolean cached = false;
    private float magnitude;
    private float direction;
    
    public static Vector random(float sx, float sy, float ex, float ey) {
        return new Vector(Rand.f(sx, ex), Rand.f(sy, ey));
    }
    
    public static Vector random(Vector start, Vector end) {
        return random(start.x, start.y, end.x, end.y);
    }
    
    public static Vector fromDirection(float length, float direction) {
        return new Vector(length * (float)Math.cos(Math.toRadians(direction)), length * (float)Math.sin(Math.toRadians(direction)));
    }
    
    private void checkCache() {
        if(!cached) {
            magnitude = (float)Math.sqrt(x * x + y * y);
            direction = (float)Math.toDegrees(Math.atan2(y, x));
            cached = true;
        }
    }
    
    public Vector() {
        x = y = 0;
    }
    
    public Vector(float dupli) {
        x = y = dupli;
    }
    
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector(Vector orig) {
        this.x = orig.x;
        this.y = orig.y;
    }
    
    public Vector copy() {
        return new Vector(this);
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
        cached = false;
    }
    
    public void set(Vector dupli) {
        x = dupli.x;
        y = dupli.y;
        cached = false;
    }
    
        
    public void zero() {
        set(0, 0);
    }
    
    public void setX(float x) { this.x = x; cached = false; }
    public void setY(float y) { this.y = y; cached = false; }
    
    public void setX(Vector dupli) { this.x = dupli.x; cached = false; }
    public void setY(Vector dupli) { this.y = dupli.y; cached = false; }
    
    public void add(float x, float y) {
        this.x += x;
        this.y += y;
        cached = false;
    }
    
    public void add(Vector dupli) {
        this.x += dupli.x;
        this.y += dupli.y;
        cached = false;
    }
    
    public void addX(float x) { this.x += x; cached = false; }
    public void addY(float y) { this.y += y; cached = false; }
    
    public void addX(Vector dupli) { this.x += dupli.x; cached = false; }
    public void addY(Vector dupli) { this.y += dupli.y; cached = false; }
    
    public void subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        cached = false;
    }
    
    public void subtract(Vector dupli) {
        this.x -= dupli.x;
        this.y -= dupli.y;
        cached = false;
    }
    
    public void subtractX(float x) { this.x -= x; cached = false; }
    public void subtractY(float y) { this.y -= y; cached = false; }
    
    public void subtractX(Vector dupli) { this.x -= dupli.x; cached = false; }
    public void subtractY(Vector dupli) { this.y -= dupli.y; cached = false; }
    
    public Vector plus(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }
    
    public Vector minus(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }
    
    public Vector times(float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }
    
    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        cached = false;
    }
    
    public void rotate(float px, float py, double theta) {
        float dx = x - px;
        float dy = y - py;
        theta = Math.toRadians(theta);
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        x = (float)(px + (dx * cos) + (dy * sin));
        y = (float)(py - (dx * sin) + (dy * cos));
        cached = false;
    }
    
    public void move(float distance, double direction) {
        x += distance * Math.cos(Math.toRadians(direction));
        y += distance * Math.sin(Math.toRadians(direction));
    }
    
    public float getMagnitude() {
        checkCache();
        return magnitude;
    }
    
    public float getDirection() {
        checkCache();
        return direction;
    }
    
    public float distance(Vector other) {
        return (float) Math.sqrt( (other.x - x) * (other.x - x) + (other.y - y) * (other.y - y) );
    }
    
    @Override
    public String toString() {
        //Only show two decimals worth of precision - I feel like this will be more useful for debugging
        float xd = Math.abs(x);
        float yd = Math.abs(y);
        int xi = (int)Math.floor(xd);
        int xf = Math.round(xd * 100) - 100 * xi;
        int yi = (int)Math.floor(yd);
        int yf = Math.round(yd * 100) - 100 * yi;
        if(x < 0) { xi = -xi; }
        if(y < 0) { yi = -yi; }
        return "Vector(" + xi + "." + xf + ", " + yi + "." + yf + ")";
    }
    
    public boolean equal(Vector v) {
        return v.x == x && v.y == y;
    }
}
