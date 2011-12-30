package com.messaging.interfaces;

import com.messaging.messages.Message;

/**
 * 
 * @author pg
 *
 * interface MessagePlatformHook
 * 
 * mainly created to give a readable and meaningful name to the object used as the hook for sending messages 
 * from the calling UI Framework to the MessagePlatform
 */
public interface MessagePlatformHook {
	
	/**
	 * used to receive and delegate a MessagePlatform message
	 * @param message
	 */
	public void onMessageReceived(Message message);
}
