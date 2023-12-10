import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class Alora implements ActionListener {
    private JFrame frame; //Main frame
    //Content, home, editing page, viewEntry, editEntry, and current panels
    private JPanel mainPanel, home, addEntry, viewEntry, editEntry, currentPanel;
    private CardLayout cl; //Card layout
    private JScrollPane scrollPane; //Scroll pane

    //Month buttons
    JButton january, february, march, april, may, june, july, august, september,
            october, november, december;
    JButton[] monthButtons = {january, february, march, april, may, june, july,
            august, september, october, november, december};

    //Month panels
    JPanel janPanel, febPanel, marPanel, aprPanel, mayPanel, junePanel, julyPanel,
            augPanel, sepPanel, octPanel, novPanel, decPanel;
    JPanel[] monthPanels = {janPanel, febPanel, marPanel, aprPanel, mayPanel,
            junePanel, julyPanel, augPanel, sepPanel, octPanel, novPanel, decPanel};

    //Month names
    String[] monthNames = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    JTextArea textArea, editTextArea; //Text area for dream entry
    JButton save, cancel, updateEntry; //Button to save, update and cancel entries

    //Array list named entries to hold journal entries
    ArrayList<journalEntries> entries = new ArrayList<journalEntries>();
    JList<journalEntries> entryList; //List to display entries
    private DefaultListModel<journalEntries> listModel; //List model for entries

    JLabel viewText, mainHeader; //Label for view button and title of main page
    JButton back; //Button to go back

    JTextField titleField, editTitleField; //Text field for the title in adding entry panel and editing entry panel

    Alora() {
        frame = new JFrame("Alora"); //Title of frame
        frame.setSize(600, 500); //Size of frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Allow user to exit frame

        cl = new CardLayout(); //Card layout to switch between panels

        listModel = new DefaultListModel<>();
        entryList = new JList<>(listModel);
        entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Only one entry can be selected at a time

        //*************MAIN PANEL*************
        mainPanel = new JPanel(cl); //Main panel to display all panels using card layout
        createPanels(); //Method to display month panels
        for (int i = 0; i < monthPanels.length; i++) {
            mainPanel.add(monthPanels[i], monthNames[i]);
        }

        //*************HOME PANEL*************
        home = new JPanel(new BorderLayout()); //Home panel

        //Adding title to home page
        mainHeader = new JLabel("✮⋆₊˚⊹Dream Journal˙₊˚✮⊹");
        mainHeader.setFont(new Font("Chalkduster", Font.BOLD, 24));
        home.add(mainHeader, BorderLayout.PAGE_START); //Add title label to the top

        createMonths(); //Method to display month buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 20, 20)); //3 rows, 4 columns grid layout
        for (int i = 0; i < monthButtons.length; i++) {
            buttonPanel.add(monthButtons[i]); //Add buttons to buttonPanel
        }
        home.add(buttonPanel, BorderLayout.CENTER); //Add buttonPanel to the center

        //*************ADDING ENTRY PANEL*************
        addEntry = new JPanel(); //Adding page panel

        //Adding title field for the dream entry
        JLabel titleLabelField = new JLabel("Title: ");
        titleField = new JTextField(20); //Text field for the title
        addEntry.add(titleLabelField);
        addEntry.add(titleField);

        textArea = new JTextArea(20, 30); //Text area for dream entry

        save = new JButton("Save"); //Button to save new dream entry
        save.addActionListener(this); //Action listener for save button

        cancel = new JButton("Cancel"); //Button to cancel new dream entry
        cancel.addActionListener(this); //Action listener for cancel button

        //Add all components to the edit entry page
        addEntry.add(textArea);
        addEntry.add(save);
        addEntry.add(cancel);

        //****EDITING ENTRY PANEL*************
        editEntry = new JPanel(); //Editing page panel

        //Adding title field for the dream entry
        JLabel editTitleLabelField = new JLabel("Title: ");
        editTitleField = new JTextField(20); //Text field for the title
        editEntry.add(editTitleLabelField);
        editEntry.add(editTitleField);

        editTextArea = new JTextArea(20, 30); //Text area for editing dream entry

        updateEntry = new JButton("Update"); //Button to update dream entry
        updateEntry.addActionListener(this); //Action listener for update button

        cancel = new JButton("Cancel"); //Button to cancel editing dream entry
        cancel.addActionListener(this); //Action listener for cancel button

        //Add components to the edit entry page
        editEntry.add(editTextArea);
        editEntry.add(updateEntry);
        editEntry.add(cancel);

        //Add the edit entry panel to the main panel
        mainPanel.add(editEntry, "editEntry");

        //*************VIEWING PAGE PANEL*************
        viewEntry = new JPanel(); //Viewing page panel

        viewText = new JLabel(); //Label to display entry and date
        viewEntry.add(viewText); //Add label to viewing page panel

        back = new JButton("Back"); //Button to go back to home page
        back.addActionListener(this); //Action listener for back button
        viewEntry.add(back); //Add back button to viewing page panel

        //Add home, addEntry, and viewEntry panels to the main panel
        mainPanel.add(home, "home");
        mainPanel.add(addEntry, "addEntry");
        mainPanel.add(viewEntry, "viewEntry");

        cl.show(mainPanel, "home"); //Show the home panel as the first visible panel

        currentPanel = home; //Set the current panel to the home panel
        frame.getContentPane().add(mainPanel); //Add the main panel to the frame
        frame.setVisible(true); //Set the frame to be visible
        frame.setResizable(false); //Set the frame to not be resizable
    }

    //Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Alora());
    }

    //Method to display month buttons
    private void createMonths() {
        home.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); //Set FlowLayout with center alignment

        for (int i = 0; i < monthButtons.length; i++) {
            monthButtons[i] = new JButton(monthNames[i]);
            monthButtons[i].addActionListener(this);
            home.add(monthButtons[i]); //Add buttons to the home panel
        }
    }

    //Method to display month panels
    private void createPanels() {
        for (int i = 0; i < monthPanels.length; i++) {
            monthPanels[i] = new JPanel(new BorderLayout()); //BorderLayout for the month panel

            //Create a panel to hold the back button and the month label using FlowLayout
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            //Create the back button add it to the top panel
            topPanel.add(createHomeButton());

            //Create the month label and add to top panel
            JLabel monthLabel = new JLabel(monthNames[i]);
            Font labelFont = monthLabel.getFont();
            monthLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 18)); //Change the font size to 18 (you can adjust the size as needed)

            // Add the month label to the top panel
            topPanel.add(monthLabel);


            //Add the top panel to the NORTH position
            monthPanels[i].add(topPanel, BorderLayout.NORTH);

            //Create a panel to hold buttons using FlowLayout
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            //Add buttons to the button panel
            buttonPanel.add(createAddEntry());
            buttonPanel.add(createEditEntry());
            buttonPanel.add(createDeleteEntry());
            buttonPanel.add(createView());

            //Add the button panel to the month panel
            monthPanels[i].add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    //Action events for buttons
    public void actionPerformed(ActionEvent e) {
        //****SAVE BUTTON****
        if (e.getSource() == save) {
            Date tempDate = new Date(); //Get the current date
            String tempTitle = titleField.getText(); //Get the title entered by the user
            String tempText = textArea.getText(); //Get the text entered in the text area
            JPanel tempPanel = currentPanel; //Store the current panel in a temporary variable
        
            //Create a new journal entry with the entered title, text, current date, and current panel
            journalEntries tempEntry = new journalEntries(tempTitle, tempText, tempDate, tempPanel);
            entries.add(tempEntry); //Add the new entry to the list of entries
            cl.show(mainPanel, getPrevPane()); //Go to the previous panel
            update(); //Update the displayed content
        
            titleField.setText(""); //Clear the titleField after saving an entry
        }
        //****UPDATE BUTTON****
        else if (e.getSource() == updateEntry) {
            journalEntries selectedEntry = entryList.getSelectedValue();
            if (selectedEntry != null) {
                String newText = editTextArea.getText(); //Get updated text from the text area
                selectedEntry.setEntry(newText); //Update the entry text
                
                //Get the new title from the editTitleField
                String newTitle = editTitleField.getText();
                selectedEntry.setTitle(newTitle); //Update the entry title
                selectedEntry.updateHeader(); //update header

                //Update the title in the month panel text
                //JLabel monthLabel = (JLabel) currentPanel.getComponent(1); //Assuming the title label is at index 1 in the month panel
                //monthLabel.setText(newTitle);

                //Switch back to the previous panel using CardLayout
                cl.show(mainPanel, getPrevPane());
                update(); //Update the displayed content

                //Clear the editTitleField and editTextArea after saving an entry
                editTitleField.setText("");
                editTextArea.setText("");
            } else {
                //Handle when no entry is selected
                JOptionPane.showMessageDialog(frame, "Please select an entry to update.");
            }
        }
        //****CANCEL BUTTON****
        else if (e.getSource() == cancel) {
            cl.show(mainPanel, getPrevPane()); //Switch back to the previous panel using CardLayout
            update(); //Update the displayed content
        }
        //****BACK BUTTON****
        else if (e.getSource() == back) {
            cl.show(mainPanel, getPrevPane()); //Switch back to the previous panel using CardLayout
            update(); //Update the displayed content
        }
        //****MONTHS BUTTON****
        for (int i = 0; i < monthButtons.length; i++) {
            if (e.getSource() == monthButtons[i]) {
                cl.show(mainPanel, monthNames[i]); //Switch to corresponding month panel using CardLayout
                currentPanel = monthPanels[i]; //Update the current panel to the selected month panel
                update(); //Update the displayed content
            }
        }
    }

    //Button to allow user to return to the homepage
    private JButton createHomeButton() {
        JButton homeButton = new JButton("Back");
        homeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            cl.show(mainPanel, "home");
            currentPanel = home;
        }
        });
        return homeButton;
    }

    //Button to add dream entries
    private JButton createAddEntry() {
        JButton addEntry = new JButton("Add Entry");
        addEntry.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
            cl.show(mainPanel, "addEntry");
        }
        });
        return addEntry;
    }

    //Button to edit dream entries
    private JButton createEditEntry() {
        JButton editEntryButton = new JButton("Edit Entry");
        editEntryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                journalEntries selectedEntry = entryList.getSelectedValue();
                if (selectedEntry != null) {
                    //Set the title in editTitleField
                    editTitleField.setText(selectedEntry.getTitle());
                    editTextArea.setText(selectedEntry.getEntry()); //Set the text in editTextArea
                    cl.show(mainPanel, "editEntry"); //Show the editEntry panel
                } else {
                    //Handle when no entry is selected
                    JOptionPane.showMessageDialog(frame, "Please select an entry to edit.");
                }
            }
        });
        return editEntryButton;
    }

    //Button to delete dream entries
    private JButton createDeleteEntry() {
        JButton deleteEntry = new JButton("Delete Entry");
        deleteEntry.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            journalEntries selectedEntry = entryList.getSelectedValue();
            if (selectedEntry != null) {
            entries.remove(selectedEntry);
            cl.show(mainPanel, getPrevPane());
            update();
            } else {
            //Handle when no entry is selected
            JOptionPane.showMessageDialog(frame, "Please select an entry to delete.");
            }
        }
        });
        return deleteEntry;
    }

    //Button to view dream entries
