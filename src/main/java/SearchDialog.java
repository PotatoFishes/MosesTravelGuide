import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchDialog {
    static JFrame f;
    JPanel p, p2, p3;
    JButton btnCancel;
    JLabel l;
    JTextField t;
    String s;

    // main class
    public SearchDialog(Object o) {
        // create a new frame
        f = new JFrame("Search Result");

        // set layout of frame
        f.setLayout(new BorderLayout());

        s = SearchService.getData(o);
        String[] str = s.split(",");

        l = new JLabel("The Full List of " + o);

        // create a new panel
        p = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();

        btnCancel = new JButton("Done");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        p.add(l);
        p2.add(btnCancel);
        p3.add(new JScrollPane(new JList<String>(str)));

        // add panel to frame
        f.add(p, BorderLayout.NORTH);
        f.add(p3, BorderLayout.CENTER);
        f.add(p2,BorderLayout.SOUTH);

        // set the size of frame
        f.setSize(500, 200);
        f.setVisible(true);
    }
}
