package com.scmspain.domain;

import java.util.List;

import com.scmspain.domain.model.TweetResponse;

/**
 * Tweet service.
 */
public interface TweetService {

    /**
     * Publish a new tweet.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     * @return Identifier of the published tweet.
     */
    Long publish(String publisher, String text);

    /**
     * List all available tweets sorted by publication date in descending order.
     *
     * @return List of tweets.
     */
    List<TweetResponse> listAll();

    /**
     * Discard a tweet.
     *
     * @param tweetId Tweet identifier.
     * @throws TweetNotFoundException if no tweet found for the specified identifier.
     */
    void discard(Long tweetId) throws TweetNotFoundException;

    /**
     * List all discarded tweets sorted by the date it was discarded on in descending order..
     *
     * @return List of discarded tweets.
     */
    List<TweetResponse> listAllDiscarded();

}
