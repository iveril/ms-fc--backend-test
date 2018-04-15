package com.scmspain.domain.model;

import java.util.Date;

/**
 * Tweet response.
 */
public class TweetResponse {

    private final String publisher;
    private final String tweet;
    private final Date publicationDate;

    /**
     * Constructor.
     *
     * @param publisher Creator of the tweet.
     * @param tweet Content of the tweet.
     * @param publicationDate Publication date of the tweet.
     */
    public TweetResponse(final String publisher, final String tweet, final Date publicationDate) {
        this.publisher = publisher;
        this.tweet = tweet;
        this.publicationDate = publicationDate;
    }

    /**
     * Gets the creator of the tweet.
     *
     * @return Creator of the tweet.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Gets the content of the tweet.
     *
     * @return Content of the tweet.
     */
    public String getTweet() {
        return tweet;
    }

    /**
     * Gets the publication date.
     *
     * @return Publication date of the tweet.
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

}
