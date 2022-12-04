import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Planner extends JPanel {
    private static JTable table;
    private static JFrame frame;
    private JTextField filterText;
    private JTextField statusText;
    private JTextArea choiceLog = new JTextArea("");
    private JFileChooser fileChooser = new JFileChooser();
    private JPanel form = new JPanel(new SpringLayout());
    private TableRowSorter<DefaultTableModel> sorter;
    private int NOTES = 4;
    private int EDITCELL = 5;
    private int REMOVECELL = 6;

    //TODO: remove these debugging/testing variables
    private SimpleDateFormat sdf =
            new SimpleDateFormat("MM/dd/YYYY hh:mm a");

    private String[] columnNames = {
            "Start Time", "End Time", "Location", "Name", "Note", "Edit", "Remove"
    };
    private Object[][] data = {
            //TODO: Event loading function
            { sdf.format(new Date()) , sdf.format(new Date()) ,"Waco, TX", "Tester" ,"This is a test value" , " . . . ", " X "}
    };

    public Planner() {
        super();
        setLayout(new SpringLayout());

        //Create a table with a sorter.
        final Class<?>[] columnClass = new Class[]{
                String.class,String.class,String.class,String.class,String.class,String.class, String.class
        };
        final DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                if(col < NOTES) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return columnClass[col];
            }
        };

        // Set table dimensions
        sorter = new TableRowSorter<DefaultTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(600, getToolkit().getScreenResolution()));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableFilterHeader filterHeader = new TableFilterHeader(table, AutoChoices.ENABLED);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

        // Remove Row button
        Action remove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int modelRow = Integer.valueOf(e.getActionCommand());
                int answer = JOptionPane.showConfirmDialog(null,
                        "Do you want to remove " + model.getValueAt(modelRow, 2)
                                + " " + model.getValueAt(modelRow,  1) + "?"
                        ,"Warning"
                        ,JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION)
                {
                    model.removeRow(modelRow);
                }
            }
        };
        ButtonColumn colButRemover = new ButtonColumn(table, remove, REMOVECELL);

        // Edit Row button
        Action editor = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditDialog(table.getSelectedRow(),model).setVisible(true);
            }
        };
        ButtonColumn colButEditor = new ButtonColumn(table, editor, EDITCELL);

        // Settings (for notifications and stuff)
        JButton settings = new JButton("Settings");
        settings.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setVisible(false);
                        // TODO: make Settings
                        //new SettingsDialog();
                        setVisible(true);
                    }
                });
            }
        });

        //Dialog Button to add new events
        JButton eventButton = new JButton("Add Event");
        eventButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        AddEvent addEvent = new AddEvent(table);
                        addEvent.setVisible(true);
                    }
                });
            }
        });
        
        JButton manageButton = new JButton("Manage Events");
        manageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ManageEvents manageEvent = new ManageEvents(table);
                        manageEvent.show();
                    }
                });
            }
        });

        // TODO: put ALL BUTTONS or ACTION LISTENERS in their own CLASSES
        //Dialog Button to add new Businesses
        JButton businessButton = new JButton("Add Business");
        businessButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //setVisible(false);
                        // TODO: make Add Business Dialog
                        //Event temp = new Event();
                        //model.insertRow(0, temp.toArray());
                        //new AddLineDialog(0, model).setVisible(true);
                        //setVisible(true);
                        AddBusiness addBus = new AddBusiness(table);
                        addBus.setVisible(true);
                        //addBus.show();
                    }
                });
            }
        });

        // Invite Friends to your Planner
        JButton pInvButton = new JButton("Invite To Planner");
        pInvButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        frame.setVisible(false);
                        // TODO: make Invite to Planner dialog
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            public void run()
                            {
                                Event temp = new Event();
                                model.insertRow(0, temp.toArray());
                                new AddLineDialog(0, model).setVisible(true);
                            }
                        });
                        frame.setVisible(true);
                    }
                });
            }
        });

        // Invite friends to your Event
        JButton eInvButton = new JButton("Invite To Event");
        eInvButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setVisible(false);
                        // TODO: make Invite to Event Dialog
                        Event temp = new Event();
                        model.insertRow(0, temp.toArray());
                        new AddLineDialog(0, model).setVisible(true);
                        setVisible(true);
                    }
                });
            }
        });

        // Open friends list
        JButton friendsButton = new JButton("Friends");
        friendsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                    	new FriendsManagerUI();
                        
                    }
                });
            }
        });

        // Buttons Setup
        form.add(settings);
        form.add(eventButton);
        form.add(manageButton);
        form.add(businessButton);
        form.add(pInvButton);
        form.add(eInvButton);
        form.add(friendsButton);
        SpringUtilities.makeCompactGrid(form, 6, 1, 6, 6, 6, 6);

        // Add Buttons to the Right of the Table
        add(form);
        SpringUtilities.makeCompactGrid(this, 1,2,10,10,10,10);
    }

    /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter()
    {
        RowFilter<DefaultTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try
        {
            rf = RowFilter.regexFilter(filterText.getText(), 0, 1, 2);
        }
        catch (java.util.regex.PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {


        //Create and set up the window.
        frame = new JFrame("Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Planner newContentPane = new Planner();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void showGUI()
    {
        createAndShowGUI();
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}