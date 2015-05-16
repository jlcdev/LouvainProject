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
    public abstract ArrayList<ArrayList<Integer>> obtain();

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
