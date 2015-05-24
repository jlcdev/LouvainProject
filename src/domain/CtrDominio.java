package domain;

import data.CtrData;
import domain.comunidades.CtoComunidad;
import domain.grafos.GrafoEntrada;
import java.util.ArrayList;
import shared.Graph;

/**
 *
 * @author Javier López Calderón
 */
public class CtrDominio
{
    private GrafoEntrada g = null;
    private CtrData ctrData= null;
    private Graph<Integer, Double> graph = null;
    private CtoComunidad generatedCto= null;
    
    public CtrDominio()
    {
        g = new GrafoEntrada();
        ctrData = new CtrData();
    }
    
    public void information()
    {
        if(graph == null) return;
        System.out.println("Vertices: "+graph.getVertexs().size());
        
    }
    
    public GrafoEntrada getGrafo()
    {
        return this.g;
    }
    
    public void setAlgorithmGraph(Graph<Integer, Double> graph)
    {
        this.graph = graph;
    }
    
    public Graph<Integer, Double> getAlgorithmGraph()
    {
        return this.graph;
    }
    
    public boolean isAlgorithmGraph()
    {
        return (graph != null?true:false);
    }
    
    public void setGeneratedCto(CtoComunidad cto)
    {
        this.generatedCto = cto;
    }
    
    public ArrayList<String> verPagGeneral()
    {
        ArrayList<String> response = new ArrayList();
        for(Integer i : this.g.getPages())
        {
            response.add(this.g.getNumberNamePage(i));
        }
        return response;
    }
    
    public ArrayList<String> verCatGeneral()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Integer i : this.g.getCategories())
        {
            response.add(this.g.getNumberNameCategory(i));
        }
        return response;
    }
    
    public ArrayList<Integer> getNumPagGeneral()
    {
        return this.g.getPages();
    }
    
    public ArrayList<Integer> getNumCatGeneral()
    {
        return this.g.getCategories();
    }
    
    public String verPag(Integer page)
    {
        return this.g.getNumberNamePage(page);
    }
    
    public String verCat(Integer page)
    {
        return this.g.getNumberNameCategory(page);
    }
    
    public Integer verNumPag(Integer page)
    {
        return this.g.getPageNumber(this.g.getNumberPage(page));
    }
    
    public Integer verNumCat(Integer category)
    {
        return this.g.getCategoryNumber(this.g.getNumberCategory(category));
    }
    
    public void modifyPage(Integer page, String change)
    {
        this.g.changePage(page, change);
    }
    
    public void modifyCategory(int category, String change)
    {
        this.g.changeCategory(category, change);
    }
    
    public void readEntryGraphFile(String path)
    {
        ctrData.setEntryPath(path);
        ctrData.readEntryGraphFile(this.g);
    }
    
    public void addToEntryGraph(String nA, String tA, String tArch, String nB, String tB)
    {
        this.g.setData(nA, tA, tArch, nB, tB);
    }
    
    public boolean saveEntryGraph(String path)
    {
        if(path==null || path.isEmpty()) return false;
        ctrData.setEntryPath(path);
        return ctrData.writeEntryGraphFile(this.g);
    }

    
    public void mostrarGrafo()
    {}
    
    public void mostrarComunidad()
    {}
    
    public void mostrarCtoComunidad()
    {}
    
    public void comparar(int com1, int com2)
    {}
    
    public void estadisticas()
    {}
    
    
}
