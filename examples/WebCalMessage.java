

import java.util.ArrayList;
import java.util.Iterator;

import com.messaging.messages.*;


/**
 * @author pg
 * 
 * Class WebCalMessage
 * contains extra fields of WebCalendar events in an array list
 *
 */
public class WebCalMessage extends Message{
	
	/**the list of events*/
	private ArrayList<WebCalEvent> events;
	
	/**
	 * Constructor
	 * 
	 * initialize the array list of events
	 */
	public WebCalMessage()
	{
	    name = "WebCalMessage";
		events = new ArrayList<WebCalEvent>();
	}
	
	/**
	 * addEvents
	 * 
	 * add an event to the array list
	 * @param event
	 */
	public void addEvent(WebCalEvent event)
	{
		events.add(event);
	}
	
	/**
	 * iterator
	 * 
	 * return an iterator for the array list to be iterated over
	 * 
	 * @return the iterator
	 */
	public Iterator<WebCalEvent> iterator()
	{
		return events.iterator();
	}
}
