package com.scmspain.infrastructure.commandbus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.scmspain.domain.command.CommandHandler;
import com.scmspain.infrastructure.commandbus.command.HelloCommand;
import com.scmspain.infrastructure.commandbus.handler.HelloCommandHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistryTest {

    private static final String HELLO_COMMAND_HANDLER = "helloCommandHandler";

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private HelloCommandHandler helloCommandHandler;

    @Test
    public void shouldReturnRegisteredHandlerForCommands() {
        String[] commandHandlers = new String[] { HELLO_COMMAND_HANDLER };
        when(applicationContext.getBeanNamesForType(CommandHandler.class)).thenReturn(commandHandlers);

        Class type = HelloCommandHandler.class;
        when(applicationContext.getType(HELLO_COMMAND_HANDLER)).thenReturn(type);

        when(applicationContext.getBean(HelloCommandHandler.class)).thenReturn(helloCommandHandler);

        Registry registry = new Registry(applicationContext);
        CommandHandler<String, HelloCommand> handler = registry.get(HelloCommand.class);

        assertThat(handler).isInstanceOf(HelloCommandHandler.class);
    }

}
