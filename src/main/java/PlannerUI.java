import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlannerUI
{
    public static JFrame frame = new JFrame("Team Tasks");
    public static JPanel forAll = new JPanel();
    public static JPanel TopPanel = new JPanel();
    public static JPanel MainCal = new JPanel();
    public static JPanel forDateList = new JPanel();
    public static ArrayList<JPanel> date = new ArrayList<>();
    public static volatile boolean logout = false;

    public PlannerUI()
    {
        //Create and show the calendar GUI
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    public static class RoundButton extends JButton
    {
        public RoundButton(String label)
        {
            super(label);
            setFocusable(false);
    /*
     These statements enlarge the button so that it
     becomes a circle rather than an oval.
    */
            Dimension size = new Dimension(25, 25);
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
            setContentAreaFilled(false);
//            setBackground();
            setOpaque(false);
        }

        protected void paintBorder(Graphics g)
        {
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }

        protected void paintComponent(Graphics g)
        {
            if (getModel().isArmed())
            {
                g.setColor(Color.gray);
            }
            else
            {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

            super.paintComponent(g);
        }

    }

    public static void CreateFrame()
    {
        frame.setSize(1200, 850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialize logout to be false
        logout = false;

        //Add menu
        JMenuBar menuBar;
        final JMenu menu;
        final JMenuItem menuItem;
        JMenuItem menuItem2;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menuBar.add(menu);
        menuItem = new JMenuItem("Blacklist");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem2 = new JMenuItem("Logout");
        menuItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logout = true;
            }

        });

        // Create Top Panel
        TopPanel = CreateTop();

        // TODO: make it automatically
        // Set date panel
        date.add(DatePanel(""));
        date.add(DatePanel(""));
        date.add(DatePanel(""));
        for (int i = 1; i < 32; i++) {
            date.add(DatePanel(String.valueOf(i)));
        }
        date.add(DatePanel(""));

        // Create Date Panel
        for (int i = 0; i < 35; i++) {
            forDateList.add(date.get(i));
        }

        MainCal.setPreferredSize(new Dimension(700, 750));
        // TODO: Sometime is 5 x 8
        forDateList.setLayout(new GridLayout(5, 7));

        //Add center panel to hold calendar and chat
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(1100, 750));

        MainCal.add(createDateLbl());
        MainCal.add(forDateList);

        centerPanel.add(MainCal, BorderLayout.WEST);
//        JPanel chatP = new JPanel();
//        chatP.setPreferredSize(new Dimension(350, 750));
//        chatP.add(c.getChatPanel());
//        centerPanel.add(c.getChatPanel(), BorderLayout.EAST);

        forAll.setPreferredSize(new Dimension(1100, 900));
        forAll.add(TopPanel, BorderLayout.NORTH);
        forAll.add(centerPanel, BorderLayout.SOUTH);

        frame.add(forAll);
        frame.setVisible(true);
    }

    public static void showAddMember()
    {
        final JFrame email = new JFrame();
        Dimension d = new Dimension(300, 70);
        email.setPreferredSize(d);
        JPanel emailPanel = new JPanel();
        emailPanel.setPreferredSize(d);
        JLabel emailLbl =  new JLabel("Email");
        JTextField emailTF = new JTextField();
        emailTF.setText("example@example.com");
        JButton search = new JButton("Search");

        email.setTitle("Enter the Email");
        emailPanel.add(emailLbl);
        emailPanel.add(emailTF);
        emailPanel.add(search);
        email.add(emailPanel);

        // TODO: wait to be implement
//            search.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
        search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                email.dispose();
            }

        });

        email.pack();
        email.setVisible(true);

    }

    public static JPanel createDateLbl()
    {
        JPanel panel = new JPanel();
        Dimension d = new Dimension(95, 20);

        JPanel ForMonday = new JPanel();
        JPanel ForTuesday = new JPanel();
        JPanel ForWednesday = new JPanel();
        JPanel ForThursday = new JPanel();
        JPanel ForFriday = new JPanel();
        JPanel ForSaturday = new JPanel();
        JPanel ForSunday = new JPanel();

        JLabel Monday = new JLabel("Monday");
        JLabel Tuesday = new JLabel("Tuesday");
        JLabel Wednesday = new JLabel("Wednesday");
        JLabel Thursday = new JLabel("Thursday");
        JLabel Friday = new JLabel("Friday");
        JLabel Saturday = new JLabel("Saturday");
        JLabel Sunday = new JLabel("Sunday");

        ForMonday.setPreferredSize(d);
        ForTuesday.setPreferredSize(d);
        ForWednesday.setPreferredSize(d);
        ForThursday.setPreferredSize(d);
        ForFriday.setPreferredSize(d);
        ForSaturday.setPreferredSize(d);
        ForSunday.setPreferredSize(d);

        ForMonday.add(Monday);
        ForTuesday.add(Tuesday);
        ForWednesday.add(Wednesday);
        ForThursday.add(Thursday);
        ForFriday.add(Friday);
        ForSaturday.add(Saturday);
        ForSunday.add(Sunday);

        panel.add(ForSunday);
        panel.add(ForMonday);
        panel.add(ForTuesday);
        panel.add(ForWednesday);
        panel.add(ForThursday);
        panel.add(ForFriday);
        panel.add(ForSaturday);

        return panel;
    }

    public static JPanel DatePanel(String str)
    {
        JPanel p = new JPanel();

        //to_DB.setPreferredSize(new Dimension(80, 20));
        JLabel date = new JLabel();
        date.setText(str);

        p.setPreferredSize(new Dimension(100, 140));

        p.add(date, BorderLayout.NORTH);
        // TODO: Get description from dicusion broad
        if (!str.equals("")) {
            JPanel for_button = new JPanel();
            for_button.setPreferredSize(new Dimension(85, 30));
            JButton to_DB = new JButton("");
            for_button.add(to_DB, BorderLayout.CENTER);
            p.add(for_button, BorderLayout.SOUTH);

            to_DB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ;
                }
            });
        }
        p.setBorder(new roundBorder.RectBorder());
        return p;
    }

    public static JPanel CreateTop()
    {
        JPanel panel = new JPanel();
//        JPanel forLabel = new JPanel();
//        JPanel forMember = new JPanel();
        Dimension d = new Dimension(1100, 40);
        panel.setPreferredSize(d);
        panel.setBorder(new roundBorder.RoundedBorder(20));
        panel.setOpaque(true);

        // TODO: for demonstration, the name may vary
        JLabel nameLabel = new JLabel("Current Month");

        // TODO: for demonstration, the member may vary
        JButton AddNewMember = new RoundButton("+");
        JButton AddNewEvent = new RoundButton("Event");
//        JButton member1 = new RoundButton("JW");
//        JButton member2 = new RoundButton("RZ");
//        JButton member3 = new RoundButton("WD");
//        JButton member4 = new RoundButton("JS");

        AddNewMember.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddMember();
            }
        });
        AddNewEvent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAddEvent();
            }
        });

        panel.add(AddNewEvent);
