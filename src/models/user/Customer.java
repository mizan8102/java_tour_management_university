package models.user;

public class Customer extends Person {
    private String customerId;

    public Customer(String name, String email, String phoneNumber, String customerId) {
        super(name, email, phoneNumber);
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
