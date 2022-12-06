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
    JPanel p, p2, p4;
    JButton btnOK, btnCancel, del;
    JLabel l, label;
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
        p4 = new JPanel();

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

        del = new JButton("Delete Account");
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Yes! Please.", "No!"};
                int result = JOptionPane.showOptionDialog(
                        f,
                        "You sure want to delete your account?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //no custom icon
                        options,  //button titles
                        options[0] //default button
                );
                if(result == JOptionPane.YES_OPTION){
                    UserLoginService.deleteUser();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.dispose();
                }else if (result == JOptionPane.NO_OPTION){
                    f.dispose();
                }
            }
        });

        del.setBackground(Color.RED);
        del.setOpaque(true);
        del.setBorderPainted(false);

        p2.add(btnOK);
        p2.add(btnCancel);
        p4.add(del);

        // add panel to frame
        f.add(p, BorderLayout.NORTH);
        f.add(p2,BorderLayout.CENTER);
        f.add(p4,BorderLayout.SOUTH);

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
