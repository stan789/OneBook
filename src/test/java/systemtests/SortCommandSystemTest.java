package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_NO_PERSON_TO_SORT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.SortCommand.SORT_EMAIL;
import static seedu.address.logic.commands.SortCommand.SORT_NAME;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.EmptyAddressBookException;

public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        Model expectedModel = getModel();

        /* Case: sort the list by name -> sorted */
        String command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        try {
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL;
        try {
            expectedModel.executeSort(SORT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: invalid arguments (no sort type)-> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);
    }

    /**
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", MESSAGE_SUCCESS, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     *
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

