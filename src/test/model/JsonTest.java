package model;

import model.Appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAppointment(String id, double locationx, double locationy, double rent, Appointment a) {
        assertEquals(a.getId(),id);
        assertEquals(locationx,a.getLocationX());
        assertEquals(locationy,a.getLocationY());
        assertEquals(rent,a.getRent());

    }
}
