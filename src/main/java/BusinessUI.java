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

public class BusinessUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 6865092312557299097L;
	
	private JTable table;
	//private JTable incomingFriends;
	private static final Class<?>[] columnClass = new Class[]{
            String.class, String.class, String.class, String.class, String.class, String.class
    };
	/*private static final Class<?>[] outGoingColumnClass = new Class[]{
            String.class, String.class, String.class
    };*/
	private static final String[] incomeingColumnNames = {"Price", "Start Date", "End Date", "ID", "Bookings", "Capacity"};
	//private static final String[] outGoingColumnNames = {"Name", "ID", " Remove "};
	//private static final int REMOVE = 2;
	//private static final int ID = 1;
	
	//private static final String[][] outSample ={ {"Bob", "1234", " X "}};
	private JTextField idEntry;
	final DefaultTableModel model;
	//final DefaultTableModel modelOutGoing;
	
	public BusinessUI(){
		super("Manage Business");
		JPanel content = new JPanel(new SpringLayout());
		this.setContentPane(content);
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model = new DefaultTableModel(null, incomeingColumnNames) {
			private static final long serialVersionUID = 2767279126624268207L;
			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return columnClass[col];
            }
        };
        Action remove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                int answer = JOptionPane.showConfirmDialog(null,
                        "Do you want to remove row " + modelRow + "?"
                        ,"Warning"
                        ,JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION)
                {
                	//Integer u = Integer.parseInt( (String) modelOutGoing.getValueAt(modelRow, ID));
                	//FriendsService.removePermission(u);
                	//modelOutGoing.removeRow(modelRow);
                }
            }
        };
        table = new JTable(model);
        //new TableFilterHeader(incomingFriends);
        new TableFilterHeader(table);
        //new ButtonColumn(outGoingFriends, remove, REMOVE);
        
        JScrollPane sp = new JScrollPane(table);
        //JScrollPane outSP = new JScrollPane(outGoingFriends);
        
        JButton addFriend = new JButton("add");
        addFriend.addActionListener(this);
        idEntry = new JTextField(15);
        
        
        JPanel tables = new JPanel();
        tables.add(sp);
        //tables.add(outSP);
        
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
        
        //updateOutgoingView();
        //updateIncomingView();
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
				//updateOutgoingView();
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
	
	private void updateServices() {
		model.setColumnCount(0);
		
	}
}