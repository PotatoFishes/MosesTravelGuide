import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BusinessUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 6865092312557299097L;

	public BusinessUI() {
		super("Manage Business");
		JPanel content = new JPanel();
		JButton add = new JButton("Add");
		add.addActionListener(this);
		content.add(add);
		this.setContentPane(content);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
