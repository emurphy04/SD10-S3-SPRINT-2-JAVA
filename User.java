import java.util.ArrayList;

public abstract class User {
    String username;
    String password;
    String email;

    ArrayList<Items> cart = new ArrayList<Items>();


    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addToCart(Items item){
        cart.add(item);
    }

    public void getCart() {
        for (int i = 0; i<cart.size(); i++){
            System.out.println(cart.get(i).toString());
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Undefined";
    }
}
