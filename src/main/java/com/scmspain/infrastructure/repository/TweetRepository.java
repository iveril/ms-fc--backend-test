package com.scmspain.infrastructure.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.model.TweetResponse;
import com.scmspain.infrastructure.repository.entities.Tweet;

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
    public Long publish(String publisher, String text) {
        Tweet tweet = new Tweet(publisher, text, new Date());
        this.entityManager.persist(tweet);
        return tweet.getId();
    }

    @Override
    public void discard(final Long tweetId) throws TweetNotFoundException {
        Tweet tweet = getTweet(tweetId);
        tweet.setDiscarded();
        this.entityManager.merge(tweet);
    }

    @Override
    public List<TweetResponse> listAll() {
        TypedQuery<Long> query =
            this.entityManager
                .createQuery("SELECT id FROM Tweet WHERE pre2015MigrationStatus<>99 AND discarded = false ORDER BY publicationDate DESC", Long.class);

        return
            query
                .getResultList()
                .stream()
                .map(this::getTweetResponse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Recover tweet from repository.
     *
     * @param id Identifier of the Tweet to retrieve
     * @return Retrieved tweet response.
     */
    private Optional<TweetResponse> getTweetResponse(final Long id) {
        try {
            Tweet tweet = getTweet(id);
            return Optional.of(new TweetResponse(tweet.getPublisher(), tweet.getText()));
        } catch (TweetNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Recover tweet from repository.
     *
     * @param id Identifier of the Tweet to retrieve
     * @return Retrieved tweet.
     */
    public Tweet getTweet(final Long id) throws TweetNotFoundException {
        Tweet tweet = this.entityManager.find(Tweet.class, id);
        if (tweet == null) {
            throw new TweetNotFoundException(id);
        }
        return tweet;
    }

}
