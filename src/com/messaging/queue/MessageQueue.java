package com.messaging.queue;

import java.util.concurrent.ArrayBlockingQueue;

import com.messaging.messages.Message;

/**
 * 
 * @author pg
 * 
 * class MessageQueue
 * 
 * wraps an ArrayBlockingQueue that contains objects of type Message
 * used for the main message queue of the MessagePlatform
 */
public class MessageQueue
{
	/** queue to wrap */
	private ArrayBlockingQueue<Message> queue;
	/** start the queue with a size of 10*/
	private final int INITIAL_QUEUE_SIZE = 10;
	
	/**
	 * Constructor
	 * 
	 * initialize the wrapped queue to the initial size
	 */
	public MessageQueue()
	{
		queue = new ArrayBlockingQueue<Message>(INITIAL_QUEUE_SIZE);
	}
	
	/**
	 * putMessage
	 * 
	 * add a message to the queue, the underlying ArrayBlockingQueue should increase
	 * the size when necessary
	 */
	public void putMessage(Message message)
	{
		queue.add(message);
	}
	
	/**
	 * takeMessage
	 * 
	 * take a message from the front of the queue. The underlying ArrayBlockingQueue
	 * will block the current thread if the queue is empty
	 */
	public Message takeMessage()
	{
		try {
			return queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * peek
	 * 
	 * returns, but does not remove, the message from the front of the queue
	 * @return the front message
	 */
	public Message peek()
	{
		return queue.peek();
	}
}