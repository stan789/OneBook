package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.BinSortCommand.SORT_EMAIL;
import static seedu.address.logic.commands.BinSortCommand.SORT_NAME;

import seedu.address.logic.commands.BinSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class BinSortCommandParser implements Parser<BinSortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BinSortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty() || (!trimmedArgs.equals(SORT_NAME) && !trimmedArgs.equals(SORT_EMAIL))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinSortCommand.MESSAGE_USAGE));
        }
        return new BinSortCommand(trimmedArgs);
    }
}
