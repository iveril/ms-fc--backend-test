package com.scmspain.infrastructure.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.infrastructure.configuration.TestConfiguration;
import com.scmspain.infrastructure.repository.TweetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext(classMode = BEFORE_CLASS)
public class ListAllDiscardedTweetsCommandITCase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TweetRepository tweetRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldListAllDiscardedTweetsSortedByDiscardedDateDescendingOrder() throws Exception {
        List<TestTweet> testTweets = testTweets();
        this.tweetRepository.discard(testTweets.get(1).tweetId);
        this.tweetRepository.discard(testTweets.get(5).tweetId);
        this.tweetRepository.discard(testTweets.get(3).tweetId);

        MvcResult getResult =
            mockMvc
                .perform(get("/discarded"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        List<Map> tweets = new ObjectMapper().readValue(content, List.class);

        assertThat(tweets).containsExactly(
                testTweets.get(3).tweet,
                testTweets.get(5).tweet,
                testTweets.get(1).tweet);
    }

    private List<TestTweet> testTweets() {
        List<TestTweet> tweets = new ArrayList<>();
        tweets.add(newTestTweet("First", "First tweet"));
        tweets.add(newTestTweet("Second", "Second tweet"));
        tweets.add(newTestTweet("Third", "Third tweet"));
        tweets.add(newTestTweet("Fourth", "Fourth tweet"));
        tweets.add(newTestTweet("Fifth", "Fifth tweet"));
        tweets.add(newTestTweet("Sixth", "Sixth tweet"));
        return tweets;
    }

    private TestTweet newTestTweet(final String publisher, final String tweet) {
        Long tweetId = this.tweetRepository.publish(publisher, tweet);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", tweetId.intValue());
        map.put("publisher", publisher);
        map.put("tweet", tweet);
        map.put("pre2015MigrationStatus", 0);

        return new TestTweet(tweetId, map);
    }

    private static class TestTweet {

        final Long tweetId;
        final  Map<String, Object> tweet;

        private TestTweet(Long tweetId, Map<String, Object> tweet) {
            this.tweetId = tweetId;
            this.tweet = tweet;
        }
    }

}
