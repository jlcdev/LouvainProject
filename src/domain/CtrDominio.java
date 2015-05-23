package domain;

import data.CtrData;
import domain.comunidades.CtoComunidad;
import domain.grafos.Categoria;
import domain.grafos.Grafo;
import domain.grafos.Node;
import domain.grafos.Pagina;
import java.util.ArrayList;
import java.util.Iterator;
import shared.Graph;

/**
 *
 * @author Javier López Calderón
 */
public class CtrDominio
{
    private Grafo g = null;
    private CtrData ctrData= null;
    private Graph<Integer, Double> graph = null;
    private CtoComunidad generatedCto= null;
    
    public CtrDominio()
    {
        g = new Grafo();
        ctrData = new CtrData();
    }
    
    public Grafo getGrafo()
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
        ArrayList<String> listPages = new ArrayList<>();
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        Node n;
        while(iter.hasNext())
        {
            n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Pagina.class) listPages.add(n.getNombre());
        }
        return listPages;
    }
    
    public ArrayList<String> verCatGeneral()
    {
        ArrayList<String> listCats = new ArrayList<>();
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        while(iter.hasNext())
        {
            Node n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Categoria.class) listCats.add(n.getNombre());
        }
        return listCats;
    }
    
    public String verPag(int num)
    {
        String res = "";
        if(num < 0) return res;
        int cont = 0;
        Node n;
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        while(iter.hasNext())
        {
            n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Pagina.class)
            {
                if(cont == num)
                {
                    res = n.getNombre();
                    break;
                }
                else ++cont;
            }
        }
        return res;
    }
    
    public String verCat(int num)
    {
        String res = "";
        if(num < 0) return res;
        Node n;
        int cont = 0;
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        while(iter.hasNext())
        {
            n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Categoria.class)
            {
                if(cont == num)
                {
                    res = n.getNombre();
                    break;
                }
                else ++cont;
            }
        }
        return res;
    }
    
    public boolean modifyPage(int page, String change)
    {
        boolean res = false;
        if(page < 0) return res;
        int cont = 0;
        Node n;
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        while(iter.hasNext())
        {
            n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Pagina.class)
            {
                if(cont == page)
                {
                    n.setNombre(change);
                    res = true;
                    break;
                }
                else ++cont;
            }
        }
        return res;
    }
    
    public boolean modifyCategory(int category, String change)
    {
        boolean res = false;
        if(category < 0) return res;
        Node n;
        int cont = 0;
        Iterator<Integer> iter = this.g.getAllVertex().iterator();
        while(iter.hasNext())
        {
            n = this.g.getNodeNumber(iter.next());
            if(n.getClass() == Categoria.class)
            {
                if(cont == category)
                {
                    n.setNombre(change);
                    res = true;
                    break;
                }
                else ++cont;
            }
        }
        return res;
    }
    
    public void readEntryGraphFile(String path)
    {
        ctrData.setEntryPath(path);
        ctrData.readEntryGraphFile(this.g);
    }
    
    public void addToEntryGraph(String entry)
    {
        this.g.addNewEntry(entry);
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
