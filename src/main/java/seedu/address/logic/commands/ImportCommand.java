package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.exceptions.EmptyFileException;

/**
 * Imports a VCard file to the address book.
 */

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": import contacts from VCard file.\n"
            + "Parameters: KEYWORD [FILE DIRECTORY]\n"
            + "Example for import from VCard file: " + COMMAND_WORD + " C:\\Users\\user\\desktop\\add.vcf(Windows)\n"
            + "Example for import from VCard file: " + COMMAND_WORD + " /Users/user/Desktop/add.vcf(MAC OS)\n";

    public static final String MESSAGE_SUCCESS = "%1$s contact/s have been imported successfully";

    public static final String MESSAGE_EMPTY_FILE = "The file is empty";

    public static final String MESSAGE_FILE_INVALID = "The file is invalid";

    private Path fileLocation;

    private Integer count = 0;

    public ImportCommand(Path fileLocation) {
        this.fileLocation = fileLocation;
    }

    //@@author stan789
    @Override
    public CommandResult execute() throws CommandException {

        try {

            count = model.importFile(fileLocation);

        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_INVALID);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }

    //@@author stan789
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
