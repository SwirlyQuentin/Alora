import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    JButton save, cancelAdd, cancelEdit, updateEntry; //Button to save, update and cancel entries

    //Array list named entries to hold journal entries
    ArrayList<journalEntries> entries = new ArrayList<journalEntries>();
    JList<journalEntries> entryList; //List to display entries
    private DefaultListModel<journalEntries> listModel; //List model for entries

    JLabel viewText, mainHeader; //Label for view button and title of main page
    JButton back; //Button to go back

    JTextField titleField, editTitleField; //Text field for the title in adding entry panel and editing entry panel

    Color lightBlue = new Color(236, 242, 255);
    Color lightPurple = new Color(191, 172, 226);
    Color darkBlue = new Color(62, 84, 172);

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

        home.setBackground(lightBlue); //Background color

        //Adding title to home page
        mainHeader = new JLabel("⊹˚₊ Dream Journal ₊˚⊹");
        mainHeader.setFont(new Font("MS Gothic", Font.BOLD, 30));
        mainHeader.setForeground(darkBlue);
        home.add(mainHeader, BorderLayout.PAGE_START); //Add title label to the top

        createMonths(); //Method to display month buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 20, 20)); //3 rows, 4 columns grid layout
        buttonPanel.setBackground(lightBlue); //Set background
        for (int i = 0; i < monthButtons.length; i++) {
            buttonPanel.add(monthButtons[i]); //Add buttons to buttonPanel
        }

        home.add(buttonPanel, BorderLayout.CENTER); //Add buttonPanel to the center

        //*************ADDING ENTRY PANEL*************
        addEntry = new JPanel(new BorderLayout()); //Using BorderLayout for addEntry panel

        addEntry.setBackground(lightBlue); //Background color

        //Panel for title and text area in a separate flow layout
        JPanel titleTextAreaPanel = new JPanel(new FlowLayout());
        titleTextAreaPanel.setBackground(lightBlue);

        //Adding title field for the dream entry
        JLabel titleLabelField = new JLabel("Title: ");
        titleLabelField.setFont(new Font("MS Gothic", Font.BOLD, 20));
        titleField = new JTextField(20); //Text field for the title
        titleTextAreaPanel.add(titleLabelField);
        titleTextAreaPanel.add(titleField);

        textArea = new JTextArea(20, 30); //Text area for dream entry

        //Adding title and text area to titleTextAreaPanel
        titleTextAreaPanel.add(textArea);

        save = new JButton("Save"); //Button to save new dream entry
        save.setBackground(darkBlue);
        save.setForeground(lightBlue);
        save.setFont(new Font("Gabriola", Font.BOLD, 20));
        save.addActionListener(this); //Action listener for save button

        cancelAdd = new JButton("Cancel"); //Button to cancel new dream entry
        cancelAdd.setBackground(darkBlue);
        cancelAdd.setForeground(lightBlue);
        cancelAdd.setFont(new Font("Gabriola", Font.BOLD, 20));
        cancelAdd.addActionListener(this); //Action listener for cancel button

        //Buttons panel in a flow layout
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(lightBlue);
        buttonsPanel.add(save);
        buttonsPanel.add(cancelAdd);

        //Adding titleTextAreaPanel to the center and buttonsPanel to the north
        addEntry.add(titleTextAreaPanel, BorderLayout.CENTER);
        addEntry.add(buttonsPanel, BorderLayout.NORTH);


        //*************EDITING ENTRY PANEL*************
        editEntry = new JPanel(new BorderLayout()); //Using BorderLayout for editEntry panel

        editEntry.setBackground(lightBlue); //Background color

        //Panel for title and text area in a separate flow layout
        JPanel editTitleTextAreaPanel = new JPanel(new FlowLayout());
        editTitleTextAreaPanel.setBackground(lightBlue);

        //Adding title field for the dream entry
        JLabel editTitleLabelField = new JLabel("Title: ");
        editTitleLabelField.setFont(new Font("MS Gothic", Font.BOLD, 20));
        editTitleField = new JTextField(20); //Text field for the title
        editTitleTextAreaPanel.add(editTitleLabelField);
        editTitleTextAreaPanel.add(editTitleField);

        editTextArea = new JTextArea(20, 30); //Text area for editing dream entry

        //Adding title and text area to editTitleTextAreaPanel
        editTitleTextAreaPanel.add(editTextArea);

        updateEntry = new JButton("Update"); //Button to update dream entry
        updateEntry.setBackground(darkBlue);
        updateEntry.setForeground(lightBlue);
        updateEntry.setFont(new Font("Gabriola", Font.BOLD, 20));
        updateEntry.addActionListener(this); //Action listener for update button

        cancelEdit = new JButton("Cancel"); //Button to cancel editing dream entry
        cancelEdit.setBackground(darkBlue);
        cancelEdit.setForeground(lightBlue);
        cancelEdit.setFont(new Font("Gabriola", Font.BOLD, 20));
        cancelEdit.addActionListener(this); //Action listener for cancel button

        //Buttons panel in a flow layout
        JPanel editButtonsPanel = new JPanel(new FlowLayout());
        editButtonsPanel.setBackground(lightBlue);
        editButtonsPanel.add(updateEntry);
        editButtonsPanel.add(cancelEdit);

        //Adding editTitleTextAreaPanel to the center and editButtonsPanel to the north
        editEntry.add(editTitleTextAreaPanel, BorderLayout.CENTER);
        editEntry.add(editButtonsPanel, BorderLayout.NORTH);

        //Add the edit entry panel to the main panel
        mainPanel.add(editEntry, "editEntry");

        //*************VIEWING PAGE PANEL*************
        viewEntry = new JPanel(); //Viewing page panel

        viewEntry.setBackground(lightBlue); //Background color

        viewText = new JLabel(); //Label to display entry and date
        viewText.setFont(new Font("MS Gothic", Font.PLAIN, 15));
        viewEntry.setFont(new Font("MS Gothic", Font.PLAIN, 20));
        viewEntry.add(viewText); //Add label to viewing page panel

        back = new JButton("Back"); //Button to go back to home page
        back.setBackground(darkBlue);
        back.setForeground(lightBlue);
        back.addActionListener(this); //Action listener for back button
        back.setFont(new Font("Gabriola", Font.BOLD, 20));
        viewEntry.add(back); //Add back button to viewing page panel

        //Add home, addEntry, and viewEntry panels to the main panel
        mainPanel.add(home, "home");
        mainPanel.add(addEntry, "addEntry");
        mainPanel.add(viewEntry, "viewEntry");

        //*************ETC
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
            monthButtons[i].setBackground(darkBlue);
            monthButtons[i].setForeground(lightBlue);
            monthButtons[i].setFont(new Font("Gabriola", Font.BOLD, 20));
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
            topPanel.setBackground(lightPurple); //Background color

            //Create the back button add it to the top panel
            topPanel.add(createHomeButton());

            //Create the month label and add to top panel
            JLabel monthLabel = new JLabel(monthNames[i]);
            monthLabel.setFont(new Font("MS Gothic", Font.BOLD, 25));
            
            topPanel.add(monthLabel); //Add the month label to the top panel

            //Add the top panel to the NORTH position
            monthPanels[i].add(topPanel, BorderLayout.NORTH);

            //Create a panel to hold buttons using FlowLayout
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(lightPurple); //Set background

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
        //****CANCEL BUTTON for adding entry****
        else if (e.getSource() == cancelAdd) {
            titleField.setText(""); //Clear the text in titleField
            textArea.setText(""); //Clear the text in the textArea
            cl.show(mainPanel, getPrevPane()); //Switch back to the previous panel using CardLayout
            update(); //Update the displayed content
        }
        //****CANCEL BUTTON for editing entry****
        else if (e.getSource() == cancelEdit) {
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
        homeButton.setBackground(darkBlue);
        homeButton.setForeground(lightBlue);
        homeButton.setFont(new Font("Gabriola", Font.BOLD, 20));
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
        addEntry.setBackground(darkBlue);
        addEntry.setForeground(lightBlue);
        addEntry.setFont(new Font("Gabriola", Font.BOLD, 20));
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
        editEntryButton.setBackground(darkBlue);
        editEntryButton.setForeground(lightBlue);
        editEntryButton.setFont(new Font("Gabriola", Font.BOLD, 20));
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
        deleteEntry.setBackground(darkBlue);
        deleteEntry.setForeground(lightBlue);
        deleteEntry.setFont(new Font("Gabriola", Font.BOLD, 20));
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
        viewButton.setBackground(darkBlue);
        viewButton.setForeground(lightBlue);
        viewButton.setFont(new Font("Gabriola", Font.BOLD, 20));
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
    
        //Edit dream entries
        entryList.setCellRenderer(new editDreamEntries());
    }

    //Method to edit dream entries
    class editDreamEntries extends DefaultListCellRenderer {
        private final int MAX_ENTRY_LENGTH = 30;

        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel entryLabel = new JLabel();
            JLabel dateLabel = new JLabel();

            if (value instanceof journalEntries) {
                journalEntries entry = (journalEntries) value;
    
                //Limit the length of the entry text and add '...' if it exceeds the maximum length
                String shortenEntry = entry.getEntry().length() > MAX_ENTRY_LENGTH ?
                        entry.getEntry().substring(0, MAX_ENTRY_LENGTH) + "..." :
                        entry.getEntry();
    
                entryLabel.setText("<html><div style='width: 200px;'><b>" + entry.getTitle() + "</b>: " + shortenEntry + "</div></html>");
                dateLabel.setText(formatDate(entry.getDate()));
    
                entryLabel.setFont(new Font("MS Gothic", Font.PLAIN, 20));
                dateLabel.setFont(new Font("MS Gothic", Font.BOLD, 15));

                panel.add(entryLabel, BorderLayout.WEST);
                panel.add(dateLabel, BorderLayout.EAST);
            }
    
            if (isSelected) {
                panel.setBackground(Color.LIGHT_GRAY); //Background color when selected
            } else {
                panel.setBackground(list.getBackground());
            }
    
            return panel;
        }
    
        //Helper method to format the date
        private String formatDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("E MM/dd/yy h:mm a");
            return dateFormat.format(date);
        }
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