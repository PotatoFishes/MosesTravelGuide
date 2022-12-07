import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

public class BrowseService extends JFrame implements ActionListener, ItemListener {
    final Class<?>[] columnClass = new Class[]{
            String.class, String.class,String.class,String.class,String.class,String.class,String.class, String.class
    };
    private String[] columnNames = {
            "ID","Price","Name", "Start Time", "End Time", "Capacity"
    };
    String s1[];
    final DefaultTableModel model;
    Service s;

    public BrowseService() {
        super("Add Service");
        JPanel content = new JPanel(new SpringLayout());
        this.setContentPane(content);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(ServiceServ.getAllServicesForTable() , columnNames) {

            private static final long serialVersionUID = -4279510772803332762L;
            @Override
            public boolean isCellEditable(int row, int col) {
                if(col < 5) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public Class <?> getColumnClass(int col)
            {
                return columnClass[col];
            }};

        JTable table = new JTable(model);
        new TableFilterHeader(table);

        JScrollPane scroll = new JScrollPane(table);

        JPanel tables = new JPanel();
        tables.add(scroll);

        JButton addButton = new JButton("Add Service");
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(table.getSelectedRow() > 0) {
                    int row = table.getSelectedRow();
                    s = ServiceDAOImp.getService((int) table.getValueAt(row, 0));
                }
            }
        });

        JButton delButton = new JButton("Cancel");
        delButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        JPanel lhs = new JPanel();
        lhs.setLayout(new GridLayout(2,1));
        lhs.add(addButton);
        lhs.add(delButton);


        JPanel panelHolders = new JPanel();
        panelHolders.add(lhs);
        panelHolders.add(tables);

        setContentPane(panelHolders);

        pack();
        this.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public Service getService() {
        return s;
    }
}