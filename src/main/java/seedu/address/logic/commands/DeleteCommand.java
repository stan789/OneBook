package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: ";

    private final Index[] targetIndex;

    public DeleteCommand(Index... targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for(Index targetIndex : targetIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndex.length > 1) {
            for(int i=1; i<targetIndex.length; i++) {
                if(targetIndex[i].getZeroBased() <= targetIndex[i-1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }
        }

        ReadOnlyPerson personToDelete;

        int i = 0;
        StringBuilder deleteMessage = new StringBuilder();
        for(Index targetIndex : targetIndex) {
            int target = targetIndex.getZeroBased() - i;
            personToDelete = lastShownList.get(target);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            deleteMessage.append(MESSAGE_DELETE_PERSON_SUCCESS);
            deleteMessage.append(personToDelete);
            deleteMessage.append("\n");
            i++;
        }

        return new CommandResult(deleteMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndex, ((DeleteCommand) other).targetIndex)); // state check
    }
}
