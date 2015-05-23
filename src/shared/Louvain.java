package shared;

/**
 *
 * @author albert campano
 */
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for applying the Louvain algorithm to a graph
 *
 * @author Cluster 7 sub 3
 * @version 1.0
 */
public class Louvain extends Algorithm
{

    private Graph<Integer, Double> graph;
    private Graph<Integer, Double> gIntermedi;
    
    private HashMap<Integer, ArrayList<ArrayList<Integer>>> communityList;
    
    private ArrayList<ArrayList<Integer>> cAnterior;
    private ArrayList<ArrayList<Integer>> cActual;
    
    private int steps;

    public Louvain()
    {
        this.graph = null;
        this.gIntermedi = null;
        this.communityList = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
        this.cAnterior = new ArrayList<ArrayList<Integer>>();
        this.cActual = new ArrayList<ArrayList<Integer>>();
    }
    
    public int getsteps()
    {
        return this.steps;
    }
    
    /**
     * Apply the Louvain algorithm
     *
     * @param g Graph<Integer, Double>
     */
    public void calc(Graph<Integer, Double> g)
    {
        if(g != null)
        {
            this.steps = 0;
            this.graph = g;
            this.gIntermedi = this.graph;
            this.inicialitzarComunitats();
            //Copiar this.cActual a this.cAnterior
            this.cAnterior = new ArrayList<ArrayList<Integer>>();
            for(ArrayList<Integer> i: this.cActual)
            {
                ArrayList<Integer> aux = new ArrayList<Integer>();
                for(int j: i)
                {
                    aux.add(j);
                }
                this.cAnterior.add(aux);
            }
            
            this.guardarComunitat(); //Per guardar l'estat inicial
            this.fase2();
            this.metode();
        }
    }
    
    public void metode()
    {
        while(true)
        {
            if(! this.fase1()) break;
            this.guardarComunitat();
            this.fase2();
        }
    }
    
    private void fase2()
    {
        double x;
        this.gIntermedi = new Graph<Integer, Double>();
        for(int i = 0; i < this.cAnterior.size(); i++) this.gIntermedi.addVertex(i);
        for(int i = 0; i < this.cAnterior.size(); i++)
        {
            for (int j = i; j < this.cAnterior.size(); j++)
            {
                x = this.pesEntreComunitats(this.cAnterior.get(i), this.cAnterior.get(j));
                if(x != 0) this.gIntermedi.addEdge(i, j, x);
            }
        }
    }
    
    private double pesEntreComunitats(ArrayList<Integer> comunitat1, ArrayList<Integer> comunitat2)
    {
        double suma = 0.0;
        for(int i: comunitat1)
        {
            for(int j: comunitat2)
            {
                Edge<Integer, Double> k = this.graph.getEdge(i, j);
                if(k != null) suma += k.getValue();
            }
        }
        if(comunitat1.equals(comunitat2)) return suma / 2.0;
        else return suma;
    }
    
    private void guardarComunitat()
    {
        //Creamos la nueva lista de comunidades
        ArrayList<ArrayList<Integer>> guardar = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> i: this.cActual)
        {
            ArrayList<Integer> nou = new ArrayList<Integer>();
            for(int j: i)
            {
                nou.addAll(new ArrayList<Integer>(this.cAnterior.get(j)) );
            }
            if(nou.size() > 0) guardar.add(nou);
        }
        
        //Guardamos la lista
        this.communityList.put(this.steps, guardar);
        this.steps++;
        
        //Ponemos esta lista en cAnterior
        this.cAnterior = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> i: guardar)
        {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            for(int j: i)
            {
                aux.add(j);
            }
            this.cAnterior.add(aux);
        }
        
    }
    
    private boolean fase1()
    {
        inicialitzarComunitats();
        double qmax, q;
        int comunitat, comunitatDef = 0;
        boolean parada = false, modificat = false;
        while(! parada)
        {
            parada = true;
            for(int i: this.gIntermedi.getVertexs())
            {
                qmax = -1.0;
                comunitat = trobarComunitat(i);
                this.cActual.get(comunitat).remove((Integer) i);
                for (int j = 0; j < this.cActual.size(); j++)
                {
                    if(comunitat != j && estaCon(i, j))
                    {
                        q = this.modularitat(i, this.cActual.get(comunitat), this.cActual.get(j));
                        if(q > qmax)
                        {
                            qmax = q;
                            comunitatDef = j;
                        }
                    }
                }
                if(qmax > 0.0)
                {
                    this.cActual.get(comunitatDef).add(i);
                    parada = false;
                    modificat = true;
                }
                else this.cActual.get(comunitat).add(i);
            }
        }
        return modificat;
    }
    
    private void inicialitzarComunitats()
    {
        this.cActual = new ArrayList<ArrayList<Integer>>();
        if(this.gIntermedi != null)
        {
            for (int i : this.gIntermedi.getVertexs())
            {
                ArrayList<Integer> aux = new ArrayList();
                aux.add(i);
                this.cActual.add(aux);
            }
        }
    }
    
    private int trobarComunitat(int vertex)
    {
        for (int i = 0; i < this.cActual.size(); i++) 
        {
            if(this.cActual.get(i).contains(vertex)) return i;
        }
        return 0;
    }
    
    private boolean estaCon(int vertex, int comunitat)
    {
        for (int i: this.gIntermedi.getNeighbors(vertex))
        {
            if(this.cActual.get(comunitat).contains((Integer) i)) return true;
        }
        return false;
    }
    
    private double modularitat(int vertex, ArrayList<Integer> cInici, ArrayList<Integer> cDesti)
    {
        double total = 0.0;
        double m = this.pesTotal();
        if (m == 0) return -1.0;
        total = (this.pesVertexComunitat(vertex, cDesti) - this.pesVertexComunitat(vertex, cInici)) / (2*m);
        double aux = this.pesIncidentsVertex(vertex)*(this.pesIncidentsComunitat(cDesti) - this.pesIncidentsComunitat(cInici));
        return total - (aux/(2*Math.pow(m, 2)));
    }
    
    private double pesIncidentsVertex(int vertex)
    {
        double suma = 0.0;
        for(int j: this.gIntermedi.getNeighbors(vertex))
        {
            if(vertex == j) suma += this.gIntermedi.getEdge(vertex, j).getValue() /2.0;
            else suma += this.gIntermedi.getEdge(vertex, j).getValue();
        }
        return suma;
    }
    
    private double pesTotal()
    {
        double suma = 0.0;
        for (int i: this.gIntermedi.getVertexs())
        {
            for(int j: this.gIntermedi.getNeighbors(i)) suma += this.gIntermedi.getEdge(i, j).getValue();
        }
        return suma / 2.0;
    }
    
    private double pesVertexComunitat(int vertex, ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        for (int i: this.gIntermedi.getNeighbors(vertex))
        {
            if(comunitat.contains(i)) suma += this.gIntermedi.getEdge(vertex, i).getValue();
        }
        return suma;
    }
    
    private double pesIncidentsComunitat(ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        for(int i: comunitat)
        {
            for(int j: this.gIntermedi.getNeighbors(i))
            {
                if(! comunitat.contains(j)) suma += this.gIntermedi.getEdge(i, j).getValue();
            }
        }
        return suma;
    }
    
    public ArrayList<ArrayList<Integer>> obtain()
    {
        if(this.p > 100 || this.p < 0 || this.steps < 0 || this.graph == null) return null;
        else
        {
            if(p == 100) return this.communityList.get(this.steps - 1);
            return this.communityList.get( (this.p * (this.steps)) / 100);  
        }
    }
}