import javax.swing.SwingUtilities;

public class Main {
    public static User currentUser;

    public static void main(String[] args) {
        DatabaseHelper.createNewDatabase();
        DatabaseHelper.createTables();
        DatabaseHelper.addDefaultAdmin();

        SwingUtilities.invokeLater(() -> {
            HalamanLogin loginPage = new HalamanLogin();
            loginPage.setVisible(true);
        });
    }
}