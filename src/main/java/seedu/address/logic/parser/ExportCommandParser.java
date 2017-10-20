package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */

public class ExportCommandParser implements Parser<ExportCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String directory = trimmedArgs.substring(0, trimmedArgs.lastIndexOf("/") + 1);
        System.out.println(directory);
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new ParseException("Invalid Directory.");
        }

        String extension = trimmedArgs.substring(trimmedArgs.lastIndexOf(".") + 1, trimmedArgs.length());
        if (!extension.equals("vcf")) {
            throw new ParseException("File created should end with .vcf");
        }

        return new ExportCommand(trimmedArgs);
    }
}

