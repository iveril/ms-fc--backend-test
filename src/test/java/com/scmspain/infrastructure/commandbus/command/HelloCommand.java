package com.scmspain.infrastructure.commandbus.command;

import com.scmspain.domain.command.Command;

public class HelloCommand implements Command<String> {

    private final String name;

    public HelloCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
