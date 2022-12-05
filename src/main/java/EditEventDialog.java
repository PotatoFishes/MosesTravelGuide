import com.mysql.cj.util.StringUtils;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class EditEventDialog extends JFrame implements ActionListener
{
    public SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
    private static JTable table;
    private static JFrame frame;
    private TableRowSorter<DefaultTableModel> sorter;
    private final DefaultTableModel parent;
    int eventID;
    private Vector<Object> adder = new Vector<>();
    private List<Service> Services = new ArrayList<>();
    private int index;
    private JTextField txtId, txtName, txtSDate, txtEDate, txtLoc, txtNote, txtServices;
    private JButton btnOK, btnCancel, btnAddServ;
    private int EIDCELL = 0;
    private int ESDATECELL = 1;
    private int EEDATECELL = 2;
    private int ELOCCELL = 3;
    private int ENAMECELL = 4;
    private int ENOTECELL = 5;
    private int SEDITCELL = 7;
    private int SREMOVECELL = 8;
    private String[] columnNames = {
            "ID", "Price", "Name", "Start Time", "End Time", "Bookings", "Capacity", "Edit", "Remove"
    };
    private Object[][] data = {
            //TODO: Service loading functions
            { "0", "0.00", "test", sdf.format(new Date()) , sdf.format(new Date()) ,"3", "5" , " . . . ", " X "}
    };

    EditEventDialog(final int ndx, final DefaultTableModel eventModel) throws ParseException {
        super("Edit Event");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = eventModel;
        index = ndx;
        eventID = Integer.parseInt(((String)parent.getValueAt(ndx,EIDCELL)).trim());

        //Create a table with a sorter.
        final Class<?>[] columnClass = new Class[]{
                String.class, String.class, String.class,String.class,String.class,String.class,String.class,String.class, String.class
        };
        final DefaultTableModel model = new DefaultTableModel(data, columnNames) {
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

        //Add the scroll pane to this panel.


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
                //new EditDialog(table.getSelectedRow(),model).setVisible(true);
            }
        };
        ButtonColumn colButEditor = new ButtonColumn(table, editor, SEDITCELL);

        //Setting Input Fields' Initial Values
        txtName = new JTextField(15);
        txtName.setText( "" + eventModel.getValueAt(ndx, 4) );
        txtSDate = new JTextField(15);
        System.out.println("" + eventModel.getValueAt(ndx, ESDATECELL));
        txtSDate.setText(sdf.parse("" + eventModel.getValueAt(ndx, ESDATECELL)).toString());
        System.out.println("" + eventModel.getValueAt(ndx, EEDATECELL));
        txtEDate = new JTextField(15);
        txtEDate.setText(sdf.parse("" + eventModel.getValueAt(ndx, EEDATECELL)).toString());
        txtLoc = new JTextField(15);
        txtLoc.setText("" + eventModel.getValueAt(ndx, ELOCCELL));
        txtNote = new JTextField(15);
        txtNote.setText("" + eventModel.getValueAt(ndx, ENOTECELL));

        //Setting Up Buttons
        btnOK = new JButton("Save");
        btnOK.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnAddServ = new JButton("Add Service");
        btnAddServ.addActionListener(this);

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
        SpringUtilities.makeCompactGrid(servicer, 2, 1, 6, 6, 6, 6);

        JPanel panelHolders = new JPanel();
        panelHolders.setLayout(new SpringLayout());
        panelHolders.add(content);
        panelHolders.add(servicer);
        SpringUtilities.makeCompactGrid(panelHolders, 1, 2, 6, 6, 6, 6);

        setContentPane(panelHolders);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addService()
    {
        Service adding = new Service();
        // TODO: make Add Service Dialog
        new AddServiceDialog(0, (DefaultTableModel) table.getModel());
        Services.add(adding);
    }

    private void initAdder()
    {
        adder.add(txtId.getText());
        adder.add(txtSDate.getText());
        adder.add(txtEDate.getText());
        adder.add(txtLoc.getText());
        adder.add(txtNote.getText());
        adder.add(" . . . ");
        adder.add(" X ");
        String temp = "";
        for(Service s : Services)
        {
            temp += s.getID() + ",";
        }
        chop(temp);
        txtServices.setText(temp);
    }

    public static void chop(String s)
    {
        if(s.length() > 0)
        {
            s = s.substring(0, s.length() - 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clicked = (JButton) e.getSource();
        if(clicked == btnOK)
        {
            initAdder();
            try
            {
                Integer.parseInt(txtId.getText());
                //List<Service> temp = new ArrayList<Service>();
                Timestamp timestamp = AddEvent.convertStringToTimestamp(txtSDate.getText());
                Timestamp timestamp2 = AddEvent.convertStringToTimestamp(txtEDate.getText());
                timestamp.after(timestamp2);

                Event temp = new Event(Integer.valueOf(txtId.getText()), txtName.getText(), timestamp, timestamp2,txtLoc.getText(), txtNote.getText(), txtServices.getText(), 1);
                EventDAOImp.updateEvent(temp);
                parent.insertRow(0, temp.toArray());
                if(!EventsServ.checkTimesValid(temp))
                {
                    JOptionPane.showConfirmDialog(null,
                            "Incorrect Time Format: Please Format as 'YYYY-MM-dd HH:mm:ss.SSS' from '1000-01-01' to '9999-12-31'"
                            , "Error"
                            , JOptionPane.OK_OPTION);
                }
                EventsServ.createEvent(temp);
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Value Given"
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                dispose();
                return;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            parent.removeRow(index);
            parent.insertRow(index,adder);
            Event temp = new Event();
            dispose();
            return;
        }
        else if(clicked == btnCancel)
        {
            dispose();
            return;
        }
        else if(clicked == btnAddServ)
        {
            this.setVisible(false);
            addService();
            this.setVisible(true);
        }
    }
}
