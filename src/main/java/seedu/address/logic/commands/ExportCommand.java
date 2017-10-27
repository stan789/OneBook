package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exports OneBook to a VCard File.
 */

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": export contacts from vcard file.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example for export by name: " + COMMAND_WORD + " C:/desktop/add.vcf\n";

    public static final String MESSAGE_SUCCESS = "OneBook has been exported to %1$s file successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file cannot be exported.";


    private String fileLocation;
    private String fileName;
    private String extension;

    public ExportCommand(String fileLocation, String fileName, String extension) {

        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.extension = extension;
    }


    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.exportFile(fileLocation, extension);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
