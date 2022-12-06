import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

public class AddServiceDialog extends JFrame implements ActionListener
{
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
    private Vector<Object> adder = new Vector<>();
    DefaultTableModel parent;
    Event e;
    private int index;
    private JTextField txtId, txtName, txtBookings, txtSDate, txtEDate, txtPrice, txtCapacity;
    private JButton btnOK, btnCancel, btnAddServ;

    AddServiceDialog( DefaultTableModel model, Event e)
    {
        super("Add Service");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        parent = model;
        index = 0;
        this.e=e;

        //Setting Input Fields' Initial Values
        txtName = new JTextField(30);
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
        SpringUtilities.makeCompactGrid(content, 7, 2, 6, 6, 6, 6);

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
                        txtName.getText(),
                        Double.valueOf(txtPrice.getText()),
                        Timestamp.valueOf(txtSDate.getText()),
                        Timestamp.valueOf(txtEDate.getText()),
<<<<<<< HEAD
=======
                        CreateEvent.convertStringToTimestamp(txtSDate.getText()),
                        CreateEvent.convertStringToTimestamp(txtEDate.getText()),
>>>>>>> origin/source
=======
>>>>>>> refs/remotes/origin/source
                        Integer.valueOf(txtCapacity.getText()));
                
                System.out.println(temp.getStartDate());
                temp=ServiceServ.addService( temp );
                this.e.addService(temp);
                System.out.println(this.e.getUsedServices());
            }
            catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,
                        "Incorrect Price Input: " + txtPrice.getText()
                        ,"Error"
                        ,JOptionPane.OK_OPTION);
                nfe.printStackTrace();
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
