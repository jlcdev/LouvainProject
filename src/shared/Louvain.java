package shared;

/**
 *
 * @author Grupo 7.3
 */
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for applying the Louvain algorithm to a graph
 *
 * @author Cluster 7 sub 3
 * @version 1.0
 * @since 01/06/2015
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

    /**
     * Constructor por defecto
     */
    public Louvain()
    {
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
        for(Integer i : this.graph.getVertexs())
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

    /**
     * Permite aplicar las diferentes fases de Louvain
     */
    private void metode()
    {
        while(this.fase1())
        {
            this.guardarComunitat();
            this.fase2();
        }
    }

    /**
     * Aplica la fase 2 de Louvain
     */
    private void fase2()
    {
        double x;
        this.gIntermedi = new Graph<Integer, Double>();
        Integer tam = this.cAnterior.size();
        for(Integer i = 0; i < tam; i++)
        {
            this.gIntermedi.addVertex(i);
        }
        for(Integer i = 0; i < tam; i++)
        {
            for(Integer j = i; j < tam; j++)
            {
                x = this.pesEntreComunitats(this.cAnterior.get(i), this.cAnterior.get(j));
                if(x != 0)
                {
                    this.gIntermedi.addEdge(i, j, x);
                }
            }
        }
    }

    /**
     * Obtiene el peso que hay entre elementos de dos comunidades
     *
     * @param comunitat1 lista de elementos de la comunidad 1
     * @param comunitat2 lista de elementos de la comunidad 2
     * @return double con el valor total del peso entre las comunidades.
     */
    private double pesEntreComunitats(ArrayList<Integer> comunitat1, ArrayList<Integer> comunitat2)
    {
        double suma = 0.0;
        for(Integer i : comunitat1)
        {
            for(Integer j : comunitat2)
            {
                Double k = this.graph.getEdge(i, j);
                if(k != null)
                {
                    suma += k;
                }
            }
        }
        if(comunitat1.equals(comunitat2))
        {
            return suma / 2.0;
        }
        else
        {
            return suma;
        }
    }

    /**
     * Almacena la información para posteriormente la función Obtain pueda
     * recuperar las comunidades de un step en concreto
     */
    private void guardarComunitat()
    {
        //Creamos la nueva lista de comunidades
        ArrayList<ArrayList<Integer>> guardar = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> i : this.cActual)
        {
            ArrayList<Integer> nou = new ArrayList<Integer>();
            for(Integer j : i)
            {
                ArrayList<Integer> x = new ArrayList<Integer>(this.cAnterior.get(j));
                nou.addAll(x);
            }
            if(nou.size() > 0)
            {
                guardar.add(nou);
            }
        }
        //Guardamos la lista
        this.communityList.put(this.steps, guardar);
        this.steps++;
        //Ponemos esta lista en cAnterior
        this.cAnterior = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> i : guardar)
        {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            for(Integer j : i)
            {
                aux.add(j);
            }
            this.cAnterior.add(aux);
        }
    }

    /**
     * Aplica la fase 1 del algoritmo
     *
     * @return false cuando la fase ha concluido
     */
    private boolean fase1()
    {
        inicialitzarComunitats();
        double qmax, q;
        Integer comunitat, comunitatDef = 0;
        boolean parada = false, modificat = false;
        double m = this.pesTotal();
        double m2 = 2 * m;
        double mqua2 = 2 * m * m;
        double ki, sumtot2, kin2, kin, sumtot;
        while(!parada)
        {
            parada = true;
            for(Integer i : this.gIntermedi.getVertexs())
            {
                qmax = 0.0;
                comunitat = this.treureVertex(i);
                ki = this.pesosVertex.get(i);
                sumtot2 = this.pesosCom.get(comunitat);
                kin2 = this.pesVertexComunitat(i, this.cActual.get(comunitat));
                for(Integer j = 0; j < this.cActual.size(); j++)
                {
                    if(estaCon(i, j) && !comunitat.equals(j))
                    {
                        q = (this.pesVertexComunitat(i, this.cActual.get(j)) - kin2) / m2;
                        q -= ki * (this.pesosCom.get(j) - sumtot2) / mqua2;
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
                else
                {
                    this.insertarVertex(i, comunitat);
                }
            }
        }
        return modificat;
    }

    /**
     * Quita el vertice de su comunidad y recalcula los pesos quitando ese nodo
     *
     * @param vertex Indice del vertice a quitar
     * @return comunidad
     */
    private Integer treureVertex(Integer vertex)
    {
        Integer comunitat = this.com.get(vertex);
        ArrayList<Integer> aux = this.cActual.get(comunitat);
        Double suma = 0.0, x;
        for(Integer i : this.gIntermedi.getNeighbors(vertex))
        {
            if(!aux.contains(i))
            {
                x = this.gIntermedi.getEdge(vertex, i);
                if(x != null)
                {
                    suma += x;
                }
            }
        }
        this.pesosCom.put(comunitat, this.pesosCom.get(comunitat) - suma);
        this.cActual.get(comunitat).remove(vertex);
        return comunitat;
    }

    /**
     * Añade el nodo a una comunidad y recalcula el peso añadiendo ese nodo
     *
     * @param vertex nodo a incluir
     * @param comunitat comunidad
     */
    private void insertarVertex(Integer vertex, Integer comunitat)
    {
        this.cActual.get(comunitat).add(vertex);
        this.com.put(vertex, comunitat);
        ArrayList<Integer> aux = this.cActual.get(comunitat);
        Double suma = 0.0, x;
        for(Integer i : this.gIntermedi.getNeighbors(vertex))
        {
            if(!aux.contains(i))
            {
                x = this.gIntermedi.getEdge(vertex, i);
                if(x != null)
                {
                    suma += x;
                }
            }
        }
        this.pesosCom.put(comunitat, this.pesosCom.get(comunitat) + suma);
    }

    /**
     * Realiza el paso inical del algoritmo haciendo que todos los nodos sean
     * comunidades de 1 elemento
     */
    private void inicialitzarComunitats()
    {
        this.pesosCom = new HashMap<>();
        this.com = new HashMap<>();
        this.cActual = new ArrayList<ArrayList<Integer>>();
        for(Integer i : this.gIntermedi.getVertexs())
        {
            ArrayList<Integer> aux = new ArrayList();
            aux.add(i);
            this.cActual.add(aux);
            Double suma = 0.0;
            for(Integer j : this.gIntermedi.getNeighbors(i))
            {
                if(!i.equals(j))
                {
                    Double x = this.gIntermedi.getEdge(i, j);
                    if(x != null)
                    {
                        suma += x;
                    }
                }
            }
            this.pesosCom.put(i, suma);
            this.com.put(i, i);
        }
    }

    /**
     * Verifica si un nodo se encuentra en una comunidad
     *
     * @param vertex nodo
     * @param comunitat comunidad
     * @return true si está, falso cualquier otra cosa
     */
    private boolean estaCon(Integer vertex, Integer comunitat)
    {
        for(Integer i : this.gIntermedi.getNeighbors(vertex))
        {
            if(this.com.get(i).equals(comunitat))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcula el peso total
     *
     * @return peso total
     */
    private double pesTotal()
    {
        this.pesosVertex = new HashMap<>();
        double suma = 0.0;
        double sVertex = 0.0;
        for(Integer i : this.gIntermedi.getVertexs())
        {
            sVertex = 0.0;
            for(Integer j : this.gIntermedi.getNeighbors(i))
            {
                Double x = this.gIntermedi.getEdge(i, j);
                if(x != null)
                {
                    sVertex += x;
                }
            }
            suma += sVertex;
            this.pesosVertex.put(i, sVertex);
        }
        return suma / 2.0;
    }

    /**
     * Calcula el peso de un determinado nodo dentro con los elementos de la
     * comunidad
     *
     * @param vertex nodo
     * @param comunitat comunidad sobre la que contrastar
     * @return double con el peso calculado
     */
    private double pesVertexComunitat(Integer vertex, ArrayList<Integer> comunitat)
    {
        double suma = 0.0;
        for(Integer i : this.gIntermedi.getNeighbors(vertex))
        {
            if(comunitat.contains(i))
            {
                Double x = this.gIntermedi.getEdge(vertex, i);
                if(x != null)
                {
                    suma += x;
                }
            }
        }
        return suma;
    }

    /**
     * Permite obtener el conjunto de comunidades
     *
     * @return lista de comunidades
     */
    @Override
    public ArrayList<ArrayList<Integer>> obtain()
    {
        if(p == 100)
        {
            return this.communityList.get(this.steps - 1);
        }
        return this.communityList.get((this.p * (this.steps)) / 100);
    }
}