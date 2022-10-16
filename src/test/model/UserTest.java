package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User testUser;
    private Appointment testAppointment;

    @BeforeEach
    void runBefore(){
        testUser = new User(0);
        testAppointment = new Appointment(500, 600, 3750.50);
        testUser.addAppointment(testAppointment);

    }
    @Test
    void testConstructor(){

    }

}