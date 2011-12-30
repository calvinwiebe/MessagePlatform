package com.messaging.adapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.messaging.interfaces.Observer;
import com.messaging.messages.Message;
import com.messaging.messages.MessageType;
import com.messaging.messages.WebCalEvent;
import com.messaging.messages.WebCalMessage;

/**
 * 
 * @author pg
 * 
 * Class WebCalAdapter
 * 
 * An Adapter that makes http requests to get .ics webcal calendars. This
 * class will parse the response and place the individual events into a WebCalMessage, and
 * return it to the calling UI layer.
 */
public class WebCalAdapter implements Adapter
{
	/**start of the event in .ics delimiter*/
	private final String EVENT_START_DELIMITER = "BEGIN:VEVENT";
	/**end of the event in .ics delimiter*/
	private final String EVENT_END_DELIMITER = "END:VEVENT";
	/**start date of the event*/
	private final String EVENT_DATE_START_DELIMITER = "DTSTART";
	/**end date of the event*/
	private final String EVENT_DATE_END_DELIMITER = "DTEND";
	/**start of the description in .ics*/
	private final String EVENT_DESCRIPTION_DELIMITER = "DESCRIPTION";
	
	private final boolean DEBUG = true;

	/**
	 * sendRequest
	 * 
	 * make an http get request to get the webcal calendar, parse it, and return the parsed message
	 * to the calling observer to be put on the queue.
	 * @return Message - a WebCalMessage
	 */
	public Message sendRequest()
	{
		//DEBUG not set, make a normal http request
		if (!DEBUG)
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("https://www.google.com/calendar/ical/calvin.wiebe%40gmail.com/public/basic.ics");
			try
			{
				HttpResponse response = client.execute(get);
				WebCalMessage message = new WebCalMessage();
				message.type = MessageType.UI_WEBCAL_RESPONSE;
				message.body = entityToString(response.getEntity());
				parseResponse(message);
				return message;
			} 
			catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}
		//DEBUG set, we may not have internet connection
		//grab the information for a static local file
		else
		{
			WebCalMessage message = new WebCalMessage();
			message.type = MessageType.UI_WEBCAL_RESPONSE;
			message.body = "";

			FileReader file = null;
			BufferedReader reader = null;
			try
			{
				file = new FileReader("C:\\Users\\pg\\workspace\\MessagePlatform\\lib\\basic.ics");
				reader = new BufferedReader(file);
				
				String line = reader.readLine();
				
				while (line != null)
				{
					message.body += line;
					message.body += System.getProperty("line.separator");
					line = reader.readLine();
				}
				
				parseResponse(message);
				message.body = "message @ time: " + Calendar.getInstance().getTimeInMillis();
				Thread.sleep((long) (Math.random()*12));
				return message;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			return null;
		}
	}
	
	/**
	 * parseResponse
	 * 
	 * parse the webcal .ics returned text and put the individual events into
	 * the WebCalMessage that is passed in by value
	 * @param message - the WebCalMessag to add the events to
	 */
	private void parseResponse(WebCalMessage message)
	{
	    String[] lines = message.body.split("\\n");
	    int last = lines.length;
	    int current = 0;
	    while (current != last)
	    {
	    	String line = lines[current];
	    	if (line.contains(EVENT_START_DELIMITER))
	    	{
	    		parseEvent(message, current);
	    	}
	    	current++;
	    }
	}

	/**
	 * parseEvent
	 * 
	 * the message now contains a body which is the returned .ics text. The parseResponse method uses this method
	 * to parse an individual event. It does this by splitting the message.body on newlines and then passes the 
	 * position of the event in the split array to this function.
	 * @param message - the WebCalMessage object
	 * @param position - position of the current event in the split array
	 */
	private void parseEvent(WebCalMessage message, int position)
	{
	    WebCalEvent event = new WebCalEvent();
	    String[] lines = message.body.split("\\n");
	    int last = lines.length;
	    int current = position;
	    while (current != last)
	    {
	    	String line = lines[current];
	    	if (line.contains(EVENT_DATE_START_DELIMITER))
	    	{
	    		String date = (line.split(":")[1]).split("T")[0];
	    		String year = date.substring(0, 4);
	    		String month = date.substring(4, 6);
	    		String day = date.substring(6, 8);
	    		event.dateStart = year + "-" + month + "-" + day;
	    	}
	    	else if (line.contains(EVENT_DATE_END_DELIMITER))
	    	{
	    		String date = (line.split(":")[1]).split("T")[0];
	    		String year = date.substring(0, 4);
	    		String month = date.substring(4, 6);
	    		String day = date.substring(6, 8);
	    		event.dateEnd = year + "-" + month + "-" + day;
	    	}
	    	else if (line.contains(EVENT_DESCRIPTION_DELIMITER))
	    	{
	    		event.description = "some desc";
	    	}
	    	else if (line.contains(EVENT_END_DELIMITER))
	    	{
	    		message.addEvent(event);
	    		break;
	    	}
	    	current++;
	    }
	}

	/**
	 * entityToString
	 * 
	 * take the entity of the http response and return the http response string body of it
	 * @param entity
	 * @return the body of the response
	 */
	private String entityToString(HttpEntity entity) throws IllegalStateException, IOException
	{
		  InputStream is = entity.getContent();
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		  StringBuilder str = new StringBuilder();

		  String line = null;
		  try {
		    while ((line = bufferedReader.readLine()) != null) {
		      str.append(line + "\n");
		    }
		  } catch (IOException e) {
		    throw new RuntimeException(e);
		  } finally {
		    try {
		      is.close();
		    } catch (IOException e) {
		      //tough luck...
		    }
		  }
		  return str.toString();
	}
	
	@Override
	public void onMessageReceived(Message message, Observer anObserver)
	{
		switch (message.type)
		{
		case BASE_WEBCAL_REQUEST:
			Message response = sendRequest();
			anObserver.onMessageReceived(response);
			break;
		default:
			break;
		}
	}

}