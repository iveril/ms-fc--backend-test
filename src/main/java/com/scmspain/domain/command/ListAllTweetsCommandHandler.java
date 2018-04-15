package com.scmspain.domain.command;

import java.util.List;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Handler for the list all tweets command.
 */
public class ListAllTweetsCommandHandler implements CommandHandler<List<TweetResponse>, ListAllTweetsCommand> {

    private final TweetService tweetService;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service.
     */
    public ListAllTweetsCommandHandler(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public List<TweetResponse> handle(ListAllTweetsCommand command) {
        return this.tweetService.listAll();
    }

}
