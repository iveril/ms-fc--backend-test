package com.scmspain.application.services;

import com.scmspain.infrastructure.configuration.entities.Tweet;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TweetService {

    private final EntityManager entityManager;
    private final MetricWriter metricWriter;

    public TweetService(EntityManager entityManager, MetricWriter metricWriter) {
        this.entityManager = entityManager;
        this.metricWriter = metricWriter;
    }

    /**
      Push tweet to repository
      Parameter - publisher - creator of the Tweet
      Parameter - text - Content of the Tweet
      Result - recovered Tweet
    */
    public void publishTweet(String publisher, String text) {
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0 && text.length() < 140) {
            Tweet tweet = new Tweet(publisher, text);

            this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
            this.entityManager.persist(tweet);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    private Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    public List<Tweet> listAllTweets() {
        List<Tweet> result = new ArrayList<>();
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }

}
