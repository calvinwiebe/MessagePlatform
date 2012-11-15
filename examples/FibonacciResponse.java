import com.messaging.messages.*;

public class FibonacciResponse extends Message{

    public long fibNum;
    public int numberQuery;
    
    public FibonacciResponse () {
        type = MessageType.MP_RESPONSE;
        name = "FibonacciResponse";
    }
    
    public String toString() {
        return "Fibonacci number of " + numberQuery + " is: " + fibNum;
    }
}
