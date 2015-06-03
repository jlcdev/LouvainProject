package domain;

import domain.comunidades.CtoComunidad;
import domain.grafos.Filters;
import domain.grafos.GrafoEntrada;
import domain.grafos.Selections;
import domain.grafos.Transformation;
import java.util.ArrayList;
import shared.Algorithm;
import shared.CliquePercolation;
import shared.GirvanNewman;
import shared.Graph;
import shared.Louvain;

/**
 * Controlador de algoritmo se encarga de mantener la coherencia de los pasos
 * del mismo y gestiona los datos relativos a los algoritmos, filtros,
 * selecciones y flags de control.
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class CtrAlgoritmo
{
    private Filters filters;
    private Selections selections;
    private int p;
    private int algorithm;
    private Algorithm communityAlgorithm = null;
    private boolean arecatselections = false;
    private boolean arepagselections = false;
    private boolean arefilters = false;

    /**
     * Constructor por defecto
     */
    public CtrAlgoritmo()
    {
        this.selections = new Selections();
    }

    /**
     * Fija los valores de los filtros
     *
     * @param a importancia de nombre comun
     * @param b importancia de categoria comun
     * @param c importancia de paginas comunes
     * @param d importancia de tener padres en comun
     * @param e importancia de tener hijos en comun
     */
    public void setFilters(Integer a, Integer b, Integer c, Integer d, Integer e)
    {
        this.filters = new Filters(a, b, c, d, e);
        this.arefilters = true;
    }

    /**
     * Sobrescribe la lista de selección actual de paginas por las entrantes
     *
     * @param list de paginas seleccionadas
     */
    public void setPageSelections(ArrayList<Integer> list)
    {
        this.selections.setPagesSelected(list);
        this.arepagselections = true;
    }

    /**
     * Sobrescribe la lista de selección actual de categorias por las entrantes
     *
     * @param list de paginas seleccionadas
     */
    public void setCatSelections(ArrayList<Integer> list)
    {
        this.selections.setCategoriesSelected(list);
        this.arecatselections = true;
    }

    /**
     * Añade una pagina concreta a la seleccion
     *
     * @param i numero de la pagina
     */
    public void setPageSelectionConcrete(Integer i)
    {
        this.selections.setPage(i);
    }

    /**
     * Añade una categoria concreta a la seleccion
     *
     * @param i numero de la categoria
     */
    public void setCategorySelectionConcrete(Integer i)
    {
        this.selections.setCategory(i);
    }

    /**
     * Fija un nuevo valor del factor de cohesion
     *
     * @param p valor de cohesion
     */
    public void setP(int p)
    {
        if(p < 0)
        {
            this.p = 0;
        }
        else if(p > 100)
        {
            this.p = 100;
        }
        else
        {
            this.p = p;
        }
    }

    /**
     * Fija un algoritmo concreto
     *
     * @param algorithm numero del algoritmo
     */
    public void setAlgorithm(int algorithm)
    {
        if(algorithm < 1)
        {
            this.algorithm = 1;
        }
        else if(algorithm > 3)
        {
            this.algorithm = 3;
        }
        else
        {
            this.algorithm = algorithm;
        }
    }

    /**
     * Da una aproximación temporal en segundos del coste del algoritmo basado
     * únicamente en la cantidad de categorias que se han seleccionado
     *
     * @return segundos aproximados de calculo
     */
    public Double timeAproximation()
    {
        Double response;
        int n = this.selections.getCategoriesSelected().size();
        int nn = n * n;
        if(n < 5000)
        {
            response = (double) (n * n) / 10000000;
        }
        else if(n < 9000)
        {
            response = 0.000000004 * nn + 0.008052 * n - 22.168;
        }
        else
        {
            response = 0.000000004 * nn + 0.008052 * n + 10;
        }
        if(response < 0.001)
        {
            response = 0.0;
        }
        long factor = (long) Math.pow(10, 2);
        response = response * factor;
        long tmp = Math.round(response);
        return (double) tmp / factor;
    }

    /**
     * Devuelve si se ha marcado alguna categoria
     *
     * @return true si se ha marcado como minimo una categoria
     */
    public boolean areCatSelections()
    {
        return this.arecatselections;
    }

    /**
     * Devuelve si se ha marcado alguna pagina
     *
     * @return true si se ha marcado como minimo una pagina
     */
    public boolean arePagSelections()
    {
        return this.arepagselections;
    }

    /**
     * Devuelve si se han seleccionado filtros
     *
     * @return
     */
    public boolean areFilters()
    {
        return this.arefilters;
    }

    /**
     * Realiza la transformación de un grafo de entrada a un grafo de algoritmo
     *
     * @param g grafo de entrada
     * @return grafo preparado para los algoritmos
     */
    public Graph<Integer, Double> generate(GrafoEntrada g)
    {
        long t1 = System.currentTimeMillis();
        GrafoEntrada nuevo = Transformation.clearGraph(g, this.selections);
        Graph<Integer, Double> response = Transformation.entryToAlgorithm(nuevo, filters);
        long t2 = System.currentTimeMillis();
        int tiempo = (int) ((t2 - t1) / 1000);
        System.out.println("System: CtrAlgoritmo: generate: Tiempo de transformación: " + tiempo + "s");
        return response;
    }

    /**
     * Obtiene las diferentes soluciones proporcionadas por los algoritmos en
     * funcion del factor de cohesion
     *
     * @param p factor de cohesion
     * @param g grafo de entrada
     * @return conjunto de comunidades para el factor de cohesion
     */
    public CtoComunidad obtain(int p, GrafoEntrada g)
    {
        if(this.communityAlgorithm == null)
        {
            return null;
        }
        this.communityAlgorithm.setP(p);
        return new CtoComunidad(this.communityAlgorithm.obtain(), g, this.algorithm, this.filters, this.selections, this.p, 0);
    }

    /**
     * Aplica el algoritmo elegido con todas las caracteristicas definidas
     *
     * @param g grafo transformado
     * @param orig grafo de entrada
     * @return conjunto de comunidades resultante de aplicar el algoritmo
     */
    public CtoComunidad ejecutar(Graph<Integer, Double> g, GrafoEntrada orig)
    {
        Graph<Integer, Double> copy = g.clone();
        int innerp = this.p;
        System.out.println("System: CtrAlgoritmo: ejecutar: Valor de p = " + innerp);
        switch(this.algorithm)
        {
            case 1:
                communityAlgorithm = new Louvain();
                break;
            case 2:
                communityAlgorithm = new GirvanNewman();
                break;
            case 3:
                communityAlgorithm = new CliquePercolation();
                break;
        }
        if(communityAlgorithm == null)
        {
            return null;
        }
        communityAlgorithm.setP(this.p);
        double t1 = System.currentTimeMillis();
        communityAlgorithm.calc(copy);
        double t2 = System.currentTimeMillis();
        return new CtoComunidad(communityAlgorithm.obtain(), orig, this.algorithm, this.filters, this.selections, innerp, (t2 - t1));
    }
}