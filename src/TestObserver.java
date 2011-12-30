

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
		
		System.out.println("Hit a key");
		while (true)
		{
			@SuppressWarnings("unused")
			String input = in.next();
			Message message = new Message();
			message.type = MessageType.BASE_WEBCAL_REQUEST;
			putMessage(message);
			System.out.println("Hit a key");
		}
	}
	
	@Override
	public void onMessageReceived(Message message)
	{
		System.out.println("TestObserver: " + message.body);		
	}

}
