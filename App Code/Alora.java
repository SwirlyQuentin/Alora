import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;


public class Alora implements ActionListener{

    private JFrame frame;

    private JPanel content;
    CardLayout cl;

    private JScrollPane scrollPane;

    JButton january;
    JButton febuary;
    JButton march;
    JButton april;
    JButton may;
    JButton june;
    JButton july;
    JButton august;
    JButton september;
    JButton october;
    JButton november;
    JButton december;

    JButton[] monthButtons = {january, febuary, march, april, may, june, july, august, september, october, november, december};
    String[] monthNames = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    JPanel home;

    JPanel janPanel;
    JPanel febPanel;
    JPanel marPanel;
    JPanel aprPanel;
    JPanel mayPanel;
    JPanel junePanel;
    JPanel julyPanel;
    JPanel augPanel;
    JPanel sepPanel;
    JPanel octPanel;
    JPanel novPanel;
    JPanel decPanel;

    JPanel[] monthPanels = {janPanel, febPanel, marPanel, aprPanel, mayPanel, junePanel, julyPanel, augPanel, sepPanel, octPanel, novPanel, decPanel};

    JPanel editingPage;
    JTextArea textArea;
    JButton save;
    JButton cancel;

    JPanel currentPanel;

    ArrayList<journalEntries> entries = new ArrayList<journalEntries>();

    JList<journalEntries> entryList;
    private DefaultListModel<journalEntries> listModel;

    JPanel viewPort;
    JLabel viewText;
    JButton back;
    

    Alora(){
        frame = new JFrame("Alora");
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cl = new CardLayout();

        listModel = new DefaultListModel<journalEntries>();
        entryList = new JList<journalEntries>(listModel);
        entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        content = new JPanel(cl);
        home = new JPanel();
        editingPage = new JPanel();
        textArea = new JTextArea(20, 30);
        save = new JButton("Save");
        save.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        editingPage.add(textArea);
        editingPage.add(save);
        editingPage.add(cancel);

        viewPort = new JPanel();
        viewText = new JLabel("View");
        viewPort.add(viewText);
        back = new JButton("Back");
        back.addActionListener(this);
        viewPort.add(back);

        content.add(home, "home");
        content.add(editingPage, "editingPage");
        content.add(viewPort, "viewPort");

        createMonths();
        createPanels();


        //add
        for (int i = 0; i < monthButtons.length; i++){
            home.add(monthButtons[i]);
        }
        
        for (int i = 0; i < monthPanels.length; i++){
            content.add(monthPanels[i], monthNames[i]);
        }
        
        currentPanel = home;
        frame.getContentPane().add(content);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Alora();
            }
        });
    }

    private void createMonths(){
        for (int i = 0; i < monthButtons.length; i++){
            monthButtons[i] = new JButton(monthNames[i]);
            monthButtons[i].addActionListener(this);
        }
    }

    private void createPanels(){
        for (int i = 0; i < monthPanels.length; i++){
            monthPanels[i] = new JPanel();
            monthPanels[i].add(createHomeButton());
            monthPanels[i].add(new JLabel(monthNames[i]));
            monthPanels[i].add(createAddEntry());
            monthPanels[i].add(createView());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save){
            Date tempDate = new Date();
            String tempText = textArea.getText();
            JPanel tempPanel = currentPanel;
            journalEntries tempEntry = new journalEntries(tempText, tempDate, tempPanel);
            entries.add(tempEntry);
            cl.show(content, getPrevPane());
            update();
        }
        else if (e.getSource() == cancel){
            cl.show(content, getPrevPane());
            update();
        }
        else if (e.getSource() == back){
            cl.show(content, getPrevPane());
            update();
        }
        for (int i = 0; i < monthButtons.length; i++){
            if (e.getSource() == monthButtons[i]){
                cl.show(content, monthNames[i]);
                currentPanel = monthPanels[i];
                update();
            }
        }
    }

    private JButton createHomeButton(){
        JButton homeButton = new JButton("Back");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(content, "home");
                currentPanel = home;
            }
        });
        return homeButton;
    }

    private JButton createAddEntry(){
        JButton addEntry = new JButton("Add Entry");
        addEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                cl.show(content, "editingPage");
            }
        });
        return addEntry;
    }

    private JButton createView(){
        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = entryList.getSelectedValue().getEntry();
                Date date = entryList.getSelectedValue().getDate();
                viewText.setText("<html><p>" + date + "</p> <br> <p>" + text +"</p> </html>");
                cl.show(content, "viewPort");
            }
        });
        return viewButton;
    }

    private void update(){
        listModel.clear();
        for (int i = 0; i < entries.size(); i++){
            if (entries.get(i).getMonth() == currentPanel){
                listModel.addElement(entries.get(i));
            }
        }
        currentPanel.add(entryList);
    }


    private String getPrevPane(){
        for (int i = 0; i < monthPanels.length; i++){
            if (currentPanel == monthPanels[i]){
                return monthNames[i];
            }
        }
        return "home";
    }


}

class journalEntries{
    String entry;
    Date date;
    JPanel month;
    String header;
    journalEntries(String entry, Date date, JPanel month){
        this.entry = entry;
        this.date = date;
        this.month = month;
        if (entry.length() < 10){
            this.header = entry + "...  " + date;
        }
        else{
            this.header = entry.substring(0,9) + "...  " + date;
        }
    }
    public String getEntry() {
        return entry;
    }
    public void setEntry(String entry) {
        this.entry = entry;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public JPanel getMonth() {
        return month;
    }
    public void setMonth(JPanel month) {
        this.month = month;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return this.header;
    }

    
    
}