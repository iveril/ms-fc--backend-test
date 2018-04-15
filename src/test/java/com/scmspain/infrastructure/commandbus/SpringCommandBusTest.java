package com.scmspain.infrastructure.commandbus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.scmspain.domain.command.CommandHandler;
import com.scmspain.infrastructure.commandbus.command.HelloCommand;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpringCommandBusTest {

    @Mock
    private Registry registry;

    @Mock
    private CommandHandler<String, HelloCommand> handler;

    @InjectMocks
    private SpringCommandBus commandBus;

    @Test
    public void shouldExecuteHandlerForCommand() {
        when(registry.get(HelloCommand.class)).thenReturn(handler);

        HelloCommand command = new HelloCommand("Schibsted");
        commandBus.execute(command);

        verify(handler).handle(command);
    }

}