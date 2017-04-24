package alfredo;

import alfredo.geom.Vector;
import alfredo.util.F;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 *
 * @author TheMonsterOfTheDeep
 */
public class Entity {
    //private static final ArrayList<Entity> entities = new ArrayList();
    private static Entity head = null;
    private static Entity tail = null;
    
    public final Vector position;
    public double direction;
    private final ArrayList<Component> components;
    private Entity previous = null;
    private Entity next = null;
    
    private boolean alive = true;
    
    public boolean isAlive() { return alive; }
    
    public int tag = 0;
    
    private static int count = 0;
    
    private static void add(Entity e) {
        if(head == null) {
            head = tail = e;
        }
        else {
            tail.next = e;
            e.previous = tail;
            tail = e;
        }
        ++count;
    }
    
    private static void insert(int index, Entity e) { 
        if(head == null) {
            if(index == 0) {
                add(e);
            }
            else {
                throw new IllegalArgumentException("Inserting Entity at too great of index!");
            }
            return;
        }
        
        Entity target = head;
        while(index > 0) {
            if(target.next == null) {
                throw new IllegalArgumentException("Inserting Entity at too great of index!");
            }
            target = target.next;
            --index;
        }
        if(target.previous != null) {
            target.previous.next = e;
            e.previous = target.previous;
        }
        if(target == head) {
            head = e;
        }
        e.next = target;
        target.previous = e;
        ++count; //Only increment count if successful
    }
    
    public static <T extends Entity> T create(Vector position, double direction) {
        T newEntity = (T)new Entity(position, direction);
        newEntity.init();
        
        add(newEntity);
        
        return newEntity;
    }
    
    public static <T extends Entity> T create(Vector position) {
        return create(position, 0);
    }
    
    public static <T extends Entity> T create(float x, float y) {
        return create(new Vector(x, y));
    }
    
    public static <T extends Entity> T insert(float x, float y) {
        T newEntity = (T)new Entity(new Vector(x, y), 0);
        newEntity.init();
        
        insert(0, newEntity);
        
        return newEntity;
    }
    
    public static <T extends Entity> T insert() {
        return insert(0, 0);
    }
    
    public static <T extends Entity> T create(Entity old) {
        if(old == null) return create(0, 0);
        return create(old.position, old.direction);
    }
    
    public static <T extends Entity> T create() {
        return create(new Vector());
    }
    
    public static void clear() {
        tail = head = null;
        count = 0;
    }
    
    protected Entity(Vector position, double direction) {
        this.position = new Vector(position);
        this.direction = direction;
        components = new ArrayList();
    }
    
    protected void init() { }
    
    public <T extends Component> T getComponent(Class<T> type) {
        for(Component c : components) {
            if(c.getClass() == type) {
                return (T)c;
            }
        }
        return null;
    }
    
    public <T extends Component> T addComponent(T c) {
        components.add(c);
        c.parent = this;
        
        c.ready();
        
        return c;
    }
    
    public Entity chain(Component c) {
        components.add(c);
        c.parent = this;
        
        c.ready();
        
        return this;
    }
    
    public Entity tag(int tag) {
        this.tag = tag;
        return this;
    }
    
    public <T extends Component> T removeComponent(Class<T> type) {
        Component c = null;
        for(Iterator<Component> i = components.iterator(); i.hasNext(); c = i.next()) {
            if(c == null) { continue; }
            if(c.getClass() == type) {
                i.remove();
                return (T)c;
            }
        }
        return null;
    }
    
    public void removeComponent(Component target) {
        target.destroy();
        components.remove(target);
    }
    
    public final void move(float distance) {
        position.add((float)(distance * Math.cos(Math.toRadians(direction))), (float)(distance * Math.sin(Math.toRadians(direction))));
    }
    
    public final void moveNormal(float distance) {
        position.add((float)(distance * Math.sin(Math.toRadians(direction))), (float)(distance * Math.cos(Math.toRadians(direction))));
    }
    
    public final Component[] getComponents() {
        return components.toArray(new Component[0]);
    }
    
    public final void destroy() {
        if(!alive) return;
        
        alive = false;
        for(Component c : components) {
            c.destroy();
        }
        if(previous != null) previous.next = next;
        if(next     != null) next.previous = previous;
        if(this == tail) {
            tail = previous;
        }
        count = 0;
    }
    
