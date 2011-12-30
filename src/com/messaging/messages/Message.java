package com.messaging.messages;

/**
 * 
 * @author pg
 *
 * The base message class that all other specific messages are derived from
 */
public class Message
{
	/**the type of message to be switched off of*/
	public MessageType type;
	/**if there is a generic string for this message, place it in this field*/
	public String body;
}