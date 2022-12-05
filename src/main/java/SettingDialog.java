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
    preferences p3;
    int num = 0;

    // main class
    public SettingDialog(User u) {
        // create a new frame
        f = new JFrame("Settings");

        // set layout of frame
        f.setLayout(new FlowLayout());

        p3 = PreferenceDAO.getPreference(u.id);

        // create checkbox
        c1 = new JCheckBox("Be Notified of Events?");
        c2 = new JCheckBox("Private");
        if (p3 != null) {
            c1.setSelected(p3.isNoti());
            c2.setSelected(p3.isPriv());
        }


        // create gender combo box
        c3 = new JComboBox(s);
        if (p3 != null) {
            for (String a : s) {
                if (p3.getGender() == a) {
                    break;
                }
                num++;
            }
        }

        if (num != 0)
            num--;
        c3.setSelectedIndex(num);
        c3.addItemListener(this);

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
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p3 = new preferences(u.id, c1.isSelected(), c2.isSelected(), c3.getSelectedItem().toString());
                PreferenceDAO.updatePreference(p3);
            }
        });

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
