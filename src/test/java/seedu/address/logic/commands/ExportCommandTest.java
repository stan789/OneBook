package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_exportVCardFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/";

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

    /**
     * Generates a new {@code ExportCommand} which upon execution, sorts the AddressBook.
     */
    private ExportCommand prepareCommand(String fileLocation) {
        ExportCommand command = new ExportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
