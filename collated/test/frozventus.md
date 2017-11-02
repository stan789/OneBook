# frozventus
###### /java/seedu/address/logic/commands/BinListCommandTest.java
``` java
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
        expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();

        binListCommand = new BinListCommand();
        binListCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

```
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
    @Test
    public void isValidBirthday() throws Exception {
        // blank Birthday
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("02-1995")); // missing day
        assertFalse(Birthday.isValidBirthday("21-1992")); // missing month
        assertFalse(Birthday.isValidBirthday("01-01")); // missing year

        // valid Birthday
        assertTrue(Birthday.isValidBirthday("~")); // there is no input for birthday field
        assertTrue(Birthday.isValidBirthday("02-03-1995"));
        assertTrue(Birthday.isValidBirthday("12-12-1999"));
        assertTrue(Birthday.isValidBirthday("05-07-2005"));
    }
}
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    public static List<ReadOnlyPerson> getTypicalPersonsForBin() {
        return new ArrayList<>(Arrays.asList(JEAN, KEN));
    }

```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    public static AddressBookData getTypicalData() {
        return new AddressBookData(getTypicalAddressBook(), getTypicalRecycleBin());
    }

}
```
