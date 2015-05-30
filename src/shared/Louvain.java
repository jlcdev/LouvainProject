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
    
    //private ArrayList<ArrayList<Integer>> cAnterior;
    //private ArrayList<ArrayList<Integer>> cActual;
    private HashMap<Integer, ArrayList<Integer>> cAnterior;
    private HashMap<Integer, ArrayList<Integer>> cActual;
    
    private Integer steps;

    public Louvain()
    {
        this.graph = null;
        this.gIntermedi = null;
        this.communityList = new HashMap<>();
        this.cActual = new HashMap<>();
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
            //this.cAnterior = (ArrayList<ArrayList<Integer>>) this.cActual.clone();
            
            this.cAnterior = new HashMap<>();
            for(Integer i: this.graph.getVertexs())
            {
                ArrayList<Integer> x = new ArrayList<>();
                x.add(i);
                this.cAnterior.put(i, x);
            }
            this.fase2();
            this.inicialitzarComunitats();
            this.guardarComunitat(); //Per guardar l'estat inicial
            this.fase2();

            this.metode();
        //}
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
        this.gIntermedi = new Graph<>();
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
        ArrayList<ArrayList<Integer>> guardar = new ArrayList<>();
        for(ArrayList<Integer> i: this.cActual.values())
        {
            ArrayList<Integer> nou = new ArrayList<>();
            for(Integer j: i)
            {
                ArrayList<Integer> x = (ArrayList<Integer>) this.cAnterior.get(j).clone();
                nou.addAll(x);
            }
            if(nou.size() > 0) guardar.add(nou);
        }
        
        //Guardamos la lista
        this.communityList.put(this.steps, guardar);
        this.steps++;
        
        //Ponemos esta lista en cAnterior
        this.cAnterior = new HashMap<>();
        Integer tam = guardar.size();
        for(Integer i = 0; i < tam; i++)
        {
            ArrayList<Integer> aux = new ArrayList<>();
            for(Integer j: guardar.get(i))
            {
                aux.add(j);
            }
            this.cAnterior.put(i, aux);
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
        int tam;
        while(! parada)
        {
            parada = true;
            for(Integer i: this.gIntermedi.getVertexs())
            {
                qmax = -1.0;
                comunitat = this.trobarComunitat(i);
                this.cActual.get(comunitat).remove(i);
                
                ki = this.pesIncidentsVertex(i);
                sumtot2 = this.pesIncidentsComunitat(this.cActual.get(comunitat));
                kin2 = this.pesVertexComunitat(i, this.cActual.get(comunitat));
                tam = this.cActual.size();
                for (Integer j = 0; j < tam; j++)
                {
                    if(this.estaCon(i, j) && !comunitat.equals(j))
                    {
                        q = (this.pesVertexComunitat(i, this.cActual.get(j)) - kin2)/m2;
                        q -= ki*(this.pesIncidentsComunitat(this.cActual.get(j))-sumtot2) / mqua2;
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
        this.cActual = new HashMap<>();
        Integer cont = 0;
        for (Integer i : this.gIntermedi.getVertexs())
        {
            ArrayList<Integer> aux = new ArrayList();
            aux.add(i);
            this.cActual.put(cont,aux);
            ++cont;
        }
    }
    
    private Integer trobarComunitat(Integer vertex)
    {
        int tam = this.cActual.size();
        for (Integer i = 0; i < tam; i++) 
        {
            if(this.cActual.get(i).contains(vertex)) return i;
        }
        return 0;
    }
    
    private boolean estaCon(Integer vertex, Integer comunitat)
    {
        for (Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(this.cActual.get(comunitat).contains(i)) return true;
        }
        return false;
    }
    
    private double pesIncidentsVertex(Integer vertex)
    {
        double suma = 0.0;
        for(Integer j: this.gIntermedi.getNeighbors(vertex))
        {
            if(vertex.equals(j)) suma += this.gIntermedi.getEdge(vertex, j).getValue() /2.0;
            else
            {
                Edge<Integer, Double> x = this.gIntermedi.getEdge(vertex, j);
                if(x != null) suma += x.getValue();
            }
        }
        return suma;
    }
    
    private double pesTotal()
    {
        double suma = 0.0;
        Edge<Integer, Double> x;
        for (Integer i: this.gIntermedi.getVertexs())
        {
            for(Integer j: this.gIntermedi.getNeighbors(i))
            {
                x = this.gIntermedi.getEdge(i, j);
                if(x != null) suma += x.getValue();
            }
        }
        return suma / 2.0;
    }
    
    private double pesVertexComunitat(Integer vertex, ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        Edge<Integer, Double> x;
        for (Integer i: this.gIntermedi.getNeighbors(vertex))
        {
            if(comunitat.contains(i))
            {
                x = this.gIntermedi.getEdge(vertex, i);
                if(x != null) suma += x.getValue();
            }
        }
        return suma;
    }
    
    private double pesIncidentsComunitat(ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        Edge<Integer, Double> x;
        for(Integer i: comunitat)
        {
            for(Integer j: this.gIntermedi.getNeighbors(i))
            {
                if(! comunitat.contains(j))
                {
                    x = this.gIntermedi.getEdge(i, j);
                    if(x != null) suma += x.getValue();
                }
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