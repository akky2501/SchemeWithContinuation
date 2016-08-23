package test;

import static lisp.Lisp.*;
import static org.junit.Assert.*;

import org.junit.Test;

import lisp.Continuation;

public class TestContinuation {

    @Test
    public void testCallCC() {
        assertTrue(eval(read("(call/cc (lambda (x) x))")) instanceof Continuation);
    }
    
    @Test
    public void testStore() {
        eval(read("(define C '())"));
        assertEquals(read("7"), eval(read("(+ 1 (* 2 (call/cc (lambda (cont) (set! C cont) 3))))")));
        assertEquals(read("21"), eval(read("(C 10)")));
        assertEquals(read("201"), eval(read("(C 100)")));
    }

    @Test
    public void testSuspendRestart() {
        assertEquals(NIL, eval(read("(define C '())")));
        assertEquals(read("(a b c)"), eval(read("(list 'a (call/cc (lambda (x) (set! C x) 'b)) 'c)")));
        assertTrue(eval(read("C")) instanceof Continuation);
        assertEquals(read("(a d c)"), eval(read("(C 'd)")));
        assertEquals(read("(a e c)"), eval(read("(C 'e)")));
    }
    
    @Test
    public void testContInLet() {
        assertEquals(NIL, eval(read("(define C '())")));
        assertEquals(read("(a . b)"), eval(read("(let ((x (call/cc (lambda (x) (set! C x) 'a))) (y 'b)) (cons x y))")));
        assertEquals(read("(c . b)"), eval(read("(C 'c)")));
    }
    
    @Test
    public void testNonLocalExits() {
        eval(read("(define (bar1 cont) (display 'bar1))"));
        eval(read("(define (bar2 cont) (display 'bar2) (display 'bar2-2) (cont 'exit))"));
        eval(read("(define (bar3 cont) (display 'bar3))"));
        eval(read("(define (test cont) (bar1 cont) (bar2 cont) (bar3 cont))"));
        assertEquals(symbol("exit"), eval(read("(call/cc (lambda (cont) (test cont)))")));
    }

    @Test
    public void testGlobalVariable() {
        eval(read("(define (list . x) x)"));
        assertEquals(NIL, eval(read("(define C '())")));
        assertEquals(symbol("a"), eval(read("(define H 'a)")));
        assertEquals(symbol("d"), eval(read("(define G 'd)")));
        assertEquals(read("(a b c d)"), eval(read("(list H 'b (call/cc (lambda (cont) (set! C cont) 'c)) G)")));
        assertEquals(symbol("z"), eval(read("(set! H 'z)")));
        assertEquals(symbol("y"), eval(read("(set! G 'y)")));
        // H is not re-evaluated.
        assertEquals(read("(a b x y)"), eval(read("(C 'x)")));
        assertEquals(read("(a b y y)"), eval(read("(C 'y)")));
    }
    
    @Test
    public void testLoop() {
//        eval(read("(define (fact n)"
//            + "(if (= n 0) 1 (* (fact (- n 1)) n)))"));
        eval(read("(define (fact n)"
            + "  (let ((r 1) (k '()))"
            + "    (call/cc (lambda (c) (set! k c) '()))"
            + "    (set! r (* r n))"
            + "    (set! n (- n 1))"
            + "    (if (= n 1) r (k 'recurse))))"));
        assertEquals(read("120"), eval(read("(fact 5)")));
    }
    
    @Test
    public void testLoop2() {
        eval(read("(define (fact2 n)"
            + "  (let ((r 1))"
            + "    (let ((k (call/cc (lambda (c) c))))"
            + "      (set! r (* r n))"
            + "      (set! n (- n 1))"
            + "      (if (= n 1) r (k k)))))"));
        assertEquals(read("120"), eval(read("(fact2 5)")));
    }
    
    @Test
    public void testArguments() {
        eval(read("(define xxu '())"));
        assertEquals(read("(a b c)"), eval(read(
            "(list 'a (call/cc (lambda (c) (set! xxu c) 'b)) 'c)")));
        assertEquals(read("(a z c)"), eval(read("(xxu 'z)")));
    }
    
    @Test
    public void testNamedLetBreak() {
        eval(read("(define (fact n)"
            + "(call/cc (lambda (c)"
            + "   (let fact ((i 1) (r 1))"
            + "       (if (> i n)"
            + "          (c r)"
            + "          (fact (+ i 1) (* r i)))))))"));
        assertEquals(read("120"), eval(read("(fact 5)")));
    }
}
