# stan789
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
@Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByName_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEmail_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEmail_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test(expected = EmptyAddressBookException.class)
    public void testEmptyAddressBookException() throws EmptyAddressBookException {
        model.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        model.executeSort(SORT_NAME);

    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook.
     */
    private SortCommand prepareCommand(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setListDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook, used for when bin is displayed
     */
    private SortCommand prepareCommand_binMode(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setBinDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return command;
    }
``` 
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
@Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_importVCardFile_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);

        Integer count = expectedModel.importFile(fileLocation);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, count);
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_importVCardFileWithOneTag_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_one_tag.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);

        Integer count = expectedModel.importFile(fileLocation);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, count);
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test(expected = IOException.class)
    public void testFileWithInvalidFormat() throws IOException {
        model.importFile(Paths.get("src/test/data/VCardFileTest/contacts_example.vcf"));
    }

    @Test
    public void execute_importEmptyVCardFile_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/empty.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = importCommand.MESSAGE_EMPTY_FILE;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    @Test
    public void execute_importInvalidVCardFileNoBeginCard_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = importCommand.MESSAGE_FILE_INVALID;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    /**
     * Generates a new {@code ImportCommand} which upon execution, sorts the AddressBook.
     */
    private ImportCommand prepareCommand(Path fileLocation) {
        ImportCommand command = new ImportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
``` 
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
@Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_exportVCardFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.vcf";
        String extension = "vcf";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_exportCsvFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.csv";
        String extension = "csv";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    @After
    public void tearDown() {
        File fileVcard = new File("src/test/data/VCardFileTest/OneBook.vcf");
        fileVcard.delete();
        File fileCsv = new File("src/test/data/VCardFileTest/OneBook.csv");
        fileCsv.delete();
    }


    @Test
    public void execute_exportEmptyString_exportFailure() throws IOException {

        String fileLocation = "";
        String extension = "";
        String fileName = "";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        assertCommandFailure(exportCommand, model, MESSAGE_WRITE_ERROR);
    }
    @Test(expected = IOException.class)
    public void emptyFileDirectoryVcardIoException() throws IOException {
        model.exportFile("", "vcf");

    }
    @Test(expected = IOException.class)
    public void emptyFileDirectoryCsvIoException() throws IOException {
        model.exportFile("", "csv");

    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, sorts the AddressBook.
     */
    private ExportCommand prepareCommand(String fileLocation, String fileName, String extension) {
        ExportCommand command = new ExportCommand(fileLocation, fileName, extension);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
``` 
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java 
public class EmailCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model, false);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model, false);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void emptyEmail_failure() {
        Person person = new PersonBuilder().withName("ALICE").withEmail(null).build();
        try {
            model.addPerson(person);
        } catch (DuplicatePersonException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(lastPersonIndex, EmailCommand.MESSAGE_EMPTY_EMAIL);
    }
    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(EmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            emailCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code EmailCommand} with parameters {@code index}.
     */
    private EmailCommandStub prepareCommand(Index index) {
        EmailCommandStub emailCommand = new EmailCommandStub(index);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return emailCommand;
    }

}

class EmailCommandStub extends EmailCommand {

    EmailCommandStub(Index index) {
        super(index);
    }

    @Override
    protected void desktopEmail(String email) throws IOException, URISyntaxException {
    }
}
``` 
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */

public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        String extension = ExportCommandParser.VCF_EXTENSION;
        String location = "src/test/data/VCardFileTest/new.vcf";
        String fileName = "new";
        assertParseSuccess(parser, location, new ExportCommand(location , fileName, extension));

    }

    @Test
    public void parse_invalidArgs_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_DIRECTORY);

    }

    @Test
    public void parse_invalidArgsWrongFileFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_EXTENSION);

    }

    @Test
    public void parse_invalidArgsWrongFileNameFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/!new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_FILE_NAME);

    }

    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

    }

}
```
###### \java\seedu\address\logic\parser\ImportParserCommandTest.java
``` java
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        String location = "src/test/data/VCardFileTest/contacts.vcf";
        Path fileLocation = Paths.get(location);
        assertParseSuccess(parser, location, new ImportCommand(fileLocation));

    }

    @Test
    public void parse_invalidArgsInvalidDirectory_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, ImportCommandParser.NO_FILE_FOUND);

    }

    @Test
    public void parse_invalidArgsCreateWrongFileFormat_throwsParserException() {
        String location = "src/test/data/ConfigUtilTest/TypicalConfig.json";
        assertParseFailure(parser, location, ImportCommandParser.FILE_WRONG_FORMAT);

    }
    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "name", new SortCommand(SortCommand.SORT_NAME));
        assertParseSuccess(parser, "email", new SortCommand(SortCommand.SORT_EMAIL));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\storage\ImportVCardFileTest.java
``` java
public class ImportVCardFileTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPersonFromFile_withoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    @Test
    public void getPersonFromFile_withoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_end.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    @Test
    public void getPersonFromFile_someWithoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_end.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    @Test
    public void getPersonFromFile_someWithoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_begin.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }
}
```
###### \java\systemtests\ImportCommandSystemTest.java
``` java
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
        model.resetData(new AddressBookData(model.getAddressBook(), new RecycleBin()));
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
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
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, count, expectedModel);

        /* Case: import VCard file with name only -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData());
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/name_only.vcf";
        try {
            count = expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/name_only.vcf"));
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
```
###### \java\systemtests\SortCommandSystemTest.java
``` java
public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        Model expectedModel = getModel();

        /* Case: import to existing address book and sort -> sorted  */
        executeCommand(ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf");
        String command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"));
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by name -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        try {
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL;
        try {
            expectedModel.executeSort(SORT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: invalid arguments (no sort type)-> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: sort from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + SORT_NAME,
                MESSAGE_NO_PERSON_TO_SORT, expectedModel);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code MESSAGE_SUCCESS},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", MESSAGE_SUCCESS, expectedModel);
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
```

