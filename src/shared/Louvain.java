package shared;

/**
 *
 * @author albert campano
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class for applying the Louvain algorithm to a graph
 *
 * @author Cluster 7 sub 3
 * @version 1.0
 */
public class Louvain extends Algorithm {

    private Graph<Integer, Double> graph;
    private HashMap<Integer, ArrayList<ArrayList<Integer>>> communityList;
    private ArrayList<ArrayList<Integer>> community;
    private int steps;

    public Louvain() {
    }

    /**
     * Apply the Louvain algorithm
     *
     * @param g Graph<Integer, Double>
     */

    @Override
    public void calc(Graph<Integer, Double> g)
    {
        this.steps = 0;
        this.graph = g;
        this.community = new ArrayList<>();
        this.communityList = new HashMap<>();

        for (int vertex : this.graph.getVertexs()) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(vertex);
            this.community.add(temp);
        }
        boolean control = true;
        while(control)
        {
            ArrayList<ArrayList<Integer>> aux = new ArrayList<>();
            for(ArrayList<Integer> i : this.community)
            {
                aux.add( (ArrayList<Integer>) i.clone());
            }
            this.communityList.put(this.steps, aux);
            steps++;
            control = this.metode();
        }
    }

    private boolean metode()
    {
        double qmax = -1.0;
        int node = 0, comunidad = 0;
        ArrayList<Integer> lvertexs = this.graph.getVertexs();
        double sumaC, sumIncidentsCom, sumNC, sumIncidentsNode;
        
        //m
        double m = pesTotal();

        for (int i : lvertexs)
        {
            sumaC = pesCom(i);
            sumIncidentsCom = pesIncident(i);
            ArrayList<Integer> lvecinos = this.graph.getNeighbors(i);
            
            for (int j : lvecinos)
            {
                if(i != j)
                {
                    sumNC = pesNodeCom(j, i);
                    sumIncidentsNode = pesIncident(j);

                    double q = deltaQ(sumaC, sumIncidentsCom, sumNC, sumIncidentsNode, m);

                    if (q > qmax && q > 0.0) {
                        qmax = q;
                        node = j;
                        comunidad = i;

                    }
                }

            }
        }
        if (qmax > 0.0) {
            incluirNode(node, comunidad);
            return true;
        } 
        else return false;

    }
    
    @Override
    public ArrayList<ArrayList<Integer>> obtain()
    {
        if(this.p > 100 || this.p < 0) return null;
        else
        {
            return this.communityList.get( (this.p * this.steps) / 100);  
        }
    }

    /**
     *
     * @param g
     * @param node
     * @param comunidad
     */
    private void incluirNode(int node, int comunidad) 
    {
        //En el arrayList el primer elemento es el nodo representativo de ese conjunto de comunidades
        ArrayList<Integer> nodeList = null;
        int posCom = 0, posNode = 0;
        for(int i = 0; i < this.community.size(); i++)
        {
            if(this.community.get(i).get(0) == node)
            {
                nodeList = this.community.get(i);
                posNode = i;
            }
            else if (this.community.get(i).get(0) == comunidad) posCom = i;
        }
        this.community.get(posCom).addAll(nodeList);
        this.community.remove(posNode);
        
        
        ArrayList<Integer> neighbors = this.graph.getNeighbors(node);
        for (int i = 0; i < neighbors.size(); ++i) {
            int vecino = neighbors.get(i);
            Edge<Integer, Double> aVecinoNode = this.graph.getEdge(node, vecino);
            double peso1 = aVecinoNode.getValue();
            double peso2 = 0;
            this.graph.removeEdge(node, vecino);
            this.graph.removeEdge(vecino, node);
            Edge<Integer, Double> aVecinoCom = this.graph.getEdge(vecino, comunidad);
            if (aVecinoCom != null) {
                if (vecino == comunidad) {
                    Edge<Integer, Double> enode = this.graph.getEdge(node, node);
                    if (enode != null) {
                        peso1 = peso1 + enode.getValue();
                    }
                    this.graph.removeEdge(node, node);
                }
                peso2 = aVecinoCom.getValue();
                this.graph.removeEdge(vecino, comunidad);
                this.graph.removeEdge(comunidad, vecino);
            }
            this.graph.addEdge(vecino, comunidad, peso1 + peso2);
        }
        this.graph.removeVertex(node);
    }

    /**
     *
     * @param a sumaC (Ein)
     * @param b sumIncidentsCom (Etot)
     * @param c sumNC (ki,in)
     * @param d sumIncidentsNode (ki)
     * @param m m
     * @return
     */
    private double deltaQ(double a, double b, double c, double d, double m) {
        double m2 = 2 * m;
        double uno = (a + c) / m2;
        double dos = Math.pow((b + d) / m2, 2.0);
        double tres = a / m2;
        double cuatro = Math.pow((b / m2), 2.0);
        double cinco = Math.pow((d / m2), 2.0);

        return ((uno - dos) - (tres - cuatro - cinco));

    }


    //Sirve para m
    private double pesTotal()
    {
        ArrayList<Integer> lvertexs = this.graph.getVertexs();
        double suma = 0;
        for (int i : lvertexs) {
            ArrayList<Edge<Integer, Double>> aux = this.graph.getEdges(i);
            for (Edge<Integer, Double> j : aux) {
                suma += j.getValue();
            }
        }

        return suma / 2;
    }

    //Sirve para ki i para Etot
    private double pesIncident(int node)
    {
        ArrayList<Edge<Integer, Double>> aux = this.graph.getEdges(node);
        double suma = 0;
        for (Edge<Integer, Double> i : aux) {
            if (node != i.getDesti()) {
                suma += i.getValue();
            }
        }
        return suma;
    }

    //ki, in
    private double pesNodeCom(int node, int com)
    {
        Edge<Integer, Double> aux = this.graph.getEdge(node, com);
        if (aux != null) return aux.getValue();
        else return 0;
    }

    //Ein
    private double pesCom(int com)
    {
        Edge<Integer, Double> aux = this.graph.getEdge(com, com);
        if (aux != null) return aux.getValue();
        else return 0;
    }
    
}