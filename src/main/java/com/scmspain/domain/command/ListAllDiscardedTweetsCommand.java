package com.scmspain.domain.command;

import java.util.List;

import com.scmspain.domain.model.TweetResponse;

/**
 * Command for list all discarded tweets.
 */
public class ListAllDiscardedTweetsCommand implements Command<List<TweetResponse>> { }