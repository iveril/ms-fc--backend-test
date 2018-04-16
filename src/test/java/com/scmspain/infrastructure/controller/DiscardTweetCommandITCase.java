package com.scmspain.infrastructure.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.scmspain.infrastructure.configuration.TestConfiguration;
import com.scmspain.infrastructure.repository.TweetRepository;
import com.scmspain.infrastructure.repository.entities.Tweet;

import static java.lang.String.format;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class DiscardTweetCommandITCase {

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
    public void shouldReturn400WhenDiscardingANullTweet() throws Exception {
        mockMvc.perform(discardTweet(null)).andExpect(status().is(400));
    }

    @Test
    public void shouldReturn404WhenDiscardingANonExistentTweet() throws Exception {
        mockMvc.perform(discardTweet(Long.valueOf("9999"))).andExpect(status().is(404));
    }

    @Test
    public void shouldReturn200WhenDiscardingTweet() throws Exception {
        Long tweetId = tweetRepository.publish("Prospect", "Breaking the law");
        mockMvc.perform(discardTweet(tweetId)).andExpect(status().is(HttpStatus.OK.value()));
        Tweet tweet = tweetRepository.getTweet(tweetId);
        assertTrue(tweet.getDiscarded());
    }

    private MockHttpServletRequestBuilder discardTweet(Long tweetId) {
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": \"%s\"}", tweetId));
    }

}
