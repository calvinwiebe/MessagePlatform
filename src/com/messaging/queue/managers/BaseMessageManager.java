package com.messaging.queue.managers;

import com.messaging.interfaces.Manager;
import com.messaging.messages.Message;
import com.messaging.queue.MessageQueue;

/**
 * 
 * @author pg
 * 
 * The message manager for the Base of the platform. This class is a Singleton thread. It constantly looks at the queue for
 * some work and then delegates it to the registered Managers. 
 *
 */
public class BaseMessageManager implements Runnable
{
	/**the queue*/
	private MessageQueue messageQueue;
	/**the http manager*/
	/* TODO
	 * make it so managers are registered with this class dynamically
	 */
	private Manager httpManager;
	
	/**
	 * Constructor
	 * 
	 * @param aMessageQueue
	 */
	public BaseMessageManager(MessageQueue aMessageQueue)
	{
		messageQueue = aMessageQueue;
		httpManager = new HttpManager(messageQueue);
	}
	
	/**
	 * run forever and look at the queue for work to be done
	 */
	private void dispatchMessage()
	{
		while (true)
		{
			Message message = messageQueue.peek();
			if (message != null)
			{
				switch (message.type)
				{
				case BASE_WEBCAL_REQUEST:
				{
					message = messageQueue.takeMessage();
					httpManager.onMessageReceived(message);
					break;
				}
				default:
					break;
				}
			}
		}
	}

	@Override
	public void run()
	{
		dispatchMessage();
	}

}