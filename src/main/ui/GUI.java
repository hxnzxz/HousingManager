package ui;

import model.Appointment;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//Creates Graphical User interface representing a housing manager
public class GUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel label;
    private JLabel scheduleView;

    private JButton addAppointment;
    private JButton viewSchedule;
    private JButton removeAppointment;
    private JButton save;
    private JButton load;

    private JTextField xcoordinate;
    private JTextField ycoordinate;
    private JTextField id;
    private JTextField rent;
    private JTextField removeId;
    User user;

    private boolean scheduleVisibility = false;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/testP3.json";

    private int height;
    private int width;

    //Citation: Image taken from https://www.politico.com/interactives/2019/what-works-next-2019-minneapolis-housing/
    //Location: /Project-Starter/src/main/ui
    private ImageIcon background;

    //Modifies: this
    //Effects: Runs housing application version GUI and sets title of application window
    public GUI() {
        super("Housing Manager");
        panelSetUp();
        user = new User();
        setUpFrame();
        jsonSetUp();
        setUpBackGround();

        scheduleView = new JLabel("");
        scheduleView.setBounds(25, 470, 400, 250);

        setUpButton();
        setUpAddAppointment();

        this.setVisible(true);

    }

    //Modifies: this
    //Effects: Sets up this JFrame's window size and close function
    public void setUpFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
    }

    //Modifies: this
    //Effects: Adds a visual component as a background of this GUI
    public void setUpBackGround() {
        background = new ImageIcon(this.getClass().getResource("HousingBackground.png"));
        label = new JLabel("", background, JLabel.CENTER);
        label.setSize(this.getSize());
        this.add(label);
    }

    //Modifies: this
    //Effects: Creates new JPanel and adds it to this JFrame
    public void panelSetUp() {
        panel = new JPanel();
        panel.setBorder(null);
        this.add(panel);
    }

    //Modifies: this, panel
    //Effects: Initializes to add all buttons to label
    public void setUpButton() {
        height = (int) this.getSize().getHeight();
        width = (int) this.getSize().getWidth();
        addButton();
        viewButton();
        removeButton();
        loadButton();
        saveButton();
    }

    //Modifies: this, panel
    //Effects: Creates button tied to adding a new appointment to user schedule
    public void addButton() {
        addAppointment = new JButton("Add Appointment");
        addAppointment.setBounds(width - 675, height - 690, 160, 30);
        System.out.println(width);
        addAppointment.addActionListener(this);
        label.add(addAppointment);
    }

    //Modifies:this, label
    //Effects: Creates button tied to changing scheduleView text
    public void viewButton() {
        viewSchedule = new JButton("View Schedule");
        viewSchedule.setBounds(width - 675, height - 150, 160, 30);
        viewSchedule.setActionCommand("view");
        viewSchedule.addActionListener(this);
        label.add(viewSchedule);
    }

    //Modifies: this, label
    //Effects: Creates a button tied to removing a desired appointment from use schedule
    public void removeButton() {
        removeAppointment = new JButton("Remove Appointment");
        removeAppointment.setBounds(width - 675, height - 600, 160, 30);
        removeAppointment.addActionListener(this);
        label.add(removeAppointment);
    }

    //Modifies: this, label
    //Effects: Creates a button tied to loading previous user state functionality
    public void loadButton() {
        load = new JButton("Load Previous Save");
        load.setBounds(width - 200, height - 150, 150, 30);
        load.setActionCommand("load");
        load.addActionListener(this);
        label.add(load);
    }

    //Modifies: this, label
    //Effects: Creates a button tied to saving current user state functionality
    public void saveButton() {
        save = new JButton("Save Current State");
        save.setBounds(width - 200, height - 100, 150, 30);
        save.setActionCommand("save");
        save.addActionListener(this);
        label.add(save);
    }

    //Modifies: this,label
    //Effects: Creates text fields for user to add data in order to create a new appointment
    public void setUpAddAppointment() {
        xcoordinate = new JTextField("X", 10);
        xcoordinate.setToolTipText("X-Coordinate");
        xcoordinate.setBounds(width - 500, height - 700, 100, 25);

        ycoordinate = new JTextField("Y", 10);
        ycoordinate.setToolTipText("Y-Coordinate");
        ycoordinate.setBounds(width - 500, height - 675, 100, 25);

        id = new JTextField("ID", 5);
        id.setToolTipText("ID");
        id.setBounds(width - 500, height - 650, 100, 25);

        rent = new JTextField("$", 5);
        rent.setToolTipText("Rent");
        rent.setBounds(width - 500, height - 625, 100, 25);

        removeId = new JTextField("Del", 5);
        removeId.setToolTipText("Remove ID");
        removeId.setBounds(width - 500, height - 575, 100, 25);

        label.add(xcoordinate);
        label.add(ycoordinate);
        label.add(id);
        label.add(rent);
        label.add(removeId);
        label.add(scheduleView);
        getContentPane().add(label);
    }

    //Effects: Creates a string of all listed appointments visible in schedule
    //Citation: html line break method adapted from:
    //https://stackoverflow.com/questions/1090098/newline-in-jlabel
    public String viewSchedule() {
        List<Appointment> appointments = user.viewSchedule();
        String schedule = "<html>";
        if (appointments.size() == 0) {
            return "No Appointments Yet!";
        }
        for (Appointment appointment : appointments) {
            String build = "ID: " + appointment.getId() + " Location: (" + appointment.getLocationX()
                    + "," + appointment.getLocationY() + ")" + " Rent: $" + appointment.getRent()
                    + System.lineSeparator();
            schedule += build + "<br/>";
        }
        return schedule + "</html>";

    }

    //Requires: xcoordinate, ycoordinate and rent to be non-empty with fields of type double
    //and ID to be non-empty with type String
    //Modifies; this, user
    //Effects: Creates a new appointment with parameters taken from xcoordinate, ycoordinate, rent and ID
    //and adds it to User schedule
    public void doAddAppointment() {
        Appointment newAppointment = new Appointment(id.getText(),
                Integer.parseInt(xcoordinate.getText()),
                Integer.parseInt(ycoordinate.getText()),
                Integer.parseInt(rent.getText()));
        this.user.viewSchedule().add(newAppointment);
        System.out.println("Added");
    }

    //Requires: this.removeID =/= null and field type must be String
    //Modifies:this, user
    //Effects: Removes appoint with corresponding ID of the String in JTextField removeId
    public void setUpRemoveAppointment() {
        System.out.println("Removed");
        String targetId = removeId.getText();
        for (Iterator<Appointment> itr = user.viewSchedule().iterator(); itr.hasNext(); ) {
            Appointment target = itr.next();
            if (target.getId().equals(targetId)) {
                itr.remove();
            }
        }
    }

    //Modifies:this, scheduleView, scheduleVisibility
    //Effects: Updates current list of appointments as an on/off toggle
    public void setUpViewSchedule() {
        this.scheduleVisibility = !this.scheduleVisibility;
        if (scheduleVisibility) {
            scheduleView.setText(viewSchedule());
        } else {
            scheduleView.setText("");
        }


    }

    //Modifies: this, user
    //Effects: loads previous user state from file
    public void setUpLoad() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded previous user from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    //Effects: saves current user state to file
    public void setUpSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved current user schedule to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    //Effects: Initializes Json writer and reader for saving/loading functionality
    public void jsonSetUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //Effects: Reads action even and performs equivalent button function
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAppointment) {
            doAddAppointment();
        } else if (e.getSource() == removeAppointment) {
            setUpRemoveAppointment();
        } else if (e.getActionCommand().equals("view")) {
            setUpViewSchedule();
        } else if (e.getActionCommand().equals("save")) {
            setUpSave();
        } else if (e.getActionCommand().equals("load")) {
            setUpLoad();
        }
    }
}
