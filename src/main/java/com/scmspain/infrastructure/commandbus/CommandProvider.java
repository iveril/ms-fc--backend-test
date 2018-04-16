package com.scmspain.infrastructure.commandbus;

import org.springframework.context.ApplicationContext;

import com.scmspain.domain.command.CommandHandler;

/**
 * Basic implementation of a command handler provider.
 * Returns a registered bean handler from the Spring framework application context.
 *
 * @param <H> type of handler
 */
class CommandProvider<H extends CommandHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Class<H> type;

    /**
     * Constructor.
     *
     * @param applicationContext Spring framework application context.
     * @param type Type of handler.
     */
    CommandProvider(ApplicationContext applicationContext, Class<H> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    /**
     * Gets a bean of the proper type from the Spring framework application context.
     * @return Spring bean.
     */
    H get() {
        return applicationContext.getBean(type);
    }

}
