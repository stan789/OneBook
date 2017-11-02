package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Read csv file from the directory.
 */

public class ExportCsvFile {

    private String fileLocation;
    public ExportCsvFile(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * Read csv file from the directory.
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
