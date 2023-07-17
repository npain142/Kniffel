package logic.exceptions;


public class PlayerMaxException extends RuntimeException{


    public PlayerMaxException() {
        super("Maximum of Players reached!");
    }



}
