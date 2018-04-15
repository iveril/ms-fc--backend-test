package com.scmspain.infrastructure.commandbus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

import com.scmspain.domain.command.Command;
import com.scmspain.domain.command.CommandHandler;

/**
 * Basic implementation of a registry with the mapping between commands and handlers based on the Spring framework
 * application context.
 */
public class Registry {

    private final Map<Class<? extends Command>, CommandProvider> providerMap = new HashMap<>();

    /**
     * Constructor.
     *
     * @param applicationContext Spring framework application context.
     */
    @Autowired
    public Registry(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String name : names) {
            register(applicationContext, name);
        }
    }

    @SuppressWarnings("unchecked")
    private void register(ApplicationContext applicationContext, String name){
        Class<CommandHandler<?,?>> handlerClass = (Class<CommandHandler<?,?>>) applicationContext.getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
        Class<? extends Command> commandType = (Class<? extends Command>) generics[1];
        providerMap.put(commandType, new CommandProvider(applicationContext, handlerClass));
    }

    /**
     * Gets the proper handler for the given command class.
     *
     * @param commandClass Command class, should implement {@link Command}.
     * @param <R> type of return value.
     * @param <C> type of the command.
     * @return Handler for the command.
     */
    @SuppressWarnings("unchecked")
    <R, C extends Command<R>> CommandHandler<R, C> get(Class<C> commandClass) {
        return providerMap.get(commandClass).get();
    }

}
