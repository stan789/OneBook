package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.RecycleBin;
import seedu.address.model.person.Person;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<AddressBookData> readAddressBook(String filePath) throws Exception {
        Optional<AddressBookData> data = new XmlAddressBookStorage(filePath)
                                            .readAddressBook(addToTestDataPathIfNotNull(filePath));
        return data.isPresent() ? Optional.of(data.get()) : Optional.empty();
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveAddressBook(new AddressBookData(original, new RecycleBin()), filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath).get().getAddressBook();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlAddressBookStorage.saveAddressBook(new AddressBookData(original, new RecycleBin()), filePath);
        readBack = xmlAddressBookStorage.readAddressBook(filePath).get().getAddressBook();
        assertEquals(original, new AddressBook(readBack));

        //Save and read without specifying file path
        original.addPerson(new Person(IDA));
        //file path not specified
        xmlAddressBookStorage.saveAddressBook(new AddressBookData(original, new RecycleBin()));
        readBack = xmlAddressBookStorage.readAddressBook().get().getAddressBook(); //file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            requireNonNull(addressBook);
            new XmlAddressBookStorage(filePath)
                    .saveAddressBook(new AddressBookData(addressBook, new RecycleBin()),
                                                         addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new AddressBook(), null);
    }


}
