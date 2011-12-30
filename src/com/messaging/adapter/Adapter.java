package com.messaging.adapter;

import com.messaging.interfaces.Observer;
import com.messaging.messages.Message;

/**
 * 
 * @author pg
 * 
 * Interface Adapter
 * 
 * provide a modular way from Managers to talk to different Adapters
 */
public interface Adapter {
	
	/**
	 * Receive a MessagePlatform message and dispatch the work inside the class
	 * @param message - the MessagePlatform message
	 * @param anObserver - observer class to be called back when the work of the message has been completed
	 */
	public void onMessageReceived(Message message, Observer anObserver);
}
