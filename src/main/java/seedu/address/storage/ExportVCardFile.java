package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * write vCard file from the directory. supports up to VCard Version 3.0
 */

public class ExportVCardFile {


    private static final Integer INDEX_ZERO = 0;
    private static final Integer INDEX_ONE = 1;
    private static final Integer INDEX_TWO = 2;
    private String fileLocation;
    private VCardFileType vcf;

    public ExportVCardFile(String fileLocation) {

        this.fileLocation = fileLocation;
        vcf = new VCardFileType();
    }

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

                bufferedWriter.write(vcf.getEnd());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
