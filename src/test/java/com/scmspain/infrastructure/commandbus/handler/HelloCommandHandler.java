package com.scmspain.infrastructure.commandbus.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scmspain.domain.command.CommandHandler;
import com.scmspain.infrastructure.commandbus.command.HelloCommand;

@Component
public class HelloCommandHandler implements CommandHandler<String, HelloCommand> {

    private MessageCollector messageCollector;

    @Autowired
    public HelloCommandHandler(MessageCollector messageCollector) {
        this.messageCollector = messageCollector;
    }

    @Override
    public String handle(HelloCommand command) {
        String message = "Hello " + command.getName();
        if (messageCollector != null) {
            messageCollector.add(message);
        }
        return message;
    }

}
