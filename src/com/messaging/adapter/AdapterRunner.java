package com.messaging.adapter;

import com.messaging.interfaces.Observer;
import com.messaging.messages.Message;


/**
 * @author pg
 *
 * class AdapterRunner
 * Class that can be run as a worker thread and encapsulates an Adapter which is 
 * delegated the work
 */
public class AdapterRunner implements Runnable{
	
	/**the encapsulated adapter*/
	private Adapter adapter;
	/**the message taken from the queue*/
	private Message message;
	/**the callback*/
	private Observer observer;

	/**
	 * Constructor
	 * 
	 * @param anAdapter
	 * @param aMessage
	 * @param anObserver
	 */
	public AdapterRunner(Adapter anAdapter, Message aMessage, Observer anObserver)
	{
		adapter = anAdapter;
		message = aMessage;
		observer = anObserver;
	}
	
	/**
	 * where the encapsulated adapter is given the work
	 */
	public void runAdapter()
	{
		adapter.onMessageReceived(message, observer);
	}
	
	@Override
	public void run()
	{
		runAdapter();
	}
}
