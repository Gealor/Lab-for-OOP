package functions.exceptions;

public class InappropriateFunctionPointException extends IllegalArgumentException{

    public InappropriateFunctionPointException(){
        super();
    }

    public InappropriateFunctionPointException(String message){
        super(message);
    }
}
