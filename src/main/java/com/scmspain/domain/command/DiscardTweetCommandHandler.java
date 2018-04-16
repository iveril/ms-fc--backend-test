package com.scmspain.domain.command;

import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.TweetService;

/**
 * Handler for the discard tweet command.
 */
public class DiscardTweetCommandHandler implements CommandHandler<Void, DiscardTweetCommand> {

    private final TweetService tweetService;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service.
     */
    public DiscardTweetCommandHandler(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public Void handle(DiscardTweetCommand command) throws TweetNotFoundException {
        tweetService.discard(command.getTweet());
        return null;
    }

}
