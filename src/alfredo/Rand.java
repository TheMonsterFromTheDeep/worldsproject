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
package alfredo;

import java.util.Random;

/**
 * Wrapper around Java's Random; provides global randomness for Alfredo
 * @author TheMonsterOfTheDeep
 */
public class Rand {
    private static final Random generator = new Random();
    
    public static int i(int start, int end) {
        return generator.nextInt(end + 1) + start;
    }
    
    public static float f(float start, float end) {
        return generator.nextFloat() * (end - start) + start;
    }
    
    public static double d(double start, double end) {
        return generator.nextDouble() * (end - start) + start;
    }
}
