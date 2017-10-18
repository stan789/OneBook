package seedu.address.storage;

public class VCard {

    private String phone="";
    private String email = "johnd@example.com";
    private String address = "311, Clementi Ave 2, #02-25";
    private String birthday = "02-01-1995";
    private String name="NO NAME";

    public void VCard(){
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
}
