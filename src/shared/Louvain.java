package shared;

import java.util.HashMap;

/**
 * Class for applying the Louvain algorithm to a graph
 * @author Cluster 7 sub 3
 * @version 1.0
 */
public class Louvain extends Algorithm
{
    public Louvain(){}
    /**
     * Apply the Louvain algorithm
     * @param g Graph<Integer, Double>
     */
    @Override
    public void calc(Graph<Integer, Double> g)
    {
        this.graphs = new HashMap<Integer, Graph<Integer,Double>>();
        for(int i=0;i<10;++i)
            this.graphs.put(i, g);
    }
    
}