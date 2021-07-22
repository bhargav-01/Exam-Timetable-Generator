package sc1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class student {
    private JPanel p2;
    private JPanel p3;
    private JTextField ts;
    private JPanel p4;
    private JPanel p7;
    private JButton ba;
    private JButton bd;
    private JTable table1;
    private JTextField tc;
    private JButton be;
    private JPanel p;
    private JPanel p5;
    private JScrollPane s1;
    private JButton bh;
    public  static JFrame frame;

    private  void table_update()
    {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {

            String username = "root";
            String password = "bhargav$987";
            String url = "jdbc:mysql://localhost:3306/project";
            conn = DriverManager.getConnection(url, username, password);

            st=conn.prepareStatement("select * from courses");
            rs=st.executeQuery();
            ResultSetMetaData rsm=rs.getMetaData();
            int c=rsm.getColumnCount();
            DefaultTableModel d=(DefaultTableModel)table1.getModel();

            d.setRowCount(0);
            while (rs.next())
            {
                Vector v2=new Vector();
                for(int i=1;i<=c;i++)
                {
                    v2.add(rs.getString("SG"));
                    v2.add(rs.getString("course1"));

                }
                d.addRow(v2);
            }
        } catch (Exception x) {
            System.out.print(x);
        }
    }

    public student() {
        String []a={"StudentGroup","Courses"};
        table1.setModel(new DefaultTableModel(null,a));
        table1.getTableHeader().setFont(new Font("Calibri",Font.BOLD,20));
        table1.getTableHeader().setOpaque(false);
        table1.getTableHeader().setForeground(Color.white);
        table1.getTableHeader().setBackground(new Color(10, 33, 131));
        int i=0;
        table_update();

        ba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Connection conn = null;
                PreparedStatement st = null;
                PreparedStatement st1 = null;
                ResultSet rs = null;
                String a =ts.getText();
                String b = tc.getText();




                try {
                     table_update();
                    String username = "root";
                    String password = "bhargav$987";
                    String url = "jdbc:mysql://localhost:3306/project";
                    conn = DriverManager.getConnection(url, username, password);
                    System.out.println(a);
                    st=conn.prepareStatement("select * from courses where SG=? ");
                    st.setString(1,a);
                    rs=st.executeQuery();
                    String s;
                    String s1;
                    if(rs.next()) {
                        s = rs.getString("course1");
                        System.out.println(s);
                        s1=s+","+b;
                        s = s + " " + b;
                        st1 = conn.prepareStatement("update  courses set course1=? where SG=? ");
                        st1.setString(1, s);
                        st1.setString(2, a);
                        st1.execute();



                    }
                    else
                    {
                        st1 = conn.prepareStatement("insert into courses(SG,course1) values(?,?) ");
                        st1.setString(1, a);
                        st1.setString(2,b);
                        st1.execute();
                    }
                    table_update();
                    JOptionPane.showMessageDialog(null,"record added");
                    tc.setText("");


                    table_update();
                } catch (Exception x) {
                    System.out.print(x);
                }

            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel d=(DefaultTableModel)table1.getModel();
                int c=table1.getSelectedRow();
                ts.setText(d.getValueAt(c,0).toString());
                tc.setText(d.getValueAt(c,1).toString());


            }
        });
        be.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DefaultTableModel d=(DefaultTableModel)table1.getModel();
                int k=table1.getSelectedRow();
                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;
                try {
                    String a = ts.getText();
                    String b = tc.getText();

                    String username = "root";
                    String password = "bhargav$987";
                    String url = "jdbc:mysql://localhost:3306/project";
                    conn = DriverManager.getConnection(url, username, password);

                    st=conn.prepareStatement("update courses set course1 = ? where SG=?");
                    st.setString(1,b);
                    st.setString(2,a);



                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null,"record upadate");

                    tc.setText("");
                    ts.setText("");

                    table_update();
                } catch (Exception x) {
                    System.out.print(x);
                }
            }
        });
        bd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DefaultTableModel d=(DefaultTableModel)table1.getModel();
                int k=table1.getSelectedRow();
                Connection conn = null;
                PreparedStatement st=null;

                ResultSet rs = null;
                try {
                    String r=ts.getText();
                    int x=JOptionPane.showConfirmDialog(null,"Do you want to delete the record?","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(x==JOptionPane.YES_OPTION) {

                        String username = "root";
                        String password = "bhargav$987";
                        String url = "jdbc:mysql://localhost:3306/project";
                        conn = DriverManager.getConnection(url, username, password);


                        st=conn.prepareStatement("delete from courses where SG=?");
                        st.setString(1,r);
                        st.execute();

                        table_update();
                        ts.setText("");
                        tc.setText("");


                    }



                } catch (Exception x) {
                    System.out.print(x);

                }

            }
        });
        bh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                home h=new home();
                h.main(new String[]{"1","2"});
            }
        });
    }

    public static void main(String[] args) {
         frame = new JFrame("Courses Information");
        frame.setContentPane(new student().p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,50);
        frame.pack();
        frame.setVisible(true);
    }
}
