package sc1;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home {
    private JPanel panel1;
    private JButton bs;
    private JButton bn;
    private JLabel newstudent;
    private JPanel p1;
    private JLabel issueBookLabel;
    public static JFrame frame;
    public home() {
        bn.setBorder(new MatteBorder(0,0,2,0, Color.blue));

        bs.setBorder(new MatteBorder(0,0,2,0, Color.blue));

        bs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                timetable t=new timetable();
                t.main(new String[]{"1","2"});
            }
        });
        bn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                student s=new student();
                s.main(new String[]{"1","2"});
            }
        });
    }



    public static void main(String[] args) {
         frame = new JFrame("home");
        frame.setContentPane(new home().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,100);
        frame.pack();
        frame.setVisible(true);
    }
}
