# frozventus
###### \java\seedu\address\commons\events\commands\DisplayBinEvent.java
``` java
/** Indicates the AddressBook in the display list is now filtered*/
public class DisplayBinEvent extends BaseEvent {

    public DisplayBinEvent () {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\DisplayListFilteredEvent.java
``` java
/** Indicates the AddressBook in the display list is now filtered*/
public class DisplayListFilteredEvent extends BaseEvent {

    public DisplayListFilteredEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\DisplayListResetEvent.java
``` java
/** Indicates the AddressBook in the display list is now filtered*/
public class DisplayListResetEvent extends BaseEvent {

    public DisplayListResetEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\PersonDeletedEvent.java
``` java
/** Indicates the AddressBook in the display list is now filtered*/
public class PersonDeletedEvent extends BaseEvent {

    public PersonDeletedEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setListDisplay() {
        this.model.setListDisplay();
    }

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setBinDisplay() {
        this.model.setBinDisplay();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Subscribe
    public void handleDisplayListResetEvent(DisplayListResetEvent event) {
        binMode = false;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Subscribe
    public void handleDisplayBinEvent(DisplayBinEvent event) {
        binMode = true;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the RecycleBin */
    ReadOnlyAddressBook getRecycleBin();

    /** Deletes the given person from addressbook */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Adds the given person to addressbook */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Restores the given person from recycle bin */
    void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given person from bin */
    void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

```
###### \java\seedu\address\model\Model.java
``` java
    /** Toggle display to show list */
    void setListDisplay();

```
###### \java\seedu\address\model\Model.java
``` java
    /** Toggle display to show bin */
    void setBinDisplay();

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the address book person list */
    ObservableList<ReadOnlyPerson> getAddressBookList();

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the recycle bin list */
    ObservableList<ReadOnlyPerson> getRecycleBinList();

    /**
     * Updates the filter of the filtered bin list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */

    void executeSort(String sortType) throws EmptyAddressBookException;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetData(AddressBookData newData) {
        addressBook.resetData(newData.getAddressBook());
        recycleBin.resetData(newData.getRecycleBin());
        indicateAddressBookChanged();
    }

    @Override
    public void executeSort(String sortType) throws EmptyAddressBookException {
        addressBook.executeSort(sortType);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ReadOnlyAddressBook getRecycleBin() {
        return recycleBin;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(new AddressBookData(addressBook, recycleBin)));
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                        DuplicatePersonException {
        addressBook.removePerson(target);
        recycleBin.addPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                         DuplicatePersonException {
        addressBook.addPerson(target);
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException {
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setListDisplay() {
        this.filteredPersons = filteredAddresses;
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setBinDisplay() {
        this.filteredPersons = filteredBin;
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getAddressBookList() {
        return filteredAddresses;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getRecycleBinList() {
        return filteredBin;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && recycleBin.equals(other.recycleBin)
                && filteredPersons.equals(other.filteredPersons);
    }

}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday has to be in the format dd-mm-yyyy";
    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[12]\\d|3[01])[-](0[1-9]|1[0-2])[-]\\d{4}";
    public static final String BIRTHDAY_NOT_ASSIGNED = "~";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {

        if (birthday == null) {
            this.value = BIRTHDAY_NOT_ASSIGNED;
        } else {
            String trimmedBirthday = birthday.trim();

            if (!trimmedBirthday.equals(BIRTHDAY_NOT_ASSIGNED)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);

                try {
                    dateFormat.parse(trimmedBirthday);
                } catch (ParseException e) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
                }
            }

            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.matches(BIRTHDAY_NOT_ASSIGNED);
    }

    public String getMonth() {
        String[] args = value.split("-");

        return args[1];
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\RecycleBin.java
``` java
/**
 * An extension of AddressBook to hold deleted contacts
 * Duplicates are not allowed (by .equals comparison)
 */
public class RecycleBin extends AddressBook {

    public RecycleBin() {}

    /**
     * Creates a RecycleBin using the Persons and Tags in the {@code toBeCopied}
     */
    public RecycleBin(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    @Override
    /**
     * Resets the existing data of this {@code RecycleBin} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }
}
```
###### \java\seedu\address\storage\AddressBookData.java
``` java
/**
 * A storage data holder to hold both addressBook and recycleBin data
 */
public class AddressBookData {

    private ReadOnlyAddressBook addressBook;
    private ReadOnlyAddressBook recycleBin;

    public AddressBookData() {
        addressBook = new AddressBook();
        recycleBin = new RecycleBin();
    }

    public AddressBookData(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = addressBook;
        this.recycleBin = recycleBin;
    }

    public void setAddressBook (ReadOnlyAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setRecycleBin (ReadOnlyAddressBook recycleBin) {
        this.recycleBin = recycleBin;
    }

    public ReadOnlyAddressBook getAddressBook () {
        return addressBook;
    }

    public ReadOnlyAddressBook getRecycleBin () {
        return recycleBin;
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Similar to {@link #saveAddressBook(AddressBookData)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(AddressBookData addressBook, String filePath)
            throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBookCombined(addressBook.getAddressBook(),
                                                                                   addressBook.getRecycleBin()));
    }

}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Returns address book in the file or an empty address book
     */
    public static AddressBookData loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            XmlSerializableAddressBookCombined data =
                    XmlUtil.getDataFromFile(file, XmlSerializableAddressBookCombined.class);
            return new AddressBookData(data.getAddressBook(), data.getRecycleBin());
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBookCombined.java
``` java
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBookCombined {

    @XmlElement
    private XmlSerializableAddressBook addressBook;
    @XmlElement
    private XmlSerializableAddressBook recycleBin;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBookCombined() {
        addressBook = new XmlSerializableAddressBook();
        recycleBin = new XmlSerializableAddressBook();
    }

    /**
     * Conversion
     */

    public XmlSerializableAddressBookCombined(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = new XmlSerializableAddressBook(addressBook);
        this.recycleBin = new XmlSerializableAddressBook(recycleBin);
    }

    public XmlSerializableAddressBook getAddressBook () {
        return addressBook;
    }

    public XmlSerializableAddressBook getRecycleBin () {
        return recycleBin;
    }
}
```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        loadDefaultPage();
        addressLarge.textProperty().unbind();
        addressLarge.textProperty().setValue("");
    }

```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        addressLarge.textProperty().bind(Bindings.convert(person.addressProperty()));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonAddress(event.getNewSelection().person);
    }

```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    @Subscribe
    public void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setListDisplay() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void switchListDisplay() {
        logic.setListDisplay();
        listName.textProperty().setValue("List");
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void switchBinDisplay() {
        logic.setBinDisplay();
        listName.textProperty().setValue("Bin");
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        addressPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleDisplayListFilteredEvent(DisplayListFilteredEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (listName.textProperty().get().equals("List")) {
            listName.textProperty().setValue("Filtered List");
        } else if (listName.textProperty().get().equals("Bin")) {
            listName.textProperty().setValue("Filtered Bin");
        }
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleDisplayListResetEvent(DisplayListResetEvent event) {
        if (listName.textProperty().get().equals("Bin") || listName.textProperty().get().equals("Filtered Bin")) {
            switchListDisplay();
            setListDisplay();
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleDisplayBinEvent(DisplayBinEvent event) {
        if (listName.textProperty().get().equals("Filtered List") || listName.textProperty().get().equals("List")) {
            switchBinDisplay();
            setListDisplay();
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
```
###### \java\seedu\address\ui\PersonDisplayCard.java
``` java
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        nameLarge.textProperty().unbind();
        nameLarge.textProperty().setValue("");
        phoneLarge.textProperty().unbind();
        phoneLarge.textProperty().setValue("");
        birthdayLarge.textProperty().unbind();
        birthdayLarge.textProperty().setValue("");
        emailLarge.textProperty().unbind();
        emailLarge.textProperty().setValue("");
        organisationLarge.textProperty().unbind();
        organisationLarge.textProperty().setValue("");
        remarkLarge.textProperty().unbind();
        remarkLarge.textProperty().setValue("");
        tagsLarge.getChildren().clear();
    }

```
###### \java\seedu\address\ui\PersonDisplayCard.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }

```
###### \java\seedu\address\ui\PersonDisplayCard.java
``` java
    @Subscribe
    private void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    private void resetSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
```
###### \resources\view\AddressPanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <VBox fx:id="addressPane" prefHeight="200.0" prefWidth="100.0">
      <children>
         <SplitPane dividerPositions="0.5" maxHeight="100.0" minHeight="100.0" prefHeight="100.0" prefWidth="200.0">
            <items>
               <Label fx:id="addressHeader" maxWidth="110.0" minWidth="110.0" prefWidth="110.0" text="Address :">
                  <padding>
                     <Insets left="5.0" />
                  </padding></Label>
               <Label fx:id="addressLarge">
                  <padding>
                     <Insets left="5.0" />
                  </padding></Label>
            </items>
         </SplitPane>
        <WebView fx:id="browser" minHeight="-Infinity" minWidth="-Infinity" VBox.vgrow="ALWAYS" />
      </children>
   </VBox>
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
            <SplitPane dividerPositions="0.2" prefWidth="200.0" VBox.vgrow="ALWAYS">
              <items>
                  <StackPane fx:id="detailsPlaceholder" prefHeight="150.0" prefWidth="200.0">
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding></StackPane>

                      <StackPane fx:id="addressPlaceholder" prefWidth="400.0">
                  <padding>
                    <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                  </padding>
                </StackPane>
              </items>
            </SplitPane>
         </children>
      </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\PersonDetailsCard.fxml
``` fxml
<StackPane alignment="TOP_LEFT" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox fx:id="detailsPane" prefWidth="160.0">
         <children>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="nameHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Name :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="nameLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="phoneHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Phone :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="phoneLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="birthdayHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Birthday :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="birthdayLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="emailHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Email :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="emailLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
               <items>
                  <Label fx:id="organisationHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Organisation :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="organisationLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
               </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="tagsHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Tags :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <FlowPane fx:id="tagsLarge" alignment="CENTER_LEFT">
                     <padding>
                        <Insets left="5.0" />
                     </padding></FlowPane>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="remarkHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Remarks :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="remarkLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
         </children>
      </VBox>
   </children>
</StackPane>
```
