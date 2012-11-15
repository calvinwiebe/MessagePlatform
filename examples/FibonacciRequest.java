import com.messaging.messages.Message;
import com.messaging.messages.MessageType;

public class FibonacciRequest extends Message{

    public int numberQuery;
    
    public FibonacciRequest () {
        type = MessageType.MP_REQUEST;
        name = "FibonacciRequest";
        numberQuery = -1;
    }
    
}
