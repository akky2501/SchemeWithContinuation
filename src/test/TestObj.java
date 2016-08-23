package test;

import static org.junit.Assert.*;
import static lisp.Lisp.*;

import org.junit.Test;

public class TestObj {

    @Test(expected = RuntimeException.class)
    public void testApplyError() {
        eval(read("(1 2 3)"));
    }

    @Test(expected = RuntimeException.class)
    public void testCarError() {
        eval(read("(car 1)"));
    }

    @Test(expected = RuntimeException.class)
    public void testCdrError() {
        eval(read("(cdr 1)"));
    }

}
