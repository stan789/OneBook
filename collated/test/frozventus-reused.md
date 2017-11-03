# frozventus-reused
###### /java/seedu/address/logic/parser/BinDeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsBinDeleteCommand() {
        assertParseSuccess(parser, "1", new BinDeleteCommand(INDEX_FIRST_PERSON));
    }

```
###### /java/seedu/address/logic/parser/BinDeleteCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinDeleteCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/RestoreCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1", new RestoreCommand(INDEX_FIRST_PERSON));
    }

```
###### /java/seedu/address/logic/parser/RestoreCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/BinListCommandTest.java
``` java
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/BinListCommandTest.java
``` java
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model, true);
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);
        showNoPerson(expectedModel);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        RestoreCommand restoreFirstCommandCopy = new RestoreCommand(INDEX_FIRST_PERSON);
        assertTrue(restoreFirstCommand.equals(restoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(restoreFirstCommand.equals(restoreSecondCommand));
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    /**
     * Returns a {@code RestoreCommand} with the parameter {@code index}.
     */
    private RestoreCommand prepareCommand(Index index) {
        RestoreCommand restoreCommand = new RestoreCommand(index);
        restoreCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return restoreCommand;
    }

```
###### /java/seedu/address/logic/commands/RestoreCommandTest.java
``` java
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    @Test
    public void equals() {
        BinDeleteCommand binDeleteFirstCommand = new BinDeleteCommand(INDEX_FIRST_PERSON);
        BinDeleteCommand binDeleteSecondCommand = new BinDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(binDeleteFirstCommand.equals(binDeleteFirstCommand));

        // same values -> returns true
        BinDeleteCommand deleteFirstCommandCopy = new BinDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(binDeleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(binDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(binDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(binDeleteFirstCommand.equals(binDeleteSecondCommand));
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    /**
     * Returns a {@code BinDeleteCommand} with the parameter {@code index}.
     */
    private BinDeleteCommand prepareCommand(Index index) {
        BinDeleteCommand binDeleteCommand = new BinDeleteCommand(index);
        binDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return binDeleteCommand;
    }

```
###### /java/seedu/address/logic/commands/BinDeleteCommandTest.java
``` java
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    /**
     * Returns a {@code RecycleBin} with three of the typical persons.
     */
    public static RecycleBin getTypicalRecycleBin() {
        RecycleBin rb = new RecycleBin();
        for (ReadOnlyPerson person : getTypicalPersonsForBin()) {
            try {
                rb.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return rb;
    }

```
