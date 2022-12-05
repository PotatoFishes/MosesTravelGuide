import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class EditDialog extends JFrame implements ActionListener
{
    private final DefaultTableModel parent;
    private Vector<Object> adder = new Vector<>();
    private List<Service> Services = new ArrayList<>();
    private int index;
    private JTextField txtId, txtName, txtType, txtSDate, txtEDate, txtLoc, txtNote;
    private JButton btnOK, btnCancel, btnAddServ;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
    private String[] columnNames = {
            "Start Time", "End Time", "Location", "Name", "Note", "Edit", "Remove"
    };
    private Object[][] data = {
            //TODO: Service loading functions
            { sdf.format(new Date()) , sdf.format(new Date()) ,"Waco, TX", "Tester" ,"This is a test value" , " . . . ", " X "}
    };
    EditDialog(final int ndx, final DefaultTableModel model)
    {
        super("Edit an Event");

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = model;
        index = ndx;

        //Setting Input Fields' Initial Values
        txtId = new JTextField(15);
        txtId.setText("ID");
        txtName = new JTextField(15);
        txtName.setText( "" + model.getValueAt(ndx, 3) );
        txtType = new JTextField(15);

        txtType.setText("Type");
        txtSDate = new JTextField(15);
        txtSDate.setText("" + model.getValueAt(ndx, 0));
        txtEDate = new JTextField(15);
        txtEDate.setText("" + model.getValueAt(ndx, 1));
        txtLoc = new JTextField(15);
        txtLoc.setText("" + model.getValueAt(ndx, 2));
        txtNote = new JTextField(15);
        txtNote.setText("Note");


        //Setting Label Names
        JPanel content = new JPanel(new SpringLayout());
        content.add(new JLabel("ID:"));
        content.add(txtId);
        content.add(new JLabel("Name:"));
        content.add(txtName);
        content.add(new JLabel("Type:"));
        content.add(txtType);
        content.add(new JLabel("Start Time:"));
        content.add(txtSDate);
        content.add(new JLabel("End Time:"));
        content.add(txtEDate);
        content.add(new JLabel("Location:"));
        content.add(txtLoc);
        content.add(new JLabel("Note:"));
        content.add(txtNote);

        btnOK = new JButton("Yes");
        btnOK.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnAddServ = new JButton("Add Service");
        btnAddServ.addActionListener(this);

        //Setting Buttons
        btnOK = new JButton("Save");
        btnOK.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnAddServ = new JButton("Add Service");
        btnAddServ.addActionListener(this);
        content.add(btnOK);
        content.add(btnCancel);
        SpringUtilities.makeCompactGrid(content, 8, 2, 6, 6, 6, 6);

        JPanel servicer = new JPanel();
        servicer.setLayout(new SpringLayout());
        servicer.add(btnAddServ);
        SpringUtilities.makeCompactGrid(servicer, 1, 1, 6, 6, 6, 6);

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
        Services.add(adding);
    }

    private void initAdder()
    {
        adder.add(txtSDate.getText());
        adder.add(txtEDate.getText());
        adder.add(txtLoc.getText());
        adder.add(txtNote.getText());
        adder.add(" . . . ");
        adder.add(" X ");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clicked = (JButton) e.getSource();
        if(clicked == btnOK)
        {
            try
            {
                Integer.parseInt(txtId.getText());
                Integer.parseInt(txtType.getText());
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Value Given"
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                this.parent.removeRow(index);
                dispose();
                return;
            }
            initAdder();
            parent.removeRow(index);
            parent.insertRow(index,adder);
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
