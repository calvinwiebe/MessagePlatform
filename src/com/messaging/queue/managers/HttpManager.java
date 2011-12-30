package com.messaging.queue.managers;

import com.messaging.adapter.Adapter;
import com.messaging.adapter.AdapterRunner;
import com.messaging.adapter.WebCalAdapter;
import com.messaging.interfaces.Manager;
import com.messaging.interfaces.Observer;
import com.messaging.messages.Message;
import com.messaging.queue.MessageQueue;

/**
 * 
 * @author pg
 * 
 * Class HttpManager
 * 
 * takes messages from the BaseMessageManager and carries out the work. Generally  used for contacting REST APIs
 * or other web services. Spawns new threads for the adapter to complete the work. 
 *
 */
public class HttpManager implements Manager, Observer{
	
	/**web cal adapter*/
	/* TODO
	 * make it so adapters are registered with this manager dynamically
	 */
	private Adapter webCalAdapter;
	/**the queue - used to put messages back on when the adapter returns*/
	private MessageQueue messageQueue;
	
	/**
	 * Constructor
	 * 
	 * @param theMessageQueue
	 */
	public HttpManager(MessageQueue theMessageQueue)
	{
		webCalAdapter = new WebCalAdapter();
		messageQueue = theMessageQueue;
	}

	@Override
	public void onMessageReceived(Message message)
	{
		switch (message.type)
		{
		case BASE_WEBCAL_REQUEST:
		{
			Runnable runner = new AdapterRunner(webCalAdapter, message, this);
			Thread worker = new Thread(runner);
			worker.start();
			break;
		}
		case UI_WEBCAL_RESPONSE:
		{
			messageQueue.putMessage(message);
			break;
		}
		}
		
	}
}
