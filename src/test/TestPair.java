package test;

import static org.junit.Assert.*;
import static lisp.Lisp.*;

import org.junit.Test;

import lisp.Pair;

public class TestPair {

    @Test
    public void testPairBuilderHead() {
        Pair.Builder b = Pair.builder();
        assertEquals(read("(a b)"), b.head(symbol("b")).head(symbol("a")).build());
    }

    @Test
    public void testPairBuilderLast1() {
        Pair.Builder b = Pair.builder();
        assertEquals(read("(a . b)"), b.last(symbol("b")).head(symbol("a")).build());
    }

    @Test
    public void testPairBuilderLast2() {
        Pair.Builder b = Pair.builder();
        assertEquals(read("(a . b)"), b.head(symbol("a")).last(symbol("b")).build());
    }

}
