# stan789
###### \java\seedu\address\commands\SortCommand.java
``` java
public SortCommand(String sortType) {
        this.sortType = sortType;
    }

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
```
###### \java\seedu\address\commands\ImportCommand.java
``` java
public ImportCommand(Path fileLocation) {
        this.fileLocation = fileLocation;
    }


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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
```
###### \java\seedu\address\commands\ExportCommand.java
``` java
public ExportCommand(String fileLocation, String fileName, String extension) {

        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.extension = extension;
    }


    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.exportFile(fileLocation, extension);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
```
###### \java\seedu\address\commands\EmailCommand.java
``` java
public EmailCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
```
###### \java\seedu\address\model\Model.java
``` java
//sort
void executeSort(String sortType) throws EmptyAddressBookException;
//import VCard file
Integer importFile(Path fileLocation) throws IOException;
//export file
void exportFile(String fileLocation, String extension) throws IOException;
```
###### \java\seedu\address\model\ModelManager.java
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
        public void executeSort(String sortType) throws EmptyAddressBookException {
            addressBook.executeSort(sortType);
            indicateAddressBookChanged();
        }
 ```
 ###### \java\seedu\address\storage\ExportCsvFile.java  
  ``` java
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
                      bufferedWriter.write("," + p.getPhone().toString());
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
```
  ###### \java\seedu\address\storage\ExportVCardFile.java  
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
```
  ###### \java\seedu\address\storage\ImportVCardFile.java  
``` java 
**
     * Read vCard file from the directory.
     * return a list of persons
     */

    public ImportVCardFile(Path fileLocation) {
        this.fileLocation = fileLocation;
        vcf = new VCardFileType();
        vCard = new VCard();
    }

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
```   
###### \java\seedu\address\storage\VCard.java  
``` java 
**
 * Stores the information from VCard file.
 */

public class VCard {

    private String phone;
    private String email;
    private String address;
    private String birthday;
    private String name;
    private String organisation;
    private String remark;
    private List<String> tag;

    public VCard() {
        name = "";
        tag = new ArrayList<String>();


    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTag(List<String> label) {
        tag = label;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getTag() {
        return tag;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getRemark() {
        return remark;
    }
}
```   
###### \java\seedu\address\storage\VCardFileType.java  
``` java 
/**
 * Format of VCard file
 */

public class VCardFileType {

    private static final String name = "FN";
    private static final String addressFormat1 = "ADR";
    private static final String addressFormat2 = "item1.ADR";
    private static final String email = "EMAIL";
    private static final String begin = "BEGIN:VCARD";
    private static final String end = "END:VCARD";
    private static final String phoneFormat1 = "TEL";
    private static final String phoneFormat2 = ".TEL";
    private static final String birthday = "BDAY";
    private static final String label = "CATEGORIES";
    private static final String version = "Version 3.0";
    private static final String organization = "ORG";
    private static final String notes = "NOTE";

    public VCardFileType() {
    }

    public String getAddressFormat1() {
        return addressFormat1;
    }

    public String getAddressFormat2() {
        return addressFormat2;
    }

    public String getBegin() {
        return begin;
    }

    public String getEmail() {
        return email;
    }

    public String getLabel() {
        return label;
    }

    public String getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    public String getPhoneFormat() {
        return phoneFormat1;
    }

    public String getPhoneFormat2() {
        return phoneFormat2;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    public String getNotes() {
        return notes;
    }
}
```   
###### \java\seedu\address\storage\exceptions\EmptyFileException.java  
``` java 
public class EmptyFileException extends IOException {
    public EmptyFileException() {
    }
```       