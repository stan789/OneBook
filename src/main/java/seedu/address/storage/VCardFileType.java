package seedu.address.storage;
/**
 * Format of VCard file
 */

public class VCardFileType {

    private String name = "FN";
    private String address = "ADR";
    private String email = "EMAIL";
    private String begin = "BEGIN:VCARD";
    private String end = "END:VCARD";
    private String phoneFormat1 = "TEL";
    private String phoneFormat2 = "TEL:";
    private String birthday = "BDAY";

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

    public String getBirthday() {
        return birthday;
    }
}
