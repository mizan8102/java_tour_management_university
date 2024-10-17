package entities;

import java.util.Arrays;
import java.util.List;

public class Person {
    private final String name;
    private final String email;
    private final String role;
    private final String phone;

    public Person(String name, String email, String role, String phone) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Role: " + role + ", Phone: " + phone;
    }
}





