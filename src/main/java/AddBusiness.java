import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class AddBusiness extends JDialog implements PropertyChangeListener{
	JTable table;
	private int ID = 0;
	private String Name="";
	private JLabel IDlabel, Namelabel;
	private static String IDcol="ID: ", Namecol="Name: ";
	private JFormattedTextField IDfield, Namefield;
	
	public AddBusiness(JTable owner) {
		super(javax.swing.SwingUtilities.windowForComponent(owner));
		table = owner;
		
		IDlabel = new JLabel(IDcol);
		Namelabel = new JLabel(Namecol);
		
		IDfield = new JFormattedTextField();
		IDfield.setValue(new Integer(ID));
		IDfield.setColumns(20);
		IDfield.addPropertyChangeListener("value", this);
		
		Namefield = new JFormattedTextField();
		Namefield.setValue(new String(Name));
		Namefield.setColumns(20);
		Namefield.addPropertyChangeListener("value", this);
		
		IDlabel.setLabelFor(IDfield);
		Namelabel.setLabelFor(Namefield);
		
		createGUI();
	}
	
	private void createGUI() {
		setPreferredSize(new Dimension(400, 200));
		setTitle(getClass().getSimpleName());
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(IDlabel);
		labelPane.add(Namelabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		}
		);
		labelPane.add(saveButton);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(IDfield);
		fieldPane.add(Namefield);
		
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
		
		if(source == IDfield) {
			ID = Integer.parseInt(IDfield.getValue().toString());
		}
		else if(source == Namefield) {
			Name = Namefield.getValue().toString();
		}
	}
}
