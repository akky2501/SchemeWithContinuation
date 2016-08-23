package test;

import static org.junit.Assert.*;
import static lisp.Lisp.*;

import org.junit.Test;

public class TestReader {

    @Test(expected = RuntimeException.class)
    public void testRightParenExpectedList() {
        eval(read("(a"));
    }

    @Test(expected = RuntimeException.class)
    public void testRightParenExpectedDotPair() {
        eval(read("(a ."));
    }

    @Test(expected = RuntimeException.class)
    public void testUnexpectedDot() {
        eval(read(". a"));
    }
    
    @Test
    public void testDotSymbol() {
        assertEquals(symbol(".abc"), read(".abc"));
    }

}
