package com.messaging.interfaces;

import com.messaging.messages.Message;

/**
 * 
 * @author pg
 *
 * interface Manager
 */
public interface Manager {
	
	/**
	 * used to receive and delegate a MessagePlatform message
	 * @param message
	 */
	public void onMessageReceived(Message message);
}
