package logic.exceptions;

public class UnknownExpressionException extends RuntimeException{

    public UnknownExpressionException() {
        super("UNKNOWN EXPRESSION!");
    }
}
