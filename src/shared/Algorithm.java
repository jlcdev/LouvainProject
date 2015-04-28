package shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * It is responsible for providing a common interface to implement 
 * any of the algorithms.
 * 
 * @author Cluster 7 sub 3
 * @version 1.0
 */
public abstract class Algorithm
{
    protected int p;
    protected HashMap<Integer, Graph<Integer,Double>> graphs;

    /**
     * Generate the solution 
     * @param g Graph
     */
    public abstract void calc(Graph<Integer,Double> g);

    /**
     * Obtain Community list
     * @return List of communities
     */
    public ArrayList<ArrayList<Integer>> obtain()
    {
        if(graphs.isEmpty() || !graphs.containsKey(p)) return null;
        Graph<Integer, Double> g = graphs.get(p);
        ArrayList<ArrayList<Integer>> response = new ArrayList<ArrayList<Integer>>();
        for(Integer v : g.getVertexs())
        {
            ArrayList<Integer> neighbors = g.getNeighbors(v);
            response.add(neighbors);
        }
        return response;
    }

    /**
     * Getter for p param
     * @return p value
     */
    public int getP()
    {
        return this.p;
    }
    /**
     * Setter for p param
     * @param p int
     */
    public void setP(int p)
    {
        this.p = p;
    }
}
