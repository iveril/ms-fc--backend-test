package com.scmspain.application.services;

import com.scmspain.domain.model.TweetResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final MetricService metricService;

    public TweetService(TweetRepository tweetRepository, MetricService metricService) {
        this.tweetRepository = tweetRepository;
        this.metricService = metricService;
    }

    public void publishTweet(String publisher, String text) {
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0 && text.length() < 140) {
            this.metricService.incrementPublishedTweets();
            this.tweetRepository.publishTweet(publisher, text);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    public List<TweetResponse> listAllTweets() {
        this.metricService.incrementTimesQueriedTweets();
        return this.tweetRepository.listAllTweets();
    }

}
