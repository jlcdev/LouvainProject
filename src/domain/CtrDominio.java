package domain;

import data.CtrData;
import domain.comunidades.CtoComunidad;
import domain.comunidades.Comunidad;
import domain.grafos.Categoria;
import domain.grafos.GrafoEntrada;
import domain.grafos.Pagina;
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
    private CtoComunidad generatedCto = null;
    private CtoComunidad importedCto = null;
    
    public CtrDominio()
    {
        g = new GrafoEntrada();
        ctrData = new CtrData();
    }
    
    public void newGrafo()
    {
        g = new GrafoEntrada();
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
    
    public ArrayList<Integer> getCatSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        for(int i = 0;i < g.getCategorySize();++i)
        {
            if(g.getCategoryArch(i).size() >= min || g.getCategoryArch(i).size() <= max)
                selection.add(i);
        }
        return selection;
    }
  
    public ArrayList<Integer> getPagSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        for(int i = 0;i < g.getPageSize();++i)
        {
            if(g.getPageArch(i).size() >= min || g.getPageArch(i).size() <= max)
                selection.add(i);
        }
        return selection;
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
    
    public ArrayList<String> verEnlacesGeneral()
    {
        ArrayList<String> response = new ArrayList<>();
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
    
    public Integer verNumPag(String pagina)
    {
        Pagina pag = new Pagina(pagina);
        return g.getPageNumber(pag);
    }
    
    public Integer verNumCat(Integer category)
    {
        return this.g.getCategoryNumber(this.g.getNumberCategory(category));
    }
    
    public Integer verNumCat(String categoria)
    {
        Categoria cat = new Categoria(categoria);
        return g.getCategoryNumber(cat);
    }
    
    public void addGrafoCat (String categoria)
    {
        g.setData(categoria,"cat","0","0","0");
    }
  
    /**
     * Añade una pagina al grafo.
     * @param pagina 
     */
    public void addGrafoPag (String pagina)
    {
        g.setData(pagina,"pag","0","0","0");
    }
  
    /**
     * Añade un enlace al grafo.
     * @param node1
     * @param node2
     * @param tipus 
     */
    public void addGrafoEnlace (String node1, String node2, String tipus)
    {
        //NO SABEMOS SI SON PAGINAS O CATEGORIAS
        g.setData(node1,"cat",tipus,node2,"0");
    }
  
    /**
     * Borra una categoria del grafo.
     * @param categoria 
     */
    public int rmvGrafoCat (String categoria)
    {
        Categoria c = new Categoria(categoria);
        int r = g.getCategoryNumber(c);
        g.removeCategoria(c);
        return r;
        
    }
    
    /**
     * Borra una pagina del grafo.
     * @param pagina 
     */
    public int rmvGrafoPag (String pagina)
    {
        Pagina p = new Pagina(pagina);
        int r = g.getPageNumber(p);
        g.removePagina(p);
        return r;// ha de retornar la pos de la pagina
    }
  
    /**
     * Borra un enlace del grafo.
     * @param node1
     * @param node2
     * @param tipus 
     */
    public void rmvGrafoEnlace (String node1, String node2, String tipus)
    {
        //no hi ha funcio remove edge
    }
    public void modifyPage(String page, String change)
    {
        Pagina p = new Pagina(page);
        this.g.changePage(g.getPageNumber(p), change);
    }
    
    public void modifyCategory(String category, String change)
    {
        Categoria c = new Categoria(category);
        this.g.changeCategory(g.getCategoryNumber(c), change);
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
    
    //TRATAMIENTO DE LOS CONJUNTOS
    
    public boolean addCtoCat (String categoria, String comunidad, Boolean importat)
    {
        Comunidad com = new Comunidad();
        if(importat)
        {
            com = importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(categoria);
        }
        else
        {
            com = generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(categoria);
        }
        return false;
    }
  
    public void addCtoCom (String comunidad, Boolean importat)
    {
        Comunidad com = new Comunidad();
        com.setNombre(comunidad);
        if(importat)importedCto.addComunidades(com);
        else generatedCto.addComunidades(com);
    }
  
    public boolean rmvCtoCat (String categoria, String comunidad, Boolean importat)
    {
        Comunidad com = new Comunidad();
        if(importat)
        {
            com = importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(categoria);
        }
        else
        {
            com = generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(categoria);
        }
        return false;
    }
  
    public void rmvCtoCom (String comunidad, Boolean importat)
    {
        if(importat)importedCto.removeComunidades(comunidad);
        else generatedCto.removeComunidades(comunidad);
    }
  
    public void modCtoNombre (int tipus, String anterior, String nuevo, String comunidad, Boolean importat)
    {
        if(tipus == 0)
        {
            if(importat) importedCto.setNombre(nuevo);
            else generatedCto.setNombre(nuevo);
        }
        else if(tipus == 1)
        {
            if(importat)importedCto.getComunidad(anterior).setNombre(nuevo);
            else generatedCto.getComunidad(anterior).setNombre(nuevo);           
        }
        else if(tipus == 2)
        {
            if(importat)importedCto.getComunidad(comunidad).modCategoria(anterior, nuevo);
            else generatedCto.getComunidad(comunidad).modCategoria(anterior, nuevo);            
        }
    }
    public void mostrarGrafo()
    {}
    
    public ArrayList<String> mostrarCtoComunidad(Boolean importat)
    {
        if(importat) return importedCto.getNameComunidades();
        else return generatedCto.getNameComunidades();
    }
        
    public ArrayList<String> mostrarComunidad(String comunidad, Boolean importat)
    {
        if(importat)
        {
            Comunidad c = importedCto.getComunidad(comunidad);
            return c.getNameCategories();
        }
        else
        {
            Comunidad c = generatedCto.getComunidad(comunidad);
            return c.getNameCategories();
        }
    }

    public void comparar(int com1, int com2)
    {}
    
    public void estadisticas()
    {}
    
    
}
