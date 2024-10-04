package models.user;

public class TourGuide extends Person {
    private String guideId;
    private String language;

    public TourGuide(String name, String email, String phoneNumber, String guideId, String language) {
        super(name, email, phoneNumber);
        this.guideId = guideId;
        this.language = language;
    }

    public String getGuideId() {
        return guideId;
    }

    public void setGuideId(String guideId) {
        this.guideId = guideId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getRole() {
        return "Tour Guide";
    }
}
