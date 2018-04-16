package com.scmspain.infrastructure.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Tweet URL entity.
 */
@Entity
public class TweetUrl {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String url;

    @ManyToOne
    private Tweet tweet;

    /**
     * Constructor to help the persistence framework to instantiate the entity.
     */
    private TweetUrl() { }

    /**
     * Constructor with parameters.
     *
     * @param tweet Tweet that owns the URL.
     * @param url URL included at a tweet.
     */
    public TweetUrl(final Tweet tweet, final String url) {
        this.url = url;
        this.tweet = tweet;
    }

    /**
     * Gets an URL included at a tweet.
     *
     * @return URL.
     */
    public String getUrl() {
        return url;
    }

}
