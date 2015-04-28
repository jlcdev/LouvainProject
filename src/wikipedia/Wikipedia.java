package wikipedia;

import Optional.LoadProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import shared.Graph;
import shared.Louvain;

public class Wikipedia
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {/*
        Graph<Integer,Double> g = new Graph();
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(5);
        g.addVertex(4);
        g.addEdge(2,3,6.3);
        g.addEdge(1,5,2.0);
        g.addEdge(1,2,2.0);
        g.addEdge(1,3,2.0);
        g.addEdge(1,4,2.0);
        Louvain l = new Louvain();
        l.calc(g);
        l.setP(4);
        ArrayList<ArrayList<Integer>> list = l.obtain();
        for(int i=0; i < list.size();++i)
        {
            System.out.println(list.get(i).toString());
        }*/
        //Read Properties file
        LoadProperties lp = new LoadProperties();
        Properties p = lp.loadJsonProperties("C:\\Users\\Javier\\Desktop\\test.json");
        //p.getProperty("version");
        System.out.println(p.getProperty("version"));
    }
    
}
