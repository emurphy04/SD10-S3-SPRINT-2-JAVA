import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

    public static void main(String[] args) {

        ArrayList<Items> cart = new ArrayList<Items>();

        String role = "buyer";

        int userIndex = 0;

        boolean isLogged = false;

        ArrayList<Items> Products = new ArrayList<Items>();

        ArrayList<User> users = new ArrayList<User>();

        Scanner scanner = new Scanner(System.in);

        //JDBC code

        try {
            Class.forName("org.postgresql.Driver");

            String sql = "select * from products";
            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                String itemName = rs.getString(1);
                String itemSku = rs.getString(2);
                double itemPrice = rs.getDouble(3);
                String itemDesc = rs.getString(4);
                String itemCat = rs.getString(5);
                String userListed = rs.getString(6);

                Products.add(new Items(itemName, itemSku, itemPrice, itemDesc, itemCat, userListed));
            }

            System.out.println(Products.get(1));

        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }

        //LOGIN/REGISTER

        System.out.println("Please login or sign up.");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.println("");
        String choice = scanner.nextLine();


        while(isLogged == false){
            switch (choice) {
                case "1":
                    //login
                    try {
                        Class.forName("org.postgresql.Driver");
            
                        String sql = "select * from users";
                        String url = "jdbc:postgresql://localhost:5432/Java";
                        String username = "postgres";
                        String password = "admin";
            
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery(sql);
            
                        while (rs.next()){
                            String user = rs.getString(1);
                            String pass = rs.getString(2);
                            String email = rs.getString(3);
                            String userrole = rs.getString(4);

                            switch (userrole) {
                                case "admin" -> users.add(new Admin(user, pass, email));
                                case "buyer" -> users.add(new Buyer(user, pass, email));
                                case "seller" -> users.add(new Seller(user, pass, email));
                            }
                        }
            
                    } catch (Exception e) {
                        System.out.println("Unable to establish a connection - "+e);
                    }

                    System.out.println("Enter your username: ");
                    String userUsername = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    String userPassword = scanner.nextLine();

                    for(int i = 0; i<users.size(); i++){
                        if (userUsername.equals(users.get(i).username)){
                            if (userPassword.equals(users.get(i).password)){
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

                case "2":
                    //sign up

                    System.out.println("Please enter a username: ");
                    String userEnteredUsername = scanner.nextLine();
                    System.out.println();

                    System.out.println("Please enter a password: ");
                    String userEnteredPassword = scanner.nextLine();
                    System.out.println();

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

                    try {
                        Class.forName("org.postgresql.Driver");
            
                        String url = "jdbc:postgresql://localhost:5432/Java";
                        String username = "postgres";
                        String password = "admin";
            
                        Connection connection = DriverManager.getConnection(url, username, password);

                        PreparedStatement st = connection.prepareStatement("INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)");
                        st.setString(1, userEnteredUsername);
                        st.setString(2, userEnteredPassword);
                        st.setString(3, userEnteredEmail);
                        st.setString(4, role);
                        st.executeUpdate();
                        st.close();
            
                    } catch (Exception e) {
                        System.out.println("Unable to establish a connection - "+e);
                    }
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
                                for(int i = 0; i<users.size(); i++){
                                    if(users.get(i).username.equals(delUser)){
                                        System.out.println("Are you sure you want to delete this user? Y/N");
                                        String choice3 = scanner.nextLine().toUpperCase();
                                        if (choice3.equals("Y")) {
                                            users.remove(i);
                                            try {
                                                Class.forName("org.postgresql.Driver");
                                    
                                                String url = "jdbc:postgresql://localhost:5432/Java";
                                                String username = "postgres";
                                                String password = "admin";
                                    
                                                Connection connection = DriverManager.getConnection(url, username, password);
                        
                                                PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE username=?");
                                                st.setString(1, delUser);
                                                st.executeUpdate();
                                                st.close();
                                    
                                            } catch (Exception e) {
                                                System.out.println("Unable to establish a connection - "+e);
                                            }
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
                                    System.out.println();
                                    System.out.println("Search by category: ");
                                    search = scanner.nextLine();
                                    System.out.println("---------------------------------------------------------------------------");
                                    for (int i = 0; i < Products.size(); i++) {
                                        if(Products.get(i).itemCat.toLowerCase().indexOf(search.toLowerCase()) != -1){
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
                    System.out.println("3. Add to cart");
                    System.out.println("4. Sell product");
                    System.out.println("5. Delete listing");
                    System.out.println("6. View own listings");
                    System.out.println("7. Sign Out");
                    System.out.println();
                    System.out.println("Please enter your choice: ");
                    choice2 = scanner.nextLine();
                    break;
            
                default:
                    break;
            }
        }
    }
}
