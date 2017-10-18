package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ImportVCardFileTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPersonFromFile_withoutBegin_throwsIOException() throws IOException {
        ImportVCardFile importVCardFile= new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }
    @Test
    public void getPersonFromFile_withoutEnd_throwsIOException() throws IOException {
        ImportVCardFile importVCardFile= new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_end.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

}
