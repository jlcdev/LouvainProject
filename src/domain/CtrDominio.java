package domain;

import data.CtrData;
import domain.comunidades.CtoComunidad;
import domain.comunidades.Comunidad;
import domain.grafos.Categoria;
import domain.grafos.GrafoEntrada;
import domain.grafos.Pagina;
import domain.grafos.Arch;
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
        this.g = new GrafoEntrada();
        this.ctrData = new CtrData();
    }
    
    public void newGrafo()
    {
        this.g = new GrafoEntrada();
    }
    
    public void information()
    {
        if(this.graph == null) return;
        System.out.println("Vertices: "+this.graph.getVertexs().size());
        
    }
    
    public GrafoEntrada getGrafo()
    {
        return this.g;
    }
    
    public ArrayList<Integer> getCatSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getCategorySize(), tam2;
        for(int i = 0;i < tam;++i)
        {
            tam2 = this.g.getCategoryArch(i).size();
            if(tam2 >= min && tam2 <= max)
                selection.add(i);
        }
        return selection;
    }
  
    public ArrayList<Integer> getPagSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getPageSize(), tam2;
        for(int i = 0;i < tam;++i)
        {
            tam2 = this.g.getPageArch(i).size();
            if(tam2 >= min && tam2 <= max)
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
        return (this.graph != null);
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
        for(Integer i : this.g.getCategories())
        {
            for(Arch arc : this.g.getCsupCArch(i))
            {
                response.add(arc.toString());
            }
            for(Arch arc : this.g.getCsubCArch(i))
            {
                response.add(arc.toString());
            }
            for(Arch arc : this.g.getCPArch(i))
            {
                response.add(arc.toString());
            }
        }
        for(Integer i : this.g.getPages())
        {
            for(Arch arc : this.g.getPCArch(i))
            {
                response.add(arc.toString());
            }
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
    
    public Integer verNumPag(String pagina)
    {
        return g.getPageNumber(new Pagina(pagina));
    }
    
    public Integer verNumCat(Integer category)
    {
        return this.g.getCategoryNumber(this.g.getNumberCategory(category));
    }
    
    public Integer verNumCat(String category)
    {
        return this.g.getCategoryNumber(new Categoria(category));
    }
    
    public Integer addGrafoCat (String category)
    {
        Categoria c = new Categoria(category);        
        this.g.addCategoria(c);
        return this.g.getCategoryNumber(c);
    }
  
    /**
     * Añade una pagina al grafo.
     * @param pagina 
     */
    public Integer addGrafoPag (String pagina)
    {
        Pagina p = new Pagina(pagina);
        this.g.addPagina(p);
        return this.g.getPageNumber(p);
    }
  
    /**
     * Añade un enlace al grafo.
     * @param node1
     * @param node2
     * @param tipus 
     */
    public void addGrafoEnlace (String node1, String node2, String tipus)
    {
        this.g.addArch(new Arch(0,0,node1,node2,Arch.typeArch.valueOf(tipus)));
    }
  
    /**
     * Borra una categoria del grafo. 
     * @param category
     * @return 
     */
    public Integer rmvGrafoCat (String category)
    {
        Categoria c = new Categoria(category);
        Integer r = this.g.getCategoryNumber(c);
        this.g.removeCategoria(c);
        return r;
        
    }
    
    /**
     * Borra una pagina del grafo.
     * @param pagina 
     * @return  
     */
    public Integer rmvGrafoPag (String pagina)
    {
        Pagina p = new Pagina(pagina);
        Integer r = this.g.getPageNumber(p);
        this.g.removePagina(p);
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
        Categoria c1, c2;
        Pagina p;
        switch(tipus)
        {
            case "CsubC":
                c1 = new Categoria(node1);
                c2 = new Categoria(node2);
                this.g.removeArchCategorySubCategory(this.g.getCategoryNumber(c1), this.g.getCategoryNumber(c2));
                break;
            case "CsupC":
                c1 = new Categoria(node1);
                c2 = new Categoria(node2);
                this.g.removeArchCategorySupCategory(this.g.getCategoryNumber(c1), this.g.getCategoryNumber(c2));
                break;
            case "PC":
                c1 = new Categoria(node2);
                p = new Pagina(node1);
                this.g.removeArchPageCategory(this.g.getPageNumber(p), this.g.getCategoryNumber(c1));
                break;
            case "CP":
                c1 = new Categoria(node1);
                p = new Pagina(node2);
                this.g.removeArchCategoryPage(this.g.getCategoryNumber(c1), this.g.getPageNumber(p));
                break;
        }
    }
    
    public void modifyPage(String page, String change)
    {
        this.g.changePage(this.g.getPageNumber(new Pagina(page)), change);
    }
    
    public void modifyCategory(String category, String change)
    {
        this.g.changeCategory(this.g.getCategoryNumber(new Categoria(category)), change);
    }
    
    public void readEntryGraphFile(String path)
    {
        this.g = new GrafoEntrada();
        this.ctrData.setEntryPath(path);
        this.ctrData.readEntryGraphFile(this.g);
    }
    
    public void addToEntryGraph(String nA, String tA, String tArch, String nB, String tB)
    {
        this.g.setData(nA, tA, tArch, nB, tB);
    }
    
    public boolean saveEntryGraph(String path)
    {
        if(path==null || path.isEmpty()) return false;
        this.ctrData.setEntryPath(path);
        return this.ctrData.writeEntryGraphFile(this.g);
    }
    
    //TRATAMIENTO DE LOS CONJUNTOS
    
    public boolean addCtoCat (String category, String comunidad, Boolean importat)
    {
        Comunidad com;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(category);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(category);
        }
        return false;
    }
  
    public void addCtoCom (String comunidad, Boolean importat)
    {
        Comunidad com = new Comunidad();
        com.setNombre(comunidad);
        if(importat)this.importedCto.addComunidades(com);
        else this.generatedCto.addComunidades(com);
    }
  
    public boolean rmvCtoCat (String category, String comunidad, Boolean importat)
    {
        Comunidad com;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(category);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(category);
        }
        return false;
    }
  
    public void rmvCtoCom (String comunidad, Boolean importat)
    {
        if(importat)this.importedCto.removeComunidades(comunidad);
        else this.generatedCto.removeComunidades(comunidad);
    }
  
    public void modCtoNombre (int tipus, String anterior, String nuevo, String comunidad, Boolean importat)
    {
        if(tipus == 0)
        {
            if(importat) this.importedCto.setNombre(nuevo);
            else this.generatedCto.setNombre(nuevo);
        }
        else if(tipus == 1)
        {
            if(importat)this.importedCto.getComunidad(anterior).setNombre(nuevo);
            else this.generatedCto.getComunidad(anterior).setNombre(nuevo);           
        }
        else if(tipus == 2)
        {
            if(importat)this.importedCto.getComunidad(comunidad).modCategoria(anterior, nuevo);
            else this.generatedCto.getComunidad(comunidad).modCategoria(anterior, nuevo);            
        }
    }
    public void mostrarGrafo()
    {}
    
    public ArrayList<String> mostrarCtoComunidad(Boolean importat)
    {
        if(importat) return this.importedCto.getNameComunidades();
        else return this.generatedCto.getNameComunidades();
    }
        
    public ArrayList<String> mostrarComunidad(String comunidad, Boolean importat)
    {
        if(importat)
        {
            Comunidad c = this.importedCto.getComunidad(comunidad);
            return c.getNameCategories();
        }
        else
        {
            Comunidad c = this.generatedCto.getComunidad(comunidad);
            return c.getNameCategories();
        }
    }

    public ArrayList<String> compararComunidades(String com1, Boolean importado1, String com2, Boolean importado2)
    {
        Comunidad c1 = new Comunidad();
        Comunidad c2 = new Comunidad();
        if(importado1)c1 = this.importedCto.getComunidad(com1);
        else c1 = this.generatedCto.getComunidad(com1);
        if(importado2)c2 = this.importedCto.getComunidad(com2);
        else c2 = this.generatedCto.getComunidad(com2);
        ArrayList<String> comparacion = new ArrayList();
        return comparacion;
    }
    
    public void saveCtoComunidad(String path)
    {
        if(path == null || this.generatedCto == null || path.isEmpty()) return;
        this.generatedCto.savetoFile();
        this.ctrData.setAlgorithmPath(path);
        this.ctrData.writeCtoComunidad(this.generatedCto);
    }
    
    public void loadCtoComunidad(String path)
    {
        if(path == null || path.isEmpty()) return;
        this.importedCto = new CtoComunidad();
        this.ctrData.setAlgorithmPath(path);
        this.ctrData.readCtoComunidad(this.importedCto);
    }
    
    public void compararConjuntos()
    {
        
    }
    
    public void estadisticas()
    {
        //OPCIONAL
    }
}
