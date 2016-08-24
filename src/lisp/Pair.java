package lisp;

public class Pair implements List {
    
    Obj car, cdr;
    
    public Pair(Obj car, Obj cdr) {
        this.car = car;
        this.cdr = cdr;
    }
    
    @Override public Obj car() { return car; } 
    @Override public Obj cdr() { return cdr; }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        Pair o = (Pair)obj;
        return o.car.equals(car) && o.cdr.equals(cdr);
    }
    
    @Override
    public Obj eval(Env env, Cont cont) {
        return car.eval(env, x -> x.apply(cdr, env, cont));
    }
    
    @Override
    public String toString() {
        if (cdr instanceof Pair && cdr.cdr() == NIL) {
            if (car == Lisp.QUOTE) return "'" + cdr.car();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(car);
        Obj e = cdr;
        for (; e instanceof Pair; e = e.cdr())
            sb.append(" ").append(e.car());
        if (e != NIL)
            sb.append(" . ").append(e);
        sb.append(")");
        return sb.toString();
    }
    
    public static class Builder {

        Obj head = NIL;
        Obj tail = NIL;
        
        public Builder head(Obj e) {
            head = new Pair(e, head);
            if (!(tail instanceof Pair))
                tail = head;
            return this;
        }
        
        public Builder tail(Obj e) {
            if (tail instanceof Pair)
                tail = ((Pair) tail).cdr = new Pair(e, tail.cdr());
            else
                head = tail = new Pair(e, tail);
            return this;
        }
        
        public Builder last(Obj e) {
            if (tail instanceof Pair)
                ((Pair) tail).cdr = e;
            else
                head = tail = e;
            return this;
        }
        
        public Obj build() {
            return head;
        }

    }
    
    public static Builder builder() { return new Builder(); }

}
