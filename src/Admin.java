public class Admin extends User {
    private String role; // Untuk membedakan dari user biasa, bisa "admin"

    public Admin(int userId, String username, String password, String address, String phoneNumber) {
        super(userId, username, password, address, phoneNumber);
        this.role = "admin"; // Set peran default sebagai admin
    }

    public String getRole() {
        return role;
    }
}