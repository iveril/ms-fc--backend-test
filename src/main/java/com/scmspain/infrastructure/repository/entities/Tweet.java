package com.scmspain.infrastructure.repository.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @Column(name = "tweet", nullable = false)
    private String text;

    @Column
    private Long pre2015MigrationStatus = 0L;

    @Column
    private Date publicationDate;

    @Column
    private Date discardedDate;

    @Column
    private Boolean discarded = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet", fetch = FetchType.EAGER)
    private List<TweetUrl> urls = new ArrayList<>();

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
     * Gets the pre 2015 migration status.
     *
     * @return Pre 2015 migration status.
     */
    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
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

    /**
     * Gets the URLs of the tweet.
     *
     * @return List of URLs of the tweet.
     */
    public List<TweetUrl> getUrls() {
        return urls;
    }

}
