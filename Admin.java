public class Admin extends User{
    String role;

    public Admin(String username, String password, String email) {
        super(username, password, email);
        this.role = "admin";
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Admin";
    }
}
