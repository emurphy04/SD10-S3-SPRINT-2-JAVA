import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserService {
    public static void main(String[] args) {

        ArrayList<Items> Products = new ArrayList<Items>();

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



            //make one of these for each value in the table and have it loop for each row, then in the loop create the
            //items objects with those values and add them to an array list, the array list is goated



        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }


        Buyer buyer1 = new Buyer("Ethan", "deadpool1", "ethanmurphy267@gmail.com");
        Items c01 = new Items("CPU Fan", "c01", 12.99, "A fan for cooling", "Cooling");
        
        buyer1.getCart();

        buyer1.addToCart(c01);

        buyer1.addToCart(Products.get(0));

        buyer1.getCart();
    }
}
