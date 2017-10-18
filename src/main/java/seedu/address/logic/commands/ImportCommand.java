package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.Exceptions.EmptyFileException;

import java.io.IOException;
import java.nio.file.Path;


public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": import contacts from vcard file.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example for import by name: " + COMMAND_WORD + " C:/desktop/add.vcf\n";

    public static final String MESSAGE_SUCCESS = "%1$s contacts have been imported successfully";

    public static final String MESSAGE_EMPTY_FILE = "The file is empty";

    public static final String MESSAGE_FILE_INVALID = "The file is invalid";


    private Path fileLocation;

    private Integer count=0;

    public ImportCommand(Path fileLocation) {
        this.fileLocation = fileLocation;
    }


    @Override
    public CommandResult execute() throws CommandException {

        try {

            count = model.importFile(fileLocation);

        } catch (EmptyFileException e){
            throw new CommandException(MESSAGE_EMPTY_FILE);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_INVALID);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
