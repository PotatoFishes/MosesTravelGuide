import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ManageEvents extends JDialog implements PropertyChangeListener{
	private EventsServ eventService = new EventsServ();
	private static List<Event> events = new ArrayList<Event>();
	JTable table;
    private JTextField filterText;
    private JTextField statusText;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private String[] columnNames = {
            "Name", "Start Time", "End Time", "Location", "Note", "Service", "Archive", "Edit", "Delete"
    };
    private Object[][] data = {};
	
	public ManageEvents(JTable owner) {
		super(javax.swing.SwingUtilities.windowForComponent(owner));
		table = owner;
		events = eventService.getEventsForPlanner();
		
        //Create a table with a sorter.
        final Class<?>[] columnClass = new Class[]{
                String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class
        };
        final DefaultTableModel model = new DefaultTableModel(eventService.getEventsForTable(), columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                    return true;
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return columnClass[col];
            }
        };
		//events = eventService.getEventsForPlanner();
		//for(int i = 0; i < table.getRowCount(); i++) {
			//for(int j = 0; j < table.getColumnCount(); j++) {
				//table.getValueAt(i, j);
			//}
		//}
		
        sorter = new TableRowSorter<DefaultTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(600, getToolkit().getScreenResolution()));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        createGUI();
	}
	
	private void createGUI() {
        //setLayout(new SpringLayout());
		setPreferredSize(new Dimension(800, 200));
		setTitle(getClass().getSimpleName());

        //JScrollPane scrollPane = new JScrollPane(table);
        //add(scrollPane);
        
		JButton serviceButton = new JButton("Add Service");
		serviceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
		);
		
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

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
