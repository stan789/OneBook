package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ImportCommandTest {
    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_importVCardFile_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);

        Integer count = expectedModel.importFile(fileLocation);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS,count);
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test(expected = IOException.class)
    public void testFileWithInvalidFormat() throws IOException {
        model.importFile(Paths.get("src/test/data/VCardFileTest/contacts_example.vcf"));
    }

    @Test
    public void execute_importEmptyVCardFile_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/empty.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = importCommand.MESSAGE_EMPTY_FILE;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    @Test
    public void execute_importInvalidVCardFileNoBeginCard_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_example.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = importCommand.MESSAGE_FILE_INVALID;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook.
     */
    private ImportCommand prepareCommand(Path fileLocation) {
        ImportCommand command = new ImportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
