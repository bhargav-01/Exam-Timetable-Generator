package sc1;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.*;


public class timetable {


    public JPanel panel1;
    public JDateChooser date;
    public JTextArea ta;
    public JPanel p3;
    public JPanel p4;
    public JTextField ts;
    public JButton ba;
    public JButton ba1;
    public  JTable table1;
    public JButton tc;
    public JScrollPane s1;
    private JButton bp;
    private JButton bh;
    public   int c=0;
    public static  JFrame frame;
    public  String t="     --------------Time Slot------------------\n";
    public String file="";
    public static  String d3;
    Graph g = new Graph();

    public String[] time = new String[10];

    void table_update()
    {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {

            String username = "root";
            String password = "bhargav$987";
            String url = "jdbc:mysql://localhost:3306/project";
            conn = DriverManager.getConnection(url, username, password);

            st=conn.prepareStatement("select * from timetable");
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
                    v2.add(rs.getString("dat"));
                    v2.add(rs.getString("ts1"));
                    v2.add(rs.getString("ts2"));

                }
                d.addRow(v2);
            }
        } catch (Exception x) {
            System.out.print(x);
        }
    }

    public timetable() {


        ba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                d3 = ((JTextField) date.getDateEditor().getUiComponent()).getText();
                String B = "\n     Date of Exam : " + d3 + "\n";
                ta.setText(B);
                t = B + t;
            }
        });
        ba1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                time[c] = ts.getText();
                t = t  + "     " + ts.getText()+"\n";
                ta.setText(t);
                c++;
            }
        });
        tc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Connection conn = null;
                PreparedStatement st = null;
                ResultSet rs = null;
                String []a={"Date",time[0],time[1]};
                table1.setModel(new DefaultTableModel(null,a));
                table1.getTableHeader().setFont(new Font("Calibri",Font.BOLD,20));
                table1.getTableHeader().setOpaque(false);
                table1.getTableHeader().setForeground(Color.white);
                table1.getTableHeader().setBackground(new Color(10, 33, 131));
                try {


                    readAndcreateGraph();
                    printAdjList();
                    graphColoringAlgo();
                    System.out.println( table1.getValueAt(3,2).toString()+"--------------------");
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
        bp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String path="";
                JFileChooser j=new JFileChooser();
                j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int x=j.showSaveDialog(bp);

                if(x==JFileChooser.APPROVE_OPTION)
                {
                    path=j.getSelectedFile().getPath();
                }
                System.out.println(path);
                Document doc=new Document();

                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(path+".pdf"));
                    doc.open();

                    PdfPTable tbl=new PdfPTable(3);


                    tbl.addCell("Date");
                    tbl.addCell(time[0]);
                    tbl.addCell(time[1]);



                    for(int i=0;i<=3;i++)
                    {
                        String date1=table1.getValueAt(i,0).toString();
                        String ts4=table1.getValueAt(i,1).toString();
                        String ts5=table1.getValueAt(i,2).toString();
                        if(table1.getValueAt(i,2).toString().equals(""))
                            ts5=" ";

                        tbl.addCell(date1);
                        tbl.addCell(ts4);
                        tbl.addCell(ts5);
                    }
                    doc.add(tbl);

                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                doc.close();

            }
        });
    }



    private void readAndcreateGraph() throws IOException
    {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String username = "root";
            String password = "bhargav$987";
            String url = "jdbc:mysql://localhost:3306/project";
            conn = DriverManager.getConnection(url, username, password);

            st = conn.prepareStatement("select * from courses");
            rs = st.executeQuery();
            ResultSetMetaData rsm = rs.getMetaData();
            int c = rsm.getColumnCount();
            String file = "";
            while (rs.next()) {

                file = "a" + ":" + rs.getString("course1");
                System.out.println(file);
                String[] tmp = file.split("\\t|,|\\s|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
                for (int y = 0; y < tmp.length; y++)
                    System.out.println(tmp[y]);

                g.addVertex(tmp[1]);
                System.out.println("--------" + tmp[1]);
                for (int y = 1; y < tmp.length; y++) {

                    if (g.hasVertex(tmp[y])) {
                        for (int z = 1; z < tmp.length; z++) {
                            if (y != z)
                                g.addEdge(tmp[y], tmp[z]);
                            else
                                continue;
                        }
                    }
                }
                System.out.println("-------------------------------");

            }

        } catch (Exception x) {
            System.out.print(x);
        }

    }

    void printAdjList() {
        System.out.println("**************** ADJACENCY LiST *****************");
        System.out.println(g.toString());
        System.out.println("*************************************************");
    }

    void graphColoringAlgo() {
        g.DFS();
        System.out.println("hello");
        g.print1();
        table_update();
    }

    private void graphColoringAlgo1() {
        g.DFS();
        g.print1();
        table_update();
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        Calendar c = Calendar.getInstance();
        date = new JDateChooser(c.getTime());
        date.setDateFormatString("dd/MM/yyyy");
        Font f = new Font("Calibri", 0, 18);
        date.setFont(f);
    }
    public static void main(String[] args) {
        frame = new JFrame("timetable");
        frame.setContentPane(new timetable().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,50);
        frame.pack();
        frame.setVisible(true);
    }


}





