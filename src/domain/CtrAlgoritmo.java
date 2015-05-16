package domain;

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
        this.filters = new Filters();
        this.catSelections = new Selections();
        this.pageSelections = new Selections();
        this.p = 0;
        this.algorithm = 0;
    }
    
    public void setFilters(String s)
    {
        if(s == null || s.isEmpty()) return;
        String as[] = s.split(",");
        if(as.length < 5 || as.length > 5) return;
        this.filters.setAll(Integer.getInteger(as[0]), Integer.getInteger(as[1]), Integer.getInteger(as[2]), Integer.getInteger(as[3]), Integer.getInteger(as[4]));
    }
    
    public void setPageSelections(ArrayList<String> listString)
    {
        this.pageSelections.setSelection(listString);
    }
    
    public void setCatSelections(ArrayList<String> listString)
    {
        this.catSelections.setSelection(listString);
    }
    
    public void setP(int p)
    {
        if(p < 0) this.p = 0;
        else if(p > 100) this.p = 100;
        else this.p = p;
    }
    
    public void setAlgorithm(int algorithm)
    {
        if(algorithm < 0) this.algorithm = 0;
        else if(algorithm > 2) this.algorithm = 2;
        else this.algorithm = algorithm;
    }
    
    public Graph<Integer, Double> generate(Grafo g)
    {
        return null;
    }
    
    public void ejecutar(Graph<Integer, Double> g)
    {
        Algorithm al = null;
        switch(this.algorithm)
        {
            case 0:
                al = new Louvain();
                break;
            case 1:
                break;
            case 2:
                break;
        }
        if(al == null) return;
        al.setP(this.p);
        al.calc(g);
        //TODO
    }
}
