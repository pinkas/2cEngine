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
    private static Map<String, Bcall<Void> > listener = new HashMap<String, Bcall<Void>>();
    private static Map<String, Object> datas = new HashMap<String, Object>();


    public static void sendMessage(String m){
        messages.add(m);
    }

    public static void sendMessage(String m, Object data){
        messages.add(m);
        datas.put(m, data);
    }

    public static void addListener(Bcall<Void> l, String mess){
        listener.put(mess, l);
    }

    public static void deliverMessages() {

        if (messages.size() < 1){
            return;
        }

        for (String s :messages){
            System.out.println( "MessageManager " + s);
            Bcall blah = listener.get(s);
            if (blah != null) {
                blah.call(null);
            }
        }
        messages.clear();
        datas.clear();
    }

}
