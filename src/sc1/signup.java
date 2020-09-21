package sc1;





import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class signup {

    private JTextField ut;
    private JPasswordField pt;
    private JComboBox qt;
    private JTextField at;
    private JButton bs;
    public   JPanel p;
    private JButton bd;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JPanel p5;
    private JPanel p7;
    private JPanel p6;
    private JPanel p1;
    public  static JFrame f;
    public  signup()
    {

        super();

        bd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                f.setVisible(false);
                login l=new login();
                l.main(new String[]{"1", "2"});
            }
        });
        bs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Connection conn=null;
                PreparedStatement st=null;
                ResultSet rs=null;
                try{
                    String username = "root";
                    String password = "bhargav$987";
                    String url = "jdbc:mysql://localhost:3306/library";
                    conn = DriverManager.getConnection(url, username, password);
                    String s=ut.getText();


                    char pass[]=pt.getPassword();
                    String s1=new String(pass);
                    String s2=(String)qt.getSelectedItem();
                    String s3=at.getText();
                    st=conn.prepareStatement("insert into login values(?,?,?,?)");
                    st.setString(1,s);
                    st.setString(2,s1);
                    st.setString(3,s2);
                    st.setString(4,s3);
                    st.execute();


                    JOptionPane.showMessageDialog(null,"Your Account Succefully  Created");
                    f.setVisible(false);
                    login l=new login();
                    l.main(new String[]{"1", "2"});
                }catch(Exception e)
                {
                    System.out.print(e.getMessage());
                }
            }
        });
        ut.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    pt.requestFocus();

                }

            }
        });
        pt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    qt.requestFocus();

                }
                if(k==KeyEvent.VK_UP) {
                    ut.requestFocus();

                }
            }
        });
        qt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    at.requestFocus();

                }
                if(k==KeyEvent.VK_UP) {
                    pt.requestFocus();

                }
            }
        });
        at.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();

                if(k==KeyEvent.VK_UP) {
                    qt.requestFocus();

                }
            }
        });
    }

    public static void main(String[] args) {
        f = new JFrame("signup");
        f.setContentPane(new signup().p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(300,100);
        f.pack();
        f.setVisible(true);


    }

    public void setVisible(boolean b) {
        f.setVisible(b);
    }
}

