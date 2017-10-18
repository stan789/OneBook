package seedu.address.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.exceptions.EmptyFileException;
import seedu.address.storage.exceptions.WrongFormatInFileException;

/**
 * Read vCard file from the directory. supports up to VCard Version 3.0
 *
 */

public class ImportVCardFile {

    private final Path fileLocation;
    private Set<Tag> tag = Collections.EMPTY_SET;
    private ArrayList<Person> person = new ArrayList<>();
    private VCardFileType vcf;
    private VCard vCard;
    private boolean checkEnd = true;
    private boolean checkBegin = false;
    private boolean wrongFormat = false;
    private Integer birthdaySize = 3;
    private Integer indexZero = 0;
    private Integer indexOne = 1;
    private Integer indexTwo = 2;
    private Integer emptySize = 0;



    /**
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
        if (file.length() == emptySize) {
            throw new EmptyFileException();
        }
        BufferedReader br = new BufferedReader(new FileReader(fileLocation.toString()));
        String string = br.readLine();
        if (!string.equals(vcf.getBegin())) {
            throw new IOException();
        }

        Files.lines(fileLocation)
                .map(line -> line.trim())
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    try {
                        sendRequest(line);
                    } catch (WrongFormatInFileException e) {
                        wrongFormat = true;
                    }
                });
        if (wrongFormat) {
            throw new IOException();
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
            try {
                if (!checkBegin) {
                    throw new WrongFormatInFileException();
                } else {
                    checkEnd = true;
                    checkBegin = false;
                    person.add(new Person(new Name(vCard.getName()), new Phone(vCard.getPhone()),
                            new Birthday(vCard.getBirthday()), new Email(vCard.getEmail()),
                            new Address(vCard.getAddress()), tag));
                }
            } catch (IllegalValueException e) {
                System.out.println(vCard.getName());
                System.out.println(vCard.getPhone());
                System.out.println("IllegalValueException");
            }
        } else {
            String[] contactArray = line.split(":");

            if (contactArray.length == indexTwo) {
                if ((line.startsWith(vcf.getPhoneFormat2()) || line.contains(vcf.getPhoneFormat()))) {
                    String phone = contactArray[indexOne];
                    phone = phone.replaceAll("[^0-9*+]", "");
                    if (vCard.getPhone().equals("")) {
                        vCard.setPhone(phone);
                    }
                }
                if (line.startsWith(vcf.getEmail())) {
                    vCard.setEmail(contactArray[indexOne]);
                }
                if (line.startsWith(vcf.getName())) {
                    String name = contactArray[indexOne];
                    vCard.setName(name);
                }
                if (line.startsWith(vcf.getAddressFormat1()) || line.contains(vcf.getAddressFormat2())) {
                    String address = "";
                    String spiltAddress = contactArray[indexOne];
                    String[] array = spiltAddress.split(";");
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("")) {
                            continue;
                        }
                        address = address.concat(array[i]);
                        if (i != array.length - 1) {
                            address = address.concat(", ");
                        }

                    }
                    vCard.setAddress(address);
                }
                if (line.startsWith(vcf.getBirthday())) {
                    String birthday = contactArray[1];
                    String[] array = birthday.split("-");
                    if (array.length == birthdaySize) {
                        birthday = array[indexTwo] + "-" + array[indexOne] + "-" + array[indexZero];
                    }
                    vCard.setBirthday(birthday);
                }
            }
        }

    }

}
