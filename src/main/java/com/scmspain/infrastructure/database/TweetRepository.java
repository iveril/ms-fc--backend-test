package com.scmspain.infrastructure.database;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;
import com.scmspain.infrastructure.database.entities.Tweet;

/**
 * Repository implementation for tweets with an entity manager.
 */
@Repository
@Transactional
public class TweetRepository implements TweetService {

    private final EntityManager entityManager;

    /**
     * Constructor.
     *
     * @param entityManager Entity manager to access the persistence context.
     */
    public TweetRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void publish(String publisher, String text) {
        Tweet tweet = new Tweet(publisher, text, new Date());
        this.entityManager.persist(tweet);
    }

    @Override
    public List<TweetResponse> listAll() {
        TypedQuery<Long> query =
            this.entityManager
                .createQuery("SELECT id FROM Tweet WHERE pre2015MigrationStatus<>99 ORDER BY publicationDate DESC", Long.class);

        return
            query
                .getResultList()
                .stream()
                .map(this::getTweet)
                .collect(Collectors.toList());
    }

    /**
     * Recover tweet from repository.
     *
     * @param id Identifier of the Tweet to retrieve
     * @return Retrieved tweet.
     */
    private TweetResponse getTweet(final Long id) {
        final Tweet tweet = this.entityManager.find(Tweet.class, id);
        return new TweetResponse(tweet.getPublisher(), tweet.getText());
    }

}
