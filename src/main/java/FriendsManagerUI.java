import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import net.coderazzi.filters.gui.TableFilterHeader;

public class FriendsManagerUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 924430730575961493L;
	
	private JTable outGoingFriends;
	private JTable incomingFriends;
	private static final Class<?>[] incomingColumnClass = new Class[]{
            String.class, String.class, String.class, String.class
    };
	private static final Class<?>[] outGoingColumnClass = new Class[]{
            String.class, String.class, String.class
    };
	private static final String[] incomeingColumnNames = {"Name", "Email", "Location", "ID"};
	private static final String[] outGoingColumnNames = {"Name", "ID", " Remove "};
	private static final int REMOVE = 2;
	
	private static final String[][] inSample = {{"Bob", "Bob@bobhouse.net", "Moon", "1234"}};
	private static final String[][] outSample ={ {"Bob", "1234", " X "}};
	private JTextField idEntry;
	
	FriendsManagerUI(){
		super("Manage Friends");
		JPanel content = new JPanel(new SpringLayout());
		this.setContentPane(content);
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final DefaultTableModel modelIncoming = new DefaultTableModel(inSample, incomeingColumnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return incomingColumnClass[col];
            }
        };
        final DefaultTableModel modelOutGoing = new DefaultTableModel(outSample, outGoingColumnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                if(col < REMOVE) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return outGoingColumnClass[col];
            }
        };
        Action remove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                int answer = JOptionPane.showConfirmDialog(null,
                        "Do you want to remove " + modelOutGoing.getValueAt(modelRow, 0)
                                + " " + modelOutGoing.getValueAt(modelRow,  1) + "?"
                        ,"Warning"
                        ,JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION)
                {
                	modelOutGoing.removeRow(modelRow);
                }
            }
        };
        incomingFriends = new JTable(modelIncoming);
        outGoingFriends = new JTable(modelOutGoing);
        new TableFilterHeader(incomingFriends);
        new TableFilterHeader(outGoingFriends);
        new ButtonColumn(outGoingFriends, remove, REMOVE);
        
        JScrollPane inSP = new JScrollPane(incomingFriends);
        JScrollPane outSP = new JScrollPane(outGoingFriends);
        
        JButton addFriend = new JButton("add");
        addFriend.addActionListener(this);
        idEntry = new JTextField(15);
        
        
        JPanel tables = new JPanel();
        tables.add(inSP);
        tables.add(outSP);
        
        JPanel controlls = new JPanel();
        controlls.setLayout(new GridLayout(0,2));
        controlls.add(new JLabel("ID entry"));
        controlls.add(idEntry);
        controlls.add(new JLabel(""));
        controlls.add(addFriend);
        controlls.setMinimumSize(controlls.getPreferredSize());
        controlls.setMaximumSize(controlls.getPreferredSize());
        
        JPanel panelHolders = new JPanel();
        panelHolders.add(controlls);
        panelHolders.add(tables);
        
        setContentPane(panelHolders);
        
        pack();
        this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Integer id = Integer.parseInt(idEntry.getText());
		}
		catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
                    "Incorrect Value Given\nPlease Enter a valid User ID"
                    ,"Error"
                    ,JOptionPane.OK_OPTION);
		}
	}
	
}
