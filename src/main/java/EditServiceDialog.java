import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EditServiceDialog extends JFrame{

	private DefaultTableModel parent;
	private JTextField txtPrice, txtName, txtSDate, txtEDate, txtBook, txtCap;
    private JButton btnOK, btnCancel;
		private int index;
		EditServiceDialog(final int ndx, final DefaultTableModel eventModel) throws ParseException {
			 setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		     parent = eventModel;
		     index = ndx;
		     
		     txtPrice = new JTextField(15);
		     txtPrice.setText(""+eventModel.getValueAt(ndx, 1));
		     txtName = new JTextField(15);
		     txtName.setText( "" + eventModel.getValueAt(ndx, 2) );
		     txtSDate = new JTextField(25);
		     txtSDate.setText("" + eventModel.getValueAt(ndx, 3));
		     txtEDate = new JTextField(25);
		     txtEDate.setText("" + eventModel.getValueAt(ndx, 4));
		     txtBook = new JTextField(15);
		     txtBook.setText("" + eventModel.getValueAt(ndx, 5));
		     txtCap = new JTextField(15);
		     txtCap.setText("" + eventModel.getValueAt(ndx, 6));
		     
		     btnOK = new JButton("Save");
		        btnOK.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                try
		                {
		                    Timestamp timestamp = CreateEvent.convertStringToTimestamp(txtSDate.getText());
		                    Timestamp timestamp2 = CreateEvent.convertStringToTimestamp(txtEDate.getText());
		                    timestamp.after(timestamp2);
		                    Service temp = ServiceDAOImp.getService(Integer.parseInt((String)eventModel.getValueAt(ndx, 0)));
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
		}
}
