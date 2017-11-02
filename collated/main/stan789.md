# stan789
###### /java/seedu/address/logic/parser/ImportCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ImportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        fileLocation = Paths.get(trimmedArgs);
        File file = new File(trimmedArgs);
        if (!file.isFile()) {
            throw new ParseException(NO_FILE_FOUND);
        }
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(VCF_EXTENSION)) {
            throw new ParseException(FILE_WRONG_FORMAT);
        }
        return new ImportCommand(fileLocation);
    }
}
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
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
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new ParseException(INVALID_DIRECTORY);
        }

        String fileName = trimmedArgs.substring(trimmedArgs.lastIndexOf("/") + 1, trimmedArgs.lastIndexOf("."));
        if (!fileName.matches("[A-Za-z0-9.-_]+")) {
            throw new ParseException(INVALID_FILE_NAME);
        }

        String extension = trimmedArgs.substring(trimmedArgs.lastIndexOf(".") + 1, trimmedArgs.length());
        if (!extension.equals(VCF_EXTENSION) && !extension.equals(CSV_EXTENSION)) {
            throw new ParseException(INVALID_EXTENSION);
        }

        return new ExportCommand(trimmedArgs, fileName, extension);
    }
}

```
###### /java/seedu/address/logic/parser/EmailCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty() || (!trimmedArgs.equals(SORT_NAME) && !trimmedArgs.equals(SORT_EMAIL))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedArgs);
    }
}
```
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
        if (personToEmail.getEmail().toString().equals("~")) {
            throw new CommandException(MESSAGE_EMPTY_EMAIL);
        }

        try {
            desktopEmail(personToEmail.getEmail().toString());
        } catch (URISyntaxException e) {
            throw new CommandException(MESSAGE_INVALID_EMAIL);
        } catch (IOException | SecurityException e) {
            throw new CommandException(MESSAGE_EMAIL_FAIL);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * redirects user to their desktop's default email application.
     */
    protected void desktopEmail(String email) throws IOException, URISyntaxException {
        if (!Desktop.isDesktopSupported()) {
            throw new IOException();
        }
        Desktop desktop = getDesktop();
        desktop.mail(new URI("mailto:" + email));
    }

```
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.exportFile(fileLocation, extension);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        try {
            if (this.binMode) {
                model.executeBinSort(sortType);
            } else {
                model.executeSort(sortType);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyAddressBookException e) {
            throw new CommandException(MESSAGE_NO_PERSON_TO_SORT);
        }

    }

```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ImportCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        try {

            count = model.importFile(fileLocation);

        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_INVALID);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }

