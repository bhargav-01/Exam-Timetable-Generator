package sc1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login {
    private JTextField tu;
    private JPasswordField tp;
    private JButton logInButton;
    private JPanel p;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JPanel p5;
    private JLabel l;
    private JLabel l1;
    public  static   JFrame frame;
    public login() {
        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
        l1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logInButton.addActionListener(new ActionListener() {
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
                    String s=tu.getText();
                    st=conn.prepareStatement("select password from login where user=?");
                    st.setString(1,s);
                    rs=st.executeQuery();
                    rs.next();

                    char pass[]=tp.getPassword();
                    String s1=new String(pass);
                    if(s1.equals(rs.getString("password")))
                    {     frame.setVisible(false);
                          home h=   new home();
                          h.main(new String[]{"1","2"});

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Wrong Password");
                    }

                }catch(Exception e)
                {
                    System.out.print(e.getMessage());
                }
            }
        });
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(false);
                forgotpassword q=new forgotpassword();
                q.main(new String[]{"1", "2"});
            }
        });
        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(false);
                signup ob=new signup();
                ob.main(new String[]{"1", "2"});
            }
        });
        tu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    tp.requestFocus();

                }
            }
        });
        tp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_ENTER) {
                    Connection conn=null;
                    PreparedStatement st=null;
                    ResultSet rs=null;
                    try{
                        String username = "root";
                        String password = "bhargav$987";
                        String url = "jdbc:mysql://localhost:3306/library";
                        conn = DriverManager.getConnection(url, username, password);
                        String s=tu.getText();
                        st=conn.prepareStatement("select password from login where user=?");
                        st.setString(1,s);
                        rs=st.executeQuery();
                        rs.next();

                        char pass[]=tp.getPassword();
                        String s1=new String(pass);
                        if(s1.equals(rs.getString("password")))
                        {     frame.setVisible(false);


                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Wrong Password");
                        }

                    }catch(Exception ex)
                    {
                        System.out.print(ex.getMessage());
                    }

                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("login1");
        frame.setContentPane(new login().p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,100);
        frame.pack();
        frame.setVisible(true);

    }
}
