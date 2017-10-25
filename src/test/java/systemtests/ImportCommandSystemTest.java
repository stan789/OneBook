package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_INVALID;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;


public class ImportCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void importing() {

        Model expectedModel = getModel();
        Model model = getModel();
        Integer count = 0;

        /* Case: export VCard file and import the file back to OneBook -> import successful */
        String command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf";
        executeCommand(ExportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf");
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBook());
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/OneBook.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);
        assertEquals(model, expectedModel);

        /* Case: import VCard file with valid format -> import successful */
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);

        /* Case: import VCard file with valid format to empty address book -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBook());
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);

    }

    @After
    public void tearDown() {
        File file = new File("src/test/data/VCardFileTest/OneBook.vcf");
        file.delete();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code String.format(MESSAGE_SUCCESS, count)},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Integer count, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", String.format(MESSAGE_SUCCESS, count), expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
