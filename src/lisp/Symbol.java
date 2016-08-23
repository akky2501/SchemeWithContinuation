package lisp;

import java.util.HashMap;
import java.util.Map;

public class Symbol implements Obj {

    private static final Map<String, Symbol> map = new HashMap<>();
    
    private final String name;
    
    private Symbol(String name) {
        this.name = name;
    }
    
    public static Symbol of(String name) {
        return map.computeIfAbsent(name, n -> new Symbol(n));
    }
    
    @Override
    public Obj eval(Env env, Cont cont) {
        return cont.apply(env.get(this));
    }
    
    @Override
    public String toString() {
        return name;
    }
}
