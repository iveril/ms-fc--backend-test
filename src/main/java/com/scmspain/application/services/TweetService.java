package com.scmspain.application.services;

import com.scmspain.infrastructure.database.entities.Tweet;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final MetricWriter metricWriter;

    public TweetService(TweetRepository tweetRepository, MetricWriter metricWriter) {
        this.tweetRepository = tweetRepository;
        this.metricWriter = metricWriter;
    }

    public void publishTweet(String publisher, String text) {
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0 && text.length() < 140) {
            this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
            this.tweetRepository.publishTweet(publisher, text);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    public List<Tweet> listAllTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        return this.tweetRepository.listAllTweets();
    }

}
