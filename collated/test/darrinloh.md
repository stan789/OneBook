# darrinloh
###### \java\seedu\address\logic\commands\ModeCommandTest.java
``` java
    @Test
    public void execute_mode_command() {
        assertCommandSuccess(modeCommand, model, ModeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        //correct input -> returns true
        assertTrue(ModeCommand.COMMAND_WORD.equals("mode"));

        //case sensitive -> returns false
        assertFalse(ModeCommand.COMMAND_WORD.equals("Mode"));
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void getUserPrefsFilePathTest() {
        assertEquals(getTempFilePath("prefs"), storageManager.getUserPrefsFilePath());
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        RecycleBin bin = getTypicalRecycleBin();
        storageManager.saveAddressBook(new AddressBookData(original, bin));
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get().getAddressBook();
        ReadOnlyAddressBook retrievedBin = storageManager.readAddressBook().get().getRecycleBin();
        assertEquals(original, new AddressBook(retrieved));
        assertEquals(bin, new RecycleBin(retrievedBin));
    }

```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBookUrlTest() {
        String expectedUrl = storageManager.getAddressBookFilePath() + "-backup.xml";
        String actualUrl = storageManager.getBackUpAddressBookFilePath();
        assertEquals(expectedUrl, actualUrl);
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBookData(new AddressBook(),
                                                                                              new RecycleBin())));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(AddressBookData addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
```
###### \java\seedu\address\ui\ModeChangeTest.java
``` java
    @Before
    public void setUp() {
        prefs = new UserPrefs();

    }

    @Test
    public void check_valid_css() {
        assertTrue(prefs.getTheme().contains(DARK_MODE));
    }

    @Test
    public void check_validCssAfterUpdating() {
        prefs.updateLastUsedThemeSetting(DARK_MODE);
        assertTrue(prefs.getTheme().contains(DARK_MODE));
    }

    @Test
    public void set_css() {
        prefs.setTheme(LIGHT_MODE);
        assertTrue(prefs.getTheme().contains(LIGHT_MODE));
    }
}
```
