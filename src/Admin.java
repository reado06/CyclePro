public class Admin extends User {
    private String role; 

    public Admin(int userId, String username, String password, String address, String phoneNumber) {
        super(userId, username, password, address, phoneNumber);
        this.role = "admin"; 
    }

    public String getRole() {
        return role;
    }
}