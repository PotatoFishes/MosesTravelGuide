import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.coderazzi.filters.gui.TableFilterHeader;

public class JoinEvent extends JFrame implements ActionListener, ItemListener{
    private static final long serialVersionUID = 924430730575961493L;
    final Class<?>[] columnClass = new Class[]{
            String.class, String.class,String.class,String.class,String.class,String.class,String.class, String.class
    };
    private String[] columnNames = {
            "ID","Start Time", "End Time", "Location", "Name", "Note"
    };
    String s1[];
    final DefaultTableModel model;

    public JoinEvent(User u) {
        super("Add Event");
        JPanel content = new JPanel(new SpringLayout());
        this.setContentPane(content);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(EventsServ.getAllEventsForTable() , columnNames) {

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

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int row = table.getSelectedRow();
                Event eve = new Event();
                try {
                    eve.setStartDate((String) table.getValueAt(row, 1));
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    eve.setEndDate((String) table.getValueAt(row, 2));
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                eve.setLocation((String) table.getValueAt(row, 3));
                eve.setName((String) table.getValueAt(row, 4));
                eve.settNote((String) table.getValueAt(row, 5));
                // TODO Yutai Update Database function
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
}
