package com.scmspain.infrastructure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.CommandException;
import com.scmspain.domain.command.DiscardTweetCommand;
import com.scmspain.domain.command.ListAllDiscardedTweetsCommand;
import com.scmspain.domain.command.ListAllTweetsCommand;
import com.scmspain.domain.command.PublishTweetCommand;
import com.scmspain.domain.model.TweetResponse;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Rest controller for tweet API.
 */
@RestController
public class TweetController {

    private final CommandBus commandBus;

    /**
     * Constructor.
     *
     * @param commandBus Command bus.
     */
    public TweetController(final CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/tweet")
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody PublishTweetCommand publishTweetCommand) throws CommandException {
        this.commandBus.execute(publishTweetCommand);
    }

    @GetMapping("/tweet")
    public List<TweetResponse> listAllTweets() throws CommandException {
        return this.commandBus.execute(new ListAllTweetsCommand());
    }

    @PostMapping("/discarded")
    public void discardTweet(@RequestBody DiscardTweetCommand discardTweetCommand) throws CommandException {
        this.commandBus.execute(discardTweetCommand);
    }

    @GetMapping("/discarded")
    public List<TweetResponse> listAllDiscardedTweets() throws CommandException {
        return this.commandBus.execute(new ListAllDiscardedTweetsCommand());
    }

}
