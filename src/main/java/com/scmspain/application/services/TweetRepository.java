package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.model.TweetResponse;

/**
 * Repository for tweets.
 */
public interface TweetRepository {

    /**
     * Push tweet to repository.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     */
    void save(String publisher, String text);

    /**
     * Recover a list with all the tweets from repository.
     *
     * @return Retrieved list of tweets.
     */
    List<TweetResponse> findAll();

}
