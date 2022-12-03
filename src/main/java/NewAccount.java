import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class NewAccount extends JPanel implements PropertyChangeListener{
    public JFrame mainFrame = new JFrame("Register");
	private String userName = "";
	private String password = "";
	private String passwordR = "";
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JLabel passwordLabelR;
	private static String colUserName = "Enter username: ";
	private static String colPassword = "Enter password: ";
	private static String colPasswordR = "Re-enter password: ";
	private JFormattedTextField userNameField;
	private JFormattedTextField passwordField;
	private JFormattedTextField passwordFieldR;
	
	public NewAccount() {
		super();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setOpaque(true);
        mainFrame.setContentPane(this);
    	
    	setPreferredSize(new Dimension(600, 400));
    	
    	userNameLabel = new JLabel(colUserName);
    	passwordLabel = new JLabel(colPassword);
    	passwordLabelR = new JLabel(colPasswordR);
    	
    	userNameField = new JFormattedTextField();
    	userNameField.setValue(new String(userName));
		userNameField.setColumns(20);
		userNameField.addPropertyChangeListener("value", this);
		
    	passwordField = new JFormattedTextField();
    	passwordField.setValue(new String(password));
		passwordField.setColumns(20);
		passwordField.addPropertyChangeListener("value", this);
		
    	passwordFieldR = new JFormattedTextField();
    	passwordFieldR.setValue(new String(passwordR));
		passwordFieldR.setColumns(20);
		passwordFieldR.addPropertyChangeListener("value", this);
		
		userNameLabel.setLabelFor(userNameField);
		passwordLabel.setLabelFor(passwordField);
		passwordLabelR.setLabelFor(passwordFieldR);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
	     	@Override
        	public void actionPerformed(ActionEvent e) {
	     		mainFrame.dispose();
        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
        			@Override
        			public void run() {
        				Planner planner = new Planner();
        				planner.main(null);
        			}
        		});
	     	}
		});
		
		JPanel buttonPane = new JPanel(new GridLayout(0,1));
		//buttonPane.add(submitButton);
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(userNameLabel);
		labelPane.add(passwordLabel);
		labelPane.add(passwordLabelR);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(userNameField);
		fieldPane.add(passwordField);
		fieldPane.add(passwordFieldR);
		//fieldPane.add(submitButton);
		
		mainFrame.add(labelPane);
		mainFrame.add(fieldPane);
		mainFrame.add(buttonPane);
		mainFrame.add(submitButton);
		
        mainFrame.pack();
        mainFrame.setVisible(true);
	}
    private static void createAndShowGUI() {
		NewAccount home = new NewAccount();
    }
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}
