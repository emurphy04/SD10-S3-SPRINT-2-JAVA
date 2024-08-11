import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    public static void main(String[] args) {

        ArrayList<Items> cart = new ArrayList<Items>();

        String role = "buyer";

        String chosenCat = "";

        int userIndex = 0;

        boolean isLogged = false;

        ItemDAO.getItems();

        ArrayList<Items> Products = ItemDAO.Products;

        UserDAO.getUsers();

        ArrayList<User> users = UserDAO.users;

        Scanner scanner = new Scanner(System.in);

        //LOGIN/REGISTER

        System.out.println("Please login or sign up.");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.println("");
        String choice = scanner.nextLine();


        while(isLogged == false){
            switch (choice) {
                case "2":
                    //sign up

                    System.out.println("Please enter a username: ");
                    String userEnteredUsername = scanner.nextLine();
                    System.out.println();

                    System.out.println("Please enter a password: ");
                    String userEnteredPassword = scanner.nextLine();
                    System.out.println();

                    String hashedPass = BCrypt.hashpw(userEnteredPassword, BCrypt.gensalt());

                    System.out.println("Please enter a email: ");
                    String userEnteredEmail = scanner.nextLine();
                    System.out.println();

                    System.out.println("What is your user role: ");
                    System.out.println("1. Buyer ");
                    System.out.println("2. Seller ");
                    System.out.println("3. Admin ");
                    String userEnteredRole = scanner.nextLine();
                    switch (userEnteredRole) {
                        case "1":
                            role = "buyer";
                            break;
                        case "2":
                            role = "seller";
                            break;
                        case "3":
                            role = "admin";
                            break;
                        default:
                            break;
                    }

                    for (int i = 0; i < users.size(); i++) {
                        if (userEnteredUsername.toLowerCase().equals(users.get(i).username.toLowerCase())) {
                            System.out.println("Username taken, please try again.");
                            choice = "1";
                            System.exit(0);
                        }
                    }

                    UserDAO.createUsers(userEnteredUsername, hashedPass, userEnteredEmail, role);
                    System.out.println("User created! Please login");
                    isLogged = true;
                    switch (role) {
                        case "admin":
                            users.add(new Admin(userEnteredUsername, userEnteredPassword, userEnteredEmail));
                            break;
                        case "buyer":
                            users.add(new Buyer(userEnteredUsername, userEnteredPassword, userEnteredEmail));
                            break;
                        case "seller":
                            users.add(new Seller(userEnteredUsername, userEnteredPassword, userEnteredEmail));
                            break;
                    
                        default:
                            break;
                    }
                    userIndex = users.size()-1;
                    break;
                    case "1":
                    //login
                    System.out.println("Enter your username: ");
                    String userUsername = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    String userPassword = scanner.nextLine();

                    for(int i = 0; i<users.size(); i++){
                        if (userUsername.equals(users.get(i).username)){
                            if (BCrypt.checkpw(userPassword, users.get(i).password)){
                                isLogged = true;
                                userIndex = i;
                            }
                        }
                    }

                    if (isLogged == false){
                        System.out.println("Username or password is incorrect.");
                        System.out.println();
                    }

                    break;
                default:
                    System.out.println("Invalid Choice, Please try again.");
                    break;
            }
        }

        //CLI
        boolean signout = false;
        while(signout == false){
            User user = users.get(userIndex);
            switch (users.get(userIndex).getRole()) {
                case "admin":
                    //make admin stuff now

                    System.out.println("Admin Interface: ");
                    System.out.println("================");
                    System.out.println();
                    System.out.println("1. List all Users");
                    System.out.println("2. Delete user");
                    System.out.println("3. List all products");
                    System.out.println("4. Sign Out");
                    System.out.println();
                    System.out.println("Please enter your choice: ");
                    String choice2 = scanner.nextLine();

                    switch (choice2) {
                        case "1":
                            System.out.println("---------------------------------------------------------------------------");
                            for(int i = 0; i<users.size(); i++){
                                System.out.println(users.get(i));
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            break;
                        case "2":
                            System.out.println("---------------------------------------------------------------------------");
                            for(int i = 0; i<users.size(); i++){
                                System.out.println(users.get(i));
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            System.out.println("Please enter the username of the user to be deleted: ");
                            String delUser = scanner.nextLine();
                            if(!(users.get(userIndex).username.equals(delUser))){
                                String currUser = users.get(userIndex).username;
                                for(int i = 0; i<users.size(); i++){
                                    if(users.get(i).username.equals(delUser)){
                                        System.out.println("Are you sure you want to delete this user? Y/N");
                                        String choice3 = scanner.nextLine().toUpperCase();
                                        if (choice3.equals("Y")) {
                                            users.remove(i);
                                            UserDAO.removeUser(delUser);
                                            userIndex = userIndex-1;
                                        } else {
                                            System.err.println("Delete cancelled.");
                                            break;
                                        }

                                        break;
                                    }
                                }
                            } else {
                                System.err.println("Cannot delete current user.");
                            }
                            break;
                        case "3":
                            System.out.println("---------------------------------------------------------------------------");
                            for(int i = 0; i<Products.size(); i++){
                                System.out.println(Products.get(i));
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            break;
                        case "4":
                            System.out.println("Exiting...");
                            signout = true;
                            break;
                    
                        default:
                            break;
                    }

                    break;

                case "buyer":
                    //made buyer stuff now

                    System.out.println("Customer Interface: ");
                    System.out.println("================");
                    System.out.println();
                    System.out.println("1. Browse all products");
                    System.out.println("2. Search for product");
                    System.out.println("3. Add sku to cart");
                    System.out.println("4. Checkout");
                    System.out.println("5. Sign Out");
                    System.out.println();
                    System.out.println("Please enter your choice: ");
                    choice2 = scanner.nextLine();

                    switch (choice2) {
                        case "1":
                            System.out.println("---------------------------------------------------------------------------");
                            for(int i = 0; i<Products.size(); i++){
                                System.out.println(Products.get(i));
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            break;
                        case "2":
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println("Search by:");
                            System.out.println("1. Name");
                            System.out.println("2. Category");
                            System.out.println("3. Seller");
                            String choice3 = scanner.nextLine();

                            switch (choice3) {
                                case "1":
                                    System.out.println();
                                    System.out.println("Search by name: ");
                                    String search = scanner.nextLine();
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).itemName.toLowerCase().indexOf(search.toLowerCase()) != -1){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                                case "2":
                                    System.out.println("");
                                    System.out.println("Which Category? ");
                                    System.out.println();
                                    System.out.println("1. GPUs");
                                    System.out.println("2. Memory");
                                    System.out.println("3. Storage");
                                    System.out.println("4. Motherboards");
                                    System.out.println("5. Cooling");
                                    System.out.println("6. PSUs");
                                    System.out.println("7. Monitors");
                                    System.out.println("8. Networking");
                                    System.out.println("9. Laptops");
                                    System.out.println("10. Peripherals");
                                    System.out.println("11. Cases");
                                    System.out.println("12. Accessories");
                                    System.out.println();
                                    String choice5 = scanner.nextLine();
                                    switch (choice5) {
                                        case "1":
                                            chosenCat = "GPUs";
                                            break;
                                        case "2":
                                            chosenCat = "Memory";
                                            break;
                                        case "3":
                                            chosenCat = "Storage";
                                            break;
                                        case "4":
                                            chosenCat = "Motherboards";
                                            break;
                                        case "5":
                                            chosenCat = "Cooling";
                                            break;
                                        case "6":
                                            chosenCat = "PSUs";
                                            break;
                                        case "7":
                                            chosenCat = "Monitors";
                                            break;
                                        case "8":
                                            chosenCat = "Networking";
                                            break;
                                        case "9":
                                            chosenCat = "Laptops";
                                            break;
                                        case "10":
                                            chosenCat = "Peripherals";
                                            break;
                                        case "11":
                                            chosenCat = "Cases";
                                            break;
                                        case "12":
                                            chosenCat = "Accessories";
                                            break;
                                        default:
                                            System.err.println("Invalid Choice... Please try again.");
                                            break;
                                    }
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).itemCat.equals(chosenCat)){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                                case "3":
                                    System.out.println();
                                    System.out.println("Search by seller: ");
                                    search = scanner.nextLine();
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).userListed.toLowerCase().indexOf(search.toLowerCase()) != -1){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                            
                                default:
                                    break;
                            }
                            break;
                        case "3":
                            boolean cartLoop = true;
                            while(cartLoop){
                                System.out.println();
                                System.out.println("Enter the sku you would like to add to your cart:");
                                System.out.println();
                                String sku = scanner.nextLine();
                                for (int i = 0; i < Products.size(); i++) {
                                    if(Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())){
                                        user.addToCart(Products.get(i));
                                        System.out.println("Product added");
                                    }
                                }
                                System.out.println();
                                System.out.println("Would you like to add another item? Y/N");
                                String choice4 = scanner.nextLine().toLowerCase();
                                if (choice4.equals("n")){
                                    cartLoop = false;
                                }
                            }
                                break;
                        case "4":
                            double price = 0;
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println(user.username+"'s cart:");
                            System.out.println();
                            System.out.println("---------------------------------------------------------------------------");
                            for (int i = 0; i < user.cart.size(); i++) {
                                System.out.println(user.cart.get(i));
                                price = price+user.cart.get(i).itemPrice;
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            System.out.println("Total Cost: $"+price);
                            break;
                        case "5":
                            System.out.println("Exiting...");
                            signout = true;
                            break;

                        default:
                            break;
                    }

                    break;

                case "seller":
                    System.out.println("seller");
                    //make seller stuff now

                    System.out.println("Seller Interface: ");
                    System.out.println("================");
                    System.out.println();
                    System.out.println("1. Browse all products");
                    System.out.println("2. Search for product");
                    System.out.println("3. Add sku to cart");
                    System.out.println("4. Checkout");
                    System.out.println("5. Add listing");
                    System.out.println("6. Update listing");
                    System.out.println("7. View own listings");
                    System.out.println("8. Delete Listing");
                    System.out.println("9. Sign Out");
                    System.out.println();
                    System.out.println("Please enter your choice: ");
                    choice2 = scanner.nextLine();

                    switch (choice2) {
                        case "1":
                            System.out.println("---------------------------------------------------------------------------");
                            for(int i = 0; i<Products.size(); i++){
                                System.out.println(Products.get(i));
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            break;
                        case "2":
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println("Search by:");
                            System.out.println("1. Name");
                            System.out.println("2. Category");
                            System.out.println("3. Seller");
                            String choice3 = scanner.nextLine();

                            switch (choice3) {
                                case "1":
                                    System.out.println();
                                    System.out.println("Search by name: ");
                                    String search = scanner.nextLine();
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).itemName.toLowerCase().indexOf(search.toLowerCase()) != -1){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                                case "2":
                                    System.out.println("");
                                    System.out.println("Which Category? ");
                                    System.out.println();
                                    System.out.println("1. GPUs");
                                    System.out.println("2. Memory");
                                    System.out.println("3. Storage");
                                    System.out.println("4. Motherboards");
                                    System.out.println("5. Cooling");
                                    System.out.println("6. PSUs");
                                    System.out.println("7. Monitors");
                                    System.out.println("8. Networking");
                                    System.out.println("9. Laptops");
                                    System.out.println("10. Peripherals");
                                    System.out.println("11. Cases");
                                    System.out.println("12. Accessories");
                                    System.out.println();
                                    String choice5 = scanner.nextLine();
                                    switch (choice5) {
                                        case "1":
                                            chosenCat = "GPUs";
                                            break;
                                        case "2":
                                            chosenCat = "Memory";
                                            break;
                                        case "3":
                                            chosenCat = "Storage";
                                            break;
                                        case "4":
                                            chosenCat = "Motherboards";
                                            break;
                                        case "5":
                                            chosenCat = "Cooling";
                                            break;
                                        case "6":
                                            chosenCat = "PSUs";
                                            break;
                                        case "7":
                                            chosenCat = "Monitors";
                                            break;
                                        case "8":
                                            chosenCat = "Networking";
                                            break;
                                        case "9":
                                            chosenCat = "Laptops";
                                            break;
                                        case "10":
                                            chosenCat = "Peripherals";
                                            break;
                                        case "11":
                                            chosenCat = "Cases";
                                            break;
                                        case "12":
                                            chosenCat = "Accessories";
                                            break;
                                        default:
                                            System.err.println("Invalid Choice... Please try again.");
                                            break;
                                    }
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).itemCat.equals(chosenCat)){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                                case "3":
                                    System.out.println();
                                    System.out.println("Search by seller: ");
                                    search = scanner.nextLine();
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).userListed.toLowerCase().indexOf(search.toLowerCase()) != -1){
                                            System.out.println(Products.get(i));
                                            System.out.println("---------------------------------------------------------------------------");
                                        }
                                    }
                                    break;
                            
                                default:
                                    break;
                            }
                            break;
                        case "3":
                            boolean cartLoop = true;
                            while(cartLoop){
                                System.out.println();
                                System.out.println("Enter the sku you would like to add to your cart:");
                                System.out.println();
                                String sku = scanner.nextLine();
                                for (int i = 0; i < Products.size(); i++) {
                                    if(Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())){
                                        user.addToCart(Products.get(i));
                                        System.out.println("Product added");
                                    }
                                }
                                System.out.println();
                                System.out.println("Would you like to add another item? Y/N");
                                String choice4 = scanner.nextLine().toLowerCase();
                                if (choice4.equals("n")){
                                    cartLoop = false;
                                }
                            }
                                break;
                        case "4":
                            double price = 0;
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println(user.username+"'s cart:");
                            System.out.println();
                            System.out.println("---------------------------------------------------------------------------");
                            for (int i = 0; i < user.cart.size(); i++) {
                                System.out.println(user.cart.get(i));
                                price = price+user.cart.get(i).itemPrice;
                                System.out.println("---------------------------------------------------------------------------");
                            }
                            System.out.println("Total Cost: $"+price);
                            break;

                        case "5":
                            //add listing
                            System.out.println("Add a listing:");
                            System.out.println("---------------------------------------------------------------------------");
                            System.out.println();
                            System.out.println("Item Name: ");
                            String userEnteredItemName = scanner.nextLine();
                            System.out.println("Item Price: ");
                            double userEnteredItemPrice = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Item Description: ");
                            String userEnteredItemDesc = scanner.nextLine();
                            System.out.println("Choose an item category: ");
                            System.out.println();
                            System.out.println("1. GPUs");
                            System.out.println("2. Memory");
                            System.out.println("3. Storage");
                            System.out.println("4. Motherboards");
                            System.out.println("5. Cooling");
                            System.out.println("6. PSUs");
                            System.out.println("7. Monitors");
                            System.out.println("8. Networking");
                            System.out.println("9. Laptops");
                            System.out.println("10. Peripherals");
                            System.out.println("11. Cases");
                            System.out.println("12. Accessories");
                            System.out.println();
                            String userEnteredItemCat = scanner.nextLine();
                                    switch (userEnteredItemCat) {
                                        case "1":
                                            chosenCat = "GPUs";
                                            break;
                                        case "2":
                                            chosenCat = "Memory";
                                            break;
                                        case "3":
                                            chosenCat = "Storage";
                                            break;
                                        case "4":
                                            chosenCat = "Motherboards";
                                            break;
                                        case "5":
                                            chosenCat = "Cooling";
                                            break;
                                        case "6":
                                            chosenCat = "PSUs";
                                            break;
                                        case "7":
                                            chosenCat = "Monitors";
                                            break;
                                        case "8":
                                            chosenCat = "Networking";
                                            break;
                                        case "9":
                                            chosenCat = "Laptops";
                                            break;
                                        case "10":
                                            chosenCat = "Peripherals";
                                            break;
                                        case "11":
                                            chosenCat = "Cases";
                                            break;
                                        case "12":
                                            chosenCat = "Accessories";
                                            break;
                                        default:
                                            System.err.println("Invalid Choice... Please try again.");
                                            break;
                                    }
                            System.out.println();
                            System.out.println("Confirm? Y/N");
                            String confirm = scanner.nextLine();
                            if(confirm.toLowerCase().equals("n")){
                                System.out.println("Item add cancelled.");
                                break;
                            }

                            ItemDAO.addItem(userEnteredItemName, userEnteredItemPrice, userEnteredItemDesc, chosenCat, user.username);
                            break;

                        case "6":
                            System.out.println("What attribute did you want to edit?");
                            System.out.println("1. Name");
                            System.out.println("2. Price");
                            System.out.println("3. Description");
                            System.out.println("4. Category");
                            String choice9 = scanner.nextLine();
                            switch (choice9) {
                                case "1":
                                    System.out.println("Enter the SKU of the listing you'd like to edit: ");
                                    String sku = scanner.nextLine();
                                    System.out.println("Enter the new name of the listing: ");
                                    String newname = scanner.nextLine();
                                    for (int i = 0; i < Products.size(); i++) {
                                        if (Products.get(i).userListed.equals(user.username)) {
                                            if (Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())) {
                                                ItemDAO.updateName(newname, sku, i);
                                                System.out.println("Item Edited.");
                                            }
                                        }
                                    }
                                    break;

                                case "2":
                                    System.out.println("Enter the SKU of the listing you'd like to edit: ");
                                    sku = scanner.nextLine();
                                    System.out.println("Enter the new price of the listing: ");
                                    double newprice = scanner.nextDouble();
                                    scanner.nextLine();
                                    for (int i = 0; i < Products.size(); i++) {
                                        if (Products.get(i).userListed.equals(user.username)) {
                                            if (Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())) {
                                                ItemDAO.updatePrice(newprice, sku, i);
                                                System.out.println("Item Edited.");
                                            }
                                        }
                                    }
                                    break;

                                case "3":
                                    System.out.println("Enter the SKU of the listing you'd like to edit: ");
                                    sku = scanner.nextLine();
                                    System.out.println("Enter the new description of the listing: ");
                                    String newdesc = scanner.nextLine();
                                    for (int i = 0; i < Products.size(); i++) {
                                        if (Products.get(i).userListed.equals(user.username)) {
                                            if (Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())) {
                                                ItemDAO.updateDesc(newdesc, sku, i);
                                                System.out.println("Item Edited.");
                                            }
                                        }
                                    }
                                    break;

                                case "4":
                                    System.out.println("Enter the SKU of the listing you'd like to edit: ");
                                    sku = scanner.nextLine();
                                    System.out.println("Enter the new category of the listing: ");
                                    System.out.println();
                                    System.out.println("1. GPUs");
                                    System.out.println("2. Memory");
                                    System.out.println("3. Storage");
                                    System.out.println("4. Motherboards");
                                    System.out.println("5. Cooling");
                                    System.out.println("6. PSUs");
                                    System.out.println("7. Monitors");
                                    System.out.println("8. Networking");
                                    System.out.println("9. Laptops");
                                    System.out.println("10. Peripherals");
                                    System.out.println("11. Cases");
                                    System.out.println("12. Accessories");
                                    System.out.println();
                                    userEnteredItemCat = scanner.nextLine();
                                            switch (userEnteredItemCat) {
                                                case "1":
                                                    chosenCat = "GPUs";
                                                    break;
                                                case "2":
                                                    chosenCat = "Memory";
                                                    break;
                                                case "3":
                                                    chosenCat = "Storage";
                                                    break;
                                                case "4":
                                                    chosenCat = "Motherboards";
                                                    break;
                                                case "5":
                                                    chosenCat = "Cooling";
                                                    break;
                                                case "6":
                                                    chosenCat = "PSUs";
                                                    break;
                                                case "7":
                                                    chosenCat = "Monitors";
                                                    break;
                                                case "8":
                                                    chosenCat = "Networking";
                                                    break;
                                                case "9":
                                                    chosenCat = "Laptops";
                                                    break;
                                                case "10":
                                                    chosenCat = "Peripherals";
                                                    break;
                                                case "11":
                                                    chosenCat = "Cases";
                                                    break;
                                                case "12":
                                                    chosenCat = "Accessories";
                                                    break;
                                                default:
                                                    System.err.println("Invalid Choice... Please try again.");
                                                    break;
                                            }
                                    System.out.println();
                                    for (int i = 0; i < Products.size(); i++) {
                                        if (Products.get(i).userListed.equals(user.username)) {
                                            if (Products.get(i).itemSku.toLowerCase().equals(sku.toLowerCase())) {
                                                ItemDAO.updateCat(chosenCat, sku, i);
                                                System.out.println("Item Edited.");
                                            }
                                        }
                                    }
                                    break;
                            
                                default:
                                    break;
                            }
                            
                            break;

                        case "7":
                            //view listings
                            System.out.println();
                            System.out.println(user.username+"'s listings: ");
                            System.out.println("---------------------------------------------------------------------------");
                            for (int i = 0; i < Products.size(); i++) {
                                if (Products.get(i).userListed.equals(user.username)) {
                                    System.out.println(Products.get(i));
                                    System.out.println("---------------------------------------------------------------------------");
                                }
                            }
                            break;

                        case "8":
                            //delete listing
                            System.out.println();
                            System.out.println(user.username+"'s listings: ");
                            System.out.println("---------------------------------------------------------------------------");
                            for (int i = 0; i < Products.size(); i++) {
                                if (Products.get(i).userListed.equals(user.username)) {
                                    System.out.println(Products.get(i));
                                    System.out.println("---------------------------------------------------------------------------");
                                }
                            }
                            System.out.println("Enter the SKU of the listing you'd like to delete: ");
                            String delSku = scanner.nextLine();
                            for (int i = 0; i < Products.size(); i++) {
                                if (Products.get(i).userListed.equals(user.username)) {
                                    if (Products.get(i).itemSku.toLowerCase().equals(delSku.toLowerCase())) {
                                        ItemDAO.removeItem(delSku);
                                        Products.remove(i);
                                        System.out.println("Item Removed.");
                                    }
                                }
                            }
                            break;

                        case "9":
                            System.out.println("Exiting...");
                            signout = true;
                            break;

                        default:
                            break;
                    }
                    break;
            
                default:
                    break;
            }
        }
    }
}
