package com.scmspain.infrastructure.commandbus;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.CommandException;
import com.scmspain.infrastructure.commandbus.command.ByeCommand;
import com.scmspain.infrastructure.commandbus.command.HelloCommand;
import com.scmspain.infrastructure.commandbus.handler.MessageCollector;
import com.scmspain.infrastructure.configuration.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class CommandBusITCase {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void executeHandlersForGivenCommands() throws CommandException {
        String actualStringReturnValue = commandBus.execute(new HelloCommand("Schibsted"));
        Void actualVoidReturnValue = commandBus.execute(new ByeCommand("AEM"));

        Assertions.assertThat(messageCollector.getMessages()).contains("Hello Schibsted", "Bye AEM");

        assertThat(actualStringReturnValue).isEqualTo("Hello Schibsted");
        assertThat(actualVoidReturnValue).isNull();
    }

}