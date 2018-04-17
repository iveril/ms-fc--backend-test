package com.scmspain.domain.model;

/**
 * Tweet response.
 */
public class TweetResponse {

    private final Long id;
    private final String publisher;
    private final String tweet;
    private final Long pre2015MigrationStatus;

    /**
     * Constructor.
     *
     * @param id Identifier of the tweet.
     * @param publisher Creator of the tweet.
     * @param tweet Content of the tweet.
     */
    public TweetResponse(final Long id, final String publisher, final String tweet, final Long pre2015MigrationStatus) {
        this.id = id;
        this.publisher = publisher;
        this.tweet = tweet;
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    /**
     * Gets the identifier of the tweet.
     *
     * @return Identifier of the tweet.
     */
    public Long getId() {
        return this.id;
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
     * Gets the pre 2015 migration status.
     *
     * @return Pre 2015 migration status.
     */
    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

}
