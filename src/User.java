public class User {
    private int userId;
    private String username;
    private String password; // Hanya untuk kelengkapan, tidak selalu diisi
    private String securityQuestion; // Hanya untuk kelengkapan
    private String address;
    private String phoneNumber;

    public User(int userId, String username, String password, String securityQuestion, String address, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; } // Hati-hati penggunaan
    public String getSecurityQuestion() { return securityQuestion; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}