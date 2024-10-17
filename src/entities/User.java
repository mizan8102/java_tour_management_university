package entities;

public class User extends Person {
    private final long id;
    private String password;

    public User(long id, String name, String email, String password, String role, String phone) {
        super(name, email, role, phone);
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + " (User ID: " + id + ")";
    }
}