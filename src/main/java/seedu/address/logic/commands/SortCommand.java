package seedu.address.logic.commands;


/**
 * Sorts and lists all persons in address book.
 *
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by name or email.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example for sort by name: " + COMMAND_WORD + " name\n"
            + "Example for sort by email: " + COMMAND_WORD + " email";

    public static final String MESSAGE_SUCCESS = "The list has been sorted.";

    public final String sortType;


    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute() {
        model.executeSort(sortType);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
