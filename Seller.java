public class Seller extends User{
    String role;

    public Seller(String username, String password, String email) {
        super(username, password, email);
        this.role = "seller";
    }

    @Override
    public String getRole() {
        // TODO Auto-generated method stub
        return this.role;
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Seller";
    }
}
