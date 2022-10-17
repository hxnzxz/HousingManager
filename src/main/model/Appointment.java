package model;

public class Appointment {
    //Representation for housing giving rent, time and location

    private double rent;               // monthly rent in $
    private double locationX;          // x point location on a coordinate
    private double locationY;          // y point location on a coordinate
    private String id;                 // Unique appoint ID for ease of finding

    public Appointment(String id, double x, double y, double rent) {
        this.id = id;
        this.rent = rent;
        this.locationX = x;
        this.locationY = y;
    }

    public String getId() {
        return id;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public double getRent() {
        return rent;
    }

    // EFFECTS: Gives distance from an appointment to position (0,0)
    public double distanceToLocation() {
        return Math.sqrt(Math.pow(this.getLocationX(), 2) + Math.pow(this.getLocationY(), 2));
    }

    // EFFECTS: Given two appointments return the one that has a cheaper rent
    public Appointment cheaper(Appointment appointment2) {
        if (getRent() > appointment2.getRent()) {
            return appointment2;
        } else {
            return this;
        }
    }

    // EFFECTS: Given two appointments return the one that is closer to (0,0) as a coordinate
    public Appointment closer(Appointment appointment2) {
        if (distanceToLocation() > appointment2.distanceToLocation()) {
            return appointment2;
        } else {
            return this;
        }
    }
}
