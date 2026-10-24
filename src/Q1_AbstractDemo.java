// Q1: abstract class with one abstract method (add) and one concrete method (multiply)
abstract class MathOperations {
    abstract int add(int a, int b);          // abstract: no body
    int multiply(int a, int b) {             // non-abstract: has a body
        return a * b;
    }
}

class Calculator extends MathOperations {
    @Override
    int add(int a, int b) {                  // subclass must implement add
        return a + b;
    }
}

public class Q1_AbstractDemo {
    public static void main(String[] args) {
        MathOperations m = new Calculator();
        System.out.println("add(5, 3)      = " + m.add(5, 3));
        System.out.println("multiply(5, 3) = " + m.multiply(5, 3));
    }
}
