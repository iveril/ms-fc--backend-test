package com.scmspain.infrastructure.controller;

import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.CommandException;
import com.scmspain.domain.command.DiscardTweetCommand;
import com.scmspain.domain.command.ListAllDiscardedTweetsCommand;
import com.scmspain.domain.command.ListAllTweetsCommand;
import com.scmspain.domain.command.PublishTweetCommand;
import com.scmspain.domain.model.TweetResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return getExceptionObject(ex);
    }

    @ExceptionHandler(TweetNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public Object tweetNotFoundException(TweetNotFoundException ex) {
        return getExceptionObject(ex);
    }

    private Object getExceptionObject(Exception ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }

}
