package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Import a VCard file to address book from a valid directory and a valid VCard file.
 *
 */

public class ImportCommandParser implements Parser<ImportCommand> {

    private Path fileLocation;

    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
            fileLocation = Paths.get(trimmedArgs);
            File file = new File(trimmedArgs);
        if (!file.isFile()) {
            throw new ParseException("NO FILE FOUND");
        }
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals("vcf")) {
            throw new ParseException("FILE IN WRONG FORMAT. FILE SHOULD BE in .vcf FORMAT");
        }
        return new ImportCommand(fileLocation);
    }
}
