package com.scmspain.infrastructure.commandbus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.test.context.ContextConfiguration;

import com.scmspain.MsFcTechTestApplication;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.infrastructure.commandbus.handler.ByeCommandHandler;
import com.scmspain.infrastructure.commandbus.handler.HelloCommandHandler;
import com.scmspain.infrastructure.commandbus.handler.MessageCollector;

import static org.mockito.Mockito.mock;

@Configuration
public class CommandBusITCaseConfiguration {

    @Bean
    public CommandBus getCommandBus(Registry registry) {
        return new SpringCommandBus(registry);
    }

}
