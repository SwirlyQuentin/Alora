import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class Alora implements ActionListener {
  private JFrame frame; // Main frame
  // Content, home, editing page, viewEntry, and current panels
  private JPanel mainPanel, home, addEntry, viewEntry, currentPanel;
  private CardLayout cl; // Card layout
  private JScrollPane scrollPane; // Scroll pane

  // Month buttons
  JButton january, february, march, april, may, june, july, august, september,
      october, november, december;
  JButton[] monthButtons = { january, february, march, april, may, june, july,
      august, september, october, november, december };

  // Month panels
  JPanel janPanel, febPanel, marPanel, aprPanel, mayPanel, junePanel, julyPanel,
      augPanel, sepPanel, octPanel, novPanel, decPanel;
  JPanel[] monthPanels = { janPanel, febPanel, marPanel, aprPanel, mayPanel,
      junePanel, julyPanel, augPanel, sepPanel, octPanel, novPanel, decPanel };

  // Month names
  String[] monthNames = { "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December" };

  JTextArea textArea; // Text area fordream entry
  JButton save, cancel; // Button to save and cancel entries

  // Array list named entries to hold journal entries
  ArrayList<journalEntries> entries = new ArrayList<journalEntries>();
  JList<journalEntries> entryList; // List to display entries
  private DefaultListModel<journalEntries> listModel; // List model for entries

  JLabel viewText; // Label for view button
  JButton back; // Button to go back

  Alora() {
    frame = new JFrame("Alora"); // Title of frame
    frame.setSize(600, 500); // Size of frame
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Allow user to exit frame

    cl = new CardLayout(); // Card layout to switch between panels

    listModel = new DefaultListModel<journalEntries>();
    entryList = new JList<journalEntries>(listModel);
    entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one entry can be selected at a time

    // *************MAIN PANEL*************
    mainPanel = new JPanel(cl); // Main panel to display all panels using card layout
    createPanels(); // Method to display month panels
    for (int i = 0; i < monthPanels.length; i++) {
      mainPanel.add(monthPanels[i], monthNames[i]);
    }

    // *************HOME PANEL*************
    home = new JPanel(new BorderLayout()); // Home panel

    // Adding title to home page
    JLabel titleLabel = new JLabel("✮⋆₊˚⊹Dream Journal˙₊˚✮⊹");
    titleLabel.setFont(new Font("Chalkduster", Font.BOLD, 24));
    home.add(titleLabel, BorderLayout.PAGE_START); // Add title label to the top

    createMonths(); // Method to display month buttons
    JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 20, 20)); // 3 rows, 4 columns grid layout
    for (int i = 0; i < monthButtons.length; i++) {
        buttonPanel.add(monthButtons[i]); // Add buttons to buttonPanel
    }
    home.add(buttonPanel, BorderLayout.CENTER); // Add buttonPanel to the center

    // *************ADDING ENTRY PANEL*************
    addEntry = new JPanel(); // Adding page panel

    textArea = new JTextArea(20, 30); // Text area for dream entry

    save = new JButton("Save"); // Button to save new dream entry
    save.addActionListener(this); // Action listener for save button

    cancel = new JButton("Cancel"); // Button to cancel new dream entry
    cancel.addActionListener(this); // Action listener for cancel button

    // Add all components to the edit entry page
    addEntry.add(textArea);
    addEntry.add(save);
    addEntry.add(cancel);

    // *************VIEWING PAGE PANEL*************
    viewEntry = new JPanel(); // Viewing page panel

    viewText = new JLabel(); // Label to display entry and date
    viewEntry.add(viewText); // Add label to viewing page panel

    back = new JButton("Back"); // Button to go back to home page
    back.addActionListener(this); // Action listener for back button
    viewEntry.add(back); // Add back button to viewing page panel

    // Add home, addEntry, and viewEntry panels to the main panel
    mainPanel.add(home, "home");
    mainPanel.add(addEntry, "addEntry");
    mainPanel.add(viewEntry, "viewEntry");

    cl.show(mainPanel, "home"); // Show the home panel as the first visible panel

    currentPanel = home; // Set the current panel to the home panel
    frame.getContentPane().add(mainPanel); // Add the main panel to the frame
    frame.setVisible(true); // Set the frame to be visible
    frame.setResizable(false); // Set the frame to not be resizable
  }

  // Main method
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Alora();
      }
    });
  }

  // Method to display month buttons
private void createMonths() {
    home.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Set FlowLayout with center alignment

    for (int i = 0; i < monthButtons.length; i++) {
        monthButtons[i] = new JButton(monthNames[i]);
        monthButtons[i].addActionListener(this);
        home.add(monthButtons[i]); // Add buttons to the home panel
    }
}


  // Method to display month panels
  private void createPanels() {
    for (int i = 0; i < monthPanels.length; i++) {
      monthPanels[i] = new JPanel();
      monthPanels[i].add(createHomeButton());
      monthPanels[i].add(new JLabel(monthNames[i]));
      monthPanels[i].add(createAddEntry());
      monthPanels[i].add(createDeleteEntry());
      monthPanels[i].add(createView());
    }
  }

  // Action events for buttons
  public void actionPerformed(ActionEvent e) {
    // ****SAVE BUTTON****
    if (e.getSource() == save) {
      Date tempDate = new Date(); // Get the current date
      String tempText = textArea.getText(); // Get the text entered in the text area
      JPanel tempPanel = currentPanel; // Store the current panel in a temporary variable

      // Create a new journal entry with the entered text, current date, and current
      // panel
      journalEntries tempEntry = new journalEntries(tempText, tempDate, tempPanel);
      entries.add(tempEntry); // Add the new entry to the list of entries
      cl.show(mainPanel, getPrevPane()); // Go to previous panel
      update(); // Update the displayed content
    }
    // ****CANCEL BUTTON****
    else if (e.getSource() == cancel) {
      cl.show(mainPanel, getPrevPane()); // Switch back to the previous panel using CardLayout
      update(); // Update the displayed content
    }
    // ****BACK BUTTON****
    else if (e.getSource() == back) {
      cl.show(mainPanel, getPrevPane()); // Switch back to the previous panel using CardLayout
      update(); // Update the displayed content
    }
    // ****MONTHS BUTTON****
    for (int i = 0; i < monthButtons.length; i++) {
      if (e.getSource() == monthButtons[i]) {
        cl.show(mainPanel, monthNames[i]); // Switch to corresponding month panel using CardLayout
        currentPanel = monthPanels[i]; // Update the current panel to the selected month panel
        update(); // Update the displayed content
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
          // Handle when no entry is selected
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
          String text = entryList.getSelectedValue().getEntry();
          Date date = entryList.getSelectedValue().getDate();
          viewText.setText("<html><p>" + date + "</p> <br> <p>" + text + "</p> </html>");
          cl.show(mainPanel, "viewEntry");
        } else {
          // Handle when no entry is selected
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
  String entry; //Dream entry text
  Date date; //Date of the dream entry
  JPanel month; //Month panel associated with the entry
  String header; //Header for the entry

  //Initialize dream entries with text, date, month panel
  journalEntries(String entry, Date date, JPanel month) {
    this.entry = entry; //Set entry text
    this.date = date; //Set date of the entry
    this.month = month; //Set month panel associated with the entry

    ///Generate a header for entry based on the text length
    if (entry.length() < 10) {
      this.header = entry + "...  " + date;
    } else {
      this.header = entry.substring(0, 9) + "...  " + date;
    }
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
    return this.header;
  }

}