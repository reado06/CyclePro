public class User {
    private int userId;
    private String username;
    private String password; 
    private String address;
    private String phoneNumber;

    public User(int userId, String username, String password, String address, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getter
    public int getUserId() { 
        return userId; 
    }
    public String getUsername() { 
        return username; 
    }
    public String getPassword() { 
        return password; 
    }
    public String getAddress() { 
        return address; 
    }
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
}