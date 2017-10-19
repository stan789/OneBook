package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the information from VCard file.
 */

public class VCard {

    private String phone;
    private String email;
    private String address;
    private String birthday;
    private String name;
    private List<String> tag;

    public VCard() {
        phone = "";
        email = "johnd@example.com";
        address = "311, Clementi Ave 2, #02-25";
        birthday = "02-01-1995";
        name = "NO NAME";
        tag = new ArrayList<String>();


    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTag(List<String> label) {
        tag = label;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getTag() {
        return tag;
    }
}
