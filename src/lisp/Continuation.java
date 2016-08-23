package lisp;

public class Continuation implements Procedure {

    final Cont cont;
    
    Continuation(Cont cont) {
        this.cont = cont;
    }
    
    @Override
    public Obj apply(Obj args, Cont cont) {
        return this.cont.apply(args.car());
    }

}
