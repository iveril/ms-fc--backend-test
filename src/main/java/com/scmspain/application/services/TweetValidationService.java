package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Tweet service implementation that adds validation to another wrapped tweet service.
 */
public class TweetValidationService implements TweetService {

    private static final int MAX_CHARACTERS = 140;

    private final TweetService tweetService;
    private final UrlRemover urlRemover;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service to wrap.
     */
    public TweetValidationService(final TweetService tweetService) {
        this.tweetService = tweetService;
        this.urlRemover = new UrlRemover();
    }

    @Override
    public List<TweetResponse> listAll() {
        return tweetService.listAll();
    }

    @Override
    public Long publish(final String publisher, final String text) {
        if (publisherEmpty(publisher)) {
            throw new IllegalArgumentException("Publisher must not be empty");
        }
        if (textEmpty(text)) {
            throw new IllegalArgumentException("Tweet must not be empty");
        }
        if (textTooLong(text)) {
            throw new IllegalArgumentException("Tweet must not be greater than " + MAX_CHARACTERS + " characters");
        }
        return this.tweetService.publish(publisher, text);
    }

    @Override
    public void discard(Long tweetId) throws TweetNotFoundException {
        if (tweetId == null) {
            throw new IllegalArgumentException("Tweet identifier must not be empty");
        }
        this.tweetService.discard(tweetId);
    }

    private boolean textEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    private boolean publisherEmpty(final String publisher) {
        return publisher == null || publisher.isEmpty();
    }

    private boolean textTooLong(final String text) {
        return urlRemover.removeUrls(text).length() > MAX_CHARACTERS;
    }

}
