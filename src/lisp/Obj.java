package lisp;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface Obj {
    
    default Obj eval(Env env, Cont cont) { return cont.apply(this); }
    default Obj apply(Obj args, Env env, Cont cont) { throw new RuntimeException("Cannot apply " + args + " to " + this); }
    default Obj car() { throw new RuntimeException("Cannot get car for " + this); }
    default Obj cdr() { throw new RuntimeException("Cannot get cdr for " + this); }
    
    default Obj map(UnaryOperator<Obj> f) {
        Pair.Builder b = Pair.builder();
        for (Obj e = this; e instanceof Pair; e = e.cdr())
            b.tail(f.apply(e.car()));
        return b.build();
    }
    
    default Obj reduce(Obj unit, BinaryOperator<Obj> f) {
        Obj prev = null;
        Obj r = unit;
        int i = 0;
        for (Obj e = this; e instanceof Pair; e = e.cdr()) {
            switch (i++) {
            case 0: prev = e.car(); break;
            case 1: r = prev; break;
            }
            r = f.apply(r, e.car());
        }
        return r;
    }

}
