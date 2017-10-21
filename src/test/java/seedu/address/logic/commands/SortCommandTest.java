package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.SORT_EMAIL;
import static seedu.address.logic.commands.SortCommand.SORT_NAME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.EmptyAddressBookException;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEmail_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test(expected = EmptyAddressBookException.class)
    public void testEmptyAddressBookException() throws EmptyAddressBookException {
        model.resetData(new AddressBook());
        model.executeSort(SORT_NAME);

    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook.
     */
    private SortCommand prepareCommand(String sortType) {
        SortCommand command = new SortCommand(sortType);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
