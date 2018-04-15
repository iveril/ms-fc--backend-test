package com.scmspain.domain;

import java.util.List;

import com.scmspain.domain.model.TweetResponse;

/**
 * Tweet service.
 */
public interface TweetService {

    /**
     * List all available tweets.
     *
     * @return List of tweets.
     */
    List<TweetResponse> listAll();

    /**
     * Publish a new tweet.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     */
    void publish(String publisher, String text);

}
