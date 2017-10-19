package cs445.a2;

/**
 * A type of runtime exception thrown when the given expression is found to
 * be invalid
 */
public class ExpressionError extends RuntimeException {
    ExpressionError(String msg) {
        super(msg);
    }
}

