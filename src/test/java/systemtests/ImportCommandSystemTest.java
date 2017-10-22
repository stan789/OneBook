package systemtests;

import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_INVALID;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.RecycleBin;
import seedu.address.storage.AddressBookData;


public class ImportCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void importing() {

        Model expectedModel = getModel();
        Integer count = 0;
        /* Case: import VCard file with valid format -> import successful */
        String command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);

        /* Case: import VCard file with valid format to empty address book -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);
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
