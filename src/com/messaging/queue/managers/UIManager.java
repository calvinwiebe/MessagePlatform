package com.messaging.queue.managers;

import com.messaging.interfaces.Manager;
import com.messaging.interfaces.MessagePlatformHook;
import com.messaging.interfaces.Observer;
import com.messaging.interfaces.UIFrameworkHook;
import com.messaging.messages.Message;
import com.messaging.queue.MessageQueue;

/**
 * 
 * @author pg
 *
 * class UIManager
 * 
 * the Manager the interacts with the calling UI framework, used as a hook between it and the platform
 */
public class UIManager implements Manager, Observer, MessagePlatformHook{
	
	/**the queue*/
	private MessageQueue messageQueue;
	/**the class in the calling UI Framework that is hooked up with the platform*/
	private UIFrameworkHook hook;
	
	/**
	 * Constructor
	 * 
	 * @param aMessageQueue
	 */
	public UIManager(MessageQueue aMessageQueue)
	{
		messageQueue = aMessageQueue;
	}
	
	/**
	 * setUIFrameworkHook
	 * 
	 * save the class from the calling UI Framework to be sent/receive messages
	 * @param aHook
	 */
	public void setUIFrameworkHook(UIFrameworkHook aHook)
	{
		hook  = aHook;
	}

	@Override
	public void onMessageReceived(Message message) {
		switch (message.type)
		{
		case BASE_WEBCAL_REQUEST:
		{
			messageQueue.putMessage(message);
			break;
		}
		case UI_WEBCAL_RESPONSE:
		{
			hook.onMessageReceived(message);
			break;
		}
		}
	}
	

}
