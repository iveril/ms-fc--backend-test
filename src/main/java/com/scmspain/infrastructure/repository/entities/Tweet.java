package com.scmspain.infrastructure.repository.entities;

import java.util.Date;

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

    @Column(name = "tweet", nullable = false, length = 2000)
    private String text;

    @Column
    private Long pre2015MigrationStatus = 0L;

    @Column
    private Date publicationDate;

    @Column
    private Date discardedDate;

    @Column
    private Boolean discarded = Boolean.FALSE;

    /**
     * Constructor to help the persistence framework to instantiate the entity.
     */
    private Tweet() { }

    /**
     * Constructor with parameters.
     *
     * @param publisher Creator of the tweet.
     * @param text Content of the tweet.
     * @param publicationDate Publication date of the tweet.
     */
    public Tweet(final String publisher, final String text, final Date publicationDate) {
        this.publisher = publisher;
        this.text = text;
        this.publicationDate = publicationDate;
    }

    /**
     * Gets de identifier of the tweet.
     *
     * @return Identifier.
     */
    public Long getId() {
        return id;
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

    /**
     * Gets the disabled mark or the tweet.
     *
     * @return True if tweet is disabled.
     */
    public Boolean getDiscarded() {
        return discarded;
    }

    /**
     * Mark the tweet as discarded.
     */
    public void setDiscarded() {
        this.discarded = true;
    }

    /**
     * Sets the discarded date.
     *
     * @param discardedDate Discarded date.
     */
    public void setDiscardedDate(Date discardedDate) {
        this.discardedDate = discardedDate;
    }

}
