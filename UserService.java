import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    public static void main(String[] args) {

        String userRole = "buyer";

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

                Products.add(new Items(itemName, itemSku, itemPrice, itemDesc, itemCat));
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
                            String role = rs.getString(4);

                            switch (role) {
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
                    break;
            
                default:
                    System.out.println("Invalid Choice, Please try again.");
                    break;
            }
        }

        //CLI

        switch (users.get(userIndex).role) {
            case "admin":
                
                break;

            case "buyer":
                
                break;

            case "seller":
                
                break;
        
            default:
                break;
        }

    }
}
