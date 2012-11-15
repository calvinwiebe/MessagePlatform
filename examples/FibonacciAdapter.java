import com.messaging.interfaces.Observer;
import com.messaging.messages.*;
import com.messaging.adapter.*;

public class FibonacciAdapter implements Adapter{

    @Override
    public void onMessageReceived(Message message, Observer anObserver) {
        FibonacciResponse response = new FibonacciResponse();
        response.numberQuery = ((FibonacciRequest) message).numberQuery;
        //response.fibNum = f(response.numberQuery);
        response.fibNum = f2(response.numberQuery);
        anObserver.onMessageReceived(response);
    }
    
    public long f(long numberQuery) {
        if ((numberQuery == 1) || 
            (numberQuery == 2)) {
            return 1;
        }
        else {
            return f(numberQuery - 1) + f(numberQuery - 2);
        }
    }
    
    public long f2(long n) {
        double phi = (1 + Math.sqrt(5))/2;
        double vi = 1 - phi;
        //
        // return: 
        //
        //     phi^n - vi^n
        //  ------------------
        //     phi - vi
        //
        return (long) ((Math.pow(phi, n) - Math.pow(vi, n) ) / Math.sqrt(5));
    }

}
