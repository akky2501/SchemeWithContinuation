package lisp;

public interface Procedure extends Applicable {

    Obj apply(Obj args, Cont cont);
    
    static Obj evlis(Obj args, Env env, Cont cont) {
          // left to right order (recursion)
//        return args instanceof Pair
//            ? args.car().eval(env, x -> evlis(args.cdr(), env, y -> cont.apply(new Pair(x, y))))
//            : cont.apply(List.NIL);

        // right to left order (recursion)
//        return args instanceof Pair
//            ? evlis(args.cdr(), env, y -> args.car().eval(env, x -> cont.apply(new Pair(x, y))))
//            : cont.apply(List.NIL);
        
        // right to left order (loop)
        while (args instanceof Pair) {
            Obj a = args;
            Cont c = cont;
            args = args.cdr();
            cont = y -> a.car().eval(env, x -> c.apply(new Pair(x, y)));
        }
        return cont.apply(List.NIL);
    }

    @Override
    default Obj apply(Obj args, Env env, Cont cont) {
        return evlis(args, env, x -> apply(x, cont));
    }
}
