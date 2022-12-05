import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class FriendsManagerUI extends JFrame{
	private static final long serialVersionUID = 924430730575961493L;
	
	
	FriendsManagerUI(){
		super("Manage Friends");
		JPanel content = new JPanel(new SpringLayout());
		this.setContentPane(content);
		this.setVisible(true);
		pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}
