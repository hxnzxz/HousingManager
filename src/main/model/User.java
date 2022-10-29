package model;

import model.Appointment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Represents a user with their current location, schedule of appointments and possible properties
    private List<Appointment> schedule;
    private int wallet;

    public User() {
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
        this.schedule.remove(target);
    }

    // REQUIRES: Target appointment's ID to exist
    // EFFECTS: Returns the appointment with the corresponding target ID
    public Appointment targetAppointment(String id) {
        for (Appointment target : this.schedule) {
            if (target.getId().equals(id)) {
                return target;
            }
        }
        System.out.println("Error: Given ID does not exist in schedule!");
        return null;
    }

    // EFFECTS: Prints out all appointments from current schedule
    public List<Appointment> viewSchedule() {
        return this.schedule;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("schedule", scheduleToJson());
        return json;
    }

    // EFFECTS: returns appoints in this schedule as a JSON array
    private JSONArray scheduleToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Appointment a : schedule) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

}
