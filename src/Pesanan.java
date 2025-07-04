public class Pesanan {
    private int orderId;
    private int userId;
    private int productId;
    private String orderDate;
    private String shippingAddress;
    private String courier;
    private String paymentMethod;
    private double totalPrice;
    private String vaNumber;
    private String status;
    private User user; 
    private Produk product; 

    // Konstruktor utama
    public Pesanan(int orderId, int userId, int productId, String orderDate,
                   String shippingAddress, String courier, String paymentMethod,
                   double totalPrice, String vaNumber, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.courier = courier;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.vaNumber = vaNumber;
        this.status = status;
    }

    public Pesanan(int orderId, User user, Produk product, String orderDate,
                   String shippingAddress, String courier, String paymentMethod,
                   double totalPrice, String vaNumber, String status) {
        this.orderId = orderId;
        this.user = user;
        this.product = product;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.courier = courier;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.vaNumber = vaNumber;
        this.status = status;
        this.userId = (user != null) ? user.getUserId() : 0;
        this.productId = (product != null) ? product.getProductId() : 0;
    }

    // --- Getters ---
    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public String getOrderDate() { return orderDate; }
    public String getShippingAddress() { return shippingAddress; }
    public String getCourier() { return courier; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTotalPrice() { return totalPrice; }
    public String getVaNumber() { return vaNumber; }
    public String getStatus() { return status; }
    public User getUser() { return user; }
    public Produk getProduct() { return product; }

    // --- Setters ---
    public void setStatus(String status) { this.status = status; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setCourier(String courier) { this.courier = courier; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setVaNumber(String vaNumber) { this.vaNumber = vaNumber; }


    @Override
    public String toString() {
        return "Pesanan [orderId=" + orderId + ", userId=" + userId + ", productId=" + productId
                + ", orderDate=" + orderDate + ", status=" + status + ", totalPrice=" + totalPrice + "]";
    }
}
