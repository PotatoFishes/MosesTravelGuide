import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AddServiceDialog extends JFrame implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm a");

    private final DefaultTableModel parent;
    private Vector<Object> adder = new Vector<>();
    private List<Service> Services = new ArrayList<>();
    private Event newE;
    private int index;
    private JTextField txtId, txtName, txtBookings, txtSDate, txtEDate, txtPrice, txtCapacity;
    private JButton btnOK, btnCancel ;
    private String[] columnNames = {
            "ID", "Name", "Price", "Start Time", "End Time", "Bookings", "Capacity", "Edit", "Remove"
    };
    private Object[][] data = {
            //TODO: Service loading functions
            { "0", "Tester" , "23.22", sdf.format(new Date()) , sdf.format(new Date()) ,"Waco, TX", "This is a test value" , " . . . ", " X "}
    };

    AddServiceDialog(DefaultTableModel model)
    {
        super("Add Service");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = model;
        index = 0;

        //Setting Input Fields' Initial Values
        txtId = new JTextField(15);
        txtId.setText("ID");
        txtName = new JTextField(15);
        txtName.setText("Name");
        txtPrice = new JTextField(15);
        txtPrice.setText("Price");
        txtSDate = new JTextField(3);
        txtSDate.setText(sdf.format(new Date()));
        txtEDate = new JTextField(15);
        txtEDate.setText(sdf.format(new Date()));
        txtBookings = new JTextField(15);
        txtBookings.setText("Bookings");
        txtCapacity = new JTextField(15);
        txtCapacity.setText("Capacity");

        //Setting Label Names
        JPanel content = new JPanel(new SpringLayout());
        content.add(new JLabel("ID:"));
        content.add(txtId);
        content.add(new JLabel("Name:"));
        content.add(txtName);
        content.add(new JLabel("Price:"));
        content.add(txtPrice);
        content.add(new JLabel("Start Date:"));
        content.add(txtSDate);
        content.add(new JLabel("End Date:"));
        content.add(txtEDate);
        content.add(new JLabel("Bookings:"));
        content.add(txtBookings);
        content.add(new JLabel("Capacity:"));
        content.add(txtCapacity);

        //Setting Buttons
        btnOK = new JButton("Save");
        btnOK.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        content.add(btnOK);
        content.add(btnCancel);
        SpringUtilities.makeCompactGrid(content, 8, 2, 6, 6, 6, 6);

        JPanel panelHolders = new JPanel();
        panelHolders.setLayout(new SpringLayout());
        panelHolders.add(content);
        SpringUtilities.makeCompactGrid(panelHolders, 1, 1, 6, 6, 6, 6);

        setContentPane(panelHolders);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initAdder()
    {
        adder.add(txtId.getText());
        adder.add(txtName.getText());
        adder.add(txtPrice.getText());
        adder.add(txtSDate.getText());
        adder.add(txtEDate.getText());
        adder.add(txtBookings.getText());
        adder.add(txtCapacity.getText());
        adder.add(" . . . ");
        adder.add(" X ");
    }

    private void addService()
    {
        Service adding = new Service();
        // TODO: make Add Service Dialog
        Services.add(adding);
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
                Double.parseDouble(txtPrice.getText());
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Value Given"
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                parent.removeRow(index);
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
            parent.removeRow(index);
            dispose();
            return;
        }
    }
}
