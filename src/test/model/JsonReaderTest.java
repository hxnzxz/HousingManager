package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistent.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            User user = reader.read();
            assertEquals(0, user.viewSchedule().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderClassicSchedule() {
        JsonReader reader = new JsonReader("./data/user.json");
        try {
            User user = reader.read();
            List<Appointment> appointments = user.viewSchedule();
            assertEquals(2, appointments.size());
            checkAppointment("HaidaHouse", 10, 5, 500000, appointments.get(0));
            checkAppointment("Walter", 50,900,4500, appointments.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
