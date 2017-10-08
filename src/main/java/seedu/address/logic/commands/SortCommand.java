package seedu.address.logic.commands;


/**
 * Sorts and lists all persons in address book.
 *
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by name.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The list has been sorted.";


    public SortCommand() {

    }

    @Override
    public CommandResult execute() {
        model.executeSort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
