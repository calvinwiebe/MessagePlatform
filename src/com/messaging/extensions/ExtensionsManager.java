package com.messaging.extensions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.messaging.adapter.Adapter;
import com.messaging.messages.Message;

/**
 * 
 * @author pg
 *
 * A manager singleton to load the class files of the extension messages and adapters
 */
public class ExtensionsManager {

    private HashMap<String, String> messageResponses;
    private HashMap<String, String> messageAdapters;
    
    private final int NUMOF_EXTENSION_VALUES = 3;
    
    /**
     * constructor
     * initializes variables and loads the extensions into the map data structure
     */
    public ExtensionsManager () {
        try {
            messageResponses = new HashMap<String, String>();
            messageAdapters = new HashMap<String, String>();
            loadExtensions();
        } catch (IOException e) {
            System.out.println("**ERROR** : Extensions could not be loaded. check .extensions file.");
            e.printStackTrace();
        }
    }
    
    public Message getResponseForRequest(String request) {
        Message message = null;
        try {
            Class messageClass = ClassLoader.getSystemClassLoader().loadClass((String)messageResponses.get(request));
            message = (Message)messageClass.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("ExtensionsManager: Could not find class " + (String)messageResponses.get(request));
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return message;
    }
    
    public Adapter getAdapterForRequest(String request) {
        Adapter adapter = null;
        try {
            Class messageClass = ClassLoader.getSystemClassLoader().loadClass((String)messageAdapters.get(request));
            adapter = (Adapter)messageClass.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("ExtensionsManager: Could not find class " + (String)messageAdapters.get(request));
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return adapter;
    }
    
    public Set<String> getRequests() {
        return messageResponses.keySet();
    }
        
    private void loadExtensions() throws IOException {
        FileInputStream in = new FileInputStream(System.getenv("MESSAGEPLATFORM_EXTENSIONS_DIR") + "/.extensions");
        Properties props = new Properties();
        props.load(in);
        for(Enumeration<Object> enumer = props.keys(); enumer.hasMoreElements();) {
            String key = (String) enumer.nextElement();
            String[] values = ((String)props.get(key)).split(",");
            if (values.length < NUMOF_EXTENSION_VALUES) {
                continue;
            }
            else {
                messageResponses.put(values[0], values[1].trim());
                messageAdapters.put(values[0], values[2].trim());
            }
        }
    }
}
