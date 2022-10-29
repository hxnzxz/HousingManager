package persistence;

import model.Appointment;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    // CITATION: Taken from given example
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private User parseWorkRoom(JSONObject jsonObject) {
        User user = new User();
        addAppointments(user, jsonObject);
        return user;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addAppointments(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
        for (Object json : jsonArray) {
            JSONObject nextAppointment = (JSONObject) json;
            addAppointment(user, nextAppointment);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addAppointment(User user, JSONObject jsonObject) {
        String id = jsonObject.getString("ID");
        Double rent = jsonObject.getDouble("Rent");
        Double xcord = jsonObject.getDouble("XLocation");
        Double ycord = jsonObject.getDouble("YLocation");
        Appointment appointment = new Appointment(id, xcord, ycord, rent);
        user.addAppointment(appointment);
    }

}
