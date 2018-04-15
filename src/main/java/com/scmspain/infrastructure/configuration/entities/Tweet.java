package com.scmspain.infrastructure.configuration.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Tweet entity.
 */
@Entity
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "tweet", nullable = false, length = 140)
    private String text;

    @Column
    private Long pre2015MigrationStatus = 0L;

    /**
     * Constructor to help the persistence framework to instantiate the entity.
     */
    private Tweet() { }

    /**
     * Constructor with parameters.
     *
     * @param publisher Tweet's publisher.
     * @param text Tweet's text.
     */
    public Tweet(final String publisher, final String text) {
        this.publisher = publisher;
        this.text = text;
    }

    /**
     * Gets the publisher of the tweet.
     *
     * @return Publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Gets the text of the tweet.
     *
     * @return Text.
     */
    public String getText() {
        return text;
    }

}
