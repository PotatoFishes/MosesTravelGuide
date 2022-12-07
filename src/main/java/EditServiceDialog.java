import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditServiceDialog extends JFrame{

	private DefaultTableModel parent;
	private JTextField txtPrice, txtName, txtSDate, txtEDate, txtBook, txtCap;
	JLabel Price, Name, SDate, EDate, Book, Cap;
    private JButton btnOK, btnCancel;
	JPanel panel, p, p2, p3, p4;
		private int index;
		EditServiceDialog(final int ndx, final DefaultTableModel eventModel) throws ParseException {
			super("Edit Service");
			panel = new JPanel();
			p = new JPanel();
			p2 = new JPanel();
			p3 = new JPanel();
			p4 = new JPanel();
			p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));

			setLayout(new SpringLayout());
		     parent = eventModel;
			 index = ndx;
			System.out.println(ndx);

			Price = new JLabel();
			Price.setText("Price: ");
			txtPrice = new JTextField(15);
			txtPrice.setText(""+eventModel.getValueAt(ndx, 1));

			Name = new JLabel();
			Name.setText("Name: ");
			txtName = new JTextField(15);
			txtName.setText( "" + eventModel.getValueAt(ndx, 2) );

			SDate = new JLabel();
			SDate.setText("Start Date: ");
			txtSDate = new JTextField(15);
			txtSDate.setText("" + eventModel.getValueAt(ndx, 3));

			EDate = new JLabel();
			EDate.setText("End Date: ");
			txtEDate = new JTextField(15);
			txtEDate.setText("" + eventModel.getValueAt(ndx, 4));

			Book = new JLabel();
			Book.setText("Bookings: ");
			txtBook = new JTextField(15);
			txtBook.setText("" + eventModel.getValueAt(ndx, 5));

			Cap = new JLabel();
			Cap.setText("Capacity: ");
			txtCap = new JTextField(15);
			txtCap.setText("" + eventModel.getValueAt(ndx, 6));

			panel.add(Price);
			panel.add(txtPrice);
			panel.add(Name);
			panel.add(txtName);

			p.add(SDate);
			p.add(txtSDate);
			p.add(EDate);
			p.add(txtEDate);

			p2.add(Book);
			p2.add(txtBook);
			p2.add(Cap);
			p2.add(txtCap);
		     
		     btnOK = new JButton("Save");
		        btnOK.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                try
		                {
		                    Timestamp timestamp = Timestamp.valueOf(txtSDate.getText());
		                    Timestamp timestamp2 = Timestamp.valueOf(txtEDate.getText());
		                    timestamp.after(timestamp2);
		                    Service temp = ServiceDAOImp.getService((Integer) eventModel.getValueAt(ndx, 0));
		                    temp.price=Double.parseDouble(txtPrice.getText());
		                    temp.name=txtName.getText();
		                    temp.sDate=timestamp;
		                    temp.eDate=timestamp2;
		                    temp.bookings=Integer.parseInt(txtBook.getText());
		                    temp.capacity=Integer.parseInt(txtCap.getText());
		                    ServiceServ.createService(temp);
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

				p3.add(btnOK);
				p3.add(btnCancel);

				p4.add(panel);
				p4.add(p);
				p4.add(p2);
				p4.add(p3);
				add(p4);
				setVisible(true);
				setSize(new Dimension(700, 300));
		}
}
