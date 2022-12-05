import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class InviteEvents extends JDialog implements PropertyChangeListener{
	List<JTable> tables = new ArrayList<JTable>();
	private FriendsService fService = new FriendsService();
    private TableRowSorter<DefaultTableModel> sorter;
    
    private List<String> friendNames = new ArrayList<String>();
	
    private String[] columnNames = {
            "Name", "Invited"
    };
    private Object[][] data;
	
	public InviteEvents(JTable owner) {
		super(javax.swing.SwingUtilities.windowForComponent(owner));
		
		data = new Object[1][2];
		data[0][0] = "Bob";
		
        final Class<?>[] columnClass = new Class[]{
                String.class, Boolean.class
        };
        final DefaultTableModel model = new DefaultTableModel(data, columnNames) {
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
        
        friendNames = fService.getFriendNames();
        
        for(int i = 0; i < friendNames.size(); i++) {
        	JTable temp = new JTable();
        	Object[][] events = new Object[friendNames.size()][2];
        	
        	
            final DefaultTableModel model2 = new DefaultTableModel(events, columnNames) {
                @Override
                public boolean isCellEditable(int row, int col) {
                	if(col == 1) {
                        return true;
                	}
                	return false;
                }
                @Override
                public Class <?> getColumnClass(int col)
                {
                    return columnClass[col];
                }
            };
        	
            temp = new JTable(model2);
            temp.setRowSorter(sorter);
            temp.setPreferredScrollableViewportSize(new Dimension(600, getToolkit().getScreenResolution()));
            temp.setFillsViewportHeight(true);
            temp.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            tables.add(temp);
        }
		
		createGUI();
	}
	
	private void createGUI() {
		setPreferredSize(new Dimension(800, 200));
		setTitle(getClass().getSimpleName());
		
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		for(int a = 0; a < friendNames.size(); a++) {
			JLabel tmp = new JLabel(friendNames.get(a));
			
			listPane.add(tmp);
			listPane.add(tables.get(a));
		}
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//save it to the database database
				dispose();
			}
		}
		);
		listPane.add(saveButton);
		add(listPane);
		
		pack();
		setLocationRelativeTo(getParent());
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