```
###### /java/seedu/address/logic/commands/ImportCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
```
###### /java/seedu/address/storage/ExportCsvFile.java
``` java
    /**
     * Create a csv file to the directory.
     */
    public void createCsvFile(ObservableList<ReadOnlyPerson> person) throws IOException {

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("First Name,Last Name,Company,Home Street,Other Phone,Birthday,E-mail Address");
            bufferedWriter.newLine();
            for (ReadOnlyPerson p: person) {
                String[] name = p.getName().toString().split(" ", 2);
                bufferedWriter.write(name[0]);
                bufferedWriter.write(",");

                if (name.length == 2) {
                    bufferedWriter.write(name[1]);
                }
                bufferedWriter.write(",");

                if (!p.getOrganisation().toString().equals("~")) {
                    bufferedWriter.write(p.getOrganisation().toString());
                }
                bufferedWriter.write(",");

                if (!p.getAddress().toString().equals("~")) {
                    String address = p.getAddress().toString();
                    address = address.replaceAll(",", "");
                    bufferedWriter.write(address);
                }
                bufferedWriter.write(",");

                if (!p.getPhone().toString().equals("~")) {
                    bufferedWriter.write(p.getPhone().toString());
                }
                bufferedWriter.write(",");


                if (!p.getBirthday().toString().equals("~")) {
                    String birthday = p.getBirthday().toString();
                    birthday = birthday.replaceAll("-", "/");
                    bufferedWriter.write(birthday);
                }
                bufferedWriter.write(",");

                if (!p.getEmail().toString().equals("~")) {
                    bufferedWriter.write(p.getEmail().toString());
                }
                bufferedWriter.newLine();

            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IOException();
        }

    }
}
```
###### /java/seedu/address/storage/exceptions/WrongFormatInFileException.java
``` java
/**
 * Signals that the format in the file is wrong.
 */

public class WrongFormatInFileException extends IOException {
    public WrongFormatInFileException() {

    }
}
```
###### /java/seedu/address/storage/exceptions/EmptyFileException.java
``` java
/**
 * Signals that the file read is an empty file.
 */

public class EmptyFileException extends IOException {
    public EmptyFileException() {

    }
}
```
###### /java/seedu/address/storage/ExportVCardFile.java
``` java
    /**
     * write vCard file from the directory. supports up to VCard Version 3.0
     */
    public void createVCardFile(ObservableList<ReadOnlyPerson> person) throws IOException {

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            for (ReadOnlyPerson p: person) {
                bufferedWriter.write(vcf.getBegin());
                bufferedWriter.newLine();

                bufferedWriter.write(vcf.getVersion());
                bufferedWriter.newLine();

                bufferedWriter.write(vcf.getName() + ":" + p.getName());
                bufferedWriter.newLine();

                if (!p.getEmail().toString().equals("~")) {
                    bufferedWriter.write(vcf.getEmail() + ":" + p.getEmail());
                    bufferedWriter.newLine();
                }

                if (!p.getPhone().toString().equals("~")) {
                    bufferedWriter.write(vcf.getPhoneFormat() + ":" + p.getPhone());
                    bufferedWriter.newLine();
                }

                if (!p.getTags().isEmpty()) {
                    String tag = p.getTags().toString();
                    if (tag.contains("[")) {
                        tag = tag.replaceAll("[\\[\\]]", "");
                    }
                    bufferedWriter.write(vcf.getLabel() + ":" + tag);
                    bufferedWriter.newLine();
                }

                if (!p.getBirthday().toString().equals("~")) {
                    String birthday = p.getBirthday().toString();
                    String[] array = birthday.split("-");
                    birthday = array[INDEX_TWO] + "-" + array[INDEX_ONE] + "-" + array[INDEX_ZERO];
                    bufferedWriter.write(vcf.getBirthday() + ":" + birthday);
                    bufferedWriter.newLine();
                }
                if (!p.getAddress().toString().equals("~")) {
                    String address = p.getAddress().toString();
                    for (int count = 0; count < 6; count++) {
                        if (address.contains(",")) {
                            address = address.replace(",", ";");
                        } else {
                            address = address.concat(";");
                        }
                    }
                    bufferedWriter.write(vcf.getAddressFormat1() + ":" + address);
                    bufferedWriter.newLine();
                }
                if (!p.getOrganisation().toString().equals("~")) {
                    String org = p.getOrganisation().toString();
                    bufferedWriter.write(vcf.getOrganization() + ":" + org);
                    bufferedWriter.newLine();
                }
                if (!p.getRemark().toString().equals("~")) {
                    String remark = p.getRemark().toString();
                    bufferedWriter.write(vcf.getNotes() + ":" + remark);
                    bufferedWriter.newLine();
                }

                bufferedWriter.write(vcf.getEnd());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * Read vCard file from the directory. Check format in file.
     * return a list of persons
     */

    public ArrayList<Person> getPersonFromFile() throws IOException {

        File file = new File(fileLocation.toString());
        if (file.length() == EMPTY_SIZE) {
            throw new EmptyFileException();
        }
        BufferedReader br = new BufferedReader(new FileReader(fileLocation.toString()));
        String string = br.readLine();
        if (!string.equals(vcf.getBegin())) {
            throw new IOException();
        }

        for (String str : Files.readAllLines(fileLocation, Charset.defaultCharset())) {
            try {
                sendRequest(str);
            } catch (WrongFormatInFileException e) {
                throw new IOException();
            }
        }
        if (!checkEnd) {
            throw new IOException();
        }
        return person;
    }

```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * Check format in file for each line.
     * return a person
     */
    private void sendRequest(String line) throws WrongFormatInFileException {

        if (line.contains(vcf.getBegin())) {

            if (!checkEnd) {
                throw new WrongFormatInFileException();
            } else {
                vCard = new VCard();
            }
            checkEnd = false;
            checkBegin = true;
        } else if (line.contains(vcf.getEnd())) {
            endSection();
        } else {
            vCardFilePart(line);
        }
    }

```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * check the format of the different attributes of VCard object.
     */
    private void vCardFilePart(String line) {
        String[] contactArray = line.split(":");
        if (contactArray.length == INDEX_TWO) {
            if ((line.startsWith(vcf.getPhoneFormat2()) || line.contains(vcf.getPhoneFormat()))) {
                String phone = contactArray[INDEX_ONE];
                phone = phone.replaceAll("[^\\p{Graph}]", " ");
                if (vCard.getPhone() == null) {
                    vCard.setPhone(phone);
                }
            }

            if (line.startsWith(vcf.getEmail())) {
                vCard.setEmail(contactArray[INDEX_ONE]);
            }

            if (line.startsWith(vcf.getName())) {
                vCard.setName(contactArray[INDEX_ONE]);
            }

            if (line.startsWith(vcf.getAddressFormat1()) || line.contains(vcf.getAddressFormat2())) {
                addressSection(contactArray[INDEX_ONE]);
            }

            if (line.startsWith(vcf.getBirthday())) {
                String birthday = contactArray[INDEX_ONE];
                String[] array = birthday.split("-");
                if (array.length == BIRTHDAY_SIZE) {
                    birthday = array[INDEX_TWO] + "-" + array[INDEX_ONE] + "-" + array[INDEX_ZERO];
                }
                vCard.setBirthday(birthday);
            }

            if (line.startsWith(vcf.getLabel())) {
                tagSection(contactArray[INDEX_ONE]);
            }

            if (line.startsWith(vcf.getOrganization())) {
                vCard.setOrganisation(contactArray[INDEX_ONE]);
            }
            if (line.startsWith(vcf.getNotes())) {
                vCard.setRemark(contactArray[INDEX_ONE]);
            }
        }
    }

```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * change format of VCard address to OneBook address format
     */
    private void addressSection(String spiltAddress) {
        String[] array = spiltAddress.split(";");
        String address = "";
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("")) {
                continue;
            }
            address = address.concat(array[i]);
            if (i != array.length - 1) {
                address = address.concat(",");
            }

        }
        vCard.setAddress(address);
    }

