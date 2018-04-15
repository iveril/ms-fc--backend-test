package com.scmspain.infrastructure.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.infrastructure.configuration.TestConfiguration;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TweetControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweet() throws Exception {
        mockMvc
            .perform(newTweet("Prospect", "Breaking the law"))
            .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweetWithUrls() throws Exception {
        mockMvc
            .perform(newTweet("Prospect", "Breaking the law: http://www.judaspriest.com http://www.judaspriest.com http://www.judaspriest.com http://www.judaspriest.com http://www.judaspriest.com http://www.judaspriest.com"))
            .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn400WhenInsertingAnInvalidTweet() throws Exception {
        mockMvc
            .perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome! Text added to make it fail."))
            .andExpect(status().is(400));
    }

    @Test
    public void shouldReturn200WhenDiscardingTweet() throws Exception {
        mockMvc
            .perform(newTweet("Me", "Tweet to discard"))
            .andExpect(status().is(201));

        MvcResult getResult =
            mockMvc
                .perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        mockMvc
            .perform(newTweet("Yo", "How are you?"))
            .andExpect(status().is(201));

        MvcResult getResult =
            mockMvc
                .perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    @Test
    public void shouldListAllTweetsSortedByPublicationDateDescendingOrder() throws Exception {
        Map<String, String> tweet1 = newMapTweet("First", "First tweet");
        Map<String, String> tweet2 = newMapTweet("Second", "Second tweet");
        Map<String, String> tweet3 = newMapTweet("Third", "Third tweet");
        Map<String, String> tweet4 = newMapTweet("Fourth", "Fourth tweet");

        mockMvc.perform(newTweet(tweet1)).andExpect(status().is(201));
        mockMvc.perform(newTweet(tweet2)).andExpect(status().is(201));
        mockMvc.perform(newTweet(tweet3)).andExpect(status().is(201));
        mockMvc.perform(newTweet(tweet4)).andExpect(status().is(201));

        MvcResult getResult =
            mockMvc
                .perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        List<Map> tweets = new ObjectMapper().readValue(content, List.class);

        assertThat(tweets).containsExactly(tweet4, tweet3, tweet2, tweet1);
    }

    private MockHttpServletRequestBuilder newTweet(Map<String, String> tweet) {
        return newTweet(tweet.get("publisher"), tweet.get("tweet"));
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private Map<String, String> newMapTweet(final String publisher, final String tweet) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("publisher", publisher);
        map.put("tweet", tweet);
        return map;
    }

}
