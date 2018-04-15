package com.scmspain.infrastructure.database.entities;

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
     * Gets the publication date of the tweet.
     *
     * @return Publidation date.
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

}
