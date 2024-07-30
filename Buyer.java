public class Buyer extends User{
    String role;

    public Buyer(String username, String password, String email) {
        super(username, password, email);
        this.role = "buyer";
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Buyer";
    }
}
