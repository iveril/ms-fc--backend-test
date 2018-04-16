package com.scmspain.domain;

import com.scmspain.domain.command.CommandException;

public class TweetNotFoundException extends CommandException {

    public TweetNotFoundException(Long id) {
        super(String.format("Tweet with identifier %s not found", id));
    }

}
