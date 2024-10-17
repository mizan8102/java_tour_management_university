package entities;

public class Booking {
    private long id;
    private Member member;
    private Tour tour;


    public Booking(long id, Member member, Tour tour) {
        this.id = id;
        this.member = member;
        this.tour = tour;

    }

    public Booking() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "Booking for " + tour.getTitle();
    }
}
