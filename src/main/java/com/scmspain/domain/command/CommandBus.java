package com.scmspain.domain.command;

/**
 * Command bus able to execute commands.
 * Given a command will pass it to the proper handler.
 */
public interface CommandBus {

    /**
     * Look for the proper handler of the given command.
     *
     * @param command Command to execute.
     * @param <R> type of return value.
     * @param <C> type of the command.
     */
    <R, C extends Command<R>> R execute(C command);

}
