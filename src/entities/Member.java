package entities;

public class Member extends Person {
    private final long id;

    public Member(long id, String name, String email, String phone) {
        super(name, email, "member", phone);

        this.id = id;
    }

    public long getId() {
        return id;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
