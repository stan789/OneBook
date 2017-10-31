package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack(), false);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model, false);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
