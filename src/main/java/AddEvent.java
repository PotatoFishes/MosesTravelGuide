import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

/*
 * The AddEvent class allows the user to create a new event.
 */
public class AddEvent extends JDialog implements PropertyChangeListener{
	JTable table;
	private int ID = 0, Type = 0;
	private String Name="", SDate="", EDate="", Loc="", Note="";
	private JLabel IDlabel, Namelabel, Typelabel, SDatelabel, EDatelabel, Loclabel, Notelabel;
	private static String IDcol="ID: ", Namecol="Name: ", Typecol="Type: ", SDatecol="Start Date: ", EDatecol="End Date: ", Loccol="Location: ", Notecol="Note: ";
	private JFormattedTextField Namefield, Typefield, SDatefield, EDatefield, Locfield, Notefield;
	
	public AddEvent(JTable owner) {
		super(javax.swing.SwingUtilities.windowForComponent(owner));
		table = owner;
		
		//IDlabel = new JLabel(IDcol);
		Namelabel = new JLabel(Namecol);
		Typelabel = new JLabel(Typecol);
		SDatelabel = new JLabel(SDatecol);
		EDatelabel = new JLabel(EDatecol);
		Loclabel = new JLabel(Loccol);
		Notelabel = new JLabel(Notecol);
		
		/*
		IDfield = new JFormattedTextField();
		IDfield.setValue(new Integer(ID));
		IDfield.setColumns(20);
		IDfield.addPropertyChangeListener("value", this);
		*/
		Namefield = new JFormattedTextField();
		Namefield.setValue(new String(Name));
		Namefield.setColumns(20);
		Namefield.addPropertyChangeListener("value", this);
		
		Typefield = new JFormattedTextField();
		Typefield.setValue(new Integer(Type));
		Typefield.setColumns(20);
		Typefield.addPropertyChangeListener("value", this);
		
		SDatefield = new JFormattedTextField();
		SDatefield.setValue(new String(SDate));
		SDatefield.setColumns(20);
		SDatefield.addPropertyChangeListener("value", this);
		
		EDatefield = new JFormattedTextField();
		EDatefield.setValue(new String(EDate));
		EDatefield.setColumns(20);
		EDatefield.addPropertyChangeListener("value", this);
		
		Locfield = new JFormattedTextField();
		Locfield.setValue(new String(Loc));
		Locfield.setColumns(20);
		Locfield.addPropertyChangeListener("value", this);
		
		Notefield = new JFormattedTextField();
		Notefield.setValue(new String(Note));
		Notefield.setColumns(20);
		Notefield.addPropertyChangeListener("value", this);
		
		//IDlabel.setLabelFor(IDfield);
		Namelabel.setLabelFor(Namefield);
		Typelabel.setLabelFor(Typefield);
		SDatelabel.setLabelFor(SDatefield);
		EDatelabel.setLabelFor(EDatefield);
		Loclabel.setLabelFor(Locfield);
		Notelabel.setLabelFor(Notefield);
		
		createGUI();
	}
	/*
	 * function derivative from:
	 * https://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp
	 */
	public static Timestamp convertStringToTimestamp(String strDate) throws ParseException {
	    
	       // you can change format of date
	      Date date = EditDialog.sdf.parse(strDate);
	      Timestamp timeStampDate = new Timestamp(date.getTime());

	      return timeStampDate;
	  }
	
	private void createGUI() {
		setPreferredSize(new Dimension(400, 200));
		setTitle(getClass().getSimpleName());
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		//labelPane.add(IDlabel);
		labelPane.add(Namelabel);
		labelPane.add(Typelabel);
		labelPane.add(SDatelabel);
		labelPane.add(EDatelabel);
		labelPane.add(Loclabel);
		labelPane.add(Notelabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					//List<Service> temp = new ArrayList<Service>();
					Timestamp timestamp = convertStringToTimestamp(SDatefield.getText());
					Timestamp timestamp2 = convertStringToTimestamp(EDatefield.getText());
					
					Event temp = new Event(ID, Name, timestamp, timestamp2, Loc, Note, "", 1);
					EventDAOImp.updateEvent(temp);
					((DefaultTableModel)table.getModel()).insertRow(0, temp.toArray());
					dispose();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO: Display message that informs user that date was invalid
					e1.printStackTrace();
				}
				
			}
		}
		);
		labelPane.add(saveButton);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		//fieldPane.add(IDfield);
		fieldPane.add(Namefield);
		fieldPane.add(Typefield);
		fieldPane.add(SDatefield);
		fieldPane.add(EDatefield);
		fieldPane.add(Locfield);
		fieldPane.add(Notefield);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
		);
		
		fieldPane.add(cancelButton);
		
		add(labelPane, BorderLayout.CENTER);
		add(fieldPane, BorderLayout.LINE_END);
		
		pack();
		setLocationRelativeTo(getParent());
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		
		/*if(source == IDfield) {
			ID = Integer.parseInt(IDfield.getValue().toString());
		}
		else 
		*/
		if(source == Namefield) {
			Name = Namefield.getValue().toString();
		}
		else if(source == Typefield) {
			Type = Integer.parseInt(Typefield.getValue().toString());
		}
		else if(source == SDatefield) {
			SDate = SDatefield.getValue().toString();
		}
		else if(source == EDatefield) {
			EDate = EDatefield.getValue().toString();
		}
		else if(source == Locfield) {
			Loc = Locfield.getValue().toString();
		}
		else if(source == Notefield) {
			Note = Notefield.getValue().toString();
		}
	}

}
