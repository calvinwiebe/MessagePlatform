package com.messaging.extensions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

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
        messageResponses = new HashMap<String, String>();
        messageAdapters = new HashMap<String, String>();
        try {
            loadExtensions();
        } catch (IOException e) {
            System.out.println("**ERROR** : Extensions could not be loaded. check .extensions file.");
            e.printStackTrace();
        }
    }
    
    public MPResponse getResponseForRequest() {
        
    }
    
    public MPAdapter getAdapterForRequest() {
        
    }
    
    public Enumeration<Object> getResponses() {
        return messageResponses;
    }
    
    
    private void loadExtensions() throws IOException {
        FileInputStream in = new FileInputStream("/home/pg/dev/messageplatform/extensions/.extensions");
        Properties props = new Properties();
        props.load(in);
        for(Enumeration<Object> enumer = props.keys(); enumer.hasMoreElements();) {
            Object key = enumer.nextElement();
            String[] values = ((String)props.get(key)).split(",");
            if (values.length < NUMOF_EXTENSION_VALUES) {
                continue;
            }
            else {
                messageResponses.put(values[0], values[1]);
                messageAdapters.put(values[0], values[2]);
            }
        }
    }
}
