package com.scmspain.infrastructure.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.infrastructure.configuration.TestConfiguration;
import com.scmspain.infrastructure.database.TweetRepository;

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
    public void shouldListAllTweetsSortedByPublicationDateDescendingOrder() throws Exception {
        Map<String, String> tweet1 = newMapTweet("First", "First tweet");
        Map<String, String> tweet2 = newMapTweet("Second", "Second tweet");
        Map<String, String> tweet3 = newMapTweet("Third", "Third tweet");
        Map<String, String> tweet4 = newMapTweet("Fourth", "Fourth tweet");

        MvcResult getResult =
            mockMvc
                .perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        List<Map> tweets = new ObjectMapper().readValue(content, List.class);

        assertThat(tweets).containsExactly(tweet4, tweet3, tweet2, tweet1);
    }

    private Map<String, String> newMapTweet(final String publisher, final String tweet) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("publisher", publisher);
        map.put("tweet", tweet);
        this.tweetRepository.publish(publisher, tweet);
        return map;
    }

}
