package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException{

    // Конструктор по умолчанию
    public FunctionPointIndexOutOfBoundsException(){
        super();
    }

    // Конструктор с сообщением
    public FunctionPointIndexOutOfBoundsException(String message) {
        super(message);
    }
}
