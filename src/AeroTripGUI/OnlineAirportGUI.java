package AeroTripGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.Objects;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OnlineAirportGUI {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel userLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel reEnterLabel = new JLabel("Re-Type Password:");
    JTextField userText = new JTextField();
    JPasswordField passwordText = new JPasswordField();
    JPasswordField reEnterText = new JPasswordField();
    JButton button = new JButton();
    JLabel msg = new JLabel();

    public static void main(String[] args) {
        new OnlineAirportGUI().HomePage();
    }

    JButton loginButton = new JButton();
    JButton signupButton = new JButton();

    void HomePage() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setSize(350, 300);
        frame.setTitle("HomePage");
        frame.add(panel);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.add(loginButton);
        frame.add(signupButton);

        loginButton.setBounds(50, 103, 100, 30);
        loginButton.setBackground(Color.ORANGE);
        loginButton.setText("Login");
        loginButton.addActionListener(new Login());

        signupButton.setBounds(175, 103, 100, 30);
        signupButton.setBackground(Color.ORANGE);
        signupButton.setText("SignUp");
        signupButton.addActionListener(new SignUp());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class BackButton implements ActionListener {
        int num;

        BackButton(int num) {
            this.num = num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (num == 1) {
                HomePage();
            } else {
                displayAirlinerOptions();
            }
        }
    }

    void loginSignup(String buttonName) {
        frame.getContentPane().removeAll();
        frame.repaint();

        backButton = new JButton("Back");

        frame.setTitle(buttonName);
        frame.add(panel);
        frame.add(userLabel);
        frame.add(passwordLabel);
        frame.add(userText);
        frame.add(passwordText);
        frame.add(button);
        frame.add(msg);
        frame.add(backButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userLabel.setBounds(50, 50, 80, 25);
        passwordLabel.setBounds(50, 80, 80, 25);

        userText.setBounds(130, 50, 120, 25);
        passwordText.setBounds(130, 80, 120, 25);

        userText.setText("");
        passwordText.setText("");
        reEnterText.setText("");
        msg.setText("");

        button.setText(buttonName);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);

        backButton.setBounds(0, 0, 80, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new BackButton(1));

        if (buttonName.equals("LogIn")) {
            msg = new JLabel();
            frame.add(msg);
            msg.setBounds(0, 170, 350, 25);
            msg.setHorizontalAlignment(JLabel.CENTER);
            button.setBounds(120, 120, 80, 25);
            button.addActionListener(new LogInCode());
        } else {
            msg = new JLabel();
            frame.add(msg);

            frame.add(reEnterLabel);
            frame.add(reEnterText);

            reEnterLabel.setBounds(0, 110, 130, 25);
            reEnterText.setBounds(130, 110, 120, 25);

            button.setBounds(120, 150, 80, 25);

            msg.setBounds(0, 180, 350, 25);
            msg.setHorizontalAlignment(JLabel.CENTER);

            button.addActionListener(new SignUpCode());
        }
    }

    class Login implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginSignup("LogIn");
        }
    }

    class SignUp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginSignup("SignUp");
        }
    }

    class LogInCode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userText.getText().equals("") || String.valueOf(passwordText.getPassword()).equals("")) {
                msg.setText("Please Fill all the fields");
            } else {
                File db = new File("D:\\Java_Project\\LoginSignupDatabase.txt");
                String str;
                String[] details;
                int flag = 0;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(db));
                    while ((str = reader.readLine()) != null) {
                        details = str.split(",");
                        if (userText.getText().equals(details[0]) && String.valueOf(passwordText.getPassword()).equals(details[1])) {
                            flag = 1;
                            break;
                        }
                    }
                    reader.close();
                    if (flag != 1) {
                        msg.setText("No User With Given Credentials");
                    } else {
                        displayAirlinerOptions();
                    }
                } catch (Exception ex) {
                    msg.setText("Error in reading file");
                }
            }
        }
    }

    class SignUpCode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userText.getText().equals("") || String.valueOf(passwordText.getPassword()).equals("") || String.valueOf(reEnterText.getPassword()).equals("")) {
                msg.setText("Please Fill all the fields");
            } else if (!String.valueOf(passwordText.getPassword()).equals(String.valueOf(reEnterText.getPassword()))) {
                msg.setText("Passwords doesn't match");
            } else {
                File db = new File("D:\\Java_Project\\LoginSignupDatabase.txt");
                String str;
                String[] details;
                boolean flag = false;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(db));
                    while ((str = reader.readLine()) != null) {
                        details = str.split(",");
                        if (userText.getText().equals(details[0])) {
                            msg.setText("Username Already Taken");
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(db, true));
                        writer.write(userText.getText() + "," + String.valueOf(passwordText.getPassword()) + "\n");
                        writer.flush();
                        writer.close();
                        displayAirlinerOptions();
                    }
                } catch (Exception ex) {
                    msg.setText("Error in reading file");
                }
            }
        }
    }

    JButton bookTicket, enquiry, cancelTicket, printTicket, logout;

    void displayAirlinerOptions() {
        bookTicket = new JButton("Book A Ticket");
        enquiry = new JButton("Enquiry");
        cancelTicket = new JButton("Cancel Ticket");
        printTicket = new JButton("Print Ticket");
        logout = new JButton("Logout");
        JLabel airplaneImage = new JLabel(new ImageIcon("P:\\Java_Project\\airplane_final.png"));

        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setSize(600, 600);
        frame.setTitle("Online Airliner");
        frame.add(panel);
        frame.add(bookTicket);
        frame.add(enquiry);
        frame.add(cancelTicket);
        frame.add(printTicket);
        frame.add(logout);
        frame.add(airplaneImage);

        airplaneImage.setBounds(250, 25, 100, 100);

        enquiry.setBounds(200, 150, 200, 30);
        enquiry.setBackground(Color.DARK_GRAY);
        enquiry.setForeground(Color.WHITE);
        enquiry.addActionListener(new Enquiry());

        bookTicket.setBounds(200, 210, 200, 30);
        bookTicket.setBackground(Color.DARK_GRAY);
        bookTicket.setForeground(Color.WHITE);
        bookTicket.addActionListener(new BookTicket());

        cancelTicket.setBounds(200, 270, 200, 30);
        cancelTicket.setBackground(Color.DARK_GRAY);
        cancelTicket.setForeground(Color.WHITE);
        cancelTicket.addActionListener(new CancelTicket());

        printTicket.setBounds(200, 330, 200, 30);
        printTicket.setBackground(Color.DARK_GRAY);
        printTicket.setForeground(Color.WHITE);
        printTicket.addActionListener(new PrintTicket());

        logout.setBounds(250, 390, 100, 30);
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.addActionListener(new BackButton(1));
    }

    JLabel nameLabel, phoneLabel, adultLabel, childrenLabel, infantLabel, fromLabel, toLabel, refIDLabel, textMessage, phoneMessage;
    JTextField nameText, phoneText, refIDText;
    JComboBox<String> flightFromLocations, flightToLocations, adultField, childrenField, infantField;
    int refID;
    JButton backButton;
    String[] flightLocations = {"--Select--", "Hyderabad", "Chennai", "Delhi", "Bombay", "Bengaluru", "Dubai", "SanFransisco", "Istanbul", "London", "KualaLumpur"};
    String[] numberArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] adultArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Date date = new Date();
    byte i = 0;

    boolean mobileNumberValidator(String phn) {
        return phn.matches("^[6-9]\\d{9}");
    }

    class BookTicket implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            frame.repaint();

            JButton bookingButton = new JButton("Book A Ticket");

            nameLabel = new JLabel("Name:");
            phoneLabel = new JLabel("Phone:");
            adultLabel = new JLabel("Adults:");
            childrenLabel = new JLabel("Children:");
            infantLabel = new JLabel("Infants:");
            fromLabel = new JLabel("From:");
            toLabel = new JLabel("To:");
            nameText = new JTextField();
            phoneText = new JTextField();
            backButton = new JButton("Back");
            flightFromLocations = new JComboBox<>(flightLocations);
            flightToLocations = new JComboBox<>(flightLocations);
            adultField = new JComboBox<>(adultArray);
            childrenField = new JComboBox<>(numberArray);
            infantField = new JComboBox<>(numberArray);
            textMessage = new JLabel();
            phoneMessage = new JLabel();

            frame.setTitle("Booking");
            frame.add(nameLabel);
            frame.add(phoneLabel);
            frame.add(adultLabel);
            frame.add(childrenLabel);
            frame.add(infantLabel);
            frame.add(fromLabel);
            frame.add(toLabel);
            frame.add(nameText);
            frame.add(phoneText);
            frame.add(adultField);
            frame.add(childrenField);
            frame.add(infantField);
            frame.add(flightFromLocations);
            frame.add(flightToLocations);
            frame.add(bookingButton);
            frame.add(backButton);
            frame.add(textMessage);
            frame.add(phoneMessage);

            nameLabel.setBounds(180, 50, 100, 25);
            phoneLabel.setBounds(180, 100, 100, 25);
            adultLabel.setBounds(180, 150, 100, 25);
            childrenLabel.setBounds(180, 200, 100, 25);
            infantLabel.setBounds(180, 250, 100, 25);
            fromLabel.setBounds(180, 300, 100, 25);
            toLabel.setBounds(180, 350, 100, 25);

            nameText.setBounds(280, 50, 100, 25);
            phoneText.setBounds(280, 100, 100, 25);
            adultField.setBounds(280, 150, 100, 25);
            childrenField.setBounds(280, 200, 100, 25);
            infantField.setBounds(280, 250, 100, 25);
            flightFromLocations.setBounds(280, 300, 100, 25);
            flightToLocations.setBounds(280, 350, 100, 25);

            textMessage.setBounds(0, 450, 600, 50);
            textMessage.setFont(new Font("Courier", Font.PLAIN, 13));
            textMessage.setHorizontalAlignment(0);

            phoneMessage.setBounds(400, 100, 200, 25);
            phoneMessage.setForeground(Color.RED);
            phoneMessage.setHorizontalAlignment(JLabel.CENTER);

            backButton.setBounds(0, 0, 80, 30);
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(new BackButton(2));

            bookingButton.setBounds(210, 400, 150, 25);
            bookingButton.setBackground(Color.GREEN);
            bookingButton.setForeground(Color.WHITE);
            bookingButton.addActionListener(new BookingCode());
        }

        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();

        class BookingCode implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                textMessage.setText("");
                phoneMessage.setText("");
                if (nameText.getText().trim().equals("") || phoneText.getText().trim().equals("")
                        || Objects.equals(flightFromLocations.getSelectedItem(), "--Select--") ||
                        Objects.equals(flightToLocations.getSelectedItem(), "--Select--")) {
                    textMessage.setText("Please Fill all the fields");
                } else if (String.valueOf(flightFromLocations.getSelectedItem()).equals(String.valueOf(flightToLocations.getSelectedItem()))) {
                    textMessage.setText("From and To Addresses cannot be same");
                } else if (!mobileNumberValidator(phoneText.getText())) {
                    phoneMessage.setText("Enter a Valid Phone Number");
                } else {
                    try {
                        i = 0;
                        frame.getContentPane().removeAll();
                        frame.repaint();

                        frame.add(button1);
                        frame.add(button2);
                        frame.add(button3);
                        frame.add(backButton);
                        frame.add(textMessage);

                        backButton.setBounds(0, 0, 80, 30);
                        backButton.setBackground(Color.BLACK);
                        backButton.setForeground(Color.WHITE);
                        backButton.addActionListener(new BackButton(2));

                        textMessage.setBounds(0, 200, 600, 25);
                        textMessage.setHorizontalAlignment(JLabel.CENTER);
                        textMessage.setText("Select A Flight of your Choice:");

                        FileInputStream fin = new FileInputStream("P:\\Java_Project\\FlightNames.txt");
                        BufferedReader buffin = new BufferedReader(new InputStreamReader(fin));
                        boolean found = false;
                        String[] flightInfo;
                        String str;
                        while ((str = buffin.readLine()) != null) {
                            flightInfo = str.split(",");
                            if (String.valueOf(flightFromLocations.getSelectedItem()).equals(flightInfo[0]) && String.valueOf(flightToLocations.getSelectedItem()).equals(flightInfo[1])) {
                                found = true;
                                if (i == 1) {
                                    button1.setBounds(50, 235, 500, 25);
                                    button1.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + ",Cost: " + flightInfo[2] + ",Duration: " + flightInfo[3]);
                                } else if (i == 2) {
                                    button2.setBounds(50, 275, 500, 25);
                                    button2.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + ",Cost: " + flightInfo[2] + ",Duration: " + flightInfo[3]);
                                } else {
                                    button3.setBounds(50, 315, 500, 25);
                                    button3.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + ",Cost: " + flightInfo[2] + ",Duration: " + flightInfo[3]);
                                }
                                i++;
                            }
                        }
                        if (!found) {
                            textMessage.setFont(new Font("Courier", Font.PLAIN, 14));
                            textMessage.setText("Sorry! No Flights Available");
                        } else {
                            button1.addActionListener(new AfterSelection(1));
                            button2.addActionListener(new AfterSelection(2));
                            button3.addActionListener(new AfterSelection(3));
                        }
                    } catch (IOException exception) {
                        textMessage.setText("Sorry! Some Error occurred");
                    }
                }
            }
        }

        float finalCost, duration;

        class AfterSelection implements ActionListener {
            int num;
            String[] splittedText;

            AfterSelection(int num) {
                this.num = num;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (num == 1) {
                        splittedText = button1.getText().split(",");
                    } else if (num == 2) {
                        splittedText = button2.getText().split(",");
                    } else {
                        splittedText = button3.getText().split(",");
                    }

                    int adults = Integer.parseInt(String.valueOf(adultField.getSelectedItem()));
                    int children = Integer.parseInt(String.valueOf(childrenField.getSelectedItem()));
                    int infants = Integer.parseInt(String.valueOf(infantField.getSelectedItem()));
                    float cost = Float.parseFloat(splittedText[2].replace("Cost: ", ""));
                    duration = Float.parseFloat((splittedText[3].replace("Duration: ", "")).replace("hrs", ""));
                    finalCost = (float) (((cost * adults) + (0.2 * cost * children) + (0.9 * cost * infants)));
                    String[] fromTo = splittedText[1].split("->");

                    // Calculating and storing reference ID
                    FileInputStream refIDin = new FileInputStream("P:\\Java_Project\\ReferenceID.txt");
                    BufferedReader refIDbr = new BufferedReader(new InputStreamReader(refIDin));
                    refID = Integer.parseInt(refIDbr.readLine());
                    refIDbr.close();
                    FileOutputStream refIDout = new FileOutputStream("P:\\Java_Project\\ReferenceID.txt");
                    BufferedWriter refIDbw = new BufferedWriter(new OutputStreamWriter(refIDout));
                    refIDbw.write(String.valueOf(refID + 1));
                    refIDbw.flush();
                    refIDbw.close();

                    // Storing the Data in File in csv format
                    File f = new File("P:\\Java_Project\\CustomersDataBase.txt");
                    BufferedWriter bufOut = new BufferedWriter(new FileWriter(f, true));
                    bufOut.write(nameText.getText().trim() + "," + phoneText.getText().trim() + "," + adultField.getSelectedItem() + "," +
                            childrenField.getSelectedItem() + "," + infantField.getSelectedItem() + "," + flightFromLocations.getSelectedItem() + "," +
                            flightToLocations.getSelectedItem() + "," + date + "," + refID + "," + finalCost + "," + duration + "," + splittedText[0] + "\n");
                    bufOut.flush();
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    frame.add(textMessage);
                    frame.add(backButton);

                    backButton.setBounds(0, 0, 80, 30);
                    backButton.setBackground(Color.BLACK);
                    backButton.setForeground(Color.WHITE);
                    backButton.addActionListener(new BackButton(2));

                    textMessage.setBounds(0, 100, 600, 200);
                    textMessage.setHorizontalAlignment(JLabel.CENTER);
                    textMessage.setVerticalAlignment(JLabel.CENTER);
                    textMessage.setForeground(Color.GREEN);
                    textMessage.setFont(new Font("Courier", Font.BOLD, 30));
                    textMessage.setText("Booked a Ticket Successfully");

                    JButton printButton = new JButton("Print Ticket");
                    frame.add(printButton);
                    printButton.setBounds(250, 500, 100, 25);
                    printButton.setBackground(Color.DARK_GRAY);
                    printButton.setForeground(Color.WHITE);
                    printButton.addActionListener(new PrintTicketInBookingClass(splittedText[0], fromTo[0], fromTo[1], nameText.getText().trim(),
                            phoneText.getText().trim(), adults, children, infants, finalCost, duration));
                } catch (IOException exception) {
                    textMessage.setText("Sorry, Some Error Occurred, Couldn't Book ticket. Sorry for the inconvenience");
                }
            }
        }

        class PrintTicketInBookingClass implements ActionListener {
            String flightName, name, phn, from, to;
            int adults, children, infants;
            float cost, duration;

            PrintTicketInBookingClass(String flightName, String from, String to, String name, String phn, int adults, int children, int infants, float cost, float duration) {
                this.flightName = flightName;
                this.from = from;
                this.to = to;
                this.name = name;
                this.phn = phn;
                this.adults = adults;
                this.children = children;
                this.infants = infants;
                this.cost = cost;
                this.duration = duration;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                createPDF(flightName, from, to, name, phn, adults, children, infants, cost, duration);
            }
        }
    }

    JLabel label1, label2, label3;

    class Enquiry implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            frame.repaint();

            JButton enquireButton = new JButton("Enquire");
            JButton bookButton = new JButton("Book Ticket");
            fromLabel = new JLabel("From:");
            toLabel = new JLabel("To:");
            msg = new JLabel();
            backButton = new JButton("Back");
            flightFromLocations = new JComboBox<>(flightLocations);
            flightToLocations = new JComboBox<>(flightLocations);
            frame.setTitle("Enquiry");
            label1 = new JLabel();
            label2 = new JLabel();
            label3 = new JLabel();

            frame.add(fromLabel);
            frame.add(toLabel);
            frame.add(flightFromLocations);
            frame.add(flightToLocations);
            frame.add(enquireButton);
            frame.add(backButton);
            frame.add(msg);
            frame.add(label1);
            frame.add(label2);
            frame.add(label3);

            fromLabel.setBounds(200, 50, 100, 25);
            flightFromLocations.setBounds(300, 50, 100, 25);

            toLabel.setBounds(200, 100, 100, 25);
            flightToLocations.setBounds(300, 100, 100, 25);

            backButton.setBounds(0, 0, 80, 30);
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(new BackButton(2));

            label1.setBounds(0, 250, 600, 100);
            label1.setHorizontalAlignment(0);
            label1.setFont(new Font("Courier", Font.PLAIN, 15));
            label2.setBounds(0, 270, 600, 100);
            label2.setHorizontalAlignment(0);
            label2.setFont(new Font("Courier", Font.PLAIN, 15));
            label3.setBounds(0, 290, 600, 100);
            label3.setHorizontalAlignment(0);
            label3.setFont(new Font("Courier", Font.PLAIN, 15));

            bookButton.setBounds(150, 320, 400, 100);
            bookButton.addActionListener(new BookTicket());

            msg.setBounds(0, 0, 600, 600);
            msg.setHorizontalAlignment(0);
            msg.setVerticalAlignment(0);

            enquireButton.setBounds(250, 150, 100, 25);
            enquireButton.setBackground(Color.DARK_GRAY);
            enquireButton.setForeground(Color.WHITE);
            enquireButton.addActionListener(new EnquiryCode());
        }

        class EnquiryCode implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                i = 0;
                label1.setText("");
                label2.setText("");
                label3.setText("");
                msg.setText("");
                if (Objects.equals(flightFromLocations.getSelectedItem(), "--Select--") || Objects.equals(flightToLocations.getSelectedItem(), "--Select--")) {
                    msg.setText("Please Complete All the fields");
                } else {
                    try {
                        FileInputStream fin = new FileInputStream("P:\\Java_Project\\FlightNames.txt");
                        BufferedReader buffin = new BufferedReader(new InputStreamReader(fin));
                        String str;
                        boolean found = false;
                        String[] flightInfo;
                        while ((str = buffin.readLine()) != null) {
                            flightInfo = str.split(",");
                            if (Objects.equals(flightFromLocations.getSelectedItem(), flightInfo[0]) && Objects.equals(flightToLocations.getSelectedItem(), flightInfo[1])) {
                                found = true;
                                if (i == 1) {
                                    label1.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + "   Cost: " + flightInfo[2] + "   Duration: " + flightInfo[3]);
                                } else if (i == 2) {
                                    label2.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + "   Cost: " + flightInfo[2] + "   Duration: " + flightInfo[3]);
                                } else {
                                    label3.setText(flightInfo[4] + "," + flightInfo[0] + "->" + flightInfo[1] + "   Cost: " + flightInfo[2] + "   Duration: " + flightInfo[3]);
                                }
                                i++;
                            }
                        }
                        if (!found) {
                            msg.setText("Sorry! Flights from " + flightFromLocations.getSelectedItem() + " to " +
                                    flightToLocations.getSelectedItem() + " not Available");
                        }
                    } catch (Exception exception) {
                        msg.setText("Error in reading a file");
                    }
                }
            }
        }
    }

    class CancelTicket implements ActionListener {
        JLabel msg = new JLabel();
        JButton cancelButton = new JButton("Cancel the Ticket");

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            frame.repaint();

            phoneLabel = new JLabel("Phone:");
            phoneText = new JTextField();
            refIDLabel = new JLabel("Reference ID:");
            refIDText = new JTextField();
            backButton = new JButton("Back");
            phoneMessage = new JLabel();

            frame.add(phoneLabel);
            frame.add(phoneText);
            frame.add(refIDLabel);
            frame.add(refIDText);
            frame.add(msg);
            frame.add(cancelButton);
            frame.add(backButton);
            frame.add(phoneMessage);

            phoneLabel.setBounds(200, 50, 100, 25);
            phoneText.setBounds(300, 50, 100, 25);
            phoneMessage.setBounds(400, 50, 200, 25);
            phoneMessage.setForeground(Color.RED);
            phoneMessage.setHorizontalAlignment(0);

            refIDLabel.setBounds(200, 100, 100, 25);
            refIDText.setBounds(300, 100, 100, 25);

            msg.setBounds(0, 250, 600, 50);
            msg.setHorizontalAlignment(JLabel.CENTER);

            backButton.setBounds(0, 0, 80, 30);
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(new BackButton(2));

            cancelButton.setBounds(230, 150, 150, 25);
            cancelButton.setBackground(Color.RED);
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(new CancelCode());
        }

        class CancelCode implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                msg.setText("");
                phoneMessage.setText("");
                if (phoneText.getText().equals("") || refIDText.getText().equals("")) {
                    msg.setText("Please fill all the fields");
                } else if (!mobileNumberValidator(phoneText.getText())) {
                    phoneMessage.setText("Enter a Valid Phone Number");
                } else {
                    try {
                        phoneMessage.setText("");
                        File inputFile = new File("P:\\Java_Project\\CustomersDataBase.txt");
                        FileInputStream fin = new FileInputStream(inputFile);
                        File tempFile = new File("P:\\Java_Project\\temporary.tmp");
                        FileOutputStream fout = new FileOutputStream(tempFile);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fin));
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));
                        String str;
                        String[] info;
                        while ((str = br.readLine()) != null) {
                            info = str.split(",");
                            if (!phoneText.getText().trim().equals(info[1]) && !refIDText.getText().trim().equals(info[8])) {
                                bw.write(str);
                                bw.newLine();
                            } else {
                                found = true;
                            }
                        }
                        bw.flush();
                        bw.close();
                        br.close();

                        inputFile.delete();
                        boolean ren = tempFile.renameTo(inputFile);

                        if (found) {
                            msg.setText("Cancelled Your Ticket Successfully");
                        } else {
                            msg.setText("No Passenger Available with given Credentials. Please check the details and enter again");
                        }
                    } catch (Exception ex) {
                        msg.setText("Sorry Some Error Occurred");
                    }
                }
            }
        }
    }

    void createPDF(String flightName, String from, String to, String name, String phn, int adults, int children, int infants, float cost, float duration) {
        try {
            Document document = new Document();
            document.setPageSize(new com.itextpdf.text.Rectangle(1000, 300));
            PdfWriter.getInstance(document, new FileOutputStream("P:\\Java_Project\\Tickets\\" + refID + "_Ticket.pdf"));

            //open the document
            document.open();

            //creating table
            float[] columnWidths = {5f, 5f, 5f, 7f, 6f, 5f, 5f, 5f, 5f, 5f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.addCell(new PdfPCell(new Paragraph("Flight Name")));
            table.addCell(new PdfPCell(new Paragraph("From")));
            table.addCell(new PdfPCell(new Paragraph("To")));
            table.addCell(new PdfPCell(new Paragraph("Passenger Name")));
            table.addCell(new PdfPCell(new Paragraph("Phone No.")));
            table.addCell(new PdfPCell(new Paragraph("Adults")));
            table.addCell(new PdfPCell(new Paragraph("Children")));
            table.addCell(new PdfPCell(new Paragraph("Infants")));
            table.addCell(new PdfPCell(new Paragraph("Cost")));
            table.addCell(new PdfPCell(new Paragraph("Duration")));

            table.addCell(new PdfPCell(new Paragraph(flightName)));
            table.addCell(new PdfPCell(new Paragraph(from)));
            table.addCell(new PdfPCell(new Paragraph(to)));
            table.addCell(new PdfPCell(new Paragraph(name)));
            table.addCell(new PdfPCell(new Paragraph(phn)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(adults))));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(children))));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(infants))));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(cost))));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(duration))));

            document.add(table);
            document.close();

            Desktop.getDesktop().open(new File("P:\\Java_Project\\Tickets\\" + refID + "_Ticket.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PrintTicket implements ActionListener {
        JButton printingButton = new JButton("Print Ticket");

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            frame.repaint();

            phoneLabel = new JLabel("Phone:");
            phoneText = new JTextField();
            refIDLabel = new JLabel("Reference ID:");
            refIDText = new JTextField();
            backButton = new JButton("Back");
            phoneMessage = new JLabel();

            frame.add(phoneLabel);
            frame.add(phoneText);
            frame.add(refIDLabel);
            frame.add(refIDText);
            frame.add(msg);
            frame.add(printingButton);
            frame.add(backButton);
            frame.add(phoneMessage);

            phoneLabel.setBounds(200, 50, 100, 25);
            phoneText.setBounds(300, 50, 100, 25);
            phoneMessage.setBounds(400, 50, 200, 25);
            phoneMessage.setForeground(Color.RED);
            phoneMessage.setHorizontalAlignment(0);

            refIDLabel.setBounds(200, 100, 100, 25);
            refIDText.setBounds(300, 100, 100, 25);

            msg.setBounds(0, 350, 600, 25);
            msg.setHorizontalAlignment(0);
            msg.setVerticalAlignment(0);

            backButton.setBounds(0, 0, 80, 30);
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(new BackButton(2));

            printingButton.setBounds(250, 150, 100, 25);
            printingButton.setBackground(Color.DARK_GRAY);
            printingButton.setForeground(Color.WHITE);
            printingButton.addActionListener(new PrintingCode());
        }

        class PrintingCode implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mobileNumberValidator(phoneText.getText())) {
                    phoneMessage.setText("Enter a Valid Phone Number");
                } else {
                    phoneMessage.setText("");
                    File db = new File("P:\\Java_Project\\CustomersDataBase.txt");
                    String str;
                    String[] details;
                    int refIDFound = 0, phoneFound = 0;
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(db));
                        while ((str = reader.readLine()) != null) {
                            details = str.split(",");
                            if (phoneText.getText().equals(details[1])) {
                                phoneFound = 1;
                                if (refIDText.getText().equals(details[8])) {
                                    createPDF(details[11], details[5], details[6], details[0], details[1], Integer.parseInt(details[2]),
                                            Integer.parseInt(details[3]), Integer.parseInt(details[4]), Float.parseFloat(details[9]),
                                            Float.parseFloat(details[10]));
                                    refIDFound = 1;
                                    break;
                                }
                            }
                        }
                        reader.close();
                        if (phoneFound == 0) {
                            msg.setText("Phone Number not found");
                        } else if (refIDFound == 0) {
                            msg.setText("Reference ID not Found");
                        } else if (phoneFound == 1 && refIDFound == 1) {
                            msg.setText("Your ticket is stored at \"P:\\Java_Project\\Tickets\\(Your RefID)_Ticket.pdf\"");
                        }
                    } catch (Exception exception) {
                        msg.setText("Sorry, Some Error Occurred. Couldn't print your Ticket.");
                    }
                }
            }
        }
    }
}
