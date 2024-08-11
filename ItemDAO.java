import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
    static ArrayList<Items> Products = new ArrayList<Items>();
    public static void getItems() {
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

        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void addItem(String userEnteredItemName, double userEnteredItemPrice, String userEnteredItemDesc, String chosenCat, String user){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("INSERT INTO products (itemname, itemsku, itemprice, itemdesc, itemcat, userListed) VALUES (?, ?, ?, ?, ?, ?)");
            st.setString(1, userEnteredItemName);
            st.setString(2, "C"+(Products.size()+1));
            st.setDouble(3, userEnteredItemPrice);
            st.setString(4, userEnteredItemDesc);
            st.setString(5, chosenCat);
            st.setString(6, user);
            st.executeUpdate();
            st.close();
            Products.add(new Items(userEnteredItemName, "C"+(Products.size()+1), userEnteredItemPrice, userEnteredItemDesc, chosenCat, user));
            System.out.println("Item Added!");

        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void removeItem(String delSku){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("DELETE FROM products WHERE itemsku=?");
            st.setString(1, delSku.toUpperCase());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void updateName(String newname, String sku, int index){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("UPDATE products SET itemname=? WHERE itemsku=?");
            st.setString(1, newname);
            st.setString(2, sku.toUpperCase());
            st.executeUpdate();
            st.close();
            Products.get(index).setItemName(newname);
        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void updatePrice(double newprice, String sku, int index) {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("UPDATE products SET itemprice=? WHERE itemsku=?");
            st.setDouble(1, newprice);
            st.setString(2, sku.toUpperCase());
            st.executeUpdate();
            st.close();
            Products.get(index).setItemPrice(newprice);
        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void updateDesc(String newdesc, String sku, int index) {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("UPDATE products SET itemdesc=? WHERE itemsku=?");
            st.setString(1, newdesc);
            st.setString(2, sku.toUpperCase());
            st.executeUpdate();
            st.close();
            Products.get(index).setItemDesc(newdesc);
        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }

    public static void updateCat(String userEnteredItemCat, String sku, int index) {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Java";
            String username = "postgres";
            String password = "admin";

            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement st = connection.prepareStatement("UPDATE products SET itemcat=? WHERE itemsku=?");
            st.setString(1, userEnteredItemCat);
            st.setString(2, sku.toUpperCase());
            st.executeUpdate();
            st.close();
            Products.get(index).setItemCat(userEnteredItemCat);
        } catch (Exception e) {
            System.out.println("Unable to establish a connection - "+e);
        }
    }
}
