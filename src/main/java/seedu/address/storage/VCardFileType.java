package seedu.address.storage;

/**
 * Format of VCard file
 */

public class VCardFileType {

    private String name = "FN";
    private String addressFormat1 = "ADR";
    private String addressFormat2 = "item1.ADR";
    private String email = "EMAIL";
    private String begin = "BEGIN:VCARD";
    private String end = "END:VCARD";
    private String phoneFormat1 = "TEL";
    private String phoneFormat2 = "item1.TEL";
    private String birthday = "BDAY";
    private String label = "CATEGORIES";

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
}
