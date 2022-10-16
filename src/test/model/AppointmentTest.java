package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentTest {
    private Appointment testAppointment;

    @BeforeEach
    void runBefore(){
    testAppointment = new Appointment(500, 600, 3750.50);
    }

}
