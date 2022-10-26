import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EditDialog extends JFrame implements ActionListener
{
    private final DefaultTableModel parent;
    private Vector<Object> adder = new Vector<>();
    private int index;
    private JTextField txtId, txtName, txtType, txtAge, txtWeight, txtBreed, txtColor;
    private JButton btnOK, btnCancel;

    EditDialog(final int ndx, final DefaultTableModel model)
    {
        super("Edit an Animal");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        parent = model;
        index = ndx;

        txtId = new JTextField(15);
        txtId.setText(  "" + model.getValueAt(ndx,1));

        txtName = new JTextField(15);
        txtName.setText((String) model.getValueAt(ndx,2));

        txtType = new JTextField(15);
        txtType.setText((String) model.getValueAt(ndx,0));

        txtAge = new JTextField(3);
        txtAge.setText("" + model.getValueAt(ndx,3));

        txtWeight = new JTextField(15);
        txtWeight.setText("" + model.getValueAt(ndx,4));

        txtBreed = new JTextField(15);
        txtBreed.setText((String) model.getValueAt(ndx,5));

        txtColor = new JTextField(15);
        txtColor.setText((String) model.getValueAt(ndx,6));

        JPanel content = new JPanel(new SpringLayout());
        content.add(new JLabel("ID:"));
        content.add(txtId);
        content.add(new JLabel("Name:"));
        content.add(txtName);
        content.add(new JLabel("Type:"));
        content.add(txtType);
        content.add(new JLabel("Age:"));
        content.add(txtAge);
        content.add(new JLabel("Weight:"));
        content.add(txtWeight);
        content.add(new JLabel("Breed:"));
        content.add(txtBreed);
        content.add(new JLabel("Color:"));
        content.add(txtColor);

        btnOK = new JButton("Yes");
        btnOK.addActionListener(this);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);

        content.add(btnOK);
        content.add(btnCancel);

        SpringUtilities.makeCompactGrid(content, 8, 2, 6, 6, 6, 6);

        setContentPane(content);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initAdder()
    {
        adder.add(txtType.getText());
        adder.add(Integer.parseInt(txtId.getText()));
        adder.add(txtName.getText());
        adder.add(Integer.parseInt(txtAge.getText()));
        adder.add(Integer.parseInt(txtWeight.getText()));
        adder.add(txtBreed.getText());
        adder.add(txtColor.getText());
        adder.add("Edit");
        adder.add("Remove");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clicked = (JButton) e.getSource();
        if(clicked == btnOK)
        {
            if(txtWeight.getText().length() > 0)
            {
                try
                {
                    Integer.parseInt(txtWeight.getText());
                }
                catch(NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect Value for Animal Weight"
                            ,"Error"
                            ,JOptionPane.OK_OPTION);
                    dispose();
                    return;
                }
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
    }
}
