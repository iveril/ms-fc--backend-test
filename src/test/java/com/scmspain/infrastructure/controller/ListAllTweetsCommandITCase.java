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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.infrastructure.configuration.TestConfiguration;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class ListAllTweetsCommandITCase {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
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
