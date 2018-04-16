package com.scmspain.domain.command;

/**
 * A handler for a {@link Command}.
 *
 * @param <R> type of return value.
 * @param <C> type of the command.
 */
public interface CommandHandler<R, C extends Command<R>> {

    /**
     * Handles the command.
     *
     * @param command Command to handle.
     * @return Return value as specified in {@link Command}.
     * {@link Void} if none value returned.
     */
    R handle(C command) throws CommandException;

}
