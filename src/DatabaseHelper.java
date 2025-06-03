import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:cyclepro.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("A new database has been created or connection established.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                + " user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " username TEXT UNIQUE NOT NULL,"
                + " password TEXT NOT NULL,"
                + " security_question TEXT,"
                + " security_answer TEXT NOT NULL,"
                + " address TEXT,"
                + " phone_number TEXT NOT NULL"
                + ");";

        String productsTable = "CREATE TABLE IF NOT EXISTS products ("
                + " product_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT NOT NULL,"
                + " category TEXT NOT NULL CHECK(category IN ('BMX', 'Gunung', 'Lipat')),"
                + " price REAL NOT NULL,"
                + " description TEXT,"
                + " image_path TEXT,"
                + " stock INTEGER DEFAULT 10"
                + ");";

        String ordersTable = "CREATE TABLE IF NOT EXISTS orders ("
                + " order_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " user_id INTEGER,"
                + " product_id INTEGER,"
                + " order_date TEXT DEFAULT CURRENT_TIMESTAMP,"
                + " shipping_address TEXT,"
                + " courier TEXT,"
                + " payment_method TEXT,"
                + " total_price REAL,"
                + " va_number TEXT NULLABLE,"
                + " status TEXT DEFAULT 'Pending',"
                + " FOREIGN KEY (user_id) REFERENCES users (user_id),"
                + " FOREIGN KEY (product_id) REFERENCES products (product_id)"
                + ");";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(productsTable);
            stmt.execute(ordersTable);
            System.out.println("Tables created successfully.");
            // Tambahkan beberapa data produk contoh jika tabel baru dibuat
            addSampleProducts();
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void addSampleProducts() {
        String checkProducts = "SELECT COUNT(*) AS count FROM products";
        String insertProductSQL = "INSERT INTO products(name, category, price, description, image_path, stock) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkProducts)) {

            if (rs.next() && rs.getInt("count") == 0) { // Hanya tambahkan jika tabel produk kosong
                System.out.println("Adding sample products...");
                try (PreparedStatement pstmt = conn.prepareStatement(insertProductSQL)) {
                    // BMX
                    pstmt.setString(1, "BMX Pro X1"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 1500000); pstmt.setString(4, "Sepeda BMX untuk freestyle"); pstmt.setString(5, "bmx1.png"); pstmt.setInt(6, 10); pstmt.executeUpdate();
                    pstmt.setString(1, "BMX Street Master"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 1200000); pstmt.setString(4, "Sepeda BMX lincah di jalanan"); pstmt.setString(5, "bmx2.png"); pstmt.setInt(6, 15); pstmt.executeUpdate();
                    pstmt.setString(1, "BMX Lite Fun"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 900000); pstmt.setString(4, "Sepeda BMX ringan untuk pemula"); pstmt.setString(5, "bmx3.png"); pstmt.setInt(6, 20); pstmt.executeUpdate();
                    pstmt.setString(1, "BMX Racer Z"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 1800000); pstmt.setString(4, "Sepeda BMX untuk kecepatan"); pstmt.setString(5, "bmx4.png"); pstmt.setInt(6, 8); pstmt.executeUpdate();
                    pstmt.setString(1, "BMX Park King"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 1600000); pstmt.setString(4, "Sepeda BMX untuk atraksi di taman"); pstmt.setString(5, "bmx5.png"); pstmt.setInt(6, 12); pstmt.executeUpdate();
                    pstmt.setString(1, "BMX Dirt Jumper"); pstmt.setString(2, "BMX"); pstmt.setDouble(3, 1700000); pstmt.setString(4, "Sepeda BMX untuk trek tanah"); pstmt.setString(5, "bmx6.png"); pstmt.setInt(6, 7); pstmt.executeUpdate();

                    // Gunung
                    pstmt.setString(1, "Gunung Everest Peak"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 3500000); pstmt.setString(4, "Sepeda gunung tangguh semua medan"); pstmt.setString(5, "gunung1.png"); pstmt.setInt(6, 5); pstmt.executeUpdate();
                    pstmt.setString(1, "Gunung TrailBlazer X"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 2800000); pstmt.setString(4, "Sepeda gunung untuk petualangan trail"); pstmt.setString(5, "gunung2.png"); pstmt.setInt(6, 8); pstmt.executeUpdate();
                    pstmt.setString(1, "Gunung RockHopper"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 2200000); pstmt.setString(4, "Sepeda gunung handal dan nyaman"); pstmt.setString(5, "gunung3.png"); pstmt.setInt(6, 12); pstmt.executeUpdate();
                    pstmt.setString(1, "Gunung Summit Seeker"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 4000000); pstmt.setString(4, "Sepeda gunung performa tinggi"); pstmt.setString(5, "gunung4.png"); pstmt.setInt(6, 4); pstmt.executeUpdate();
                    pstmt.setString(1, "Gunung Forest Rider"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 2500000); pstmt.setString(4, "Sepeda gunung untuk menjelajah hutan"); pstmt.setString(5, "gunung5.png"); pstmt.setInt(6, 10); pstmt.executeUpdate();
                    pstmt.setString(1, "Gunung Canyon Carver"); pstmt.setString(2, "Gunung"); pstmt.setDouble(3, 3200000); pstmt.setString(4, "Sepeda gunung untuk medan curam"); pstmt.setString(5, "gunung6.png"); pstmt.setInt(6, 6); pstmt.executeUpdate();

                    // Lipat
                    pstmt.setString(1, "Lipat CityCommute"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 1800000); pstmt.setString(4, "Sepeda lipat praktis untuk perkotaan"); pstmt.setString(5, "lipat1.png"); pstmt.setInt(6, 15); pstmt.executeUpdate();
                    pstmt.setString(1, "Lipat UrbanFold"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 1500000); pstmt.setString(4, "Sepeda lipat ringkas dan stylish"); pstmt.setString(5, "lipat2.png"); pstmt.setInt(6, 18); pstmt.executeUpdate();
                    pstmt.setString(1, "Lipat QuickFold"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 2000000); pstmt.setString(4, "Sepeda lipat mudah dibawa"); pstmt.setString(5, "lipat3.png"); pstmt.setInt(6, 10); pstmt.executeUpdate();
                    pstmt.setString(1, "Lipat Metro Lite"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 1600000); pstmt.setString(4, "Sepeda lipat ringan dan efisien"); pstmt.setString(5, "lipat4.png"); pstmt.setInt(6, 20); pstmt.executeUpdate();
                    pstmt.setString(1, "Lipat TravelMate"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 2200000); pstmt.setString(4, "Sepeda lipat ideal untuk bepergian"); pstmt.setString(5, "lipat5.png"); pstmt.setInt(6, 9); pstmt.executeUpdate();
                    pstmt.setString(1, "Lipat SwiftFold"); pstmt.setString(2, "Lipat"); pstmt.setDouble(3, 1900000); pstmt.setString(4, "Sepeda lipat cepat dan nyaman"); pstmt.setString(5, "lipat6.png"); pstmt.setInt(6, 11); pstmt.executeUpdate();
                    System.out.println("Sample products added.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding sample products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static User authenticateUser(String username, String password) {
        String sql = "SELECT user_id, username, address, phone_number FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), "", "", rs.getString("address"), rs.getString("phone_number"));
            }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
        return null;
    }

    public static boolean registerUser(String username, String password, String securityQuestion, String securityAnswer, String address, String phoneNumber) {
        String sql = "INSERT INTO users(username, password, security_question, security_answer, address, phone_number) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, securityQuestion);
            pstmt.setString(4, securityAnswer);
            pstmt.setString(5, address);
            pstmt.setString(6, phoneNumber);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public static String[] recoverPassword(String username, String securityAnswer) {
        String sql = "SELECT password, security_question FROM users WHERE username = ? AND security_answer = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, securityAnswer);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new String[]{rs.getString("password"), rs.getString("security_question")};
            }
        } catch (SQLException e) {
            System.out.println("Password recovery error: " + e.getMessage());
        }
        return null;
    }

    public static String getSecurityQuestion(String username) {
        String sql = "SELECT security_question FROM users WHERE username = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("security_question");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching security question: " + e.getMessage());
        }
        return null;
    }


    public static java.util.List<Produk> getProductsByCategory(String category) {
        java.util.List<Produk> products = new java.util.ArrayList<>();
        String sql = "SELECT product_id, name, category, price, description, image_path, stock FROM products WHERE category = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Produk(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("image_path"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return products;
    }

    public static boolean createOrder(int userId, int productId, String shippingAddress, String courier, String paymentMethod, double totalPrice, String vaNumber) {
        String sql = "INSERT INTO orders(user_id, product_id, shipping_address, courier, payment_method, total_price, va_number, status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.setString(3, shippingAddress);
            pstmt.setString(4, courier);
            pstmt.setString(5, paymentMethod);
            pstmt.setDouble(6, totalPrice);
            if (vaNumber != null) {
                pstmt.setString(7, vaNumber);
            } else {
                pstmt.setNull(7, java.sql.Types.VARCHAR);
            }
            pstmt.setString(8, "Diproses"); // atau 'Pending' lalu update
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
            return false;
        }
    }
}
