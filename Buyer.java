public class Buyer extends User{

    public Buyer(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "User: "+username+" | email: "+email+" | Role: Buyer";
    }
}
