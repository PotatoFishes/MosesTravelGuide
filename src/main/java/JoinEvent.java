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

<<<<<<< HEAD
	      return timeStampDate;
	  }
	
	private void createGUI() {
		setPreferredSize(new Dimension(400, 200));
		setTitle(getClass().getSimpleName());
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		//labelPane.add(IDlabel);
		labelPane.add(Namelabel);
		labelPane.add(Typelabel);
		labelPane.add(SDatelabel);
		labelPane.add(EDatelabel);
		labelPane.add(Loclabel);
		labelPane.add(Notelabel);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//List<Service> temp = new ArrayList<Service>();
					Timestamp timestamp = convertStringToTimestamp(SDatefield.getText());
					Timestamp timestamp2 = convertStringToTimestamp(EDatefield.getText());
					timestamp.after(timestamp2);
					
					Event temp = new Event(ID, Name, timestamp, timestamp2, Loc, Note, "", );
					if(!EventsServ.checkTimesValid(temp))
					{
						JOptionPane.showConfirmDialog(null,
								"Incorrect Time Format: " + SDatefield.getText() + " is equal to or after " + EDatefield.getText()
								, "Error"
								, JOptionPane.OK_CANCEL_OPTION);
						System.out.println("Can't Insert event " + temp.toString());
					}
					else
					{
						System.out.println("Can Insert event " + temp.toString());
						EventsServ.createEvent(temp);
						((DefaultTableModel)table.getModel()).insertRow(0, temp.toArray());
						dispose();
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO: Display message that informs user that date was invalid
					JOptionPane.showConfirmDialog(null,
							"Incorrect Time Format: Please Format as 'YYYY-MM-dd HH:mm:ss.SSS'\n from '1000-01-01 00:00:00.000' to '9999-12-31 00:00:00.000'"
							, "Error"
							, JOptionPane.OK_CANCEL_OPTION);
					e1.printStackTrace();
				}
			}
		}
		);
		labelPane.add(saveButton);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		//fieldPane.add(IDfield);
		fieldPane.add(Namefield);
		fieldPane.add(Typefield);
		fieldPane.add(SDatefield);
		fieldPane.add(EDatefield);
		fieldPane.add(Locfield);
		fieldPane.add(Notefield);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
		);
		
		fieldPane.add(cancelButton);
		
		add(labelPane, BorderLayout.CENTER);
		add(fieldPane, BorderLayout.LINE_END);
		
		pack();
		setLocationRelativeTo(getParent());
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		
		/*if(source == IDfield) {
			ID = Integer.parseInt(IDfield.getValue().toString());
		}
		else 
		*/
		if(source == Namefield) {
			Name = Namefield.getValue().toString();
		}
		else if(source == Typefield) {
			Type = Integer.parseInt(Typefield.getValue().toString());
		}
		else if(source == SDatefield) {
			SDate = SDatefield.getValue().toString();
		}
		else if(source == EDatefield) {
			EDate = EDatefield.getValue().toString();
		}
		else if(source == Locfield) {
			Loc = Locfield.getValue().toString();
		}
		else if(source == Notefield) {
			Note = Notefield.getValue().toString();
		}
	}
=======
        model = new DefaultTableModel(EventsServ.getAllEventsForTable() , columnNames) {
>>>>>>> origin/source

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
