import java.util.*;

class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        }
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        return "Product[Name=" + name + ", Price=" + price + ", Stock=" + stock + "]";
    }
}

class ShoppingCart {
    private Map<Product, Integer> cart;

    public ShoppingCart() {
        this.cart = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            cart.put(product, cart.getOrDefault(product, 0) + quantity);
            product.reduceStock(quantity);
            System.out.println(quantity + " x " + product.getName() + " added to the cart.");
        } else {
            System.out.println("Insufficient stock for product: " + product.getName());
        }
    }

    public void removeProduct(Product product, int quantity) {
        if (cart.containsKey(product)) {
            int currentQuantity = cart.get(product);
            if (quantity >= currentQuantity) {
                cart.remove(product);
                product.increaseStock(currentQuantity);
                System.out.println(product.getName() + " removed from the cart.");
            } else {
                cart.put(product, currentQuantity - quantity);
                product.increaseStock(quantity);
                System.out.println(quantity + " x " + product.getName() + " removed from the cart.");
            }
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\n--- Shopping Cart ---");
            double total = 0;
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double cost = product.getPrice() * quantity;
                total += cost;
                System.out.println(quantity + " x " + product.getName() + " @ " + product.getPrice() + " each = " + cost);
            }
            System.out.println("Total: " + total);
        }
    }

    public void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add items before checking out.");
        } else {
            System.out.println("\nChecking out...");
            viewCart();
            System.out.println("Payment simulated successfully. Thank you for your purchase!");
            cart.clear();
        }
    }
}

public class OnlineShoppingCart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 75000, 10));
        products.add(new Product("Smartphone", 25000, 20));
        products.add(new Product("Headphones", 2000, 50));
        products.add(new Product("Keyboard", 1500, 30));

        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("\n--- Online Shopping Cart ---");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- Available Products ---");
                    for (int i = 0; i < products.size(); i++) {
                        System.out.println((i + 1) + ". " + products.get(i));
                    }
                    break;
                case 2:
                    System.out.print("Enter product number to add to cart: ");
                    int productIndex = scanner.nextInt() - 1;
                    if (productIndex >= 0 && productIndex < products.size()) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        cart.addProduct(products.get(productIndex), quantity);
                    } else {
                        System.out.println("Invalid product selection.");
                    }
                    break;
                case 3:
                    System.out.print("Enter product number to remove from cart: ");
                    productIndex = scanner.nextInt() - 1;
                    if (productIndex >= 0 && productIndex < products.size()) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        cart.removeProduct(products.get(productIndex), quantity);
                    } else {
                        System.out.println("Invalid product selection.");
                    }
                    break;
                case 4:
                    cart.viewCart();
                    break;
                case 5:
                    cart.checkout();
                    break;
                case 6:
                    System.out.println("Exiting Online Shopping Cart. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
