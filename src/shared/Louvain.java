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
    private HashMap<Integer, Double> pesosVertex;
    private HashMap<Integer, Double> pesosCom;
    private HashMap<Integer, Integer> com;
    
    private ArrayList<ArrayList<Integer>> cAnterior;
    private ArrayList<ArrayList<Integer>> cActual;
    
    private Integer steps;
    public long tiempo;

    public Louvain()
    {
        this.tiempo = 0;
        this.communityList = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
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
        
        this.cAnterior = new ArrayList<ArrayList<Integer>>();
        for(Integer i: this.graph.getVertexs())
        {
            ArrayList<Integer> x = new ArrayList<Integer>();
            x.add(i);
            this.cAnterior.add(x);
        }
        this.fase2();
        this.inicialitzarComunitats();
        this.guardarComunitat(); //Per guardar l'estat inicial
        this.fase2();

        this.metode();
    }
    
    public void metode()
    {
        while(this.fase1())
        {
            this.guardarComunitat();
            this.fase2();
        }
    }
    
    private void fase2()
    {
        double x;
        this.gIntermedi = new Graph<Integer, Double>();
        Integer tam = this.cAnterior.size();
        for(Integer i = 0; i < tam; i++) this.gIntermedi.addVertex(i);
        for(Integer i = 0; i < tam; i++)
        {
            for (Integer j = i; j < tam; j++)
            {
                x = this.pesEntreComunitats(this.cAnterior.get(i), this.cAnterior.get(j));
                if(x != 0) this.gIntermedi.addEdge(i, j, x);

            }
        }

    }
    
    private double pesEntreComunitats(ArrayList<Integer> comunitat1, ArrayList<Integer> comunitat2)
    {

        double suma = 0.0;
        for(Integer i: comunitat1)
        {
            for(Integer j: comunitat2)
            {
                Double k = this.graph.getEdge(i, j);
                if(k != null) suma += k;
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
            for(Integer j: i)
            {
                ArrayList<Integer> x = new ArrayList<Integer>(this.cAnterior.get(j));
                nou.addAll(x);
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
            for(Integer j: i) aux.add(j);
            this.cAnterior.add(aux);
        }
    }
    
    private boolean fase1()
    {

        inicialitzarComunitats();
        double qmax, q;
        Integer comunitat, comunitatDef = 0;
        boolean parada = false, modificat = false;
        
        double m = this.pesTotal();
        double m2 = 2*m;
        double mqua2 = 2*m*m;
        double ki, sumtot2, kin2, kin, sumtot;
        
        while(! parada)
        {

            parada = true;
            for(Integer i: this.gIntermedi.getVertexs())
            {

                qmax = 0.0;
                comunitat = this.treureVertex(i);
                
                
                ki = this.pesosVertex.get(i);
                sumtot2 = this.pesosCom.get(comunitat);
                kin2 = this.pesVertexComunitat(i, this.cActual.get(comunitat));
                
                for (Integer j = 0; j < this.cActual.size(); j++)
                {
                    if(estaCon(i, j) && !comunitat.equals(j))
                    {
                        q = (this.pesVertexComunitat(i, this.cActual.get(j)) - kin2)/ m2;
                        q -= ki*(this.pesosCom.get(j) - sumtot2) / mqua2;
                        if(q > qmax)
                        {
                            qmax = q;
                            comunitatDef = j;
                        }
                    }
                }
                if(qmax > 0.0)
                {
                    this.insertarVertex(i, comunitatDef);
                    parada = false;
                    modificat = true;
                }
                else this.insertarVertex(i, comunitat);
            }
        }
        return modificat;
    }

    private Integer treureVertex(Integer vertex)
    {
        Integer comunitat = this.com.get(vertex);
        ArrayList<Integer> aux = this.cActual.get(comunitat);
        Double suma = 0.0, x;
        for(Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(! aux.contains(i))
            {
                x = this.gIntermedi.getEdge(vertex, i);
                if(x != null) suma += x;
            }
        }
        this.pesosCom.put(comunitat, this.pesosCom.get(comunitat) - suma);
        this.cActual.get(comunitat).remove(vertex);
        return comunitat;
    }
    
    private void insertarVertex(Integer vertex, Integer comunitat)
    {
        this.cActual.get(comunitat).add(vertex);
        this.com.put(vertex, comunitat);
        ArrayList<Integer> aux = this.cActual.get(comunitat);
        Double suma = 0.0, x;
        for(Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(! aux.contains(i))
            {
                x = this.gIntermedi.getEdge(vertex, i);
                if(x != null) suma += x;
            }
        }
        this.pesosCom.put(comunitat, this.pesosCom.get(comunitat) + suma);
    }
    
    private void inicialitzarComunitats()
    {
        this.pesosCom = new HashMap<>();
        this.com = new HashMap<>();
        this.cActual = new ArrayList<ArrayList<Integer>>();
        for (Integer i : this.gIntermedi.getVertexs())
        {
            ArrayList<Integer> aux = new ArrayList();
            aux.add(i);
            this.cActual.add(aux);
            
            Double suma = 0.0;
            for(Integer j: this.gIntermedi.getNeighbors(i))
            {
                if(! i.equals(j))
                {
                    Double x = this.gIntermedi.getEdge(i, j);
                    if(x != null) suma += x;
                }
            }
            this.pesosCom.put(i, suma);
            this.com.put(i, i);
        }
    }
    
    private boolean estaCon(Integer vertex, Integer comunitat)
    {
        for (Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(this.com.get(i).equals(comunitat)) return true;
        }
        return false;
    }
    
    private double pesTotal()
    {
        this.pesosVertex = new HashMap<>();
        double suma = 0.0;
        double sVertex = 0.0;
        for (Integer i: this.gIntermedi.getVertexs())
        {
            sVertex = 0.0;
            for(Integer j: this.gIntermedi.getNeighbors(i))
            {
                Double x = this.gIntermedi.getEdge(i, j);
                if(x != null) sVertex += x;
            }
            suma += sVertex;
            this.pesosVertex.put(i, sVertex);
            
        }
        return suma / 2.0;
    }
    
    private double pesVertexComunitat(Integer vertex, ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        for (Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(comunitat.contains(i))
            {
                Double x = this.gIntermedi.getEdge(vertex, i);
                if(x != null) suma += x;
            }
        }
        return suma;
    }
    
    @Override
    public ArrayList<ArrayList<Integer>> obtain()
    {    
        if(p == 100) return this.communityList.get(this.steps - 1);
        return this.communityList.get( (this.p * (this.steps)) / 100);  
    }
}