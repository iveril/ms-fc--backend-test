package com.scmspain.domain.command;

import java.util.List;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Handler for the list all discarded tweets command.
 */
public class ListAllDiscardedTweetsCommandHandler implements CommandHandler<List<TweetResponse>, ListAllDiscardedTweetsCommand> {

    private final TweetService tweetService;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service.
     */
    public ListAllDiscardedTweetsCommandHandler(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public List<TweetResponse> handle(ListAllDiscardedTweetsCommand command) {
        return this.tweetService.listAllDiscarded();
    }

}
