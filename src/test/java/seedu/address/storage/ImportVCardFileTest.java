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
    public void getPerson_withoutBegin_throwsIOException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_begin.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }
    @Test
    public void getPerson_withoutEnd_throwsIOException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_end.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

    @Test
    public void getPerson_someWithoutEnd_throwsIOException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_end.vcf"));
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

}
