package sc1;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class forgotpassword {
    private JComboBox tq;
    private JTextField ta;
    private JTextField tu;
    private JTextField tp;
    private JButton br;
    private JButton bd;
    private JPanel p;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JPanel p5;
    private JPanel p6;
    private JPanel p7;
    private JLabel user;
    public  static  JFrame frame;
    public forgotpassword() {
        br.addActionListener(new ActionListener() {
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




                    String s2=(String)tq.getSelectedItem();
                    String s3=ta.getText();
                    st=conn.prepareStatement("select * from login where user=?");

                    st.setString(1,s);

                    rs=st.executeQuery();
                    rs.next();
                    String s1=rs.getString("question");

                    if(s1.equals(s2) && rs.getString("anwser").equals(s3)  )
                    {
                        tp.setText(rs.getString("password"));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Your Question or answer is wrong");
                    }





                }catch(Exception e)
                {
                    System.out.print(e.getMessage());
                }
            }
        });
        bd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                login l=new login();
                l.main(new String[]{"1", "2"});
                frame.setVisible(false);
            }
        });

        tu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    tq.requestFocus();

                }

            }
        });
        tq.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_DOWN ||k==KeyEvent.VK_ENTER) {
                    ta.requestFocus();

                }
                if(k==KeyEvent.VK_UP) {
                    tu.requestFocus();

                }


            }
        });
        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int k=e.getKeyCode();
                if(k==KeyEvent.VK_UP) {
                    tq.requestFocus();

                }

            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("forgot password");
        frame.setContentPane(new forgotpassword().p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,100);
        frame.pack();
        frame.setVisible(true);
    }
}
