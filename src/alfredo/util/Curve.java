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
package alfredo.util;

import alfredo.geom.Vector;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Curve {
    private static class Segment {
        Vector a, b, c, d;
        
        public Segment(Vector[] V, Vector[] D, int index) {
            if(index == V.length - 1) {
                throw new IllegalArgumentException("For N points, there should only be N - 1 segments!");
            }
            
            a = V[index];
            b = D[index];
            //Why Java don't you have operator overloading
            c = V[index + 1].minus(V[index]).times(3).minus(D[index].times(2)).minus(D[index + 1]);
            d = V[index].minus(V[index + 1]).times(2).plus(D[index]).plus(D[index + 1]);
        }
        
        public Vector evaluate(float t) {
            return a.plus(b.times(t)).plus(c.times(t * t)).plus(d.times(t * t *t));
        }
        
        public Vector derivative(float t) {
            return b.plus(c.times(2 * t)).plus(d.times(3 * t * t));
        }
    }
    
    public final Vector[] controlPoints;
    private final Segment[] segments;
    
    public Curve(float value) {
        this.controlPoints = new Vector[] { new Vector(0, value), new Vector(1, value) };
        segments = new Segment[1];
        
        calculate();
    }
    
    public Curve(Vector... controlPoints) {
        this.controlPoints = controlPoints;
        segments = new Segment[controlPoints.length - 1];
        
        calculate();
    }
    
    private double getk(float[] in, int index) {
        if(index == 0) {
            return 3 * (in[1] - in[0]);
        }
        if(index == in.length - 1) {
            return 3 * (in[in.length - 1] - in[in.length - 2]);
        }
        else {
            return 3 * (in[index + 1] - in[index - 1]);
        }
    }
    
    private double solveCof(float[] in, float[] out, double clast, double mlast, int index) {
        double m, c;
        if(index == out.length - 1) {
            m = 2 - (1 / mlast);
            c = (getk(in, index) - clast) / m;
            out[index] = (float)c;
            return c;
        }
        else if(index == 0) {
            m = 2;
            c = getk(in, 0) / m;
        }
        else {
            m = 4 - (1 / mlast);
            c = (getk(in, index) - clast) / m;
        }
        double next = solveCof(in, out, c, m, index + 1);
        double result = c - (next / m);
        out[index] = (float)result;
        return result;
    }
    
    public void calculate() {
        Vector[] solved = new Vector[controlPoints.length];
        for(int i = 0; i < solved.length; ++i) {
            solved[i] = new Vector();
        }
        
        float[] in = new float[controlPoints.length];
        float[] out = new float[controlPoints.length];
        for(int i = 0; i < in.length; ++i) {
            in[i] = controlPoints[i].x;
        }
        solveCof(in, out, 0, 0, 0);
        
        for(int i = 0; i < in.length; ++i) {
            solved[i].x = out[i];
            in[i] = controlPoints[i].y;
        }
        solveCof(in, out, 0, 0, 0);
        
        for(int i = 0; i < out.length; ++i) {
            solved[i].y = out[i];
        }
        
        for(int i = 0; i < segments.length; ++i) {
            segments[i] = new Segment(controlPoints, solved, i);
        }
    }
    
    public Vector evaluate(float timestep) {
        int bucket = (int)(timestep * segments.length);
        if(bucket > segments.length - 1) { bucket = segments.length - 1; }
        
        return segments[bucket].evaluate(timestep * segments.length - bucket);
    }
    
    public Vector derivative(float timestep) {
        int bucket = (int)(timestep * segments.length);
        if(bucket > segments.length - 1) { bucket = segments.length - 1; }
        
        return segments[bucket].derivative(timestep * segments.length - bucket);
    }
    
    public float direction(float timestep) {
        return derivative(timestep).getDirection();
    }
}
