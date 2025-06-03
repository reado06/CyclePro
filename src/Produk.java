public class Produk {
    private int productId;
    private String name;
    private String category;
    private double price;
    private String description;
    private String imagePath; // path ke gambar
    private int stock;

    public Produk(int productId, String name, String category, double price, String description, String imagePath, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.stock = stock;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public int getStock() { return stock; }

    @Override
    public String toString() { // Berguna untuk debugging atau JComboBox jika diperlukan
        return name + " - Rp" + String.format("%,.0f", price);
    }
}