```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * create a list of tags from VCard file tags.
     */
    private void tagSection (String label) {
        List<String> tagList = new ArrayList<>();
        if (label.contains(",")) {
            tagList.addAll(Arrays.asList(label.split(",")));
        } else {
            tagList.add(label);
        }
        vCard.setTag(tagList);
    }

```
###### /java/seedu/address/storage/ImportVCardFile.java
``` java
    /**
     * add a new person to Arraylist if a person's format is correct and start with begin VCard and
     * end VCard statement.
     */
    private void endSection() throws WrongFormatInFileException {
        try {
            if (!checkBegin) {
                throw new WrongFormatInFileException();
            } else {
                checkEnd = true;
                checkBegin = false;
                Set<Tag> tag = new HashSet<>();
                for (String string : vCard.getTag()) {
                    tag.add(new Tag(string));
                }
                person.add(new Person(new Name(vCard.getName()), new Phone(vCard.getPhone()),
                        new Birthday(vCard.getBirthday()), new Email(vCard.getEmail()),
                        new Address(vCard.getAddress()), new Organisation(vCard.getOrganisation()),
                        new Remark((vCard.getRemark())), tag));
            }
        } catch (IllegalValueException e) {
            System.out.println("IllegalValueException" + vCard.getName() + " " + vCard.getPhone());
        }
    }

}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the list by type(name or email) in alphabetical order.
     */
    public void sort(String sortType) throws EmptyAddressBookException {
        if (internalList.isEmpty()) {
            throw new EmptyAddressBookException();
        }

        switch(sortType) {

        case SORT_NAME:
            internalList.sort(Comparator.comparing(p -> p.getName().toString().toLowerCase()));
            break;

        case SORT_EMAIL:
            internalList.sort(Comparator.comparing(p -> p.getEmail().toString().toLowerCase()));
            break;

        default:
        }

    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Person(editedPerson));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public ReadOnlyPerson remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return toRemove;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }


    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/person/exceptions/EmptyAddressBookException.java
``` java
/**
 * Signals that the operation will result in executing an empty address.
 */

public class EmptyAddressBookException extends Exception {
    public EmptyAddressBookException() {
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Sorts the list by type(name or email) in alphabetical order.
     */

    public void executeSort(String sortType) throws EmptyAddressBookException {
        persons.sort(sortType);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    protected void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        ReadOnlyPerson deletedPerson = persons.remove(key);
        if (deletedPerson != null) {
            return deletedPerson;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void executeSort(String sortType) throws EmptyAddressBookException {
        addressBook.executeSort(sortType);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public Integer importFile(Path fileLocation) throws IOException {
        ImportVCardFile importFile = new ImportVCardFile(fileLocation);
        ArrayList<Person> person = importFile.getPersonFromFile();
        for (Person p : person) {
            try {
                addPerson(p);
            } catch (DuplicatePersonException e) {
                System.out.println("DuplicatePersonException" + p.getName());
            }
        }
        return person.size();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void exportFile(String fileLocation, String extension) throws IOException {
        ObservableList<ReadOnlyPerson> person = getAddressBook().getPersonList();
        if (extension.equals("vcf")) {
            ExportVCardFile exportVCardFile = new ExportVCardFile(fileLocation);
            exportVCardFile.createVCardFile(person);
        } else {
            ExportCsvFile exportCsvFile = new ExportCsvFile(fileLocation);
            exportCsvFile.createCsvFile(person);
        }

    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Updates the filter of the filtered bin list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void executeSort(String sortType) throws EmptyAddressBookException;

```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns an integer which is the number of persons that are succcessfully imported*/
    Integer importFile(Path fileLocation) throws IOException;

```
###### /java/seedu/address/model/Model.java
``` java
    /** Exports the current contacts in OneBook to VCard or Csv file*/
    void exportFile(String fileLocation, String extension) throws IOException;
}
```
