package com.scmspain.infrastructure.database;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.scmspain.application.services.TweetRepository;
import com.scmspain.infrastructure.database.entities.Tweet;

/**
 * Repository implementation for tweets with an entity manager.
 */
@Repository
@Transactional
public class TweetEntityManagerRepository implements TweetRepository {

    private final EntityManager entityManager;

    /**
     * Constructor.
     *
     * @param entityManager Entity manager to access the persistence context.
     */
    public TweetEntityManagerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Push tweet to repository.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     */
    @Override
    public void publishTweet(String publisher, String text) {
        Tweet tweet = new Tweet(publisher, text);
        this.entityManager.persist(tweet);
    }

    /**
     * Recover a list with all the tweets from repository.
     * @return Retrieved list of tweets.
     */
    @Override
    public List<Tweet> listAllTweets() {
        TypedQuery<Long> query =
            this.entityManager
                .createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);

        return query
                .getResultList()
                .stream()
                .map(this::getTweet)
                .collect(Collectors.toList());
    }

    /**
     * Recover tweet from repository.
     * @param id Identifier of the Tweet to retrieve
     * @return Retrieved tweet.
     */
    private Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

}
