package com.messaging.queue.managers;

import com.messaging.extensions.ExtensionsManager;
import com.messaging.interfaces.MessagePlatformHook;
import com.messaging.interfaces.UIFrameworkHook;
import com.messaging.messages.Message;
import com.messaging.queue.MessageQueue;

/**
 * 
 * @author pg
 *
 * class UIMessageManager
 * 
 * Singleton thread class. Grabs messages from the queue that the UI cares about 
 * and passes them to the UI Framework hook
 */
public class UIMessageManager implements Runnable
{
	/**the queue*/
	private MessageQueue messageQueue;
	private ExtensionsManager extensionsManager;
	/**the manager that is hooked to the UI*/
	/* TODO
	 * make it so managers are registered with this class dynamically
	 */
	private UIManager uiManager;
	
	/**
	 * Constructor
	 * 
	 * @param aMessageQueue
	 */
	public UIMessageManager(MessageQueue aMessageQueue, ExtensionsManager anExtensionsManager)
	{
		messageQueue = aMessageQueue;
		extensionsManager = anExtensionsManager;
		uiManager = new UIManager(messageQueue);
	}
	
	/**
	 * setUIFrameworkHook
	 * gives the ui framework hook to the ui manager
	 * @param aHook
	 */
	public void setUIFrameworkHook(UIFrameworkHook aHook)
	{
		uiManager.setUIFrameworkHook(aHook);
	}
	
	/**
	 * getHook
	 * 
	 * gives the uiManager to the calling UI framework as the hook to the platform
	 * @return MessagePlatformHook the uiManager
	 */
	public MessagePlatformHook getHook()
	{
		return uiManager;
	}
	
	/**
	 * dispatchMessage
	 * 
	 * run forever looking at the queue for messages to pass to the UI
	 */
	private void dispatchMessage()
	{
		while (true)
		{
			Message message = messageQueue.peek();
			if (message != null)
			{
				switch (message.type)
				{
				case UI_WEBCAL_RESPONSE:
				{
					message = messageQueue.takeMessage();
					uiManager.onMessageReceived(message);
					break;
				}
				default:
					break;
				}
			}
		}
	}

	@Override
	public void run() {
		dispatchMessage();
	}
	
	
}