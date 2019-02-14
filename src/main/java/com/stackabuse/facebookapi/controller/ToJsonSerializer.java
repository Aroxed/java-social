package com.stackabuse.facebookapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJsonSerializer {

    ObjectMapper objectMapper;
    String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    Object body;

    public ToJsonSerializer(String command, Object body, ObjectMapper objectMapper) {
        this.command = command;
        this.body = body;
        this.objectMapper = objectMapper;
    }

    public String serialize() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}