package lisp;

public class Num implements Obj, Comparable<Num> {

    public static final Num ZERO = of(0);
    public static final Num ONE = of(1);

    final int value;
    
    private Num(int value) {
        this.value = value;
    }
    
    private Num(String value) {
        this.value = Integer.parseInt(value);
    }
    
    public static Num of(int value) {
        return new Num(value);
    }
    
    public static Num of(String value) {
        return new Num(value);
    }
    
    public Num plus(Num n) { return of(value + n.value); }
    public Num minus(Num n) { return of(value - n.value); }
    public Num mult(Num n) { return of(value * n.value); }
    public Num div(Num n) { return of(value / n.value); }
 
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Num && ((Num)obj).value == value;
    }

    @Override
    public int compareTo(Num o) {
        return Integer.compare(value, o.value);
    }
    
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
