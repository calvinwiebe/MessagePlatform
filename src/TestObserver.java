

import java.io.InputStream;
import java.util.Scanner;

import com.messaging.interfaces.MessagePlatformHook;
import com.messaging.interfaces.UIFrameworkHook;
import com.messaging.messages.Message;
import com.messaging.messages.MessageType;

public class TestObserver implements UIFrameworkHook{
	
	private MessagePlatformHook hook;

	public void setHook(MessagePlatformHook aHook)
	{
		hook = aHook;
	}
	
	private void putMessage(Message message)
	{
		hook.onMessageReceived(message);
	}
	
	public void start()
	{
		InputStream is = System.in;
		Scanner in = new Scanner(is);
		
		while (true)
		{
		    System.out.println("Choose an option:");
            System.out.println("1) Run WebCal");
            System.out.println("2) Calculate a Fibonacci number");
			String input = in.next();
			switch (Integer.parseInt(input))
			{
    			case 1:
    			{
    			    WebCalMessage message = new WebCalMessage();
                    message.type = MessageType.MP_REQUEST;
                    putMessage(message);
                    break;
    			}
			case 2:
    			{
    			    System.out.println("Enter a number");
    			    String fibInput = in.next();
    			    FibonacciRequest message = new FibonacciRequest();
                    message.type = MessageType.MP_REQUEST;
                    message.numberQuery = Integer.parseInt(fibInput);
                    putMessage(message);
                    break;
    			}
            default:
                break;
			}
		}
	}
	
	@Override
	public void onMessageReceived(Message message)
	{
		System.out.println("\n\nTestObserver: " + message.toString() + "\n\n");		
	}

}
