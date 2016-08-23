package lisp;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private final Map<Symbol, Obj> map = new HashMap<>();
    
    private final Env next;
    
    public Env(Env next) {
        this.next = next;
    }
    
    private static Env find(Env env, Symbol key) {
        for (Env e = env; e != null; e = e.next)
            if (e.map.containsKey(key))
                return e;
        throw new RuntimeException("Variable " + key + " not found");
    }
    
    public Obj get(Symbol key) {
        return find(this, key).map.get(key);
    }
    
    public Obj set(Symbol key, Obj value) {
        find(this, key).map.put(key, value);
        return value;
    }
    
    public Obj define(Symbol key, Obj value) {
        map.put(key, value);
        return value;
    }
    
    @Override
    public String toString() {
        return map.toString() + (next != null ? " -> " + next : "");
    }
}
