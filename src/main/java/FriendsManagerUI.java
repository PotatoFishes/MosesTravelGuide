import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class FriendsManagerUI extends JFrame implements ActionListener, ItemListener{
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
	private static final int ID = 1;
	
	private static final String[][] inSample = {{"Bob", "Bob@bobhouse.net", "Moon", "1234"}};
	private static final String[][] outSample ={ {"Bob", "1234", " X "}};
	private JTextField idEntry;
	private JTextField nameEntry;
	final DefaultTableModel modelIncoming;
	final DefaultTableModel modelOutGoing;
	
	private static JComboBox dropD;
	private SortedSet<String> users = new TreeSet<String>();
	
	FriendsManagerUI(){
		super("Manage Friends");
		JPanel content = new JPanel(new SpringLayout());
		this.setContentPane(content);
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modelIncoming = new DefaultTableModel(null, incomeingColumnNames) {
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
        modelOutGoing = new DefaultTableModel(null, outGoingColumnNames) {
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
                	Integer u = Integer.parseInt( (String) modelOutGoing.getValueAt(modelRow, ID));
                	FriendsService.removePermission(u);
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
        nameEntry = new JTextField(15);
        
        
        JPanel tables = new JPanel();
        tables.add(inSP);
        tables.add(outSP);
        
		String s1[] = { "--", "--", "--"};
		if(users.size() > 0) {
	        dropD = new JComboBox(users.toArray());
		}
		else {
	        dropD = new JComboBox(s1);
		}
        
        //ReportDialog s = new ReportDialog(table);
        dropD.addItemListener(this);
        
        JPanel controlls = new JPanel();
        controlls.setLayout(new GridLayout(0,2));
        controlls.add(new JLabel("Enter username"));
        controlls.add(idEntry);
        controlls.add(new JLabel(""));
        controlls.add(addFriend);
        controlls.setMinimumSize(controlls.getPreferredSize());
        controlls.setMaximumSize(controlls.getPreferredSize());
        
        JPanel panelHolders = new JPanel();
        panelHolders.add(dropD);
        panelHolders.add(controlls);
        panelHolders.add(tables);
        
        setContentPane(panelHolders);
        
        pack();
        this.setVisible(true);
        
        updateOutgoingView();
        updateIncomingView();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Integer id = Integer.parseInt(idEntry.getText());
			if(id.equals(UserLoginService.getUser().id)) {
				JOptionPane.showMessageDialog(null,
	                    "You cannot be your own friend\nPlease Enter a valid User ID"
	                    ,"Error"
	                    ,JOptionPane.OK_OPTION);
			}
			else if(UserDAO.checkExists(id)) {
				FriendsService.addPermission(id);
				updateOutgoingView();
			}
			else {
				JOptionPane.showMessageDialog(null,
	                    "User " + id + " does not exist\nPlease Enter a valid User ID"
	                    ,"Error"
	                    ,JOptionPane.OK_OPTION);
			}
		}
		catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
                    "Incorrect Value Given\nPlease Enter a valid User ID"
                    ,"Error"
                    ,JOptionPane.OK_OPTION);
		}
	}
	//This 
	private void updateOutgoingView() {
		Set<String[]> strs = FriendsService.getFriendsAccess();
		String[][] out = strs.toArray(new String[strs.size()][]);
		
		modelOutGoing.setRowCount(0);
		for(int r = 0; r < out.length; r++) {
			Vector<Object> cols = new Vector<Object>();
			for(int c = 0; c < 3; c++) {
				cols.add(out[r][c]);
			}
			modelOutGoing.addRow(cols);
		}
	}
	
	private void updateIncomingView() {
		Set<String[]> strs = FriendsService.accessFriends();
		String[][] out = strs.toArray(new String[strs.size()][]);
		
		modelIncoming.setRowCount(0);
		for(int r = 0; r < out.length; r++) {
			Vector<Object> cols = new Vector<Object>();
			for(int c = 0; c < 4; c++) {
				cols.add(out[r][c]);
			}
			modelIncoming.addRow(cols);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}
