package com.scmspain.infrastructure.commandbus.command;

import com.scmspain.domain.command.Command;

public class ByeCommand implements Command<Void> {

    private final String name;

    public ByeCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
