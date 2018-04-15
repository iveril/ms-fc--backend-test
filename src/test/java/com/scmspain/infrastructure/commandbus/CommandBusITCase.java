package com.scmspain.infrastructure.commandbus;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.scmspain.domain.command.CommandBus;
import com.scmspain.infrastructure.commandbus.command.ByeCommand;
import com.scmspain.infrastructure.commandbus.command.HelloCommand;
import com.scmspain.infrastructure.commandbus.handler.ByeCommandHandler;
import com.scmspain.infrastructure.commandbus.handler.HelloCommandHandler;
import com.scmspain.infrastructure.commandbus.handler.MessageCollector;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {
    HelloCommandHandler.class,
    ByeCommandHandler.class,
    MessageCollector.class,
    Registry.class
})
@Import(CommandBusITCaseConfiguration.class)
@RunWith(SpringRunner.class)
public class CommandBusITCase {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void executeHandlersForGivenCommands() {
        String actualStringReturnValue = commandBus.execute(new HelloCommand("Schibsted"));
        Void actualVoidReturnValue = commandBus.execute(new ByeCommand("AEM"));

        Assertions.assertThat(messageCollector.getMessages()).contains("Hello Schibsted", "Bye AEM");

        assertThat(actualStringReturnValue).isEqualTo("Hello Schibsted");
        assertThat(actualVoidReturnValue).isNull();
    }

}