public class Admin extends User{
    String adminKey;

    public Admin(String username, String password, String email, String adminKey) {
        super(username, password, email);
        this.adminKey = adminKey;
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Admin | Admin Key: "+adminKey;
    }
}
