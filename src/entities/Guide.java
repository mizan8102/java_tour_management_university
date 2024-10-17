package entities;

import java.util.List;

public class Guide extends Person {
    private final int experienceYears;
    private final List<String> languages;
    private final long id;

    public Guide(long id, String name, String email, String phone, int experienceYears, List<String> languages) {
        super(name, email, "guide", phone);
        this.experienceYears = experienceYears;
        this.languages = languages;
        this.id = id;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public List<String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return super.toString() + " (Guide with " + experienceYears + " years experience, Languages: " + languages + ")";
    }
}