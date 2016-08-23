package lisp;

public interface Applicable extends Obj {

    Obj apply(Obj args, Env env, Cont cont);

}
