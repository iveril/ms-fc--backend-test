package com.scmspain.infrastructure.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.scmspain.infrastructure.database.entities.Tweet;

/**
 * Repository implementation for tweets with an entity manager.
 */
@Repository
@Transactional
public class TweetRepository {

    private final EntityManager entityManager;

    /**
     * Constructor.
     *
     * @param entityManager Entity manager to access the persistence context.
     */
    public TweetRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Push tweet to repository.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     */
    public void publishTweet(String publisher, String text) {
        Tweet tweet = new Tweet(publisher, text);
        this.entityManager.persist(tweet);
    }

    /**
     * Recover tweet from repository.
     * @param id Identifier of the Tweet to retrieve
     * @return Retrieved tweet.
     */
    private Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    /**
     * Recover a list with all the tweets from repository.
     * @return Retrieved list of tweets.
     */
    public List<Tweet> listAllTweets() {
        List<Tweet> result = new ArrayList<>();
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }

}
