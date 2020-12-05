package com.example.pekon.helloandroid;

/**
 * create by dsw on 2020-10-18
 **/
public class EventEntity {

    private String type;
    private String message;

    public EventEntity() {
    }


    public EventEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public EventEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getType() {
        return type;
    }

    public EventEntity setType(String type) {
        this.type = type;
        return this;
    }
}
