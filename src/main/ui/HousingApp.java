package ui;

import model.Appointment;
import model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


//Housing Application UI
public class HousingApp {
    private Scanner input;
    User user;
    boolean running;


    public HousingApp() {
        runHousingApp();
    }

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
            } else {
                running = false;
            }
        }
    }

    private void betterAppointmentOption() {
        betterAppointmentOption();
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

    private void removeAppointmentOption() {
        System.out.println("Please enter the ID of the appointment you would like to remove");
        String targetId = input.next();

        for (Iterator<Appointment> itr = user.viewSchedule().iterator(); itr.hasNext(); ) {
            Appointment target = itr.next();
            if (targetId.equals(targetId)) {
                itr.remove(); // right call
            }
        }
    }

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
        System.out.println("\t 5 -> EXIT APPLICATION!");
    }

}
