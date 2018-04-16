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
public class ListAllTweetsCommandITCase {

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
    public void shouldListNotDiscardedTweetsSortedByPublicationDateDescendingOrder() throws Exception {
        List<TestTweet> testTweets = testTweets();
        this.tweetRepository.discard(testTweets.get(1).tweetId);
        this.tweetRepository.discard(testTweets.get(3).tweetId);

        MvcResult getResult =
            mockMvc
                .perform(get("/tweet"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        List<Map> tweets = new ObjectMapper().readValue(content, List.class);

        assertThat(tweets).containsExactly(
                testTweets.get(5).tweet,
                testTweets.get(4).tweet,
                testTweets.get(2).tweet,
                testTweets.get(0).tweet);
    }

    private List<TestTweet> testTweets() {
        List<TestTweet> tweets = new ArrayList<>();
        tweets.add(newTestTweet("First", "First tweet https://www.google.com"));
        tweets.add(newTestTweet("Second", "Second https://www.google.com tweet"));
        tweets.add(newTestTweet("Third", "https://www.google.com Third tweet"));
        tweets.add(newTestTweet("Fourth", "Fourth tweet http://www.google.com"));
        tweets.add(newTestTweet("Fifth", "Fifth http://www.google.com tweet"));
        tweets.add(newTestTweet("Sixth", "http://www.google.com Sixth tweet"));
        return tweets;
    }

    private TestTweet newTestTweet(final String publisher, final String tweet) {
        Long tweetId = this.tweetRepository.publish(publisher, tweet);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("publisher", publisher);
        map.put("tweet", tweet);

        return new TestTweet(tweetId, map);
    }

    private static class TestTweet {

        final Long tweetId;
        final  Map<String, String> tweet;

        private TestTweet(Long tweetId, Map<String, String> tweet) {
            this.tweetId = tweetId;
            this.tweet = tweet;
        }
    }

}
