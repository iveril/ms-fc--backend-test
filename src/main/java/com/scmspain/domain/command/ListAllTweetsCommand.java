package com.scmspain.domain.command;

import java.util.List;

import com.scmspain.domain.model.TweetResponse;

/**
 * Command for list all available tweets.
 */
public class ListAllTweetsCommand implements Command<List<TweetResponse>> { }
