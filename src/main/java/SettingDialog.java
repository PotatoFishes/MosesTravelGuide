import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SettingDialog extends JFrame implements ItemListener {
    static JFrame f;
    JCheckBox c1, c2;
    JComboBox c3;
    String s[] = { "hidden", "male", "female", "others" };
    JPanel p, p2;
    JButton btnOK, btnCancel;
    JLabel l;

    // main class
    public SettingDialog() {
        // create a new frame
        f = new JFrame("Settings");

        // set layout of frame
        f.setLayout(new FlowLayout());

        // create checkbox
        c1 = new JCheckBox("Be Notified of Events?");
        c2 = new JCheckBox("Setting Option 2");
        //TODO set the settings options

        // create gender combo box
        c3 = new JComboBox(s);
        c3.setSelectedIndex(0);
        c3.addItemListener(this);
        //TODO set gender

        l = new JLabel("Select Gender");

        // create a new panel
        p = new JPanel();
        p2 = new JPanel();

        // add checkbox to panel
        p.add(c1);
        p.add(c2);

        p.add(l, BorderLayout.SOUTH);
        p.add(c3, BorderLayout.SOUTH);

        btnOK = new JButton("Save");
        //btnOK.addActionListener(); //TODO save to database

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        p2.add(btnOK);
        p2.add(btnCancel);

        // add panel to frame
        f.add(p);
        f.add(p2,BorderLayout.SOUTH);

        // set the size of frame
        f.setSize(500, 200);
        f.setVisible(true);
    }

    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == c3)
            l.setText(c3.getSelectedItem() + " selected");
    }
}
