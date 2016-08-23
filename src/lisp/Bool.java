package lisp;

public class Bool implements Obj {
    
    static final Bool TRUE = new Bool(true) {
        @Override public String toString() { return "#t"; }
    };

    static final Bool FALSE = new Bool(false) {
        @Override public String toString() { return "#f"; }
    };

    final boolean value;
    
    private Bool(boolean value) {
        this.value = value;
    }
    
}
