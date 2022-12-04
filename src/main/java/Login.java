import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class Login extends JPanel implements PropertyChangeListener{
    private UserDAO userDAO = new UserDAO();
	
    private JPanel loginForm;
    private JLabel Label;
    public JFrame mainFrame = new JFrame("Login");
	private String userName = "";
	private String password = "";
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private static String colUserName = "Enter username: ";
	private static String colPassword = "Enter password: ";
	private JFormattedTextField userNameField;
	private JFormattedTextField passwordField;
    
    public Login() {
    	super();
    	//setLayout(new GroupLayout(this));
    	//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	//setLayout(new BorderLayout());
    	
    	//GroupLayout layout = new GroupLayout();
    	
        //JFrame mainFrame = new JFrame("Login");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setOpaque(true);
        mainFrame.setContentPane(this);
    	
    	setPreferredSize(new Dimension(600, 400));
    	
    	userNameLabel = new JLabel(colUserName);
    	passwordLabel = new JLabel(colPassword);
    	
    	userNameField = new JFormattedTextField();
    	userNameField.setValue(new String(userName));
		userNameField.setColumns(20);
		userNameField.addPropertyChangeListener("value", this);
		
    	passwordField = new JFormattedTextField();
    	passwordField.setValue(new String(password));
		passwordField.setColumns(20);
		passwordField.addPropertyChangeListener("value", this);
		
		userNameLabel.setLabelFor(userNameField);
		passwordLabel.setLabelFor(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
	     	@Override
        	public void actionPerformed(ActionEvent e) {
	     		//check if username and password are valid
	     		if(userDAO.nameExists(userName.toLowerCase()) && userDAO.passwordExists(password)) {
		     		
		     		mainFrame.dispose();
	        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        			@Override
	        			public void run() {
	        				Planner planner = new Planner();
	        				planner.main(null);
	        			}
	        		});
	     		}
	     	}
		});
		
		JButton newButton = new JButton("New Account");
		newButton.addActionListener(new ActionListener() {
	     	@Override
        	public void actionPerformed(ActionEvent e) {
	     		mainFrame.dispose();
        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
        			@Override
        			public void run() {
        				NewAccount account = new NewAccount();
        			}
        		});
	     	}
		});
		
		JPanel buttonPane = new JPanel(new GridLayout(0,1));
		buttonPane.add(loginButton);
		buttonPane.add(newButton);
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(userNameLabel);
		labelPane.add(passwordLabel);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(userNameField);
		fieldPane.add(passwordField);
		
		//add(labelPane, BorderLayout.CENTER);
		//add(fieldPane, BorderLayout.LINE_END);
		//add(buttonPane, BorderLayout.PAGE_END);
		
		mainFrame.add(labelPane);
		mainFrame.add(fieldPane);
		mainFrame.add(buttonPane);
		
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void createUIComponents() {
        //JFrame frame = new JFrame("Login");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*Login home = new Login();
        home.setOpaque(true);
        frame.setContentPane(home);
        
        frame.pack();
        frame.setVisible(true);*/
    	
        //JFrame mainFrame = new JFrame("Login");
        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Login home = new Login();
        
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
	}
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createUIComponents();
            }
        });
    }
}
