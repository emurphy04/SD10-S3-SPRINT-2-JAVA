public class Seller extends User{

    public Seller(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Seller";
    }
}
