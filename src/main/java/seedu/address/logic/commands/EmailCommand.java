package seedu.address.logic.commands;

import static java.awt.Desktop.getDesktop;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Email to a specfic contacts
 */

public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_SUCCESS = "Email open successful";

    public static final String MESSAGE_EMPTY_EMAIL = "The person selected does not have an email.";

    public static final String MESSAGE_INVALID_EMAIL = "The person selected does not have a valid email.";

    public static final String MESSAGE_EMAIL_FAIL = "Desktop default email application not found or cant be opened.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Email to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public EmailCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
        if (personToEmail.getEmail().toString().equals("~")) {
            throw new CommandException(MESSAGE_EMPTY_EMAIL);
        }

        try {
            Desktop desktop = getDesktop();
            desktop.mail(new URI("mailto:" + personToEmail.getEmail().toString()));
        } catch (URISyntaxException e) {
            throw new CommandException(MESSAGE_INVALID_EMAIL);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EMAIL_FAIL);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
