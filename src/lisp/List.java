package lisp;

public interface List extends Obj {
    
    static final List NIL = new List() {
        public String toString() {
            return "()";
        }
    };

}
