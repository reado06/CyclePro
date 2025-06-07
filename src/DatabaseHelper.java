import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                + " address TEXT,"
                + " phone_number TEXT NOT NULL,"
                + " role TEXT DEFAULT 'user'" // <-- Pastikan tidak ada koma di akhir baris ini!
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
                + " va_number TEXT," // <-- Nullable di SQL biasanya tidak perlu "NULLABLE" secara eksplisit
                + " status TEXT DEFAULT 'Pending',"
                + " FOREIGN KEY (user_id) REFERENCES users (user_id),"
                + " FOREIGN KEY (product_id) REFERENCES products (product_id)"
                + ");";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(productsTable);
            stmt.execute(ordersTable);
            System.out.println("Tables created successfully (users table updated).");
            addSampleProducts();
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
            e.printStackTrace(); // Tambahkan ini agar stack trace muncul dan lebih informatif
        }
    }

    public static void addSampleProducts() {
        String checkProducts = "SELECT COUNT(*) AS count FROM products";
        String insertProductSQL = "INSERT INTO products(name, category, price, description, image_path, stock) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkProducts)) {

            if (rs.next() && rs.getInt("count") == 0) {
                System.out.println("Adding sample products...");
                try (PreparedStatement pstmt = conn.prepareStatement(insertProductSQL)) {
                    pstmt.setString(1, "BMX Pro X1"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 1500000); 
                    pstmt.setString(4, "Sepeda BMX untuk freestyle"); 
                    pstmt.setString(5, "bmx1.png"); 
                    pstmt.setInt(6, 10); 
                    pstmt.executeUpdate();
                    
                    pstmt.setString(1, "BMX Street Master"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 1200000); 
                    pstmt.setString(4, "Sepeda BMX lincah di jalanan"); 
                    pstmt.setString(5, "bmx2.png"); 
                    pstmt.setInt(6, 15); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "BMX Lite Fun"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 900000); 
                    pstmt.setString(4, "Sepeda BMX ringan untuk pemula"); 
                    pstmt.setString(5, "bmx3.png"); 
                    pstmt.setInt(6, 20); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "BMX Racer Z"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 1800000); 
                    pstmt.setString(4, "Sepeda BMX untuk kecepatan"); 
                    pstmt.setString(5, "bmx4.png"); 
                    pstmt.setInt(6, 8); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "BMX Park King"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 1600000); 
                    pstmt.setString(4, "Sepeda BMX untuk atraksi di taman"); 
                    pstmt.setString(5, "bmx5.png"); 
                    pstmt.setInt(6, 12); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "BMX Dirt Jumper"); 
                    pstmt.setString(2, "BMX"); 
                    pstmt.setDouble(3, 1700000); 
                    pstmt.setString(4, "Sepeda BMX untuk trek tanah"); 
                    pstmt.setString(5, "bmx6.png"); 
                    pstmt.setInt(6, 7); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung Everest Peak"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 3500000); 
                    pstmt.setString(4, "Sepeda gunung tangguh semua medan"); 
                    pstmt.setString(5, "gunung1.png"); 
                    pstmt.setInt(6, 5); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung TrailBlazer X"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 2800000); 
                    pstmt.setString(4, "Sepeda gunung untuk petualangan trail"); 
                    pstmt.setString(5, "gunung2.png"); 
                    pstmt.setInt(6, 8); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung RockHopper"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 2200000); 
                    pstmt.setString(4, "Sepeda gunung handal dan nyaman"); 
                    pstmt.setString(5, "gunung3.png"); 
                    pstmt.setInt(6, 12); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung Summit Seeker"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 4000000); 
                    pstmt.setString(4, "Sepeda gunung performa tinggi"); 
                    pstmt.setString(5, "gunung4.png"); 
                    pstmt.setInt(6, 4); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung Forest Rider"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 2500000); 
                    pstmt.setString(4, "Sepeda gunung untuk menjelajah hutan"); 
                    pstmt.setString(5, "gunung5.png"); 
                    pstmt.setInt(6, 10); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Gunung Canyon Carver"); 
                    pstmt.setString(2, "Gunung"); 
                    pstmt.setDouble(3, 3200000); 
                    pstmt.setString(4, "Sepeda gunung untuk medan curam"); 
                    pstmt.setString(5, "gunung6.png"); 
                    pstmt.setInt(6, 6); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat CityCommute"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 1800000); 
                    pstmt.setString(4, "Sepeda lipat praktis untuk perkotaan"); 
                    pstmt.setString(5, "lipat1.png"); 
                    pstmt.setInt(6, 15); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat UrbanFold"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 1500000); 
                    pstmt.setString(4, "Sepeda lipat ringkas dan stylish"); 
                    pstmt.setString(5, "lipat2.png"); 
                    pstmt.setInt(6, 18); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat QuickFold"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 2000000); 
                    pstmt.setString(4, "Sepeda lipat mudah dibawa"); 
                    pstmt.setString(5, "lipat3.png"); 
                    pstmt.setInt(6, 10); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat Metro Lite"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 1600000); 
                    pstmt.setString(4, "Sepeda lipat ringan dan efisien"); 
                    pstmt.setString(5, "lipat4.png"); 
                    pstmt.setInt(6, 20); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat TravelMate"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 2200000); 
                    pstmt.setString(4, "Sepeda lipat ideal untuk bepergian"); 
                    pstmt.setString(5, "lipat5.png"); 
                    pstmt.setInt(6, 9); 
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Lipat SwiftFold"); 
                    pstmt.setString(2, "Lipat"); 
                    pstmt.setDouble(3, 1900000); 
                    pstmt.setString(4, "Sepeda lipat cepat dan nyaman"); 
                    pstmt.setString(5, "lipat6.png"); 
                    pstmt.setInt(6, 11); 
                    pstmt.executeUpdate();
                    System.out.println("Sample products added.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding sample products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Admin authenticateAdmin(String username, String password) {
        String sql = "SELECT user_id, username, password, address, phone_number, role FROM users WHERE username = ? AND password = ? AND role = 'admin'";
        System.out.println("DEBUG: Mencoba otentikasi admin dengan username: " + username + " dan password: " + password); // Tambah ini
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("DEBUG: Data admin ditemukan di database!"); 
                System.out.println("DEBUG: Role yang ditemukan: " + rs.getString("role")); 
                return new Admin(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("phone_number")
                );
            } else {
                System.out.println("DEBUG: Tidak ada data admin ditemukan untuk username: " + username + " dengan role 'admin'."); // Tambah ini
            }
        } catch (SQLException e) {
            System.out.println("Authentication error for admin: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void addDefaultAdmin() {
        String sql = "INSERT INTO users(username, password, address, phone_number, role) VALUES(?,?,?,?,?)";
        String checkAdminSql = "SELECT COUNT(*) FROM users WHERE username = 'admin' AND role = 'admin'";
        System.out.println("DEBUG: Memeriksa apakah admin default sudah ada..."); // Tambah ini
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkAdminSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("DEBUG: Admin default belum ada, mencoba membuat..."); // Tambah ini
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, "admin");
                    pstmt.setString(2, "admin123");
                    pstmt.setString(3, "Admin Address");
                    pstmt.setString(4, "081122334455");
                    pstmt.setString(5, "admin");
                    int rowsAffected = pstmt.executeUpdate(); // Ambil jumlah baris yang terpengaruh
                    if (rowsAffected > 0) {
                        System.out.println("Default admin user 'admin' created with password 'admin123'. Rows affected: " + rowsAffected); // Perbarui ini
                    } else {
                        System.out.println("WARNING: Admin user 'admin' not created. Rows affected: " + rowsAffected); // Tambah ini
                    }
                }
            } else {
                System.out.println("DEBUG: Admin default sudah ada."); // Tambah ini
            }
        } catch (SQLException e) {
            System.out.println("Error adding default admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

   public static User authenticateUser(String username, String password) {
    // Tambahkan 'password' di SELECT agar bisa diambil oleh konstruktor User
    String sql = "SELECT user_id, username, password, address, phone_number FROM users WHERE username = ? AND password = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            // Ambil password dari ResultSet dan masukkan ke konstruktor User
            return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("address"), rs.getString("phone_number"));
        }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
        return null;
    }

    public static boolean registerUser(String username, String password, String address, String phoneNumber) {
        String sql = "INSERT INTO users(username, password, address, phone_number) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, address);
            pstmt.setString(4, phoneNumber);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public static List<Produk> getProductsByCategory(String category) {
        List<Produk> products = new ArrayList<>();
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
            pstmt.setString(8, "Diproses");
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
            return false;
        }
    }

    public static List<Pesanan> getAllOrders() {
        List<Pesanan> orders = new ArrayList<>();
        // SQL JOIN untuk mengambil data pesanan, user, dan produk sekaligus
        String sql = "SELECT o.order_id, o.user_id, o.product_id, o.order_date, " +
                    "o.shipping_address, o.courier, o.payment_method, o.total_price, o.va_number, o.status, " +
                    "u.username AS user_username, u.address AS user_address, u.phone_number AS user_phone_number, " +
                    "p.name AS product_name, p.category AS product_category, p.price AS product_price, p.description AS product_description, p.image_path AS product_image_path, p.stock AS product_stock " +
                    "FROM orders o " +
                    "JOIN users u ON o.user_id = u.user_id " +
                    "JOIN products p ON o.product_id = p.product_id";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql); // Gunakan PreparedStatement meski tanpa parameter agar lebih aman
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Buat objek User
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("user_username"),
                    "", // Password tidak perlu diambil dari DB untuk objek ini
                    rs.getString("user_address"),
                    rs.getString("user_phone_number")
                );

                // Buat objek Produk
                Produk product = new Produk(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_category"),
                    rs.getDouble("product_price"),
                    rs.getString("product_description"),
                    rs.getString("product_image_path"),
                    rs.getInt("product_stock")
                );

                // Buat objek Pesanan
                Pesanan order = new Pesanan(
                    rs.getInt("order_id"),
                    user, // Masukkan objek User
                    product, // Masukkan objek Produk
                    rs.getString("order_date"),
                    rs.getString("shipping_address"),
                    rs.getString("courier"),
                    rs.getString("payment_method"),
                    rs.getDouble("total_price"),
                    rs.getString("va_number"),
                    rs.getString("status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all orders: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Pesanan> getOrdersByUserId(int userId) {
    List<Pesanan> orders = new ArrayList<>();
    String sql = "SELECT o.order_id, o.user_id, o.product_id, o.order_date, " +
                 "o.shipping_address, o.courier, o.payment_method, o.total_price, o.va_number, o.status, " +
                 "u.username AS user_username, u.address AS user_address, u.phone_number AS user_phone_number, " +
                 "p.name AS product_name, p.category AS product_category, p.price AS product_price, p.description AS product_description, p.image_path AS product_image_path, p.stock AS product_stock " +
                 "FROM orders o " +
                 "JOIN users u ON o.user_id = u.user_id " +
                 "JOIN products p ON o.product_id = p.product_id " +
                 "WHERE o.user_id = ?"; // <-- Filter berdasarkan user_id

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, userId); // Set parameter userId
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("user_username"),
                    "", // Password tidak perlu diambil
                    rs.getString("user_address"),
                    rs.getString("user_phone_number")
                );

                Produk product = new Produk(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_category"),
                    rs.getDouble("product_price"),
                    rs.getString("product_description"),
                    rs.getString("product_image_path"),
                    rs.getInt("product_stock")
                );

                Pesanan order = new Pesanan(
                    rs.getInt("order_id"),
                    user,
                    product,
                    rs.getString("order_date"),
                    rs.getString("shipping_address"),
                    rs.getString("courier"),
                    rs.getString("payment_method"),
                    rs.getDouble("total_price"),
                    rs.getString("va_number"),
                    rs.getString("status")
                );
                orders.add(order);
            }
        }
        } catch (SQLException e) {
            System.out.println("Error fetching orders by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public static boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}