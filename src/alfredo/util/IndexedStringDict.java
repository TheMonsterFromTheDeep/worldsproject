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

import java.util.ArrayList;

/**
 *
 * @author TheMonsterOfTheDeep
 * @param <T> The type that this Dict holds
 */
public class IndexedStringDict<T> {
    private class Element {
        String key;
        T value;
        
        public Element(String key, T value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final ArrayList<Element> storage;
    
    public IndexedStringDict() {
        storage = new ArrayList();
    }
    
    public Or<T> get(String key) {
        for(Element e : storage) {
            if(e.key.equals(key)) {
                return new Or(e.value);
            }
        }
        return new Or(null);
    }
    
    public Or<Integer> indexOf(String key) {
        int index = 0;
        for(Element e : storage) {
            if(e.key.equals(key)) {
                return new Or(index);
            }
            ++index;
        }
        return new Or(null);
    }
    
    public boolean valid(int index) {
        return index >= 0 && index < storage.size();
    }
    
    public Or<T> get(int index) {
        if(valid(index)) {
            return new Or(storage.get(index).value);
        }
        return new Or(null);
    }
    
    public T getAlways(int index) {
        if(valid(index)) {
            return (T)storage.get(index).value;
        }
        return null;
    }
    
    public int add(String key, T value) {
        storage.add(new Element(key, value));
        return storage.size() - 1;
    }
}
