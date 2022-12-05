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
    private UserLoginService userService = new UserLoginService();
    
	private String userName = "";
	private String password = "";
	private String passwordR = "";
	private String email = "";
	private String location = "";
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JLabel passwordLabelR;
	private JLabel emailLabel;
	private JLabel locationLabel;
	private static String colUserName = "Enter username: ";
	private static String colPassword = "Enter password: ";
	private static String colPasswordR = "Re-enter password: ";
	private static String colEmail = "Enter email: ";
	private static String colLocation = "Enter location: ";
	private JFormattedTextField userNameField;
	private JFormattedTextField passwordField;
	private JFormattedTextField passwordFieldR;
	private JFormattedTextField emailField;
	private JFormattedTextField locationField;
	
	public NewAccount() {
		super();
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setOpaque(true);
        mainFrame.setContentPane(this);
    	
    	setPreferredSize(new Dimension(600, 400));
    	
    	userNameLabel = new JLabel(colUserName);
    	passwordLabel = new JLabel(colPassword);
    	passwordLabelR = new JLabel(colPasswordR);
    	emailLabel = new JLabel(colEmail);
    	locationLabel = new JLabel(colLocation);
    	
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
		
    	emailField = new JFormattedTextField();
    	emailField.setValue(new String(email));
    	emailField.setColumns(20);
    	emailField.addPropertyChangeListener("value", this);
    	
    	locationField = new JFormattedTextField();
    	locationField.setValue(new String(location));
    	locationField.setColumns(20);
    	locationField.addPropertyChangeListener("value", this);
		
		userNameLabel.setLabelFor(userNameField);
		passwordLabel.setLabelFor(passwordField);
		passwordLabelR.setLabelFor(passwordFieldR);
		emailLabel.setLabelFor(emailField);
		locationLabel.setLabelFor(locationField);
		
		User user = new User();
		user.setUsername("Bob");
		user.setPassword("1234");
		//userDAO.addUser(user);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
	     	@Override
        	public void actionPerformed(ActionEvent e) {
	     		if(!userName.equals("") && !password.equals("") && !email.equals("") && !location.equals("")) {
	     			if(password.equals(passwordR)) {
	     				//if(!userDAO.nameExists(userName.toLowerCase())) {
	     				if(UserLoginService.validateUser(userName, password) == null) {
		     				//create user here
		     				User user = new User();
		     				user.setLocation(location);
		     				user.setEmail(email);
		     				user.setPassword(password);
		     				user.setUsername(userName);

		     				UserLoginService.updateUser(user);
		     				
				     		mainFrame.dispose();
			        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			        			@Override
			        			public void run() {
			        				Planner planner = new Planner();
			        				planner.main(null);
			        			}
			        		});
	     				}
	     				//else {
	     					//userName = "";
	     			    	//userNameField.setValue(new String(userName));
	     				//}
	     			}
	     		}
	     	}
		});
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(emailLabel);
		labelPane.add(locationLabel);
		labelPane.add(userNameLabel);
		labelPane.add(passwordLabel);
		labelPane.add(passwordLabelR);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(emailField);
		fieldPane.add(locationField);
		fieldPane.add(userNameField);
		fieldPane.add(passwordField);
		fieldPane.add(passwordFieldR);
		
		mainFrame.add(labelPane);
		mainFrame.add(fieldPane);
		mainFrame.add(submitButton);
		
        mainFrame.pack();
        mainFrame.setVisible(true);
	}
    private static void createAndShowGUI() {
		NewAccount home = new NewAccount();
    }
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		
		if(source == userNameField) {
			userName = userNameField.getValue().toString();
		}
		else if(source == passwordField) {
			password = passwordField.getValue().toString();
		}
		else if(source == passwordFieldR) {
			passwordR = passwordFieldR.getValue().toString();
		}
		else if(source == emailField) {
			email = emailField.getValue().toString();
		}
		else if(source == locationField) {
			location = locationField.getValue().toString();
		}
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
