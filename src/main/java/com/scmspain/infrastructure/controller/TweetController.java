package com.scmspain.infrastructure.controller;

import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.ListAllTweetsCommand;
import com.scmspain.domain.command.PublishTweetCommand;
import com.scmspain.domain.model.TweetResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

    @GetMapping("/tweet")
    public List<TweetResponse> listAllTweets() {
        return this.commandBus.execute(new ListAllTweetsCommand());
    }

    @PostMapping("/tweet")
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody PublishTweetCommand publishTweetCommand) {
        this.commandBus.execute(publishTweetCommand);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }

}
