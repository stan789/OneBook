package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_WRITE_ERROR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_exportVCardFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.vcf";

        ExportCommand exportCommand = prepareCommand(fileLocation);
        expectedModel.exportFile(fileLocation);
        String expectedMessage = ExportCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    @After
    public void tearDown() {
        File file = new File("src/test/data/VCardFileTest/OneBook.vcf");
        file.delete();
    }

    @Test
    public void execute_exportEmptyString_exportFailure() throws IOException {

        String fileLocation = "";

        ExportCommand exportCommand = prepareCommand(fileLocation);
        assertCommandFailure(exportCommand, model, MESSAGE_WRITE_ERROR);
    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, sorts the AddressBook.
     */
    private ExportCommand prepareCommand(String fileLocation) {
        ExportCommand command = new ExportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
