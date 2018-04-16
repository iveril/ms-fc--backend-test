package com.scmspain.infrastructure.commandbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scmspain.domain.command.Command;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.CommandException;
import com.scmspain.domain.command.CommandHandler;

/**
 * Basic implementation of a command bus based on Spring framework application context.
 */
@Component
public class SpringCommandBus implements CommandBus {

    private final Registry registry;

    /**
     * Creates a new instance with the given registry.
     *
     * @param registry registry
     */
    @Autowired
    public SpringCommandBus(Registry registry) {
        this.registry = registry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, C extends Command<R>> R execute(C command) throws CommandException {
        CommandHandler<R, C> commandHandler = (CommandHandler<R, C>) registry.get(command.getClass());
        return commandHandler.handle(command);
    }

}
