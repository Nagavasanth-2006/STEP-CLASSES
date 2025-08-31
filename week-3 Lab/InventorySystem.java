class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String supplierName;
    private String category;

    private static int totalProducts = 0;
    private static double totalInventoryValue = 0;
    private static int lowStockCount = 0;
    private static String[] categories = new String[100]; // track unique categories
    private static int categoryCount = 0;

    public Product(String productId, String productName, double price, int quantity, String supplierName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.category = category;
        totalProducts++;
        addCategory(category);
    }

    private static void addCategory(String category) {
        for (int i = 0; i < categoryCount; i++) {
            if (categories[i].equalsIgnoreCase(category)) {
                return; // category already added
            }
        }
        categories[categoryCount++] = category;
    }

    public void addStock(int quantity) {
        if (quantity > 0) {
            this.quantity += quantity;
        }
    }

    public void reduceStock(int quantity) {
        if (quantity > 0 && quantity <= this.quantity) {
            this.quantity -= quantity;
        }
    }

    public boolean isLowStock() {
        return this.quantity < 10;
    }

    public double calculateProductValue() {
        return price * quantity;
    }

    public void updatePrice(double newPrice) {
        if (newPrice > 0) {
            this.price = newPrice;
        }
    }

    public void displayProductInfo() {
        System.out.printf("ID: %s | Name: %s | Price: %.2f | Qty: %d | Supplier: %s | Category: %s%n",
            productId, productName, price, quantity, supplierName, category);
    }

    public static double calculateTotalInventoryValue(Product[] products) {
        double total = 0;
        for (Product p : products) {
            if (p != null) {
                total += p.calculateProductValue();
            }
        }
        totalInventoryValue = total;
        return total;
    }

    public static void findLowStockProducts(Product[] products) {
        lowStockCount = 0;
        System.out.println("Low Stock Products (Qty < 10):");
        for (Product p : products) {
            if (p != null && p.isLowStock()) {
                p.displayProductInfo();
                lowStockCount++;
            }
        }
        if (lowStockCount == 0) {
            System.out.println("None");
        }
    }

    public static void generateInventoryReport(Product[] products) {
        System.out.println("=== Inventory Report ===");
        System.out.println("Total Products: " + totalProducts);
        System.out.println("Categories:");
        for (int i = 0; i < categoryCount; i++) {
            System.out.println("- " + categories[i]);
        }
        double totalValue = calculateTotalInventoryValue(products);
        System.out.printf("Total Inventory Value: %.2f%n", totalValue);
        findLowStockProducts(products);
        System.out.println("========================");
    }

    public static Product searchProduct(Product[] products, String productName) {
        for (Product p : products) {
            if (p != null && p.productName.equalsIgnoreCase(productName)) {
                return p;
            }
        }
        return null;
    }
}

public class InventorySystem {
    public static void main(String[] args) {
        Product[] products = new Product[5];

        products[0] = new Product("P001", "Laptop", 800, 15, "SupplierA", "Electronics");
        products[1] = new Product("P002", "Smartphone", 500, 8, "SupplierB", "Electronics");
        products[2] = new Product("P003", "Desk Chair", 120, 12, "SupplierC", "Furniture");
        products[3] = new Product("P004", "Notebook", 3, 50, "SupplierD", "Stationery");
        products[4] = new Product("P005", "Pen", 1, 5, "SupplierD", "Stationery");

        for (Product p : products) {
            p.displayProductInfo();
        }

        System.out.println("\nAdding stock to Pen (P005)...");
        Product pen = Product.searchProduct(products, "Pen");
        if (pen != null) {
            pen.addStock(20);
            pen.displayProductInfo();
        }

        System.out.println("\nReducing stock of Smartphone (P002)...");
        Product smartphone = Product.searchProduct(products, "Smartphone");
        if (smartphone != null) {
            smartphone.reduceStock(5);
            smartphone.displayProductInfo();
        }

        System.out.println("\nUpdating price of Desk Chair...");
        Product chair = Product.searchProduct(products, "Desk Chair");
        if (chair != null) {
            chair.updatePrice(130);
            chair.displayProductInfo();
        }

        System.out.println();
        Product.generateInventoryReport(products);
    }
}
