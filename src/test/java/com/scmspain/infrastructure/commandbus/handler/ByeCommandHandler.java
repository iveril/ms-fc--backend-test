package com.scmspain.infrastructure.commandbus.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scmspain.domain.command.CommandHandler;
import com.scmspain.infrastructure.commandbus.command.ByeCommand;

@Component
public class ByeCommandHandler implements CommandHandler<Void, ByeCommand> {

    private MessageCollector messageCollector;

    @Autowired
    public ByeCommandHandler(MessageCollector messageCollector) {
        this.messageCollector = messageCollector;
    }

    @Override
    public Void handle(ByeCommand command) {
        messageCollector.add("Bye " + command.getName());
        return null;
    }

}
