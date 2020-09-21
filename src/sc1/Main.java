package sc1;

import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;
import java.util.*;

public class Main {
    Graph g = new Graph();

    private void readAndcreateGraph(String fileName) throws IOException
    {
        File file = new File("C:\\untitled3\\src\\sc1\\new.txt");

        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;


            while( (line = br.readLine()) != null ) {
                String[] tmp = line.split("\\t|,|\\s|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
                for(int y=0;y<tmp.length;y++)
                System.out.println(tmp[y]);

                g.addVertex(tmp[1]);
                System.out.println("--------"+tmp[1]);
                for(int y=1;y<tmp.length;y++) {

                    if(g.hasVertex(tmp[y])) {
                        for(int z=1; z<tmp.length; z++) {
                            if(y != z)
                                g.addEdge(tmp[y],tmp[z]);
                            else
                                continue;
                        }
                    }
                }
            System.out.println("-------------------------------");
            }
        }catch(Exception e) {
            e.printStackTrace();
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
    }

    private void graphColoringAlgo1() {
        g.DFS();
        g.print1();
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Main mn = new Main();
        mn.readAndcreateGraph("C:\\untitled3\\src\\sc1\\new.txt");
        mn.printAdjList();
        mn.graphColoringAlgo();


    }

}
