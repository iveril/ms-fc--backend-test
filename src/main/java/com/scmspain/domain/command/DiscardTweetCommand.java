package com.scmspain.domain.command;

/**
 * Command to discard a tweet.
 */
public class DiscardTweetCommand implements Command<Void> {

    private Long tweet;

    /**
     * Gets the tweet identifier to discard.
     * @return Tweet identifier.
     */
    public Long getTweet() {
        return tweet;
    }

}
