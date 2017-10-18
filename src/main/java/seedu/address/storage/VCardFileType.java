package seedu.address.storage;
/**
 * API of the Storage component
 */

public class VCardFileType {

    private String name = "FN";
    private String address= "ADR";
    private String email = "EMAIL";
    private String begin = "BEGIN:VCARD";
    private String end = "END:VCARD";
    private String phoneFormat1 = "TEL";
    private String phoneFormat2 = "item1.TEL";

    public VCardFileType() {
    }

    public String getAddress() {
        return address;
    }

    public String getBegin() {
        return begin;
    }

    public String getEmail() {
        return email;
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
}
