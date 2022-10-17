package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User testUser;
    private Appointment testAppointment;
    private Appointment testAppointment2;

    @BeforeEach
    void runBefore() {
        testUser = new User();
        testAppointment = new Appointment("123", 500, 600, 3750.50);
        testAppointment2 = new Appointment("abc", 40, -780, 100);

    }

    @Test
    void testConstructor() {
        assertTrue(testUser.viewSchedule().isEmpty());
        assertEquals(0,testUser.viewSchedule().size());
        testUser.addAppointment(testAppointment);

        assertFalse(testUser.viewSchedule().isEmpty());
        assertEquals(1, testUser.viewSchedule().size());
        assertEquals(testAppointment, testUser.viewSchedule().get(0));

        List<Appointment> testViewSchedule = testUser.viewSchedule();

        assertEquals("123", testViewSchedule.get(0).getId());
        assertEquals(500, testViewSchedule.get(0).getLocationX());
        assertEquals(600, testViewSchedule.get(0).getLocationY());
        assertEquals(3750.50, testViewSchedule.get(0).getRent());
        assertEquals(testAppointment, testUser.targetAppointment("123"));
    }

    @Test
    void testWorkingWithMultipleSchedules() {
        testUser.addAppointment(testAppointment);
        testUser.addAppointment(testAppointment2);

        List<Appointment> testViewSchedule = testUser.viewSchedule();
        assertEquals(2, testViewSchedule.size());
        assertEquals(testAppointment, testViewSchedule.get(0));
        assertEquals(testAppointment2, testViewSchedule.get(1));
        testUser.removeAppointment(testAppointment);
        testViewSchedule = testUser.viewSchedule();
        assertEquals(1, testViewSchedule.size());
        assertEquals(testAppointment2, testViewSchedule.get(0));

    }

    @Test
    void testAppointmentTarget() {
        testUser.addAppointment(testAppointment);
        testUser.addAppointment(testAppointment2);

        assertEquals(testAppointment, testUser.targetAppointment("123"));
        assertEquals(testAppointment2, testUser.targetAppointment("abc"));
        assertEquals(null, testUser.targetAppointment("12"));
    }

    @Test
    void testBetterAppointment() {
        Appointment betterAppointment1 = new Appointment("789", 600, 500, 5);
        assertEquals(testAppointment, testAppointment.closer(betterAppointment1));
        assertEquals(betterAppointment1, betterAppointment1.closer(testAppointment));

        assertEquals(betterAppointment1, testAppointment.cheaper(betterAppointment1));
        assertEquals(betterAppointment1, betterAppointment1.cheaper(testAppointment));


        //BIAS
        Appointment betterAppointment2 = new Appointment("780", 600, 500, 3750.50);
        assertEquals(betterAppointment2, betterAppointment2.closer(testAppointment));
        assertEquals(testAppointment, testAppointment.closer(betterAppointment2));

        assertEquals(betterAppointment2, betterAppointment2.cheaper(testAppointment));
        assertEquals(testAppointment, testAppointment.cheaper(betterAppointment2));

        Appointment boundary1 = new Appointment("13", 4, 4, 6);
        Appointment boundary2 = new Appointment("12", 3, 3, 5);

        assertEquals(boundary2, boundary2.closer(boundary1));
        assertEquals(boundary2, boundary1.closer(boundary2));

        assertEquals(boundary2, boundary1.closer(boundary2));
        assertEquals(boundary2, boundary2.closer(boundary1));

    }

}