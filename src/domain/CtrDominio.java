package domain;

import data.CtrData;
import domain.comunidades.CtoComunidad;
import domain.comunidades.Comunidad;
import domain.comunidades.Purity;
import domain.grafos.Categoria;
import domain.grafos.GrafoEntrada;
import domain.grafos.Pagina;
import domain.grafos.Arch;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import optional.Visual;
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
    private Purity p = null;
    
    
    public CtrDominio()
    {
        this.g = new GrafoEntrada();
        this.ctrData = new CtrData();
        this.p = new Purity();
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
    
    public void windowGraphEntry()
    {
        if(this.g == null) return;
        ArrayList<Integer> nodes = new ArrayList();
        nodes.addAll(this.g.getCategories());
        nodes.addAll(this.g.getPages());
        HashMap<Integer, HashMap<Integer, String>> edges = new HashMap(5814);
        Iterator<Entry<Integer, ArrayList<Arch>>> it = this.g.getCsubCArch().entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            HashMap<Integer, String> edge = new HashMap(5814);
            for(Arch arc : entry.getValue())
            {
                edge.put(arc.getDestiny(), this.g.getNumberCategory(arc.getDestiny()).getNombre());
            }
            edges.put(entry.getKey(), edge);
        }
        it = this.g.getCsupCArch().entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            HashMap<Integer, String> edge = new HashMap(5814);
            for(Arch arc : entry.getValue())
            {
                edge.put(arc.getDestiny(), this.g.getNumberCategory(arc.getDestiny()).getNombre());
            }
            edges.put(entry.getKey(), edge);
        }
        it = this.g.getPCArch().entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            HashMap<Integer, String> edge = new HashMap(5814);
            for(Arch arc : entry.getValue())
            {
                edge.put(arc.getDestiny(), this.g.getNumberCategory(arc.getDestiny()).getNombre());
            }
            edges.put(entry.getKey(), edge);
        }
        it = this.g.getCPArch().entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            HashMap<Integer, String> edge = new HashMap(5814);
            for(Arch arc : entry.getValue())
            {
                edge.put(arc.getDestiny(), this.g.getNumberPage(arc.getDestiny()).getNombre());
            }
            edges.put(entry.getKey(), edge);
        }
        Visual v = new Visual(nodes, edges, 1, Color.WHITE);
        v.launchWindow();
    }
    
    public void windowGraphAlgorithm()
    {
        if(this.graph == null) return;
        Visual v = new Visual(this.graph.getVertexs(), this.graph.getEdgesFull(), 2, Color.WHITE);
        v.launchWindow();
    }
    
    public GrafoEntrada getGrafo()
    {
        return this.g;
    }
    
    public String getArchInformation(Arch arc)
    {
        String nodeA = this.g.getNumberNameCategory(arc.getOrigin());
        String nodeB = this.g.getNumberNameCategory(arc.getDestiny());
        String typeA = "cat";
        String typeB = "cat";
        String typeArch = "CsupC";
        switch(arc.getTypeArch())
        {
            case CsubC:
                typeArch = "CsubC";
                break;
            case CP:
                typeArch = "CP";
                nodeB = this.g.getNumberNamePage(arc.getDestiny());
                typeB = "page";
                break;
            case PC:
                typeArch = "PC";
                nodeA = this.g.getNumberNamePage(arc.getOrigin());
                typeA = "page";
                break;
        }
        return nodeA+"   "+typeA+"   "+typeArch+"   "+nodeB+"   "+typeB;
    }
    
    public ArrayList<Integer> getCatSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getCategorySize(), tam2;
        for(int i = 0;i < tam;++i)
        {
            tam2 = this.g.getCategoryArch(this.g.getCategories().get(i)).size();
            if(tam2 >= min && tam2 <= max)
                selection.add(this.g.getCategories().get(i));
        }
        return selection;
    }
  
    public ArrayList<Integer> getPagSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getPageSize(), tam2;
        for(int i = 0;i < tam;++i)
        {
            tam2 = this.g.getPageArch(this.g.getPages().get(i)).size();
            if(tam2 >= min && tam2 <= max)
                selection.add(this.g.getPages().get(i));
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
                response.add(getArchInformation(arc));
            }
            for(Arch arc : this.g.getCsubCArch(i))
            {
                response.add(getArchInformation(arc));
            }
            for(Arch arc : this.g.getCPArch(i))
            {
                response.add(getArchInformation(arc));
            }
        }
        for(Integer i : this.g.getPages())
        {
            for(Arch arc : this.g.getPCArch(i))
            {
                response.add(getArchInformation(arc));
            }
        }
        return response;
    }
    
    public ArrayList<String> verEnlacesGeneralNode(boolean category, String name)
    {
        ArrayList<String> response = new ArrayList<>();        
        if(category)
        {
            
            Integer i = this.verNumCat(name);
            for(Arch arc : this.g.getCsupCArch(i))
            {
                response.add(getArchInformation(arc));
            }
            for(Arch arc : this.g.getCsubCArch(i))
            {
                response.add(getArchInformation(arc));
            }
            for(Arch arc : this.g.getCPArch(i))
            {
                response.add(getArchInformation(arc));
            }                      
        }
        else
        {
            Integer i = this.verNumPag(name);
            for(Arch arc : this.g.getPCArch(i))
            {
                response.add(getArchInformation(arc));
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
        if(! this.g.addCategoria(c))return -1;
        return this.g.getCategoryNumber(c);
    }
  
    /**
     * Añade una pagina al grafo.
     * @param pagina 
     * @return  
     */
    public Integer addGrafoPag (String pagina)
    {
        Pagina p = new Pagina(pagina);
        if(! this.g.addPagina(p))return -1;
        return this.g.getPageNumber(p);
    }
  
     /**
     * Añade un enlace al grafo.
     * @param node1
     * @param node2
     * @param tipus 
     * @return
     */
    public boolean addGrafoEnlace (String node1, String node2, String tipus)
    {
        int origen = -1;
        int destino = -1;
        Categoria c1 = new Categoria(node1);
        Categoria c2 = new Categoria(node2);
        switch(tipus)
        {            
            case("CsubC"):
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                break;
            case("CsupC"):
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                break;
            case("CP"):
                Pagina p1 = new Pagina(node2);
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getPageNumber(p1);
                break;
            case("PC"):
                Pagina p2 = new Pagina(node1);
                origen = this.g.getPageNumber(p2);
                destino = this.g.getCategoryNumber(c2);
                break;
        }
        if(origen == -1 || destino == -1)return false;
        this.g.addArch(new Arch(origen,destino,Arch.typeArch.valueOf(tipus)));
        return true;
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
     * @return
     */
    public boolean rmvGrafoEnlace (String node1, String node2, String tipus)
    {
        Categoria c1 = new Categoria(node1);
        Categoria c2 = new Categoria(node2);
        int origen = -1;
        int destino = -1;
        switch(tipus)
        {
            case "CsubC":
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                this.g.removeArchCategorySubCategory(origen, destino);
                break;
            case "CsupC":
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                this.g.removeArchCategorySupCategory(origen, destino);
                break;
            case "PC":
                Pagina p2 = new Pagina(node2);
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getPageNumber(p2);
                this.g.removeArchPageCategory(origen, destino);
                break;
            case "CP":
                Pagina p1 = new Pagina(node1);
                origen = this.g.getPageNumber(p1);
                destino = this.g.getCategoryNumber(c2);
                this.g.removeArchCategoryPage(origen, destino);
                break;
        }
        return (origen != -1 && destino != -1);
    }
    
    public boolean modifyPage(String page, String change)
    {
        Pagina p = new Pagina(change);
        if(this.g.getPageNumber(p) != -1)return false;
        else this.g.changePage(this.g.getPageNumber(new Pagina(page)), change);
        return true;
    }
    
    public boolean modifyCategory(String category, String change)
    {
        Categoria c = new Categoria(change);
        if(this.g.getCategoryNumber(c) != -1) return false;
        this.g.changeCategory(this.g.getCategoryNumber(new Categoria(category)), change);
        return true;
    }
    
    public void readEntryGraphFile(String path)
    {
        //this.g = new GrafoEntrada();
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
    
    public boolean addCtoCat (String category, String comunidad, boolean importat)
    {
        Comunidad com;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(category);
            this.importedCto.setModificado(true);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.addCategoria(category);
            this.generatedCto.setModificado(true);
        }
        return false;
    }
  
    public void addCtoCom (String comunidad, boolean importat)
    {
        Comunidad com = new Comunidad();
        com.setNombre(comunidad);
        if(importat)
        {
            this.importedCto.addComunidades(com);
            this.importedCto.setModificado(true);
        }
        else
        {
            this.generatedCto.addComunidades(com);
            this.generatedCto.setModificado(true);
        }
    }
  
    public boolean rmvCtoCat (String category, String comunidad, boolean importat)
    {
        Comunidad com;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(category);
            this.importedCto.setModificado(true);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)return true;
            com.removeCategoria(category);
            this.generatedCto.setModificado(true);
        }
        return false;
    }
  
    public void rmvCtoCom (String comunidad, boolean importat)
    {
        if(importat)
        {
            this.importedCto.removeComunidades(comunidad);
            this.importedCto.setModificado(true);
        }
        else
        {
            this.generatedCto.removeComunidades(comunidad);
            this.generatedCto.setModificado(true);
        }
    }
  
    public void modCtoNombre (String anterior, String nuevo, boolean importat)
    {      
        if(importat)this.importedCto.getComunidad(anterior).setNombre(nuevo);
        else this.generatedCto.getComunidad(anterior).setNombre(nuevo);
    }
    
    public ArrayList<String> mostrarCtoComunidad(boolean importat)
    {
        if(importat) return this.importedCto.getNameComunidades();
        else return this.generatedCto.getNameComunidades();
    }
        
    public ArrayList<String> mostrarComunidad(String comunidad, boolean importat)
    {
        if(importat) return this.importedCto.getComunidad(comunidad).getNameCategories();
        else return this.generatedCto.getComunidad(comunidad).getNameCategories();
    }
    
    public void saveCtoComunidad(String path, boolean importado)
    {
        if(!importado)
        {
            if(path == null || this.generatedCto == null || path.isEmpty()) return;
            this.generatedCto.savetoFile();
            this.ctrData.setAlgorithmPath(path);
            this.ctrData.writeCtoComunidad(this.generatedCto);
        }
        else
        {
            if(path == null || this.importedCto == null || path.isEmpty()) return;
            this.importedCto.savetoFile();
            this.ctrData.setAlgorithmPath(path);
            this.ctrData.writeCtoComunidad(this.importedCto);
        }        
    }
    
    public void loadCtoComunidad(String path)
    {
        if(path == null || path.isEmpty()) return;
        this.importedCto = new CtoComunidad();
        this.ctrData.setAlgorithmPath(path);
        this.ctrData.readCtoComunidad(this.importedCto);
    }
 
    public int numCatCom (String comunidad, boolean importado)
    {
        if(importado) return importedCto.getComunidad(comunidad).getNumCategorias();
        return generatedCto.getComunidad(comunidad).getNumCategorias();
    }
    public ArrayList<String> commonCategories(String com1, boolean importado1, String com2, boolean importado2)
    {
        Comunidad c1 = new Comunidad();
        Comunidad c2 = new Comunidad();
        if(importado1)c1 = this.importedCto.getComunidad(com1);
        else c1 = this.generatedCto.getComunidad(com1);
        if(importado2)c2 = this.importedCto.getComunidad(com2);
        else c2 = this.generatedCto.getComunidad(com2);
        ArrayList<String> comparacion = new ArrayList();
        ArrayList<String> comunidad1 = c1.getNameCategories();
        ArrayList<String> comunidad2 = c2.getNameCategories();
        for(String i: comunidad1)
            if(comunidad2.contains(i))comparacion.add(i);
        return comparacion;
    }
    
    public double getPorcentaje(String comunidad, boolean importado)
    {
        Integer com, conj;
        if(importado)
        {
            com = this.importedCto.getComunidad(comunidad).getNumCategorias();
            conj = this.importedCto.getSelections().getCategoriesSelected().size();
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad).getNumCategorias();
            conj = this.generatedCto.getSelections().getCategoriesSelected().size();
        }
        return (double)((com/conj)*100);
    }
    
    public int[] infoConjunto(boolean imported)
    {
        int[] info = new int[10];
        if(imported)
        {
            info[0] = this.importedCto.getNumComunidades();
            info[1] = this.importedCto.getAlgortimo();
            info[2] = this.importedCto.getP();
            info[3] =  this.importedCto.getFiltros().getPname();
            info[4] =  this.importedCto.getFiltros().getPcat();
            info[5] =  this.importedCto.getFiltros().getPpag();
            info[6] = this.importedCto.getFiltros().getPfat();
            info[7] =  this.importedCto.getFiltros().getPson();
            info[8] =  this.importedCto.getSelections().getCategoriesSelected().size();
            info[9] =  this.importedCto.getSelections().getPagesSelected().size();
        }
        else
        {
            info[0] = this.generatedCto.getNumComunidades();
            info[1] = this.generatedCto.getAlgortimo();
            info[2] = this.generatedCto.getP();
            info[3] =  this.generatedCto.getFiltros().getPname();
            info[4] =  this.generatedCto.getFiltros().getPcat();
            info[5] =  this.generatedCto.getFiltros().getPpag();
            info[6] = this.generatedCto.getFiltros().getPfat();
            info[7] =  this.generatedCto.getFiltros().getPson();
            info[8] =  this.generatedCto.getSelections().getCategoriesSelected().size();
            info[9] =  this.generatedCto.getSelections().getPagesSelected().size();
        }
        return info;
    }
    
    public double getTexec (boolean imported)
    {
        if(imported)return this.importedCto.getTimeExecution();
        return this.generatedCto.getTimeExecution();
    }
    
    public double getPurityOne(boolean imported)
    {
        if(imported)return this.p.calcPurity(this.importedCto);
        return  this.p.calcPurity(this.generatedCto);
    }
    public double getPurityBoth()
    {
        return this.p.calcPurity(this.importedCto,this.generatedCto);
    }
    
    public boolean existsCjto(boolean imported)
    {
        if(imported)
        {
            if(this.importedCto != null) return !this.importedCto.isEmpty();
            return false;            
        }
        if(this.generatedCto != null) return !this.generatedCto.isEmpty();
        return false; 
    }
}
