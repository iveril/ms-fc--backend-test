package com.scmspain.infrastructure.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.scmspain.application.services.UrlExtractor;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.model.TweetResponse;
import com.scmspain.infrastructure.repository.entities.Tweet;
import com.scmspain.infrastructure.repository.entities.TweetUrl;

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
        UrlExtractor urlExtractor = new UrlExtractor(text);
        Tweet tweet = new Tweet(publisher, urlExtractor.getText(), new Date());
        for (String url : urlExtractor.getUrls()) {
            tweet.getUrls().add(new TweetUrl(tweet, url));
        }
        this.entityManager.persist(tweet);
        return tweet.getId();
    }

    @Override
    public void discard(final Long tweetId) throws TweetNotFoundException {
        Tweet tweet = getTweet(tweetId);
        tweet.setDiscarded();
        tweet.setDiscardedDate(new Date());
        this.entityManager.merge(tweet);
    }

    @Override
    public List<TweetResponse> listAll() {
        return listTweets("SELECT id FROM Tweet WHERE pre2015MigrationStatus<>99 AND discarded = false ORDER BY publicationDate DESC");
    }

    @Override
    public List<TweetResponse> listAllDiscarded() {
        return listTweets("SELECT id FROM Tweet WHERE pre2015MigrationStatus<>99 AND discarded = true ORDER BY discardedDate DESC");
    }

    private List<TweetResponse> listTweets(String qlString) {
        TypedQuery<Long> query =
            this.entityManager
                .createQuery(qlString, Long.class);

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
            String text = rebuildText(tweet);
            return Optional.of(new TweetResponse(tweet.getPublisher(), text));
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

    private String rebuildText(Tweet tweet) {
        List<String> urls =
            tweet
                .getUrls()
                .stream()
                .map(TweetUrl::getUrl)
                .collect(Collectors.toList());

        return UrlExtractor.rebuild(tweet.getText(), urls);
    }

}
