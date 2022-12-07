import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class EditEventDialog extends JFrame {
    private static JTable table;
    private TableRowSorter<DefaultTableModel> sorter;
    private final DefaultTableModel parent;
    int eventID;
    Event evt;
    private Vector<Object> adder = new Vector<>();
    private int index;
    private JTextField txtName, txtSDate, txtEDate, txtLoc, txtNote, txtServices = new JTextField(15);
    private JButton btnOK, btnCancel, btnAddServ, re;
    private int EIDCELL = 0;
    private int ESDATECELL = 1;
    private int EEDATECELL = 2;
    private int ELOCCELL = 3;
    private int ENOTECELL = 5;
    private static int SEDITCELL = 7;
    private int SREMOVECELL = 8;
    private static DefaultTableModel model;
    private static String[] columnNames = {
            "ID", "Price", "Name", "Start Time", "End Time", "Bookings", "Capacity", "Edit", "Remove"
    };
    static final Class<?>[] columnClass = new Class[]{
            String.class, String.class, String.class,String.class,String.class,String.class,String.class,String.class, String.class
    };
    Object[][] o;
    EditEventDialog(final int ndx, final DefaultTableModel eventModel) throws ParseException {
        super("Edit Event");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = eventModel;
        index = ndx;
        eventID = Integer.parseInt(((String)parent.getValueAt(ndx,EIDCELL)).trim());
        evt=EventDAOImp.getEvent(eventID);
        o = ServiceServ.getServicesForTable(evt);
         model = new DefaultTableModel(o, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                if(col < SEDITCELL) {
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
        ButtonColumn colButRemover = new ButtonColumn(table, remove, SREMOVECELL);
        // Edit Row button
        Action editor = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new EditServiceDialog(table.getSelectedRow(), model).setVisible(true);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        ButtonColumn colButEditor = new ButtonColumn(table, editor, SEDITCELL);

        //Setting Input Fields' Initial Values
        txtName = new JTextField(15);
        txtName.setText( "" + eventModel.getValueAt(ndx, 4) );
        txtSDate = new JTextField(25);
        txtSDate.setText("" + eventModel.getValueAt(ndx, ESDATECELL));
        txtEDate = new JTextField(25);
        txtEDate.setText("" + eventModel.getValueAt(ndx, EEDATECELL));
        txtLoc = new JTextField(15);
        txtLoc.setText("" + eventModel.getValueAt(ndx, ELOCCELL));
        txtNote = new JTextField(15);
        txtNote.setText("" + eventModel.getValueAt(ndx, ENOTECELL));

        //Setting Up Buttons
        btnOK = new JButton("Save");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initAdder();
                try
                {
                    Timestamp timestamp = CreateEvent.convertStringToTimestamp(txtSDate.getText());
                    Timestamp timestamp2 = CreateEvent.convertStringToTimestamp(txtEDate.getText());
                    timestamp.after(timestamp2);

                    String tempS = "";
                    for(Service s : evt.usedServices)
                    {
                        tempS += s.getID() + ",";
                    }
                    tempS = chop(tempS);
                    txtServices.setText(tempS + "");

                    System.out.println(tempS );
                    Event temp = evt;
                    temp.sDate.setTime(timestamp.getTime());
                    temp.eDate.setTime(timestamp2.getTime());
                    temp.name=txtName.getText();
                    temp.loc=txtLoc.getText();
                    temp.note=txtNote.getText();
                    System.out.println(temp.getUsedServices());
                    parent.removeRow(index);
                    parent.insertRow(index,temp.toArray());
                    EventsServ.EditEvent(temp);
                }
                catch(NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect Value Given"
                            ,"Error"
                            ,JOptionPane.OK_OPTION);
                    dispose();
                    return;
                }
                catch (ParseException ex)
                {
                    ex.printStackTrace();
                }

                dispose();
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnAddServ = new JButton("Create Service");
        btnAddServ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               AddServiceDialog a = new AddServiceDialog(model,evt);
               evt = a.getEvent();
               table.repaint();
            }
        });
        btnAddServ.setToolTipText("Need to save event to see added service");

        re = new JButton("Browse Services");
        re.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BrowseService b = new BrowseService();
                evt.addService(b.getService());
                table.repaint();
            }
        });
        re.setToolTipText("You must create a service if you would like to browse");

        //Setting Label Names
        JPanel content = new JPanel(new SpringLayout());
        content.add(new JLabel("ID:"));
        content.add(new JLabel("" + eventID));
        content.add(new JLabel("Name:"));
        content.add(txtName);
        content.add(new JLabel("Start Time:"));
        content.add(txtSDate);
        content.add(new JLabel("End Time:"));
        content.add(txtEDate);
        content.add(new JLabel("Location:"));
        content.add(txtLoc);
        content.add(new JLabel("Note:"));
        content.add(txtNote);

        //Setting Event Content Buttons
        content.add(btnOK);
        content.add(btnCancel);
        SpringUtilities.makeCompactGrid(content, 7, 2, 6, 6, 6, 6);

        //Setting Service Content Buttons
        JPanel servicer = new JPanel();
        servicer.setLayout(new SpringLayout());
        servicer.add(btnAddServ);
        servicer.add(scrollPane);
        servicer.add(re);
        SpringUtilities.makeCompactGrid(servicer, 3, 1, 6, 6, 6, 6);

        JPanel panelHolders = new JPanel();
        panelHolders.setLayout(new SpringLayout());
        panelHolders.add(content);
        panelHolders.add(servicer);
        SpringUtilities.makeCompactGrid(panelHolders, 1, 2, 6, 6, 6, 6);

        setContentPane(panelHolders);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
    }

    private void initAdder()
    {
        adder.add(txtSDate.getText() + "");
        adder.add(txtEDate.getText() + "");
        adder.add(txtLoc.getText() + "");
        adder.add(txtNote.getText() + "");
        adder.add(" . . . ");
        adder.add(" X ");
    }

    public static String chop(String s)
    {
        if(s.length() > 0)
        {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
