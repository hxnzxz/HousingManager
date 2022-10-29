package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User();
            JsonWriter writer = new JsonWriter("./data/non\0-existent.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            User user = new User();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            user = reader.read();
            assertEquals(0, user.viewSchedule().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterClassicSchedule() {
        try {
            User user = new User();
            user.addAppointment(new Appointment("nootka", -20, 30, 60.3));
            user.addAppointment(new Appointment("brocks",50,0,90.51));
            JsonWriter writer = new JsonWriter("./data/testWriterNormalSchedule.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalSchedule.json");
            user = reader.read();
            List<Appointment> schedule = user.viewSchedule();
            assertEquals(2, schedule.size());
            checkAppointment("nootka", -20, 30, 60.3,schedule.get(0));
            checkAppointment("brocks",50,0,90.51,schedule.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
