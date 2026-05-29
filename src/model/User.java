// # [M] Class data dan akses database
package src.model;

public class User {
   private int userID;
    private String username;
    private String email;
    private String password;
    private Profile detailProfile;

    public User() {
    }

    public User(int userID, String username, String email, String password, Profile detailProfile) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.detailProfile = detailProfile;
    }

    public void register(String email, String password) {
        this.email = email;
        this.password = password;

        System.out.println("Registrasi berhasil untuk email: " + email);
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User " + username + " berhasil logout.");
    }

    public void editProfile(String newUsername, String newEmail) {
        this.username = newUsername;
        this.email = newEmail;

        System.out.println("Data akun berhasil diperbarui.");
    }

    public String getUsername() {
        return username;
    }

    public void showUserInfo() {
        System.out.println("=== DETAIL USER ===");
        System.out.println("User ID  : " + userID);
        System.out.println("Username : " + username);
        System.out.println("Email    : " + email);

        if (detailProfile != null) {
            detailProfile.displayProfile();
        } else {
            System.out.println("Profile belum diisi.");
        }
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public Profile getDetailProfile() {
        return detailProfile;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDetailProfile(Profile detailProfile) {
        this.detailProfile = detailProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", detailProfile=" + detailProfile +
                '}';
    }
    // constructor, getter, setter, dan method register, login, dll

}
