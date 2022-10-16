package model;

import model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Represents a user with their current location, schedule of appointments and possible properties
    private List<Appointment> schedule;
    private int wallet;

    public User(int money) {
        this.wallet = money;
        this.schedule = new ArrayList<>();

    }

    // REQUIRES: Appointment time must not conflict with any current appointments
    // MODIFIES: this
    // EFFECTS: Adds a new appointment onto user schedule
    public void addAppointment(Appointment target) {
        this.schedule.add(target);

    }

    // REQUIRES: Target appointment must exist in schedule
    // MODIFIES: this
    // EFFECTS: Removes appointment from user schedule
    public void removeAppointment(Appointment target) {

    }

    // EFFECTS: Prints out all appointments from current schedule
    public List<Appointment> viewSchedule() {
        return null;
    }

}
