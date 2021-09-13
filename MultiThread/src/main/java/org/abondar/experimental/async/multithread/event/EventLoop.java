package org.abondar.experimental.async.multithread.event;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class EventLoop {
    private final ConcurrentLinkedDeque<Event> events;
    private final ConcurrentHashMap<String, Consumer<Object>> handlers;

    public EventLoop(){
        this.events = new ConcurrentLinkedDeque<>();
        this.handlers = new ConcurrentHashMap<>();
    }

    public EventLoop on(String key, Consumer<Object> handler){
        handlers.put(key,handler);
        return this;
    }

    public void dispatch(Event event){
        events.add(event);
    }

    public void stop(){
        Thread.currentThread().interrupt();
    }

    public void run(){
        while (!(events.isEmpty() && Thread.interrupted())){
            if (!events.isEmpty()){
                Event event = events.pop();
                if (handlers.containsKey(event.key)){
                    handlers.get(event.key).accept(event.data);
                } else {
                    System.err.printf("No handler for key %s\n",event.key);
                }
            }
        }
    }


    public static final class Event {

        private final String key;
        private final Object data;

        public Event(String key, Object data) {
            this.key = key;
            this.data = data;
        }


    }
}