class Graph  {


    private TreeMap<Vertex, TreeSet<Vertex>> myAdjList;
    private TreeMap<String, Vertex> myVertices;
    private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
    private int myNumVertices;
    private int myNumEdges;
    private ArrayList<Vertex> stack = new ArrayList<Vertex>();
    private ArrayList<Vertex> res = new ArrayList<Vertex>();
    private ArrayList<Vertex> res1 = new ArrayList<Vertex>();
    private ArrayList<Vertex> res2 = new ArrayList<Vertex>();
    private ArrayList<Vertex> res3 = new ArrayList<Vertex>();
    private ArrayList<Vertex> res4 = new ArrayList<Vertex>();
    private ArrayList<Vertex> res5 = new ArrayList<Vertex>();
    private ArrayList<Vertex> res6 = new ArrayList<Vertex>();

    private boolean visited[];
    int counter;
    int c;
    /**
     * Construct empty Graph
     */
    public Graph() {
        myAdjList = new TreeMap<Vertex, TreeSet<Vertex>>();
        myVertices = new TreeMap<String, Vertex>();
        myNumVertices = myNumEdges = 0;

    }



    /**
     * Add a new vertex name with no neighbors (if vertex does not yet exist)
     *            vertex to be added
     */
    public Vertex addVertex(String name) {
        Vertex v = null;
        v = myVertices.get(name);
        if (v == null) {
            v = new Vertex(name);
            myVertices.put(name, v);
            myAdjList.put(v, new TreeSet<Vertex>());
            myNumVertices += 1;
        }
        return v;
    }


    public Vertex getVertex(String name) {
        return myVertices.get(name);
    }

    /**
     * Returns true iff v is in this Graph, false otherwise
     *  name a String name of a Vertex that may be in
     * this Graph
     * @return true iff v is in this Graph
     */
    public boolean hasVertex(String name) {
        return myVertices.containsKey(name);
    }

    /**
     * Is from-to, an edge in this Graph. The graph is
     * undirected so the order of from and to does not
     * matter.
     *  from the name of the first Vertex
     * to the name of the second Vertex
     *  true iff from-to exists in this Graph
     */
    public boolean hasEdge(String from, String to) {

        if (!hasVertex(from) || !hasVertex(to))
            return false;
        return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
    }

    /**
     * Add to to from's set of neighbors, and add from to to's
     * set of neighbors. Does not add an edge if another edge
     * already exists
     *  from the name of the first Vertex
     *  to the name of the second Vertex
     */
    public void addEdge(String from, String to) {
        Vertex v, w;
        if (hasEdge(from, to))
            return;
        myNumEdges += 1;
        if ((v = getVertex(from)) == null)
            v = addVertex(from);
        if ((w = getVertex(to)) == null)
            w = addVertex(to);
        myAdjList.get(v).add(w);
        myAdjList.get(w).add(v);
    }

    /**
     * Return an iterator over the neighbors of Vertex named v
     * @return an Iterator over Vertices that are adjacent
     * to the Vertex named v, empty set if v is not in graph
     */
    public Iterable<Vertex> adjacentTo(String name) {
        if (!hasVertex(name))
            return EMPTY_SET;
        return myAdjList.get(getVertex(name));
    }

