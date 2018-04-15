package com.scmspain.domain.command;

import com.scmspain.domain.TweetService;

/**
 * Handler for the publish tweet command.
 */
public class PublishTweetCommandHandler implements CommandHandler<Void, PublishTweetCommand> {

    private final TweetService tweetService;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service.
     */
    public PublishTweetCommandHandler(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public Void handle(PublishTweetCommand command) {
        tweetService.publish(command.getPublisher(), command.getTweet());
        return null;
    }

}
