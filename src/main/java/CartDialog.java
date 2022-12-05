import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CartDialog {
    static JFrame f;
    JPanel p, p2;
    JButton btnOK, btnCancel;
    JLabel l;
    JTextField t;
    Event e = new Event();
    int sum = 0, total = 0;

    // main class
    public CartDialog() {
        // create a new frame
        f = new JFrame("Settings");

        // set layout of frame
        f.setLayout(new FlowLayout());

        List<Service> li = e.getUsedServices(null);

        for(Service s: li)
            sum += s.getPrice();

        l = new JLabel("Total Price for Services: $" + sum);
        t = new JTextField("Insert amount to be paid in decimal");
        t.setEditable(true);

        // create a new panel
        p = new JPanel();
        p2 = new JPanel();

        btnOK = new JButton("Send");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                total = Integer.parseInt(t.getText());
                if (total > 0) {
                    sum *= -1;
                    sum += total;
                }
                l.setText("Balance Remaining: $" + sum);
                JOptionPane.showConfirmDialog(null,
                        "Remaining balance: " + sum
                        , "Balance"
                        , JOptionPane.OK_CANCEL_OPTION);
                if (sum <= total) {
                    JOptionPane.showConfirmDialog(null,
                            "You have Paid for the services"
                            , "Balance"
                            , JOptionPane.OK_CANCEL_OPTION);
                    f.dispose();
                }
            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        p.add(l);
        p.add(t);
        p2.add(btnOK);
        p2.add(btnCancel);

        // add panel to frame
        f.add(p, BorderLayout.NORTH);
        f.add(p2,BorderLayout.SOUTH);

        // set the size of frame
        f.setSize(500, 200);
        f.setVisible(true);
    }
}
