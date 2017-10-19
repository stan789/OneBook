package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        String location = "src/test/data/VCardFileTest/contacts.vcf";
        Path fileLocation = Paths.get(location);
        assertParseSuccess(parser, location, new ImportCommand(fileLocation));

    }

    @Test
    public void parse_invalidArgs_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, "NO FILE FOUND");

    }

    @Test
    public void parse_invalidArgsWrongFileFormat_throwsParserException() {
        String location = "src/test/data/ConfigUtilTest/TypicalConfig.json";
        assertParseFailure(parser, location, "FILE IN WRONG FORMAT. FILE SHOULD BE in .vcf FORMAT");

    }
    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

    }


}
