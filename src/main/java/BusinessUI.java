import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	private static final Class<?>[] incomingColumnClass = new Class[]{
            String.class, String.class, String.class, String.class, String.class, String.class
    };

	private static final String[] ColumnNames = {"Price", "Start Date", "End Date", "ID", "Bookings", "Capacity"};
    //id, eventName, Start, End, Location, notes, usedServices, userid, createdB

	private JTextField idEntry;
	final DefaultTableModel model;
    JButton delButton, createButton;
	
	public BusinessUI(int u){
		super("Manage Business");
		JPanel content = new JPanel(new SpringLayout());
		this.setContentPane(content);
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model = new DefaultTableModel(EventsServ.getAllEventsForBusiness(u), ColumnNames) {
			private static final long serialVersionUID = 2767279126624268207L;
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
                    System.out.println("" + model.getValueAt(modelRow, 0) );
                    EventsServ.removeEvent(Integer.valueOf( "" + model.getValueAt(modelRow, 0)));
                    model.removeRow(modelRow);
                }
            }
        };
        table = new JTable(model);

        new TableFilterHeader(table);
        
        JScrollPane sp = new JScrollPane(table);


        createButton = new JButton("Create Event");
        createButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new CreateEvent(table);
            }
        });

        delButton = new JButton("Cancel");
        delButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        
        JPanel tables = new JPanel();
        tables.add(sp);
        
        JPanel controlls = new JPanel();
        controlls.setLayout(new GridLayout(1,2));
        controlls.add(createButton);
        controlls.add(delButton);

        
        JPanel panelHolders = new JPanel();
        panelHolders.add(controlls);
        panelHolders.add(tables);
        
        setContentPane(panelHolders);
        
        pack();
        this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	private void updateServices() {
		model.setColumnCount(0);
	}
}