private JButton createView() {
    JButton viewButton = new JButton("View");
    viewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            journalEntries selectedEntry = entryList.getSelectedValue();
            if (selectedEntry != null) {
                String title = entryList.getSelectedValue().title; //Get the title of the selected entry
                String text = entryList.getSelectedValue().getEntry(); //Get the entry text
                Date date = entryList.getSelectedValue().getDate(); //Get the date

                //Display title, entry text, and date in the viewText label
                viewText.setText("<html><p>" + date + "</p><br><h3>" + title + "</h3><br><p>" + text + "</p></html>");

                cl.show(mainPanel, "viewEntry"); //Switch to the viewEntry panel
            } else {
                //Handle when no entry is selected
                JOptionPane.showMessageDialog(frame, "Please select an entry to view.");
            }
        }
    });
    return viewButton;
}


    //Method to update the displayed content
    private void update() {
        listModel.clear();
        for (int i = 0; i < entries.size(); i++) {
        if (entries.get(i).getMonth() == currentPanel) {
            listModel.addElement(entries.get(i));
        }
        }
        currentPanel.add(entryList);
    }

    //Method to get previous pane
    private String getPrevPane() {
        for (int i = 0; i < monthPanels.length; i++) {
        if (currentPanel == monthPanels[i]) {
            return monthNames[i];
        }
        }
        return "home";
    }

    }

