import java.sql.Connection;
import java.sql.DriverManager;

public class UserService {
    public static void main(String[] args) {
        String url="jdbc:mysql:"
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(null, null, null)

        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }


        Buyer buyer1 = new Buyer("Ethan", "deadpool1", "ethanmurphy267@gmail.com");
        Items c01 = new Items("CPU Fan", "c01", 12.99, "A fan for cooling", "Cooling");
        
        buyer1.getCart();

        buyer1.addToCart(c01);

        buyer1.getCart();
    }
}