    /**
     * Return an iterator over the neighbors of Vertex v

     * @return an Iterator over Vertices that are adjacent
     * to the Vertex v, empty set if v is not in graph
     */
    public Iterable<Vertex> adjacentTo(Vertex v) {
        if (!myAdjList.containsKey(v))
            return EMPTY_SET;
        return myAdjList.get(v);
    }

    /**
     * Returns an Iterator over all Vertices in this Graph
     * @return an Iterator over all Vertices in this Graph
     */
    public Iterable<Vertex> getVertices() {
        return myVertices.values();
    }

    public int numVertices()
    {
        return myNumVertices;
    }

    public int numEdges()
    {
        return myNumEdges;
    }
    // A function used by DFS
    public void DFS() {

        visited = new boolean[numVertices()];
        System.out.println(visited);
        counter = 0;
        c=0;
        System.out.println(myVertices.values());
        for(Vertex u: myVertices.values()) {
            if(u.color == Vertex.Color.WHITE) {
                visitDFSIterative1(u);
            }
        }
    }

    /**
     * DFS with iterative method
     * Make use of stack
     */
    public void print1()
    {
        Connection conn=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            String username = "root";
            String password = "bhargav$987";
            String url = "jdbc:mysql://localhost:3306/project";
            conn = DriverManager.getConnection(url, username, password);

            int count = 0;
            st=conn.prepareStatement("delete  from timetable ");
            st.execute();

            String d=timetable.d3;
            if (res.size() != 0) {
                System.out.print("Final Exam Period 1 => ");
                for (int a = 0; a < res.size(); a++) {
                    System.out.print(res.get(a));
                }

                String s1="";
                for (int a = 0; a < res.size(); a++) {
                    s1=s1+"  "+res.get(a);
                }
                st = conn.prepareStatement("insert into timetable(dat,ts1) values(?,?) ");
                st.setString(1,d);
                st.setString(2,s1);
                st.execute();


            }
            if (res1.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 2 => ");

                for (int a = 0; a < res1.size(); a++) {
                    System.out.print(res1.get(a));
                }

                String s1="";
                for (int a = 0; a < res1.size(); a++) {
                    s1=s1+"  "+res1.get(a);
                }

                st = conn.prepareStatement("update  timetable set ts2=? where dat=? ");
                st.setString(1, s1);
                st.setString(2, d);
                st.execute();

            }

            if (res2.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 3 => ");
                for (int a = 0; a < res2.size(); a++) {
                    System.out.print(res2.get(a));
                }

                String s1="";
                int x=(Integer.parseInt(d.substring(0,2))+1)%30;
                System.out.println("------------------------------------"+d.substring(0,1));
                String s2=x+d.substring(2,d.length());

                for (int a = 0; a < res2.size(); a++) {
                    s1=s1+"  "+res2.get(a);
                }

                st = conn.prepareStatement("insert into timetable(dat,ts1) values(?,?) ");
                st.setString(1,s2);
                st.setString(2,s1);
                st.execute();
            }

            if (res3.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 4 => ");
                for (int a = 0; a < res3.size(); a++) {
                    System.out.print(res3.get(a));
                }

                String s1="";
                int x=(Integer.parseInt(d.substring(0,2))+1)%30;
                String s2=x+d.substring(2,d.length());
                for (int a = 0; a < res3.size(); a++) {
                    s1=s1+"  "+res3.get(a);
                }

                st = conn.prepareStatement("update  timetable set ts2=? where dat=? ");
                st.setString(1, s1);
                st.setString(2, s2);
                st.execute();
            }
            if (res4.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 5 => ");
                for (int a = 0; a < res4.size(); a++) {
                    System.out.print(res4.get(a));
                }

                String s1="";
                int x=(Integer.parseInt(d.substring(0,2))+2)%30;
                String s2=x+d.substring(2,d.length());

                for (int a = 0; a < res4.size(); a++) {
                    s1=s1+"  "+res4.get(a);
                }

                st = conn.prepareStatement("insert into timetable(dat,ts1) values(?,?) ");
                st.setString(1,s2);
                st.setString(2,s1);
                st.execute();
            }

            if (res5.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 6 => ");
                System.out.println();
                System.out.print("Final Exam Period 4 => ");
                for (int a = 0; a < res5.size(); a++) {
                    System.out.print(res5.get(a));
                }

                String s1="";
                int x=(Integer.parseInt(d.substring(0,2))+2)%30;
                String s2=x+d.substring(2,d.length());

                for (int a = 0; a < res5.size(); a++) {
                    s1=s1+"  "+res5.get(a);
                }

                st = conn.prepareStatement("update  timetable set ts2=? where dat=? ");
                st.setString(1, s1);
                st.setString(2, s2);
                st.execute();
            }

            if (res6.size() != 0) {
                System.out.println();
                System.out.print("Final Exam Period 5 => ");
                for (int a = 0; a < res6.size(); a++) {
                    System.out.print(res6.get(a));
                }

                String s1="";
                int x=(Integer.parseInt(d.substring(0,2))+3)%30;
                String s2=x+d.substring(2,d.length());

                for (int a = 0; a < res6.size(); a++) {
                    s1=s1+"  "+res6.get(a);
                }

                st = conn.prepareStatement("insert into timetable(dat,ts1) values(?,?) ");
                st.setString(1,s2);
                st.setString(2,s1);
                st.execute();
            }

        }
        catch (Exception e)
        {
            System.out.println("hello");
        }

    }

    private void visitDFSIterative1(Vertex u) {
        if(u.color==Vertex.Color.WHITE) {
            System.out.println(u+" --=>"+c);
            if (c == 0) {
                u.color = Vertex.Color.GREY;
                res.add(u);
            }
            if (c == 1) {
                u.color = Vertex.Color.RED;
                res1.add(u);
            }
            if (c == 2) {
                u.color = Vertex.Color.BLACK;
                res2.add(u);
            }
            if (c == 3) {
                u.color = Vertex.Color.GREEN;
                res3.add(u);
            }
            if (c == 4) {
                u.color = Vertex.Color.PINK;
                res4.add(u);
            }
            if (c == 5) {
                u.color = Vertex.Color.BROWN;
                res5.add(u);
            }
            if (c == 6) {
                u.color = Vertex.Color.YELLOW;
                res6.add(u);
            }

            System.out.println(u.color+" =>"+c);

            Iterator<Vertex> i = myAdjList.get(u).iterator();
            for (Vertex k : myVertices.values()) {
                int flag1=1;
                int flag = 1;
                System.out.println(u+">>>"+myAdjList.get(u));
                while (i.hasNext())
                {   Vertex t=i.next();
                    System.out.println(u+">>>"+t);
                    if (t== k){
                        System.out.print(t+" ");
                        System.out.println();
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    System.out.println(k+"-----------"+k.color);
                    Iterator<Vertex> j = myAdjList.get(k).iterator();

                    while (j.hasNext()) {
                        Vertex v = j.next();

                        if (c == 0) {
                            if (v.color == Vertex.Color.GREY) {
                                flag1=0;
                                break;
                            }
                        }
                        if (c == 1) {
                            if (v.color == Vertex.Color.RED ){
                                flag1=0;
                                break;
                            }

                        }
                        if (c == 2) {
                            if (v.color == Vertex.Color.BLACK){

                                flag1=0;
                                break;
                            }

                        }
                        if (c == 3) {
                            if (v.color == Vertex.Color.GREEN){

                                flag1=0;
                                break;
                            }

                        }
                        if (c == 4) {
                            if (v.color == Vertex.Color.PINK){

                                flag1=0;
                                break;
                            }

                        }
                        if (c == 5) {
                            if (v.color == Vertex.Color.BROWN){

                                flag1=0;
                                break;
                            }

                        }
                        if (c == 6) {
                            if (v.color == Vertex.Color.YELLOW){

                                flag1=0;
                                break;
                            }

                        }
                    }
                    if(flag1==1)
                    {

                        System.out.println(k+">>"+k.color+" >"+k);

                        if(c==0 && k.color==Vertex.Color.WHITE) {
                            k.color = Vertex.Color.GREY;
                            res.add(k);
                        }
                        if(c==1 && k.color==Vertex.Color.WHITE) {
                            k.color = Vertex.Color.RED;
                            res1.add(k);
                        }
                        if(c==2 && k.color==Vertex.Color.WHITE) {
                            k.color = Vertex.Color.BLACK;
                            res2.add(k);
                        }
                        //k.color!=Vertex.Color.RED && k.color!=Vertex.Color.GREY && k.color != Vertex.Color.BLACK
                        if(c==3 && k.color==Vertex.Color.WHITE ) {
                            k.color = Vertex.Color.GREEN;
                            res3.add(k);

                        }
                        // k.color!=Vertex.Color.RED && k.color!=Vertex.Color.GREY && k.color != Vertex.Color.BLACK && k.color != Vertex.Color.GREEN
                        if(c==4 &&  k.color==Vertex.Color.WHITE) {
                            k.color = Vertex.Color.PINK;
                            res4.add(k);
                        }
                        // k.color!=Vertex.Color.RED && k.color!=Vertex.Color.GREY && k.color != Vertex.Color.BLACK && k.color != Vertex.Color.GREEN && k.color != Vertex.Color.PINK
                        if(c==5 &&  k.color==Vertex.Color.WHITE ) {
                            k.color = Vertex.Color.BROWN;
                            res5.add(k);
                        }
                        if(c==6 &&  k.color==Vertex.Color.WHITE ) {
                            k.color = Vertex.Color.YELLOW;
                            res6.add(k);
                        }
                    }
                }
            }
            for(Vertex l: myVertices.values()) {
                System.out.println(l+" =>"+l.color+" for "+c);
            }
            c++;
        }
    }

    private void visitDFSIterative(Vertex u) {
        res.add(u);
        u.color = Vertex.Color.GREY;
        stack.add(u);
        visited[counter]=true;
        counter++;

        while(!stack.isEmpty()) {
            Vertex v = stack.remove(stack.size()-1);

            Iterator<Vertex> i = myAdjList.get(v).iterator();
            System.out.println(v+"---------"+i.next());
            while(i.hasNext()){
                Vertex tmp = i.next();
                if(!visited[counter]) {
                    if(tmp.color == Vertex.Color.WHITE) {
                        tmp.predecessor = v;
                        tmp.color =Vertex.Color.GREY;
                        res.add(tmp);
                        stack.add(tmp);
                        visited[counter]=true;

                    }

                }
            }

            v.color = Vertex.Color.BLACK;

        }
        u.color =Vertex.Color.BLACK;
    }

    public void printRes() {
        System.out.println(res);
        System.out.println(res.get(1));

        System.out.print("Final Exam Period 1 => ");
        for(int a=0; a<res.size(); a++) {
            System.out.print(res.get(a)+" ");
            res.remove(a);
        }

        System.out.println("\n");
        System.out.print("Final Exam Period 2 => ");
        for(int a=0; a<res.size(); a++) {
            System.out.print(res.get(a)+" ");
            res.remove(a);
        }

        System.out.println("\n");
        System.out.print("Final Exam Period 3 => ");
        for(int a=0; a<res.size(); a++) {
            System.out.print(res.get(a)+" ");
            res.remove(a);
        }
        System.out.println("\n");
        System.out.print("Final Exam Period 4 => ");
        for(int a=0; a<res.size(); a++) {
            System.out.print(res.get(a)+" ");
            res.remove(a);
        }
        System.out.println("\n");
        System.out.println("**********************************************");
    }

    /*
     * Returns adjacency-list representation of graph
     */
    public String toString() {
        String s = "";
        for (Vertex v : myVertices.values()) {
            s += v + "-> ";
            for (Vertex w : myAdjList.get(v)) {
                s += w + "-";
            }
            s += "\n";
        }
        return s;
    }
}

class Vertex implements Comparable<Vertex> {

    public enum Color {
        WHITE, GREY, BLACK,RED,GREEN,PINK,BROWN,YELLOW
    }

    /**
     * label for Vertex
     */
    public String name;
    /**
     * length of shortest path from source
     */

    public Vertex predecessor; // previous vertex

    public static final int INFINITY = Integer.MAX_VALUE;

    public Color color;

    public Vertex(String v)
    {
        name = v;
        predecessor = null;
        color = Color.WHITE;
    }

    /**
     * The name of the Vertex is assumed to be unique, so it
     * is used as a HashCode
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return name.hashCode();
    }

    public String toString()
    {
        return name;
    }
    /**
     * Compare on the basis of lexicographically
     */
    public int compareTo(Vertex other)
    {
        return name.compareTo(other.name);
    }
}

