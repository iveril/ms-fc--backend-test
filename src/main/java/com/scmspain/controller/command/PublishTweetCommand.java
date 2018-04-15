package com.scmspain.controller.command;

/**
 * Command for publish a tweet.
 */
public class PublishTweetCommand {

    private String publisher;
    private String tweet;

    /**
     * Gets the publisher of the tweet to be published.
     *
     * @return Publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Gets the tweet text of the tweet to be published.
     *
     * @return Tweet text.
     */
    public String getTweet() {
        return tweet;
    }

}
