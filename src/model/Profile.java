package src.model;

public class Profile {
    private String fullName;
    private String phoneNumber;
    private String address;

    public Profile() {
    }

    public Profile(String fullName, String phoneNumber, String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void updateProfile(String fullName, String phoneNumber, String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void displayProfile() {
        System.out.println("=== DETAIL PROFILE ===");
        System.out.println("Nama Lengkap : " + fullName);
        System.out.println("No HP        : " + phoneNumber);
        System.out.println("Alamat       : " + address);
    }

    public String getProfileInfo() {
        return "Nama Lengkap: " + fullName +
                ", No HP: " + phoneNumber +
                ", Alamat: " + address;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return getProfileInfo();
    }
    // constructor, getter, setter, dan method updateProfile/displayProfile
}
