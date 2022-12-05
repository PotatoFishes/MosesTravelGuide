import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AddServiceDialog extends JFrame implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
    private Vector<Object> adder = new Vector<>();
    List<Service> finding = new ArrayList<>();
    DefaultTableModel parent;
    static int ids = 0;
    private int index;
    private JTextField txtId, txtName, txtBookings, txtSDate, txtEDate, txtPrice, txtCapacity;
    private JButton btnOK, btnCancel, btnAddServ;

    AddServiceDialog( DefaultTableModel model, List<Service> updating)
    {
        super("Add Service");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = model;
        index = 0;
        finding = updating;

        //Setting Input Fields' Initial Values
        txtId = new JTextField("" + ids);
        txtName = new JTextField(15);
        txtName.setText("Name");
        txtPrice = new JTextField(15);
        txtPrice.setText("0.00");
        txtSDate = new JTextField(25);
        txtSDate.setText(new Timestamp(new Date().getTime()).toString());
        txtEDate = new JTextField(25);
        txtEDate.setText(new Timestamp(new Date().getTime()).toString());
        txtBookings = new JTextField(15);
        txtBookings.setText("Bookings");
        txtCapacity = new JTextField(15);
        txtCapacity.setText("10");

        //Setting Buttons
        btnOK = new JButton("Save");
        btnOK.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);

        //Setting Label Names
        JPanel content = new JPanel(new SpringLayout());
        content.add(new JLabel("ID: "));
        content.add(new JLabel("" + ids));
        content.add(new JLabel("Name: "));
        content.add(txtName);
        content.add(new JLabel("Price: "));
        content.add(txtPrice);
        content.add(new JLabel("Start Date: "));
        content.add(txtSDate);
        content.add(new JLabel("End Date: "));
        content.add(txtEDate);
        content.add(new JLabel("Bookings: "));
        content.add(txtBookings);
        content.add(new JLabel("Note: "));
        content.add(txtCapacity);
        content.add(btnOK);
        content.add(btnCancel);
        SpringUtilities.makeCompactGrid(content, 8, 2, 6, 6, 6, 6);

        JPanel panelHolders = new JPanel();
        panelHolders.setLayout(new SpringLayout());
        panelHolders.add(content);
        SpringUtilities.makeCompactGrid(panelHolders, 1, 1, 6, 6, 6, 6);

        setContentPane(panelHolders);
        pack();
        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clicked = (JButton) e.getSource();
        if(clicked == btnOK)
        {
            try
            {
                Double.parseDouble(txtPrice.getText());
                initAdder();
                Service temp = new Service(
                        Integer.valueOf(txtId.getText()),
                        txtName.getText(),
                        Double.valueOf(txtPrice.getText()),
                        AddEvent.convertStringToTimestamp(txtSDate.getText()),
                        AddEvent.convertStringToTimestamp(txtEDate.getText()),
                        Integer.valueOf(txtCapacity.getText()));
                ServiceServ.addService( temp );
                finding.add(temp);
                ids ++;
                parent.insertRow(index,adder);
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Price Input: " + txtPrice.getText()
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                nfe.printStackTrace();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Time Format: Please Format as 'YYYY-MM-dd HH:mm:ss.SSS' from '1000-01-01' to '9999-12-31'"
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                ex.printStackTrace();
            }
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
