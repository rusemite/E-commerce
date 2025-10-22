package cc.ku.ict.module3.ECommerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        // Seed sample catalog (prices in pennies)
        products.add(new Book(1, "The Hobbit", 1299, 12));
        products.add(new Book(2, "1984", 999, 8));
        products.add(new Electronics(3, "Smartphone X", 59900, 5));
        products.add(new Electronics(4, "Laptop Pro 14", 129900, 3));
        products.add(new Clothing(5, "Classic T-Shirt", 1999, 25));
        products.add(new Clothing(6, "Blue Jeans", 3999, 15));

        List<User> users = new ArrayList<>(); // in-memory users
        List<Order> orders = new ArrayList<>(); // in-memory storage of orders

        Scanner sc = new Scanner(System.in);
        String answer = "";

        while (true) {
            System.out.println("\n--- Welcome to my Store! ---");
            System.out.println("[ PRESS x TO EXIT ]\n");
            System.out.println("--- MENU ---");
            System.out.println("Enter v to view products.\nEnter n to add a new product.\nEnter o to place an order.\nEnter m to view all orders.\nEnter u to list users.\nEnter a to add a user.\nEnter x to exit.\n");

            answer = sc.nextLine();
            switch (answer) {
                case "x" -> {
                    return;
                }
                case "v" -> {
                    products.forEach(p -> System.out.println(p.displayInfo()));
                }
                case "n" -> {
                    try {
                        System.out.print("Enter product id: ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter product name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Enter price in pennies (e.g., 1999): ");
                        int price = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter stock quantity: ");
                        int stock = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter category (Book/Electronics/Clothing): ");
                        String cat = sc.nextLine().trim();
                        Product toAdd;
                        if (cat.equalsIgnoreCase("Book")) {
                            toAdd = new Book(id, name, price, stock);
                        } else if (cat.equalsIgnoreCase("Electronics")) {
                            toAdd = new Electronics(id, name, price, stock);
                        } else if (cat.equalsIgnoreCase("Clothing")) {
                            toAdd = new Clothing(id, name, price, stock);
                        } else {

                            toAdd = new Product(id, name, price, stock, "Undefined");
                        }
                        products.add(toAdd);
                        System.out.println("Product added: " + toAdd.displayInfo());
                    } catch (Exception ex) {
                        System.out.println("Failed to add product. Please check your inputs.");
                    }
                }
                case "o" -> {

                    List<Product> cart = new ArrayList<>();
                    while (true) {
                        System.out.println("\n--- START ORDER ---");
                        System.out.println("Enter product id to add, 'c' to checkout, or 'b' to cancel order.");
                        products.forEach(p -> System.out.println("ID: " + p.getId() + " | " + p.getName() + " | Price: " + p.getPrice().format() + " | In stock: " + p.getStockQuantity()));
                        String in = sc.nextLine();
                        if (in.equals("b")) {
                            System.out.println("Order canceled. Returning to main menu.\n");
                            break;
                        }
                        if (in.equals("c")) {
                            if (cart.isEmpty()) {
                                System.out.println("Your cart is empty. Add items before checkout.\n");
                                continue;
                            }
                            int totalPennies = cart.stream().mapToInt(p -> p.getPrice().getValue()).sum();
                            System.out.println("Cart total: " + new Money(totalPennies).format());
                            System.out.print("Please enter your name to place the order: ");
                            String buyerName = sc.nextLine().trim();
                            if (buyerName.isEmpty()) {
                                buyerName = "Guest";
                            }
                            System.out.print("Are you an employee? (y/n): ");
                            String empAns = sc.nextLine().trim();
                            boolean isEmployee = empAns.equalsIgnoreCase("y") || empAns.equalsIgnoreCase("yes");
                            int finalTotalPennies = isEmployee ? (totalPennies / 2) : totalPennies;
                            // Create a temporary customer with provided name
                            Customer tempCustomer = new Customer(orders.size() + 1, buyerName, "guest@local", "guest", 0);
                            // Decrement stock for each product in cart
                            for (Product p : cart) {
                                p.setStockQuantity(p.getStockQuantity() - 1);
                            }
                            Order order = new Order(orders.size() + 1, tempCustomer, new ArrayList<>(cart), finalTotalPennies);
                            orders.add(order);
                            System.out.println("\nOrder placed successfully!\n");
                            order.printReceipt();
                            if (isEmployee) {
                                System.out.println("Note: Employee discount 50% applied. Original total: "
                                        + new Money(totalPennies).format() + ", Discounted total: " + new Money(finalTotalPennies).format());
                            }
                            System.out.println();
                            break; // back to main menu after placing order
                        }
                        // Otherwise, treat input as product id
                        try {
                            int pid = Integer.parseInt(in);
                            Optional<Product> productOpt = products.stream().filter(p -> p.getId() == pid).findFirst();
                            if (productOpt.isEmpty()) {
                                System.out.println("No product with ID " + pid + ".");
                                continue;
                            }
                            Product prod = productOpt.get();
                            if (prod.getStockQuantity() <= 0) {
                                System.out.println("Sorry, '" + prod.getName() + "' is out of stock.");
                                continue;
                            }
                            System.out.print("Quantity to add (default 1): ");
                            String qStr = sc.nextLine().trim();
                            int qty = 1;
                            if (!qStr.isEmpty()) {
                                try { qty = Integer.parseInt(qStr); } catch (NumberFormatException e) { qty = 1; }
                            }
                            if (qty <= 0) {
                                System.out.println("Quantity must be positive.");
                                continue;
                            }
                            if (qty > prod.getStockQuantity()) {
                                System.out.println("Only " + prod.getStockQuantity() + " in stock.");
                                continue;
                            }
                            for (int i = 0; i < qty; i++) {
                                cart.add(prod);
                            }
                            System.out.println(qty + " x '" + prod.getName() + "' added to cart.");
                            int cartTotal = cart.stream().mapToInt(p -> p.getPrice().getValue()).sum();
                            System.out.println("Current cart total: " + new Money(cartTotal).format());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter a product ID, 'c' to checkout, or 'b' to cancel.");
                        }
                    }
                }
                case "m" -> {
                    if (orders.isEmpty()) {
                        System.out.println("No orders have been placed yet.\n");
                    } else {
                        System.out.println("\n--- ALL ORDERS ---");
                        for (Order o : orders) {
                            o.printReceipt();
                            System.out.println("--------------------");
                        }
                        System.out.println();
                    }
                }
                case "u" -> {
                    if (users.isEmpty()) {
                        System.out.println("No users in the system yet.\n");
                    } else {
                        System.out.println("\n--- USERS ---");
                        for (User u : users) {
                            System.out.println(u);
                            System.out.println("--------------------");
                        }
                    }
                }
                case "a" -> {
                    try {
                        System.out.print("Add user type (Customer/Employee): ");
                        String type = sc.nextLine().trim();
                        System.out.print("Enter id: ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine().trim();
                        System.out.print("Enter password: ");
                        String password = sc.nextLine().trim();
                        if (type.equalsIgnoreCase("Customer")) {
                            System.out.print("Enter initial balance in pennies: ");
                            int bal = Integer.parseInt(sc.nextLine().trim());
                            users.add(new Customer(id, name, email, password, bal));
                        } else if (type.equalsIgnoreCase("Employee")) {
                            users.add(new Employee(id, name, email, password));
                        } else {
                            System.out.println("Unknown user type.");
                            break;
                        }
                        System.out.println("User added successfully. Total users: " + users.size());
                    } catch (Exception ex) {
                        System.out.println("Failed to add user. Please check your inputs.");
                    }
                }
                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}