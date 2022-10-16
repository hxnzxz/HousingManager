package model;

public class Appointment {
    //Representation for housing giving rent, time and location

    private double rent;                // monthly rent in $
    private double locationX;          // x point location on a coordinate
    private double locationY;          // y point location on a coordinate

    public Appointment(double x, double y, double rent) {
        this.rent = rent;
        this.locationX = x;
        this.locationY = y;
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
}
