package com.messaging;

import com.messaging.interfaces.MessagePlatformHook;
import com.messaging.interfaces.UIFrameworkHook;
import com.messaging.queue.MessageQueue;
import com.messaging.queue.managers.BaseMessageManager;
import com.messaging.queue.managers.UIMessageManager;

/**
 * 
 * @author pg
 * 
 * class MessagePlatform
 * 
 * The main starting point of the MessagePlatform. This instantiates the main message queue,
 * the consumer/producer thread for the base, and the consumer/producer thread for the UI.
 * 
 * The implementing UI framework can hook into the consumer/producer UI thread to send/receive messages
 * from the MessagePlatform. 
 */
public class MessagePlatform {
	
	/**the main message queue of the MessagePlatform*/
	MessageQueue theMessageQueue;
	/**the consumer/producer for the base*/
	BaseMessageManager baseMessageManager;
	/**the consumer/producer for the ui*/
	UIMessageManager uiMessageManager;
	
	/**
	 * Constructor
	 * 
	 * intializes the queue and the consumer threads
	 * @param uiHookCallback - object to send UI messages to
	 */
	public MessagePlatform()
	{
		theMessageQueue = new MessageQueue();
		baseMessageManager = new BaseMessageManager(theMessageQueue);
		uiMessageManager = new UIMessageManager(theMessageQueue);		
	}
	
	/**
	 * register
	 * 
	 * registers the ui framework observer with the platform to receive messages
	 * @param aHook - the ui framework observer
	 * @return MessagePlatformHook - the platform hook for the ui framework to send messages to
	 */
	public MessagePlatformHook register(UIFrameworkHook aHook)
	{
		uiMessageManager.setUIFrameworkHook(aHook);
		return uiMessageManager.getHook();
	}

	/**
	 * start
	 * 
	 * kicks off all the threads
	 * @return Thread - the base thread, used by the calling UI Framework to join to the main methods thread
	 */
	public Thread start()
	{
		Thread baseMessageThread = new Thread(baseMessageManager);
		Thread uiMessageThread = new Thread(uiMessageManager);
		
		baseMessageThread.start();
		uiMessageThread.start();	

		return baseMessageThread;
	}
}
