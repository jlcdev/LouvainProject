package domain;

import domain.comunidades.Comunidad;
import domain.comunidades.CtoComunidad;
import domain.grafos.Conversion;
import domain.grafos.Filters;
import domain.grafos.Grafo;
import domain.grafos.Selections;
import java.util.ArrayList;
import shared.Algorithm;
import shared.Graph;
import shared.Louvain;

/**
 *
 * @author Javier López Calderón
 */
public class CtrAlgoritmo
{
    private Filters filters;
    private Selections pageSelections;
    private Selections catSelections;
    private int p;
    private int algorithm;
    
    public CtrAlgoritmo()
    {
        this.catSelections = new Selections();
        this.pageSelections = new Selections();
    }
    
    public void setFilters(Integer a, Integer b, Integer c, Integer d, Integer e)
    {
        this.filters = new Filters(a, b, c, d, e);
    }
    
    public void setPageSelections(ArrayList<String> listString)
    {
        this.pageSelections.setSelection(listString);
    }
    
    public void setConcretePageSelection(String s)
    {
        this.pageSelections.addToSelection(s);
    }
    
    public void setCatSelections(ArrayList<String> listString)
    {
        this.catSelections.setSelection(listString);
    }
    
    public void setConcreteCatSelection(String s)
    {
        this.catSelections.addToSelection(s);
    }
    
    public void setP(int p)
    {
        if(p < 0) this.p = 0;
        else if(p > 100) this.p = 100;
        else this.p = p;
    }
    
    public void setAlgorithm(int algorithm)
    {
        if(algorithm < 1) this.algorithm = 1;
        else if(algorithm > 3) this.algorithm = 3;
        else this.algorithm = algorithm;
    }
    
    public Graph<Integer, Double> generate(Grafo g)
    {
        return Conversion.toWeightedGraph(g, this.filters, this.catSelections, this.catSelections);
    }
    
    public CtoComunidad ejecutar(Graph<Integer, Double> g, Grafo orig)
    {
        Algorithm communityAlgorithm = null;
        switch(this.algorithm)
        {
            case 1:
                communityAlgorithm = new Louvain();
                break;
            case 2:
                break;
            case 3:
                break;
        }
        if(communityAlgorithm == null) return null;
        communityAlgorithm.setP(this.p);
        communityAlgorithm.calc(g);
        return new CtoComunidad(communityAlgorithm.obtain(), orig, this.algorithm, this.filters, this.catSelections, this.pageSelections);
    }
}
