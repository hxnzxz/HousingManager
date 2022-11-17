package ui;

import model.Appointment;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

    //Citation: Image taken from https://www.politico.com/interactives/2019/what-works-next-2019-minneapolis-housing/
    //Location: out/production/Project-Starter/ui
    private ImageIcon background;

    public GUI() {
        super("Housing Manager");
        panelSetUp();
        user = new User();

        jsonSetUp();
        setUpBackGround();

        setUpButton();
        setUpAddAppointment();
        scheduleView = new JLabel("hmm");
        scheduleView.setBounds(700, 700, 90, 200);
        panel.add(scheduleView);
        this.setVisible(true);


    }

    //Modifies:
    //Effects:
    public void setUpBackGround() {
        background = new ImageIcon(this.getClass().getResource("HousingBackground.png"));
        label = new JLabel(background);
        label.setSize(this.getSize());
        panel.add(label);
    }

    //Modifies: this
    //Effects: ..
    public void panelSetUp() {
        panel = new JPanel();
        // panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBorder(null);
        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(700, 700);
    }

    //Modifies: this, panel
    //Effects: ..
    public void setUpButton() {
        addButton();
        viewButton();
        removeButton();
        loadButton();
        saveButton();
    }

    //Modifies:
    //Effects:
    public void addButton() {
        addAppointment = new JButton("Add Appointment");
        addAppointment.setBounds(200, 10, 500, 50);
        addAppointment.addActionListener(this);
        panel.add(addAppointment);
    }

    //Modifies:
    //Effects:
    public void viewButton() {
        viewSchedule = new JButton("View Schedule");
        viewSchedule.setBounds(10, 20, 50, 50);
        viewSchedule.setActionCommand("view");
        viewSchedule.addActionListener(this);
        panel.add(viewSchedule);
    }

    //Modifies:
    //Effects:
    public void removeButton() {
        removeAppointment = new JButton("Remove Appointment");
        removeAppointment.setBounds(10, 30, 50, 50);
        removeAppointment.addActionListener(this);
        panel.add(removeAppointment);
    }

    //Modifies:
    //Effects:
    public void loadButton() {
        load = new JButton("Load Previous Save");
        load.setBounds(700, 0, 20, 20);
        load.setActionCommand("load");
        load.addActionListener(this);
        panel.add(load);
    }

    //Modifies:
    //Effects:
    public void saveButton() {
        save = new JButton("Save Current State");
        save.setBounds(650, 0, 50, 20);
        save.setActionCommand("save");
        save.addActionListener(this);
        panel.add(save);
    }

    //Modifies: this,panel
    // Effects: ..
    public void setUpAddAppointment() {
        xcoordinate = new JTextField("X",10);
        xcoordinate.setToolTipText("X-Coordinate");
        xcoordinate.setBounds(50, 60, 10, 10);

        ycoordinate = new JTextField("Y",10);
        ycoordinate.setToolTipText("Y-Coordinate");
        ycoordinate.setBounds(50, 70, 10, 10);

        id = new JTextField("ID",5);
        id.setToolTipText("ID");
        id.setBounds(50, 80, 10, 10);

        rent = new JTextField("$",5);
        rent.setToolTipText("Rent");
        rent.setBounds(50, 90, 10, 10);

        removeId = new JTextField("Del",5);
        removeId.setToolTipText("Remove ID");
        removeId.setBounds(50, 100, 10, 10);

        panel.add(xcoordinate);
        panel.add(ycoordinate);
        panel.add(id);
        panel.add(rent);
        panel.add(removeId);
    }

    //Effects
    public String viewSchedule() {
        List<Appointment> appointments = user.viewSchedule();
        String schedule = "";
        for (Appointment appointment : appointments) {
            String build = "ID: " + appointment.getId() + " Location: (" + appointment.getLocationX()
                    + "," + appointment.getLocationY() + ")" + " Rent: $" + appointment.getRent();
            schedule += build + "\n";
        }
        return schedule;

    }

    //Modifies;
    //Effects:
    public void doAddAppointment() {
        Appointment newAppointment = new Appointment(id.getText(),
                Integer.parseInt(xcoordinate.getText()),
                Integer.parseInt(ycoordinate.getText()),
                Integer.parseInt(rent.getText()));
        this.user.viewSchedule().add(newAppointment);
        System.out.println("Added");
    }

    //Modifies:
    //Effects:
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

    //Modifies:
    //Effects:
    public void setUpViewSchedule() {
        this.scheduleVisibility = !this.scheduleVisibility;
        if (scheduleVisibility) {
            scheduleView.setText(viewSchedule());
        }
    }

    //Modifies:
    //Effects:
    public void setUpLoad() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded previous user from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    //Modifies:
    //Effects:
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

    public void jsonSetUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    @Override
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
