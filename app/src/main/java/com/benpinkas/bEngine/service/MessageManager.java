package com.benpinkas.bEngine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Ben on 04-Oct-14.
 */
public class MessageManager  {

    private static ArrayList<String> messages = new ArrayList<String>();
    private static Map<String, Callable<Void> > listener = new HashMap<String, Callable<Void>>();


    public static void sendMessage(String m){
        messages.add(m);
    }

    public static void addListener(Callable<Void> l, String mess){
        listener.put(mess, l);
    }

    public static void deliverMessages() {

        if (messages.size() < 1){
            return;
        }

        for (String s :messages){
            try {
                listener.get(s).call();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            messages.remove(s);
        }
    }

}
