import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO {
    static ArrayList<User> users = new ArrayList<User>();
    public static void getUsers(){
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
    }

    public static void createUsers(String userEnteredUsername, String hashedPass, String userEnteredEmail, String role){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)");
            st.setString(1, userEnteredUsername);
            st.setString(2, hashedPass);
            st.setString(3, userEnteredEmail);
            st.setString(4, role);
            st.executeUpdate();
            st.close();

        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void removeUser(String delUser){
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
    }
}
