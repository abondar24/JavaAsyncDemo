package org.abondar.experimental.async.vertx.verticle.edgeservice;

public class Message {

    private final String id;
    private final String body;

    public Message(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
