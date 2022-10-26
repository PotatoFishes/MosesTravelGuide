import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AddLineDialog extends JFrame implements ActionListener
{
    private final DefaultTableModel parent;
    private Vector<Object> adder = new Vector<>();
    private int index;
    private JTextField txtId, txtName, txtType, txtSDate, txtEDate, txtLoc, txtNote;
    private JButton btnOK, btnCancel, btnAddServ;

    AddLineDialog(final int ndx, final DefaultTableModel model)
    {
        super("Add an Event");
        setLayout(new SpringLayout());
        parent = model;
        index = ndx;

        //Setting Input Fields' Initial Values
        txtId = new JTextField(15);
        txtId.setText("ID");
        txtName = new JTextField(15);
        txtName.setText("Name");
        txtType = new JTextField(15);
        txtType.setText("Type");
        txtSDate = new JTextField(3);
        txtSDate.setText("Start Time");
        txtEDate = new JTextField(15);
        txtEDate.setText("End Time");
        txtLoc = new JTextField(15);
        txtLoc.setText("Location");
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

        this.setContentPane(content);
        //this.add(btnAddServ);
        SpringUtilities.makeCompactGrid(content, 1, 2, 6, 6, 6, 6);

        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initAdder()
    {
        adder.add(txtSDate.getText());
        adder.add(txtEDate.getText());
        adder.add(txtLoc.getText());
        adder.add(txtNote.getText());
        adder.add("Edit");
        adder.add("Remove");
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
                this.parent.removeRow(this.index);
                dispose();
                return;
            }
            initAdder();

            parent.removeRow(this.index);
            parent.insertRow(this.index,adder);
            dispose();
            return;
        }
        else if(clicked == btnCancel)
        {
            parent.removeRow(index);
            dispose();
            return;
        }
        else if(clicked == btnAddServ)
        {
            this.setVisible(false);
            // TODO: make Add Service Dialog
            this.setVisible(true);
        }
    }
}
