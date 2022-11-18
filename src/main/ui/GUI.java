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

    private int height;
    private int width;

    //Citation: Image taken from https://www.politico.com/interactives/2019/what-works-next-2019-minneapolis-housing/
    //Location: out/production/Project-Starter/ui
    private ImageIcon background;

    public GUI() {
        super("Housing Manager");
        panelSetUp();
        user = new User();
        setUpFrame();
        jsonSetUp();
        setUpBackGround();

        setUpButton();
        setUpAddAppointment();
        scheduleView = new JLabel("pickle");
        scheduleView.setBounds(500, 650, 100, 25);
//        label.add(scheduleView);

        this.setVisible(true);

    }

    //Modifies:
    //Effects:
    public void setUpFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.pack();
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
    }

    //Modifies:
    //Effects:
    public void setUpBackGround() {
        background = new ImageIcon(this.getClass().getResource("HousingBackground.png"));
        label = new JLabel("", background, JLabel.CENTER);
        label.setSize(this.getSize());
        //panel.add(label);
        this.add(label);
    }

    //Modifies: this
    //Effects: ..
    public void panelSetUp() {
        panel = new JPanel();
        // panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBorder(null);
        // this.setLayout(null);
        this.add(panel);
    }

    //Modifies: this, panel
    //Effects: ..
    public void setUpButton() {
        height = (int) this.getSize().getHeight();
        width = (int) this.getSize().getWidth();
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
        addAppointment.setBounds(width - 675, height - 690, 160, 30);
        System.out.println(width);
        addAppointment.addActionListener(this);
        label.add(addAppointment);
    }

    //Modifies:
    //Effects:
    public void viewButton() {
        viewSchedule = new JButton("View Schedule");
        viewSchedule.setBounds(width - 675, height - 500, 160, 30);
        viewSchedule.setActionCommand("view");
        viewSchedule.addActionListener(this);
        label.add(viewSchedule);
    }

    //Modifies:
    //Effects:
    public void removeButton() {
        removeAppointment = new JButton("Remove Appointment");
        removeAppointment.setBounds(width - 675, height - 600, 160, 30);
        removeAppointment.addActionListener(this);
        label.add(removeAppointment);
    }

    //Modifies:
    //Effects:
    public void loadButton() {
        load = new JButton("Load Previous Save");
        load.setBounds(width - 200, height - 150, 150, 30);
        load.setActionCommand("load");
        load.addActionListener(this);
        label.add(load);
    }

    //Modifies:
    //Effects:
    public void saveButton() {
        save = new JButton("Save Current State");
        save.setBounds(width - 200, height - 100, 150, 30);
        save.setActionCommand("save");
        save.addActionListener(this);
        label.add(save);
    }

    //Modifies: this,panel
    // Effects: ..
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
    }

    //Effects
    public String viewSchedule() {
        List<Appointment> appointments = user.viewSchedule();
        String schedule = "";
        if (appointments.size() == 0) {
            return "No appointments yet!";
        }
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
//        if (scheduleVisibility) {
//            scheduleView.setText(viewSchedule());
//            label.add(scheduleView);
//            getContentPane().add(label);
//        }
        JOptionPane.showMessageDialog(null, viewSchedule(), "Schedule", 1);

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
