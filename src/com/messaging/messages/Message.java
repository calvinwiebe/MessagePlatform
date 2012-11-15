package com.messaging.messages;

/**
 * 
 * @author pg
 *
 * The base message class that all other specific messages are derived from
 */
public class Message
{
	/**the type of message - a request or a response*/
	public MessageType type;
	/**the name of the message */
	public String name;
	/**if there is a generic string for this message, place it in this field*/
	public String body;
}