    public static Iterable<Entity> all() {
        return () -> new Iterator() {
            Entity current = head;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            @Override
            public Object next() {
                Entity next = current;
                current = current.next;
                return next;
            }
        };
    }
    
    public static Entity random() {
        if(count == 0) { return null; }
        int index = Rand.i(0, count - 1);
        for(Entity e : all()) {
            if(index == 0) { return e; }
            --index;
        }
        return null;
    }
    
    private static class ClassIterator<T extends Component> implements Iterator {
        Entity current;
        Class<T> type;
        
        private void find() {
            if(current == null) {
                return;
            }
            
            while(current.getComponent(type) == null) {
                if(current.next == null) {
                    current = null;
                    return;
                }
                current = current.next;
            }
        }
        
        public ClassIterator(Class<T> type) {
            this.type = type;
            current = head;
            find();
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            Entity next = current;
            current = current.next;
            find();
            return next;
        }
    }
    
    public static <T extends Component> Iterable<Entity> all(Class<T> type) {
        return () -> new ClassIterator(type);
    }
    
    public static <T extends Component> Entity random(Class<T> type) {
        Iterable<Entity> ents = all(type);
        Iterator i = ents.iterator();
        int index = 0;
        while(i.hasNext()) {
            ++index;
            i.next();
        }
        if(index == 0) { return null; }
        index = Rand.i(0, index - 1);
        for(Entity e : ents) {
            if(index == 0) {
                return e;
            }
            --index;
        }
        return null;
    }
    
    private static class TagIterator implements Iterator {
        Entity current;
        int tag;
        
        private void find() {
            if(current == null) {
                return;
            }
            
            while(current.tag != tag) {
                if(current.next == null) {
                    current = null;
                    return;
                }
                current = current.next;
            }
        }
        
        public TagIterator(int tag) {
            this.tag = tag;
            current = head;
            find();
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            Entity next = current;
            current = current.next;
            find();
            return next;
        }
    }
    
    public static Iterable<Entity> all(int tag) {
        return () -> new TagIterator(tag);
    }
    
    public static Entity first() {
        return head;
    }
    
    public static <T extends Component> Entity first(Class<T> type) {
        for(Entity e : all()) {
            if(e.getComponent(type) != null) { return e; }
        }
        return null;
    }
    
    public static Entity first(int tag) {
        for(Entity e : all()) {
            if(e.tag == tag) { return e; }
        }
        return null;
    }
    
    public static interface SortComparison {
        int compare(Entity a, Entity b);
    }
    
    public static void sort(SortComparison comp) {
        ArrayList<Entity> ents = new ArrayList();
        for(Entity e : all()) {
            ents.add(e);
        }
        ents.sort((Object o1, Object o2) -> comp.compare((Entity)o1, (Entity)o2));
        
        if(ents.size() == 0) { return; }
        
        head = ents.get(0);
        head.previous = null;
        for(int i = 0; i < ents.size() - 1; ++i) {
            ents.get(i).next = ents.get(i + 1);
            ents.get(i + 1).previous = ents.get(i);
        }
        tail = ents.get(ents.size() - 1);
        tail.next = null;
    }
    
    public Entity closest(int tag) {
        Entity closest = Entity.first(tag);
        if(closest == null) { return null; }
        float record = F.euler(closest.position.minus(position));
        
        for(Entity e : all(tag)) {
            if(F.euler(e.position.minus(position)) < record) {
                closest = e;
                record = F.euler(e.position.minus(position));
            }
        }
        
        return closest;
    }
    
    public <T extends Component> Entity closest(Class<T> type) {
        Entity closest = Entity.first(type);
        if(closest == null) { return null; }
        float record = F.euler(closest.position.minus(position));
        
        for(Entity e : all(type)) {
            if(F.euler(e.position.minus(position)) < record) {
                closest = e;
                record = F.euler(e.position.minus(position));
            }
        }
        
        return closest;
    }
    
    public <T extends Component> Entity firstOther(Class<T> type) {
        for(Entity e : all()) {
            if(e.getComponent(type) != null && e != this) { return e; }
        }
        return null;
    }
}
