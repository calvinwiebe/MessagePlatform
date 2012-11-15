package com.messaging.queue.managers;

import java.util.Iterator;

import com.messaging.adapter.Adapter;
import com.messaging.adapter.AdapterRunner;
import com.messaging.extensions.ExtensionsManager;
import com.messaging.interfaces.Observer;
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
public class BaseMessageManager implements Runnable, Observer
{
	/**the queue*/
	private MessageQueue messageQueue;
	private ExtensionsManager extensionsManager;
	/**the http manager*/
	
	/**
	 * Constructor
	 * 
	 * @param aMessageQueue
	 */
	public BaseMessageManager(MessageQueue aMessageQueue, ExtensionsManager anExtensionsManager)
	{
		messageQueue = aMessageQueue;
		extensionsManager = anExtensionsManager;
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
				case MP_REQUEST:
				{
					Iterator<String> requests = extensionsManager.getRequests().iterator();
					while (requests.hasNext()) {
					    String request = requests.next();
					    if (message.name.equals(request)) {
					        //We dynamically load an Adapter using reflection from the extensions folder
					        //create an instance and pass the work off to that thread
					        Adapter adapter = extensionsManager.getAdapterForRequest(request);
					        Runnable runnable = new AdapterRunner(adapter, messageQueue.takeMessage(), this);
					        Thread worker = new Thread(runnable);
					        worker.start();
					    }
					}
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

    @Override
    public void onMessageReceived(Message message) {
        messageQueue.putMessage(message);
    }

}