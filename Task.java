import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Task extends JFrame{

    // Main UI components
    private JFrame mainFrame;
    private JLabel displayTableJLabel;
    private DefaultTableModel modelForTable;
    private JTable displayJTable;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu filter;
    private JMenuItem createMenu;
    private JMenuItem deleteMenu;
    private JMenuItem editMenu;
    private JMenuItem filterMenu;


    // creating task UI
    private JLabel createTaskJLabel;
    private JComboBox<String> datePostCalendar;
    private JComboBox<String> dateSubmitCalendar;
    private JLabel datePostJLabel;
    private JLabel dateSubmitJLabel;
    private JLabel submitJLabel;
    private JLabel titleJLabel;
    private JTextField titleTextField;
    private JComboBox<String> subjectBox;
    private JButton addDescriptionButton;
    private JButton addTaskButton;

    // delete task ui components
    private JFrame deleteTaskFrame;
    private JLabel deleteTaskJLabel;
    private JLabel deleteTaskPaneJLabel;
    private JComboBox<String> theRowUserWantToDeleteBox;
    private JButton deleteTaskButton;

    
    Task() throws IOException{
        createUI();
        displayContentsToTheTable();
    }

    private void createUI(){
        mainFrame = new JFrame("To-do List");
        mainFrame.setLayout(null);

        // for displaying the table
        displayTableJLabel = new JLabel();
        displayTableJLabel.setBounds(5, 0, 620, 630);
        Border border = BorderFactory.createLoweredBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, "Task panel");
        displayTableJLabel.setBorder(titledBorder);
        mainFrame.add(displayTableJLabel);

        // setting the menu bar
        menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        createMenu = new JMenuItem("Create");
        createMenu.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // create actionlistener to add task
                    createTask();
                }
                
            }
        );
        fileMenu.add(createMenu);

        deleteMenu = new JMenuItem("Delete");
        deleteMenu.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteTask();                    
                }
                
            }
        );
        fileMenu.add(deleteMenu);

        editMenu = new JMenuItem("Edit");
        editMenu.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // create actionlistener to edit task
                    
                }
                
            }
        );
        fileMenu.add(editMenu);

        filter = new JMenu("Filter");
        filterMenu = new JMenuItem("Filter");
        filterMenu.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // create actionlistener filter
                   System.out.println("Hi");
                }
                
            }
        );
        filter.add(filterMenu);
        menuBar.add(filterMenu);

       

        // contructing the table
        modelForTable = new DefaultTableModel();
        modelForTable.addColumn("Date Posted");
        modelForTable.addColumn("Date to Submit");
        modelForTable.addColumn("Title");
        modelForTable.addColumn("Subject");
        modelForTable.addColumn("Remark");
        displayJTable = new JTable(modelForTable);
        String[] rowData = {"Date Posted", "Date to Submit", "Title", "Subject", "Remark"};
        modelForTable.addRow(rowData);
        
        displayJTable.setEnabled(false);
        displayJTable.setBounds(15,15,600,605);
        mainFrame.add(displayJTable);

        mainFrame.setSize(650,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    private void createTask(){

        JFrame createTaskFrame = new JFrame("Create Task");
        createTaskFrame.setLayout(null);

        createTaskJLabel = new JLabel();
        createTaskJLabel.setBounds(5, 5, 470, 200);
        Border border = BorderFactory.createLoweredBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border);
        createTaskJLabel.setBorder(titledBorder);
        createTaskFrame.add(createTaskJLabel);

        datePostCalendar = getCalendar(100,20);
        datePostJLabel = new JLabel("Date posted: ");
        datePostJLabel.setBounds(20, 20, 100,30);
        createTaskFrame.add(datePostJLabel);
        createTaskFrame.add(datePostCalendar);

        dateSubmitCalendar = getCalendar(340, 20);
        dateSubmitJLabel = new JLabel("Date to Submit: ");
        dateSubmitJLabel.setBounds(240,20, 100, 30);
        createTaskFrame.add(dateSubmitCalendar);
        createTaskFrame.add(dateSubmitJLabel);

        titleJLabel = new JLabel("Title: ");
        titleJLabel.setBounds(20, 70, 100, 30);
        createTaskFrame.add(titleJLabel);

        titleTextField = new JTextField("Write something here");
        titleTextField.setBounds(50, 70, 170, 30);
        createTaskFrame.add(titleTextField);

        submitJLabel = new JLabel("Subject: ");
        submitJLabel.setBounds(240, 70, 100, 30);
        createTaskFrame.add(submitJLabel);

        subjectBox = getSujectComboBox(310, 70);
        createTaskFrame.add(subjectBox);

        addDescriptionButton = new JButton("Add Description");
        addDescriptionButton.setBounds(200, 120, 100, 30);
        createTaskFrame.add(addDescriptionButton);

        addTaskButton = new JButton("Add Task");
        addTaskButton.setBounds(340, 150, 100, 30);
        createTaskFrame.add(addTaskButton);
        addTaskButton.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                   try {
                    addTheTaskToTheTable();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                }
                
            }
        );

        createTaskFrame.setSize(500, 250);
        createTaskFrame.setLocationRelativeTo(null);
        createTaskFrame.setVisible(true);

    }

    private void addTheTaskToTheTable() throws IOException{
        String datePost = (String) datePostCalendar.getSelectedItem();
        String dateSubmit = (String) dateSubmitCalendar.getSelectedItem();
        String title = (String) titleTextField.getText();
        String subject = (String) subjectBox.getSelectedItem();
        // if(title == null){
        //     JOptionPane.showMessageDialog(null, "All fields must be completed");
        //     return;
        // }
        String[] rowData = {datePost, dateSubmit, title, subject, "Ongoing"};
        modelForTable.addRow(rowData);
        saveTheContentsToAFile(datePost, dateSubmit, title, subject);
    }

    private void saveTheContentsToAFile(String datePost, String dateSubmit, String title, String subject) throws IOException{
        FileWriter fileWriter = new FileWriter("input.txt", true);
        fileWriter.write(datePost + '-' + dateSubmit + '-' + title + '-' + subject + '-' + "Ongoing-" + "\n" );
        fileWriter.close();
    }

    private void deleteTask(){
        deleteTaskFrame = new JFrame("Delete Task");
        deleteTaskFrame.setLayout(null);
        
        deleteTaskPaneJLabel = new JLabel();
        deleteTaskPaneJLabel.setBounds(5, 5, 370, 150);
        Border border = BorderFactory.createLoweredBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border);
        deleteTaskPaneJLabel.setBorder(titledBorder);
        deleteTaskFrame.add(deleteTaskPaneJLabel);
 
        deleteTaskJLabel = new JLabel();
        deleteTaskJLabel.setText("Select the row you want to delete");
        Font newFont = deleteTaskJLabel.getFont().deriveFont(15f);
        deleteTaskJLabel.setFont(newFont);
        deleteTaskJLabel.setBounds(80, 10, 250, 50);
        deleteTaskFrame.add(deleteTaskJLabel);
 
        theRowUserWantToDeleteBox = getIntRowBox(130, 60);
        deleteTaskFrame.add(theRowUserWantToDeleteBox);
 
        deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setBounds(240, 110, 120,30);
        deleteTaskFrame.add(deleteTaskButton);
        deleteTaskButton.addActionListener(
             new ActionListener() {
 
                 @Override
                 public void actionPerformed(ActionEvent e) {
                    try {
                        deletingTheTaskFromTheTable();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                 }
                 
             }
        );
 
        deleteTaskFrame.setSize(400, 200);
        deleteTaskFrame.setLocationRelativeTo(null);
        deleteTaskFrame.setVisible(true);
    }

    private void deletingTheTaskFromTheTable() throws IOException{
        String theRowThatUserPickedToBeDeleted = (String) theRowUserWantToDeleteBox.getSelectedItem();
        int theIntRow = Integer.parseInt(theRowThatUserPickedToBeDeleted);

        String lineToBeRemove = "";

        for(int i = 0; i < modelForTable.getColumnCount(); i++){
            lineToBeRemove += modelForTable.getValueAt(theIntRow, i);
            lineToBeRemove += "-";
        }

        modelForTable.removeRow(theIntRow);
        deletingTheTaskFromTheTextFile(lineToBeRemove);

        displayTableJLabel.repaint();
    }

    private void deletingTheTaskFromTheTextFile(String lineThatWillBeRemove) throws IOException{

        File fileToRead = new File("input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));

        File fileToWrite = new File("temp.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileToWrite));
        
        String line;
        
        while((line = bufferedReader.readLine()) != null){


            if(line.contains(lineThatWillBeRemove)){
            continue;
            }

            bufferedWriter.write(line+"\n");
           
        }

        bufferedReader.close();
        bufferedWriter.close();
        fileToRead.delete();
        fileToWrite.renameTo(fileToRead);
    }

    private void displayContentsToTheTable() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        String line;
        while((line = bufferedReader.readLine()) != null){
            String[] tokens = line.split("-");
            modelForTable.addRow(tokens);
                
        }
        bufferedReader.close();
    }

    private JComboBox<String> getIntRowBox(int x, int y){
        int rowCount = modelForTable.getRowCount();
        String[] rowNums = new String[rowCount - 1];
        for(int i = 1 ; i < rowCount; i++){
            rowNums[i-1] = String.valueOf(i);
        }

        JComboBox<String> rowNumBox = new JComboBox<>(rowNums);
        rowNumBox.setSelectedIndex(0);
        rowNumBox.setBounds(x, y, 120, 30);
        return rowNumBox;
    }

    private JComboBox<String> getCalendar(int x, int y){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        // Create an array of date strings to populate the ComboBox
        String[] dates = new String[365];
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        for (int i = 0; i < dates.length; i++) {
            dates[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        
        // Create the ComboBox and set its model
        JComboBox<String> comboBox = new JComboBox<>(dates);
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(x, y, 120, 30);

        return comboBox;
    }

    public JComboBox<String> getSujectComboBox(int x, int y){
        String[] subjects = {"Discrete Structures", 
                            "Statistics", 
                            "Visual Arts", 
                            "MMW", 
                            "NSTP", 
                            "PE2", 
                            "INFO (LAB)", 
                            "INFO (LEC)", 
                            "COMPROG (LAB)", 
                            "COMPROG (LEC)", 
                            "STS"};

        JComboBox<String> comboBox = new JComboBox<>(subjects);
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(x, y, 140, 30);

        return comboBox;
    }
    public static void main(String[] args) throws IOException {
        Task task = new Task();
        task.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}