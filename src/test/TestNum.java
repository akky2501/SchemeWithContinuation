package test;

import static org.junit.Assert.*;

import org.junit.Test;
import static lisp.Lisp.*;

public class TestNum {
  
    @Test
    public void testNumEqTrue() {
        assertEquals(read("#t"), eval(read("(= 2 2)")));
    }
  
    @Test
    public void testNumEqFalse() {
        assertEquals(read("#f"), eval(read("(= 1 2)")));
    }
  
    @Test
    public void testNumEqFalseNotNum() {
        assertEquals(read("#f"), eval(read("(= 1 'a)")));
    }
    
    @Test
    public void testNumLtTrue() {
        assertEquals(read("#t"), eval(read("(< 1 2)")));
    }
    
    @Test
    public void testNumLtFalse() {
        assertEquals(read("#f"), eval(read("(< 1 1)")));
    }
    
    @Test
    public void testNumLeTrue() {
        assertEquals(read("#t"), eval(read("(<= 1 2)")));
    }
    
    @Test
    public void testNumLeFalse() {
        assertEquals(read("#f"), eval(read("(<= 2 1)")));
    }
    
    @Test
    public void testNumGtTrue() {
        assertEquals(read("#t"), eval(read("(> 2 1)")));
    }
    
    @Test
    public void testNumGtFalse() {
        assertEquals(read("#f"), eval(read("(> 1 1)")));
    }
    
    @Test
    public void testNumGeTrue() {
        assertEquals(read("#t"), eval(read("(>= 2 1)")));
    }
    
    @Test
    public void testNumGeFalse() {
        assertEquals(read("#f"), eval(read("(>= 1 2)")));
    }
    
    @Test
    public void testNumPlus0() {
        assertEquals(read("0"), eval(read("(+)")));
    }
    
    @Test
    public void testNumPlus1() {
        assertEquals(read("1"), eval(read("(+ 1)")));
    }
    
    @Test
    public void testNumPlus2() {
        assertEquals(read("3"), eval(read("(+ 1 2)")));
    }
    
    @Test
    public void testNumPlus3() {
        assertEquals(read("6"), eval(read("(+ 1 2 3)")));
    }
    
    @Test
    public void testNumMinus0() {
        assertEquals(read("0"), eval(read("(-)")));
    }
    
    @Test
    public void testNumMinus1() {
        assertEquals(read("-1"), eval(read("(- 1)")));
    }
    
    @Test
    public void testNumMinus2() {
        assertEquals(read("-1"), eval(read("(- 1 2)")));
    }
    
    @Test
    public void testMult0() {
        assertEquals(read("1"), eval(read("(*)")));
    }
    
    @Test
    public void testMult1() {
        assertEquals(read("2"), eval(read("(* 2)")));
    }
    
    @Test
    public void testMult2() {
        assertEquals(read("6"), eval(read("(* 2 3)")));
    }
    
    @Test
    public void testDiv0() {
        assertEquals(read("1"), eval(read("(/)")));
    }
    
    @Test
    public void testDiv1() {
        assertEquals(read("0"), eval(read("(/ 2)")));
    }
    
    @Test
    public void testDiv2() {
        assertEquals(read("3"), eval(read("(/ 9 3)")));
    }
}
