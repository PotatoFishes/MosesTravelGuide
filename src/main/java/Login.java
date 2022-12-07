import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Login extends JPanel implements PropertyChangeListener{
    private UserLoginService userService = new UserLoginService();
	
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
		     	if(userService.validateUser(userName, password) != null) {
		     		mainFrame.dispose();
	        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        			@Override
	        			public void run() {
	        				Planner planner = new Planner();
	        				planner.main(null);
	        			}
	        		});
	     		}
			 	else
				{
					JOptionPane.showConfirmDialog(null,
							"Incorrect Username or Password"
							, "Error"
							, JOptionPane.OK_CANCEL_OPTION);
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
		
		JButton forButton = new JButton("Forgot Password");
		forButton.addActionListener(new ActionListener() {
	     	@Override
        	public void actionPerformed(ActionEvent e) {
        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
        			@Override
        			public void run() {
        				
        				JPanel pan=new JPanel();
        				pan.setLayout(new FlowLayout());
        				pan.add(new JLabel("Enter username for recovery"));
        				JTextField t=new JTextField(30);
        				JButton b=new JButton("Okay");
        				b.addActionListener(new ActionListener() {
        			     	@Override
        		        	public void actionPerformed(ActionEvent e) {
        		        		javax.swing.SwingUtilities.invokeLater(new Runnable() {
        		        			@Override
        		        			public void run() {
        		        				User u=UserDAO.getUser(t.getText());
        		        				
        		        				if(u!=null)
        		        				{
        		        					System.out.println(u.getEmail());
        		        					NotificationService.notify(u);
        		        					JOptionPane.showConfirmDialog(null,
        		        							"Email Sent"
        		        							, "Sent"
        		        							, JOptionPane.OK_OPTION);
        		        				}
        		        				else
        		        				{
        		        					JOptionPane.showConfirmDialog(null,
        		        							"Username don't exist"
        		        							, "Error"
        		        							, JOptionPane.OK_OPTION);
        		        				}
        		        			}
        		        		});
        			     	}
        				});
        				pan.add(t);
        				pan.add(b);
        				JDialog jd=new JDialog();
        				jd.add(pan);
        				jd.pack();
        				jd.setVisible(true);
        			}
        		});
	     	}
		});
		
		JPanel buttonPane = new JPanel(new SpringLayout());
		buttonPane.add(loginButton);
		buttonPane.add(newButton);
		buttonPane.add(forButton);
		
	    SpringUtilities.makeCompactGrid(buttonPane, 1,3,10,10,10,10);
		
		JPanel mainPane = new JPanel(new SpringLayout());
		mainPane.add(userNameLabel);
		mainPane.add(userNameField);
		mainPane.add(passwordLabel);
		mainPane.add(passwordField);
		mainPane.add(buttonPane);
		
        SpringUtilities.makeCompactGrid(mainPane, 5,1,10,10,10,10);
		
		mainFrame.add(mainPane);
		
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void createUIComponents() {
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