//        forLabel.add(nameLabel);
//        forMember.add(member1);
//        forMember.add(member2);
//        forMember.add(member3);
//        forMember.add(member4);
//        forMember.add(AddNewMember);
//        panel.add(forLabel, BorderLayout.LINE_START);
//        panel.add(forMember, BorderLayout.LINE_END);
        panel.add(nameLabel);
//        panel.add(member1);
//        panel.add(member2);
//        panel.add(member3);
//        panel.add(member4);
        panel.add(AddNewMember);
        return panel;
    }

    private static void createAndShowGUI()
    {
        CreateFrame();
    }

    public static void createAddEvent()
    {
        JFrame Event = new JFrame();
        Event.setSize(new Dimension(400, 200));
        Event.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel eventlbl = new JLabel("Event: ");
        JLabel locatlbl = new JLabel("Location: ");
        JLabel descplbl = new JLabel("Description: ");
        JLabel startlbl = new JLabel("Start Time: ");
        JLabel endinlbl = new JLabel("End Time: ");

        JTextField event = new JTextField("Event Name", 20);
        JTextField locat = new JTextField("Location", 20);
        JTextArea descp = new JTextArea("Description");
        descp.setBorder(new roundBorder.RectBorder());
        JTextField start = new JTextField("yyyy-MM-DD HH(0-23):mm", 20);
        JTextField end = new JTextField("yyyy-MM-DD HH(0-23):mm", 20);

        eventlbl.setLabelFor(event);
        locatlbl.setLabelFor(locat);
        descplbl.setLabelFor(descp);
        startlbl.setLabelFor(start);
        endinlbl.setLabelFor(end);

        JPanel layoutOg = new JPanel();
        layoutOg.setLayout(new SpringLayout());
        layoutOg.add(eventlbl);
        layoutOg.add(event);
        layoutOg.add(locatlbl);
        layoutOg.add(locat);
        layoutOg.add(descplbl);
        layoutOg.add(descp);
        layoutOg.add(startlbl);
        layoutOg.add(start);
        layoutOg.add(endinlbl);
        layoutOg.add(end);

//Lay out the panel.
        SpringUtilities.makeCompactGrid(layoutOg,
                5, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        JButton post = new JButton("POST");
        Event.add(layoutOg);
        Event.add(post, BorderLayout.SOUTH);

        //TODO: POST button
//        post.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
        Event.setVisible(true);
    }
}
