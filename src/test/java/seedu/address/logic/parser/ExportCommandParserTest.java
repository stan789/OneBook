package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        String location = "src/test/data/VCardFileTest/new.vcf";
        assertParseSuccess(parser, location, new ExportCommand(location));

    }

    @Test
    public void parse_invalidArgs_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, "Invalid Directory.");

    }

    @Test
    public void parse_invalidArgsWrongFileFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/new.txt";
        assertParseFailure(parser, location, "File created should end with .vcf");

    }

    @Test
    public void parse_invalidArgsWrongFileNameFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/!new.txt";
        assertParseFailure(parser, location, "Format for file name is invalid.");

    }

    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

    }

}
