package seedu.address.storage;

/**
 * Format of VCard file
 */

public class VCardFileType {

    private static final String name = "FN";
    private static final String addressFormat1 = "ADR";
    private static final String addressFormat2 = "item1.ADR";
    private static final String email = "EMAIL";
    private static final String begin = "BEGIN:VCARD";
    private static final String end = "END:VCARD";
    private static final String phoneFormat1 = "TEL";
    private static final String phoneFormat2 = "item1.TEL";
    private static final String birthday = "BDAY";
    private static final String label = "CATEGORIES";

    public VCardFileType() {
    }

    public String getAddressFormat1() { return addressFormat1; }

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
