package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.storage.Exceptions.EmptyFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ImportVCardFile {

    private final Path fileLocation;
    private Set<Tag> tag = Collections.EMPTY_SET;
    private ArrayList<Person> person = new ArrayList<>();
    private VCardFileType vcf;
    private VCard VCard;
    private boolean checkEnd = true;
    private boolean checkBegin = false;


    public ImportVCardFile(Path fileLocation) {
        this.fileLocation = fileLocation;
        vcf = new VCardFileType();
        VCard = new VCard();
    }

    public ArrayList<Person> getPersonFromFile() throws IOException {

        File file = new File(fileLocation.toString());
        if(file.length()==0){
            throw new EmptyFileException();
        }
        BufferedReader br = new BufferedReader(new FileReader(fileLocation.toString()));
        String string = br.readLine();
        if(!string.equals(vcf.getBegin())){
            throw new IOException();
        }

        Files.lines(fileLocation)
                .map(line -> line.trim())
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    try {
                        sendRequest(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        if (!checkEnd) {
            throw new IOException();
        }
        return person;
    }


    private void sendRequest(String line) throws IOException {

        if (line.contains(vcf.getBegin())) {

            if(!checkEnd){
                throw new IOException();
            }
            else{
                VCard = new VCard();
            }
            checkEnd = false;
            checkBegin = true;
        }

        else if(line.contains(vcf.getEnd())) {
            try {
                if(!checkBegin) {
                    throw new IOException();
                }
                else{
                    checkEnd = true;
                    checkBegin = false;
                    person.add(new Person(new Name(VCard.getName()), new Phone(VCard.getPhone()),
                            new Birthday(VCard.getBirthday()), new Email(VCard.getEmail()),
                            new Address(VCard.getAddress()), tag));
                }
            } catch (IllegalValueException e) {
                System.out.println(VCard.getName());
                System.out.println(VCard.getPhone());
                System.out.println("IllegalValueException");
            }
        }
        else {
            String[] contactArray = line.split(":");

            if (contactArray.length == 2) {
                if ((line.startsWith(vcf.getPhoneFormat2()) || line.startsWith(vcf.getPhoneFormat()))) {
                    String phone = contactArray[1];

                    phone = phone.replaceAll("[^0-9.*+]", "");
                    if(VCard.getPhone().isEmpty()) {
                        VCard.setPhone(phone);
                    }
                }


                if (line.startsWith(vcf.getEmail())) {
                    VCard.setEmail(contactArray[1]);

                }
                if (line.startsWith(vcf.getName())) {
                    String name = contactArray[1];
                    VCard.setName(name);

                }
            }
        }

    }

}