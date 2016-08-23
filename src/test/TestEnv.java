package test;

import static org.junit.Assert.*;
import static lisp.Lisp.*;

import org.junit.Test;

import lisp.Env;
import lisp.Num;

public class TestEnv {

    @Test
    public void testToString() {
        Env a = new Env(null);
        a.define(symbol("a"), Num.ZERO);
        Env b = new Env(a);
        b.define(symbol("b"), Num.ONE);
        assertEquals("{b=1} -> {a=0}", b.toString());
    }

}
