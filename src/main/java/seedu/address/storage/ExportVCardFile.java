package seedu.address.storage;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExportVCardFile {


    private static final Integer INDEX_ZERO = 0;
    private static final Integer INDEX_ONE = 1;
    private static final Integer INDEX_TWO = 2;
    private Path fileLocation;
    private VCardFileType vcf;

    public ExportVCardFile(Path fileLocation){

        this.fileLocation = fileLocation;
        vcf = new VCardFileType();
    }

    public void createVCardFile(ObservableList<ReadOnlyPerson> person) throws IOException {

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(fileLocation.toString()));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            for(ReadOnlyPerson p: person) {
                bufferedWriter.write(vcf.getBegin());
                bufferedWriter.newLine();

                bufferedWriter.write(vcf.getVersion());
                bufferedWriter.newLine();

                bufferedWriter.write(vcf.getName() + ":" + p.getName());
                bufferedWriter.newLine();

                if (!p.getEmail().toString().equals("-")) {
                    bufferedWriter.write(vcf.getEmail() + ":" + p.getEmail());
                    bufferedWriter.newLine();
                }

                if (!p.getPhone().toString().equals("-")) {
                    bufferedWriter.write(vcf.getPhoneFormat() + ":" + p.getPhone());
                    bufferedWriter.newLine();
                }

                if(!p.getTags().isEmpty()) {
                    String tag = p.getTags().toString();
                    if(tag.contains("[")) {
                        tag = tag.replaceAll("[\\[\\]]","");
                    }
                    bufferedWriter.write(vcf.getLabel() + ":" + tag);//set of tags
                    System.out.println(p.getTags().toString());
                    bufferedWriter.newLine();
                }

                if (!p.getBirthday().toString().equals("-")) {
                    String birthday = p.getBirthday().toString();
                    String[] array = birthday.split("-");
                    birthday = array[INDEX_TWO] + "-" + array[INDEX_ONE] + "-" + array[INDEX_ZERO];
                    bufferedWriter.write(vcf.getBirthday() + ":" + birthday);
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
