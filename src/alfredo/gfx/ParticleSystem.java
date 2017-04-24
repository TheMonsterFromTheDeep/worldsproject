/*
 * The MIT License
 *
 * Copyright 2017 TheMonsterOfTheDeep.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package alfredo.gfx;

import alfredo.Component;
import alfredo.Entity;
import alfredo.Rand;
import alfredo.geom.Vector;
import alfredo.util.Curve;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class ParticleSystem extends Component {
    private Curve size = new Curve(1);
    private Curve velocity = new Curve(1);
    private Curve alpha = new Curve(1);
    /* in ticks */
    private int lifetime = 100;
    /* in particles / tick */
    public int rate = 5; /* TODO: Make rates have finer control */
    /* in ticks */
    public int length = 100;
    
    private Sprite particle;
    
    private final ArrayList<Particle> particles;
    
    public static ParticleSystem create(Vector center, Sprite particle) {
        Entity parent = Entity.create(center);
        return parent.addComponent(new ParticleSystem(particle));
    }
    
    public ParticleSystem(Sprite particle) {
        this.particle = particle;
        particles = new ArrayList();
    }
    
    public ParticleSystem size(float size) {
        this.size = new Curve(size);
        return this;
    }
    
    public ParticleSystem size(Vector... points) {
        this.size = new Curve(points);
        return this;
    }
    
    public ParticleSystem speed(float speed) {
        this.velocity = new Curve(speed);
        return this;
    }
    
    public ParticleSystem speed(Vector... points) {
        this.velocity = new Curve(points);
        return this;
    }
    
    public ParticleSystem alpha(float alpha) {
        this.alpha = new Curve(alpha);
        return this;
    }
    
    public ParticleSystem alpha(Vector... points) {
        this.alpha = new Curve(points);
        return this;
    }
    
    public ParticleSystem lifetime(int lifetime) {
        this.lifetime = lifetime;
        return this;
    }
    
    public ParticleSystem length(int length) {
        this.length = length;
        return this;
    }
    
    public ParticleSystem rate(int rate) {
        this.rate = rate;
        return this;
    }
    
    @Override
    public void tick() {
        if(length > 0) {
            for(int i = 0; i < rate; ++i) {
                Particle p = new Particle(parent.position, Vector.fromDirection(1, Rand.f(0, 360)), lifetime);
                particles.add(p);
            }
        }
        
        --length;
        if(length < -lifetime) { //All particles are "dead"
            parent.destroy();
        }
        
        Iterator i = particles.iterator();
        Particle p;
        while(i.hasNext()) {
            p = (Particle) i.next();
            float time = 1 - (p.life / (float)lifetime);
            if(time <= 1) {
                p.size = size.evaluate(time).y;
                p.alpha = alpha.evaluate(time).y;
                p.step(velocity.evaluate(time).y);
            }
            --p.life;
            if(p.life <= 0) {
                i.remove();
            }
        }
    }
    
    @Override
    public void ui(Spriter g) {
        for(Particle p : particles) {
            g.draw(particle, p.x, p.y, p.size, 0, p.alpha);
        }
    }
}
