

import com.messaging.MessagePlatform;
import com.messaging.interfaces.MessagePlatformHook;

public class TestMessagePlatform {

	public static void main(String[] args)
	{
		TestObserver observer = new TestObserver();
		MessagePlatform platform = new MessagePlatform();
		MessagePlatformHook hook = platform.register(observer);
		observer.setHook(hook);
		Thread platformThread = platform.start();
		observer.start();
		try {
			platformThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
