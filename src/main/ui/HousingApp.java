package ui;

import model.Appointment;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


//Housing Application UI
public class HousingApp {
    //private static final String JSON_STORE = "./data/user.json";
    private static final String JSON_STORE = "./data/testReaderEmptySchedule.json";
    private Scanner input;
    User user;
    boolean running;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs housing application
    public HousingApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runHousingApp();
    }

    // MODIFIES: this
    // EFFECTS: Modifies User domain based on user input
    private void runHousingApp() {
        this.running = true;
        this.user = new User();
        input = new Scanner(System.in);
        while (running) {
            displayMenu();
            int choice = input.nextInt();

            if (choice == 1) {
                scheduleOption();
            } else if (choice == 2) {
                addAppointmentOption();
            } else if (choice == 3) {
                removeAppointmentOption();
            } else if (choice == 4) {
                betterAppointmentOption();
            } else if (choice == 5) {
                saveUser();
            } else if (choice == 6) {
                loadUser();
            } else {
                running = false;
            }
        }
    }

    // EFFECTS: Provides description of a better appointment based on vague understanding
    // of rent and distance
    private void betterAppointmentOption() {
        System.out.println("Please enter the ID of the first appointment");
        String id1 = input.next();
        System.out.println("Please enter the ID of your second appointment");
        String id2 = input.next();
        Appointment appointment1 = user.targetAppointment(id1);
        Appointment appointment2 = user.targetAppointment(id2);
        String placeholder = "Appointment: ";
        if (appointment1.cheaper(appointment2) == appointment1.closer(appointment2)) {
            System.out.println(placeholder + appointment1.cheaper(appointment2).getId()
                    + " is currently the closest and cheapest option");
        } else {
            System.out.println(placeholder + appointment1.cheaper(appointment2).getId()
                    + " is currently the cheapest option, however " + placeholder
                    + appointment1.closer(appointment2).getId() + " is currently the closer option.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes desired appointment from schedule via ID
    private void removeAppointmentOption() {
        System.out.println("Please enter the ID of the appointment you would like to remove");
        String targetId = input.next();
        // Taken from
        // :https://www.java67.com/2015/10/how-to-solve-concurrentmodificationexception-in-java-arraylist.html
        for (Iterator<Appointment> itr = user.viewSchedule().iterator(); itr.hasNext(); ) {
            Appointment target = itr.next();
            if (target.getId().equals(targetId)) {
                itr.remove();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new appointment designed from scratch by user

    private void addAppointmentOption() {
        System.out.println("Please enter a unique ID for your new appointment");
        String id = input.next();
        System.out.println("Please enter the x location of this appointment");
        double x = input.nextDouble();
        System.out.println("Please enter the y location of this appointment");
        double y = input.nextDouble();
        System.out.println("Please enter the estimated monthly rent for this appointment");
        double rent = input.nextDouble();
        user.addAppointment(new Appointment(id, x, y, rent));
        System.out.println("Appointment added!");
    }

    // EFFECTS: Prints current schedule of appoints if any
    private void scheduleOption() {
        if (user.viewSchedule().isEmpty()) {
            System.out.println("You currently don't have any appointments!");
        } else {
            for (Appointment target : user.viewSchedule()) {
                System.out.println("ID: " + target.getId() + " Location: (" + target.getLocationX()
                        + "," + target.getLocationY() + ")" + " Rent: $" + target.getRent());
            }
        }
    }

    // EFFECTS: Prints implemented methods for user to choose
    private void displayMenu() {
        System.out.println("Would you like to :");
        System.out.println("\t 1 -> View your current schedule");
        System.out.println("\t 2 -> Add an appointment to your schedule");
        System.out.println("\t 3 -> Remove an appointment from your schedule");
        System.out.println("\t 4 -> Compare two appointments");
        System.out.println("\t 5 -> Save your current user schedule");
        System.out.println("\t 6 -> Load previous user schedule");
        System.out.println("\t 7 -> EXIT APPLICATION!");
    }

    // EFFECTS: saves the user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved current user schedule to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded previous user from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