class journalEntries {
    String title; //Title of the dream entry
    String entry; //Dream entry text
    Date date; //Date of the dream entry
    JPanel month; //Month panel associated with the entry
    String header; //Header for the entry

    //Initialize dream entries with title, text, date, and month panel
    journalEntries(String title, String entry, Date date, JPanel month) {
        this.title = title; //Set entry title
        this.entry = entry; //Set entry text
        this.date = date; //Set date of the entry
        this.month = month; //Set month panel associated with the entry

        //Generate a header for entry based on the title and text length
        if (title.length() + entry.length() < 10) {
            this.header = title + ": " + entry + "...  " + date;
        } else {
            this.header = title + ": " + entry.substring(0, Math.min(9, entry.length())) + "...  " + date;
        }
    }

    //Getter and Setter method to set the title of the entry
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    //Getter method to get the entry text
    public String getEntry() {
        return entry;
    }

    //Setter method to set the entry text
    public void setEntry(String entry) {
        this.entry = entry;
    }

    //Getter method to get the date of the entry
    public Date getDate() {
        return date;
    }

    //Setter method to get the date of the entry
    public void setDate(Date date) {
        this.date = date;
    }

    //Getter method to get the month panel associated with the entry
    public JPanel getMonth() {
        return month;
    }

    //Setter method to set the month panel associated with the entry
    public void setMonth(JPanel month) {
        this.month = month;
    }

    //Getter method to get the header of the entry
    public String getHeader() {
        return header;
    }

    //Setter method to set the header of the entry
    public void setHeader(String header) {
        this.header = header;
    }

    //Method to display the entry
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MM/dd/yy h:mm a");
        String formattedDate = dateFormat.format(this.date);
        
        //Modify the return statement to include the formatted date
        return this.title + ": " + this.entry.substring(0, Math.min(9, this.entry.length())) +
                "...  " + formattedDate;
    }

    public void updateHeader(){
        this.header = title + ": " + entry.substring(0, Math.min(9, entry.length())) + "...  " + date;
    }
}