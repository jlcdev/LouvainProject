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
 *
 * @author Javier López Calderón
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
    
    public CtrAlgoritmo()
    {
        this.selections = new Selections();
    }
    
    public void setFilters(Integer a, Integer b, Integer c, Integer d, Integer e)
    {
        this.filters = new Filters(a, b, c, d, e);
        this.arefilters = true;
    }
    
    public void setPageSelections(ArrayList<Integer> list)
    {
        this.selections.setPagesSelected(list);
        this.arecatselections = true;
    }
    
    public void setCatSelections(ArrayList<Integer> list)
    {
        this.selections.setCategoriesSelected(list);
        this.arepagselections = true;
    }
    
    public void setPageSelectionConcrete(Integer i)
    {
        this.selections.setPage(i);
    }
    
    public void setCategorySelectionConcrete(Integer i)
    {
        this.selections.setCategory(i);
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
    
    public boolean areCatSelections()
    {
        return this.arecatselections;
    }
    
    public boolean arePagSelections()
    {
        return this.arepagselections;
    }
    
    public boolean areFilters()
    {
        return this.arefilters;
    }
    
    public Graph<Integer, Double> generate(GrafoEntrada g)
    {
        long t1 = System.currentTimeMillis();
        GrafoEntrada nuevo = Transformation.clearGraph(g, this.selections);
        Graph<Integer, Double> response = Transformation.entryToAlgorithm(nuevo, filters);
        long t2 = System.currentTimeMillis();
        System.out.println("Time Generate: "+(t2-t1)+"ms");
        return response;
    }
    
    public CtoComunidad obtain(int p, GrafoEntrada g)
    {
        if(this.communityAlgorithm == null) return null;
        this.communityAlgorithm.setP(p);
        return new CtoComunidad(this.communityAlgorithm.obtain(), g, this.algorithm, this.filters, this.selections, this.p, 0);
    }
    
    public CtoComunidad ejecutar(Graph<Integer, Double> g, GrafoEntrada orig)
    {
        Graph<Integer, Double> copy = g.clone();
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
        if(communityAlgorithm == null) return null;
        communityAlgorithm.setP(this.p);
        double t1 = System.currentTimeMillis();
        communityAlgorithm.calc(copy);
        double t2 = System.currentTimeMillis();
        return new CtoComunidad(communityAlgorithm.obtain(), orig, this.algorithm, this.filters, this.selections, communityAlgorithm.getP(), (t2-t1));
    }
}
