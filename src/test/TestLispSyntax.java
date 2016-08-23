package test;

import static org.junit.Assert.*;

import org.junit.Test;
import static lisp.Lisp.*;

public class TestLispSyntax {

    @Test
    public void testQuoteSymbol() {
        assertEquals(read("a"), eval(read("'a")));
    }

    @Test
    public void testQuoteList() {
        assertEquals(read("(a b)"), eval(read("'(a b)")));
    }

    @Test
    public void testIfElseTrue() {
        assertEquals(read("a"), eval(read("(if #t 'a 'b)")));
    }

    @Test
    public void testIfElseFalse() {
        assertEquals(read("b"), eval(read("(if #f 'a 'b)")));
    }

    @Test
    public void testIfFalse() {
        assertEquals(read("#f"), eval(read("(if #f 'a)")));
    }
    
    @Test
    public void testLambda() {
        assertEquals(read("(a . b)"), eval(read("((lambda (x y) (cons x y)) 'a 'b)")));
    }
    
    @Test
    public void testLambdaVarargs() {
        assertEquals(read("(a b)"), eval(read("((lambda x x) 'a 'b)")));
    }
 
    @Test
    public void testDefineSymbol() {
        assertEquals(read("a"), eval(read("(define A (car '(a b)))")));
        assertEquals(read("a"), eval(read("A")));
    }
  
    @Test
    public void testDefineLambda() {
        eval(read("(define (kar x) (car x))"));
        assertEquals(read("a"), eval(read("(kar '(a b))")));
    }

    @Test
    public void testSet() {
        assertEquals(read("a"), eval(read("(define A 'a)")));
        assertEquals(read("b"), eval(read("(set! A 'b)")));
        assertEquals(read("b"), eval(read("A")));
    }
    
    @Test
    public void testLet() {
        assertEquals(read("(a . b)"), eval(read("(let ((x 'a) (y 'b)) (cons x y))")));
    }
    
    @Test
    public void testLetNamed() {
        eval(read("(define (fact n)"
            + " (let loop ((r 1) (i 1))"
            + "   (if (> i n)"
            + "     r"
            + "     (loop (* r i) (+ i 1)))))"));
        assertEquals(read("24"), eval(read("(fact 4)")));
    }
    
    @Test
    public void testLetstar() {
        assertEquals(read("2"), eval(read("(let* ((a 1) (b (+ a 1))) b)")));
    }
    
    @Test
    public void testLetrec() {
        eval(read("(define (fact n)"
            + " (letrec ((f (lambda (i)"
            + "   (if (<= i 1)"
            + "     1"
            + "     (* i (f (- i 1)))))))"
            + "   (f n)))"));
        assertEquals(read("24"), eval(read("(fact 4)")));
    }
    
    @Test
    public void testBegin() {
        assertEquals(read("2"), eval(read("(begin 0 1 2)")));
    }
    
}
