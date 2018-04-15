package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Tweet service implementation that adds validation to another wrapped tweet service.
 */
public class TweetValidationService implements TweetService {

    private static final int MAX_CHARACTERS = 140;

    private final TweetService tweetService;
    private final UrlExtractor urlExtractor;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service to wrap.
     */
    public TweetValidationService(final TweetService tweetService) {
        this.tweetService = tweetService;
        this.urlExtractor = new UrlExtractor();
    }

    @Override
    public List<TweetResponse> listAll() {
        return tweetService.listAll();
    }

    @Override
    public void publish(final String publisher, final String text) {
        if (publisherEmpty(publisher)) {
            throw new IllegalArgumentException("Publisher must not be empty");
        }
        if (textEmpty(text)) {
            throw new IllegalArgumentException("Tweet must not be empty");
        }
        if (textTooLong(text)) {
            throw new IllegalArgumentException("Tweet must not be greater than " + MAX_CHARACTERS + " characters");
        }
        this.tweetService.publish(publisher, text);
    }

    private boolean textEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    private boolean publisherEmpty(final String publisher) {
        return publisher == null || publisher.isEmpty();
    }

    private boolean textTooLong(final String text) {
        return urlExtractor.extract(text).length() > MAX_CHARACTERS;
    }

}
