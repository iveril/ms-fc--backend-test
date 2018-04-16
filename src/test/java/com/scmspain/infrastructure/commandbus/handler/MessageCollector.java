package com.scmspain.infrastructure.commandbus.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MessageCollector {

    private List<String> messages = new ArrayList<>();

    void add(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

}
