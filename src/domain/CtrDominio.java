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
import java.util.Map.Entry;
import optional.Visual;
import shared.Graph;

/**
 * Controlador encargado de gestionar los datos y logica de la capa de dominio
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class CtrDominio
{
    private GrafoEntrada g = null;
    private CtrData ctrData = null;
    private Graph<Integer, Double> graph = null;
    private CtoComunidad generatedCto = null;
    private CtoComunidad importedCto = null;
    private Purity p = null;

    /**
     * Constructor de la clase CtrDominio
     */
    public CtrDominio()
    {
        this.g = new GrafoEntrada();
        this.ctrData = new CtrData();
        this.p = new Purity();
    }

    /**
     * Crea un nuevo GrafoEntrada sobrescribiendo el anterior y posteriormente
     * llama al GarbageCollector de Java para reducir la memória usada.
     */
    public void newGrafo()
    {
        this.g = new GrafoEntrada();
        System.gc();
    }

    /**
     * Funcion experimental que printa por pantalla una visualizacion del grafo
     * de entrada empleando una libreria de dibujo
     */
    public void windowGraphEntry()
    {
        if(this.g == null)
        {
            return;
        }
        ArrayList<Integer> nodes = new ArrayList();
        nodes.addAll(this.g.getCategories());
        nodes.addAll(this.g.getPages());
        ArrayList<ArrayList<Integer>> edges = new ArrayList();
        for(Entry<Integer, ArrayList<Arch>> entry : this.g.getCsubCArch().entrySet())
        {
            ArrayList<Integer> arcs = new ArrayList();
            for(Arch arc : entry.getValue())
            {
                arcs.add(arc.getDestiny());
            }
            edges.add(arcs);
        }
        Visual v = new Visual(nodes, edges, 2, Color.WHITE);
        v.launchWindow();
    }

    /**
     * Funcion experimental que printa por pantalla una visualizacion del grafo
     * del algoritmo empleando una libreria de dibujo
     */
    public void windowGraphAlgorithm()
    {
        if(this.graph == null)
        {
            return;
        }
        ArrayList<ArrayList<Integer>> edges = new ArrayList();
        for(Integer origin : this.graph.getVertexs())
        {
            ArrayList<Integer> arcs = new ArrayList();
            for(Integer destiny : this.graph.getNeighbors(origin))
            {
                arcs.add(destiny);
            }
            edges.add(arcs);
        }
        Visual v = new Visual(this.graph.getVertexs(), edges, 2, Color.WHITE);
        v.launchWindow();
    }

    /**
     * Obtiene el grafo de entrada
     *
     * @return grafo de entrada
     */
    public GrafoEntrada getGrafo()
    {
        return this.g;
    }

    /**
     * Dado un arco obtiene la informacionn relativa a los nodos y genera una
     * cadena de texto con la informacion
     *
     * @param arc
     * @return cadena de texto con toda la informacion del arco
     */
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
        return nodeA + "   " + typeA + "   " + typeArch + "   " + nodeB + "   " + typeB;
    }

    /**
     * Fija un rango y devuelve una lista de categorias que pertenecen al dicho
     * rango
     *
     * @param min entero con la posicion minima
     * @param max entero con la posicion maxima
     * @return lista de todas las categorias entre las dos posiciones
     */
    public ArrayList<Integer> getCatSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getCategorySize(), tam2;
        for(int i = 0; i < tam; ++i)
        {
            tam2 = this.g.getCategoryArch(this.g.getCategories().get(i)).size();
            if(tam2 >= min && tam2 <= max)
            {
                selection.add(this.g.getCategories().get(i));
            }
        }
        return selection;
    }

    /**
     * Fija un rango y devuelve una lista de paginas que pertenecen al dicho
     * rango
     *
     * @param min entero con la posicion minima
     * @param max entero con la posicion maxima
     * @return lista de todas las paginas entre las dos posiciones
     */
    public ArrayList<Integer> getPagSelection(int min, int max)
    {
        ArrayList<Integer> selection = new ArrayList();
        int tam = this.g.getPageSize(), tam2;
        for(int i = 0; i < tam; ++i)
        {
            tam2 = this.g.getPageArch(this.g.getPages().get(i)).size();
            if(tam2 >= min && tam2 <= max)
            {
                selection.add(this.g.getPages().get(i));
            }
        }
        return selection;
    }

    /**
     * Guarda el grafo del algoritmo dentro del controlador de dominio
     *
     * @param graph grafo del algoritmo
     */
    public void setAlgorithmGraph(Graph<Integer, Double> graph)
    {
        this.graph = graph;
    }

    /**
     * Obtiene el grafo del algoritmo
     *
     * @return grafo del algoritmo
     */
    public Graph<Integer, Double> getAlgorithmGraph()
    {
        return this.graph;
    }

    /**
     * Verifica si existe un grafo de algoritmo
     *
     * @return true si existe
     */
    public boolean isAlgorithmGraph()
    {
        return (this.graph != null);
    }

    /**
     * Verifica si el grafo de entrada esta vacio
     *
     * @return true si el grafo de entrada se encuentra vacio
     */
    public boolean isGraphEmpty()
    {
        return this.g.getCategorySize() == 0 && this.g.getPageSize() == 0 && this.g.getNumberEdges() == 0;
    }

    /**
     * Guarda el conjunto de comunidades en el controlador de dominio
     *
     * @param cto conjunto de comunidad
     */
    public void setGeneratedCto(CtoComunidad cto)
    {
        this.generatedCto = cto;
    }

    /**
     * Permite recuperar una lista con los nombres de las paginas presentes en
     * el grafo de entrada
     *
     * @return lista de los nombres de las paginas del grafo de entrada
     */
    public ArrayList<String> verPagGeneral()
    {
        ArrayList<String> response = new ArrayList();
        for(Integer i : this.g.getPages())
        {
            response.add(this.g.getNumberNamePage(i));
        }
        return response;
    }

    /**
     * Permite recuperar una lista con los nombres de las categorias presentes
     * en el grafo de entrada
     *
     * @return lista de los nombres de las categorias del grafo de entrada
     */
    public ArrayList<String> verCatGeneral()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Integer i : this.g.getCategories())
        {
            response.add(this.g.getNumberNameCategory(i));
        }
        return response;
    }

    /**
     * Permite recuperar una lista con los arcos presentes en el grafo de
     * entrada
     *
     * @return lista de los nombres de los arcos del grafo de entrada
     */
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

    /**
     * Permite obtener la lista de enlaces que tiene un determinado nodo
     *
     * @param category discrimina si es categoria o no (true == categoria)
     * @param name nombre para localizar el nodo
     * @return lista de enlaces del nodo solicitado
     */
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

    /**
     * Obtiene una lista con todos los indices de la paginas
     *
     * @return lista de paginas
     */
    public ArrayList<Integer> getNumPagGeneral()
    {
        return this.g.getPages();
    }

    /**
     * Obtiene una lista con todos los indices de la categorias
     *
     * @return lista de categorias
     */
    public ArrayList<Integer> getNumCatGeneral()
    {
        return this.g.getCategories();
    }

    /**
     * Traduce el id de pagina por el nombre de la misma
     *
     * @param page entero con el indice de pagina
     * @return string con el nombre de la pagina
     */
    public String verPag(Integer page)
    {
        return this.g.getNumberNamePage(page);
    }

    /**
     * Traduce el id de una categoria por el nombre de la misma
     *
     * @param cat entero con el indice de categoria
     * @return string con el nombre de la categoria
     */
    public String verCat(Integer cat)
    {
        return this.g.getNumberNameCategory(cat);
    }

    /**
     * Traduce un nombre de pagina al indice correspondiente
     *
     * @param pagina nombre de la pagina
     * @return indice de la pagina
     */
    public Integer verNumPag(String pagina)
    {
        return g.getPageNumber(new Pagina(pagina));
    }

    /**
     * Traduce un nombre de categoria al indice correspondiente
     *
     * @param category nombre de la categoria
     * @return indice de la categoria
     */
    public Integer verNumCat(String category)
    {
        return this.g.getCategoryNumber(new Categoria(category));
    }

    /**
     * Traduce un nombre de categoria al indice correspondiente
     *
     * @param category nombre de la categoria
     * @return indice de la categoria
     */
    public Integer verNumCat(Integer category)
    {
        return this.g.getCategoryNumber(this.g.getNumberCategory(category));
    }

    /**
     * Crea una nueva categoria y retorna el indice de la misma
     *
     * @param category nombre de la categoria
     * @return indice de la categoria
     */
    public Integer addGrafoCat(String category)
    {
        Categoria c = new Categoria(category);
        if(!this.g.addCategoria(c))
        {
            return -1;
        }
        return this.g.getCategoryNumber(c);
    }

    /**
     * Añade una pagina al grafo.
     *
     * @param pagina nombre de la pagina
     * @return indice de la pagina
     */
    public Integer addGrafoPag(String pagina)
    {
        Pagina p = new Pagina(pagina);
        if(!this.g.addPagina(p))
        {
            return -1;
        }
        return this.g.getPageNumber(p);
    }

    /**
     * Añade un enlace al grafo.
     *
     * @param node1 nombre del primer nodo
     * @param node2 nombre del segundo nodo
     * @param tipus tipo de arco
     * @return true si se ha podido realizar la acción
     */
    public boolean addGrafoEnlace(String node1, String node2, String tipus)
    {
        int origen = -1;
        int destino = -1;
        Categoria c1 = new Categoria(node1);
        Categoria c2 = new Categoria(node2);
        switch(tipus)
        {
            case ("CsubC"):
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                break;
            case ("CsupC"):
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getCategoryNumber(c2);
                break;
            case ("CP"):
                Pagina p1 = new Pagina(node2);
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getPageNumber(p1);
                break;
            case ("PC"):
                Pagina p2 = new Pagina(node1);
                origen = this.g.getPageNumber(p2);
                destino = this.g.getCategoryNumber(c2);
                break;
        }
        if(origen == -1 || destino == -1)
        {
            return false;
        }
        this.g.addArch(new Arch(origen, destino, Arch.typeArch.valueOf(tipus)));
        return true;
    }

    /**
     * Borra una categoria del grafo.
     *
     * @param category nombre de la categoria
     * @return indice de la categoria eliminada
     */
    public Integer rmvGrafoCat(String category)
    {
        Categoria c = new Categoria(category);
        Integer r = this.g.getCategoryNumber(c);
        if(r != -1)
        {
            this.g.removeCategoria(c);
        }
        return r;
    }

    /**
     * Borra una pagina del grafo.
     *
     * @param pagina nombre de la pagina
     * @return indice de la pagina
     */
    public Integer rmvGrafoPag(String pagina)
    {
        Pagina p = new Pagina(pagina);
        Integer r = this.g.getPageNumber(p);
        if(r != -1)
        {
            this.g.removePagina(p);
        }
        return r;// ha de retornar la pos de la pagina
    }

    /**
     * Borra un enlace del grafo.
     *
     * @param node1 nombre del primer nodo
     * @param node2 nombre del segundo nodo
     * @param tipus tipo de arco
     * @return true si ha tenido éxito el borrado
     */
    public boolean rmvGrafoEnlace(String node1, String node2, String tipus)
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
                Pagina p1 = new Pagina(node1);
                origen = this.g.getPageNumber(p1);
                destino = this.g.getCategoryNumber(c2);
                this.g.removeArchPageCategory(origen, destino);
                break;
            case "CP":
                Pagina p2 = new Pagina(node2);
                origen = this.g.getCategoryNumber(c1);
                destino = this.g.getPageNumber(p2);
                this.g.removeArchCategoryPage(origen, destino);
                break;
        }
        return (origen != -1 && destino != -1);
    }

    /**
     * Cambia el nombre de una pagina
     *
     * @param page nombre de la pagina original
     * @param change nombre nuevo para la pagina
     * @return true si se ha podido aplicar
     */
    public boolean modifyPage(String page, String change)
    {
        Pagina p = new Pagina(change);
        if(this.g.getPageNumber(p) != -1)
        {
            return false;
        }
        else
        {
            this.g.changePage(this.g.getPageNumber(new Pagina(page)), change);
        }
        return true;
    }

    /**
     * Cambia el nombre de una categoria
     *
     * @param category nombre de la pagina original
     * @param change nombre nuevo para la categoria
     * @return true si se ha podido aplicar
     */
    public boolean modifyCategory(String category, String change)
    {
        Categoria c = new Categoria(change);
        if(this.g.getCategoryNumber(c) != -1)
        {
            return false;
        }
        this.g.changeCategory(this.g.getCategoryNumber(new Categoria(category)), change);
        return true;
    }

    /**
     * Añade al grafo de entrada lo que hay en el fichero seleccionado por el
     * path
     *
     * @param path ruta absoluta a un fichero para leerlo
     */
    public void readEntryGraphFile(String path)
    {
        this.ctrData.setEntryPath(path);
        this.ctrData.readEntryGraphFile(this.g);
    }

    /**
     * Permite leer las diferentes entradas de una linea de fichero y rellenar
     * el grafo de entrada con información nueva
     *
     * @param nA nombre del nodo A
     * @param tA tipo de nodo
     * @param tArch tipo de arco
     * @param nB nombre del nodo B
     * @param tB tipo de nodo
     */
    public void addToEntryGraph(String nA, String tA, String tArch, String nB, String tB)
    {
        this.g.setData(nA, tA, tArch, nB, tB);
    }

    /**
     * Guarda el grafo de entrada en un fichero de texto especificado en el path
     * si no existe lo crea
     *
     * @param path ruta absoluta del fichero
     * @return true si se ha podido completar con exito
     */
    public boolean saveEntryGraph(String path)
    {
        if(path == null || path.isEmpty())
        {
            return false;
        }
        this.ctrData.setEntryPath(path);
        return this.ctrData.writeEntryGraphFile(this.g);
    }

    /**
     * Añade una categoria en una comunidad
     *
     * @param category nombre de la categoria
     * @param comunidad nombre de la comunidad
     * @param importat true si es importado
     * @return true si se ha podido completar
     */
    public boolean addCtoCat(String category, String comunidad, boolean importat)
    {
        Comunidad com;
        boolean e;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)
            {
                return false;
            }
            e = com.addCategoria(category);
            this.importedCto.setModificado(true);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)
            {
                return false;
            }
            e = com.addCategoria(category);
            this.generatedCto.setModificado(true);
        }
        return e;
    }

    /**
     * Añade una comunidad a un conjunto de comunidad
     *
     * @param comunidad nombre de la comunidad
     * @param importat true si es importado
     * @return true si se ha podido completar
     */
    public boolean addCtoCom(String comunidad, boolean importat)
    {
        Comunidad com = new Comunidad();
        boolean e;
        com.setNombre(comunidad);
        if(importat)
        {
            e = this.importedCto.addComunidades(com);
            this.importedCto.setModificado(true);
        }
        else
        {
            e = this.generatedCto.addComunidades(com);
            this.generatedCto.setModificado(true);
        }
        return e;
    }

    /**
     * Elimina una categoria de una comunidad
     *
     * @param category nombre de la categoria
     * @param comunidad nombre de la comunidad
     * @param importat true si es importado
     * @return true si se ha completado
     */
    public boolean rmvCtoCat(String category, String comunidad, boolean importat)
    {
        Comunidad com;
        boolean e;
        if(importat)
        {
            com = this.importedCto.getComunidad(comunidad);
            if(com == null)
            {
                return false;
            }
            e = com.removeCategoria(category);
            this.importedCto.setModificado(true);
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad);
            if(com == null)
            {
                return false;
            }
            e = com.removeCategoria(category);
            this.generatedCto.setModificado(true);
        }
        return e;
    }

    /**
     * Elimina una comunidad de un conjunto de comunidades
     *
     * @param comunidad nombre de la comunidad
     * @param importat true si se ha importado
     * @return indice del elemento eliminado
     */
    public int rmvCtoCom(String comunidad, boolean importat)
    {
        int e;
        if(importat)
        {
            e = this.importedCto.removeComunidades(comunidad);
            this.importedCto.setModificado(true);
        }
        else
        {
            e = this.generatedCto.removeComunidades(comunidad);
            this.generatedCto.setModificado(true);
        }
        return e;
    }

    /**
     * Modifica el nombre de una comunidad
     *
     * @param anterior nombre anterior
     * @param nuevo nombre nuevo
     * @param importat true si es importado
     * @return true si se ha completado
     */
    public boolean modCtoNombre(String anterior, String nuevo, boolean importat)
    {
        boolean e1 = true;
        boolean e2 = false;
        if(importat)
        {
            for(Comunidad community : this.importedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(anterior))
                {
                    e2 = true;
                }
                if(community.getNombre().equals(nuevo))
                {
                    e1 = false;
                }
            }
            if(e1 && e2)
            {
                this.importedCto.getComunidad(anterior).setNombre(nuevo);
                return true;
            }
        }
        else
        {
            for(Comunidad community : this.generatedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(anterior))
                {
                    e2 = true;
                }
                if(community.getNombre().equals(nuevo))
                {
                    e1 = false;
                }
            }
            if(e1 && e2)
            {
                this.generatedCto.getComunidad(anterior).setNombre(nuevo);
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene toda la lista de elementos que forman una comunidad
     *
     * @param importat true si es importado
     * @return lista de todos las comunidades
     */
    public ArrayList<String> mostrarCtoComunidad(boolean importat)
    {
        if(importat)
        {
            return this.importedCto.getNameComunidades();
        }
        else
        {
            return this.generatedCto.getNameComunidades();
        }
    }

    /**
     * Obtiene la lista de lista de los elementos que pertenecen a una comunidad
     *
     * @param comunidad nombre de la comunidad
     * @param importat true si es importado
     * @return lista de nombre de categorias de una comunidad
     */
    public ArrayList<String> mostrarComunidad(String comunidad, boolean importat)
    {
        boolean e = false;
        ArrayList<String> s = new ArrayList();
        if(importat)
        {
            for(Comunidad community : this.importedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(comunidad))
                {
                    e = true;
                    break;
                }
            }
            if(e)
            {
                s = this.importedCto.getComunidad(comunidad).getNameCategories();
            }
            else
            {
                s = null;
            }
        }
        else
        {
            for(Comunidad community : this.generatedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(comunidad))
                {
                    e = true;
                    break;
                }
            }
            if(e)
            {
                s = this.generatedCto.getComunidad(comunidad).getNameCategories();
            }
            else
            {
                s = null;
            }
        }
        return s;
    }

    /**
     * Guarda un conjunto de comunidades en un fichero
     *
     * @param path ruta absoluta donde se desea almacenar
     * @param importado true si es importado
     */
    public void saveCtoComunidad(String path, boolean importado)
    {
        if(!importado)
        {
            if(path == null || this.generatedCto == null || path.isEmpty())
            {
                return;
            }
            this.generatedCto.savetoFile();
            this.ctrData.setAlgorithmPath(path);
            this.ctrData.writeCtoComunidad(this.generatedCto);
        }
        else
        {
            if(path == null || this.importedCto == null || path.isEmpty())
            {
                return;
            }
            this.importedCto.savetoFile();
            this.ctrData.setAlgorithmPath(path);
            this.ctrData.writeCtoComunidad(this.importedCto);
        }
    }

    /**
     * Permite leer de un fichero el contenido para un conjunto de comunidad
     *
     * @param path ruta absoluta al fichero
     */
    public void loadCtoComunidad(String path)
    {
        if(path == null || path.isEmpty())
        {
            return;
        }
        this.importedCto = new CtoComunidad();
        this.ctrData.setAlgorithmPath(path);
        this.ctrData.readCtoComunidad(this.importedCto);
    }

    /**
     * Obtiene el numero de categorias en una comunidad
     *
     * @param comunidad nombre de la comunidad
     * @param importado true si es importado
     * @return total de categorias en una comunidad
     */
    public int numCatCom(String comunidad, boolean importado)
    {
        if(importado)
        {
            return importedCto.getComunidad(comunidad).getNumCategorias();
        }
        return generatedCto.getComunidad(comunidad).getNumCategorias();
    }

    /**
     * Obtiene las categorias comunes
     *
     * @param com1 nombre de la comunidad 1
     * @param importado1 true si es importado
     * @param com2 nombre de la comunidad 2
     * @param importado2 true si es importado
     * @return lista de categorias en comun
     */
    public ArrayList<String> commonCategories(String com1, boolean importado1, String com2, boolean importado2)
    {
        Comunidad c1, c2;
        ArrayList<String> comparacion = new ArrayList();
        if(importado1)
        {
            c1 = this.importedCto.getComunidad(com1);
        }
        else
        {
            c1 = this.generatedCto.getComunidad(com1);
        }
        if(importado2)
        {
            c2 = this.importedCto.getComunidad(com2);
        }
        else
        {
            c2 = this.generatedCto.getComunidad(com2);
        }
        if(c1 != null && c2 != null)
        {
            ArrayList<String> comunidad1 = c1.getNameCategories();
            ArrayList<String> comunidad2 = c2.getNameCategories();
            for(String i : comunidad1)
            {
                if(comunidad2.contains(i))
                {
                    comparacion.add(i);
                }
            }
        }
        return comparacion;
    }

    /**
     * Obtiene el porcentaje de categorias de una comunidad frente al total de
     * las comunidades que se han seleccionado
     *
     * @param comunidad nombre de la comunidad
     * @param importado true si es importado
     * @return porcentaje
     */
    public double getPorcentaje(String comunidad, boolean importado)
    {
        int com, conj = 0;
        if(importado)
        {
            com = this.importedCto.getComunidad(comunidad).getNumCategorias();
            if(!this.importedCto.isModificado())
            {
                conj = this.importedCto.getSelections().getCategoriesSelected().size();
            }
            else
            {
                for(int i = 0; i < this.importedCto.getNumComunidades(); ++i)
                {
                    conj += this.importedCto.getCtoComunidades().get(i).getNumCategorias();
                }
            }
        }
        else
        {
            com = this.generatedCto.getComunidad(comunidad).getNumCategorias();
            if(!this.generatedCto.isModificado())
            {
                conj = this.generatedCto.getSelections().getCategoriesSelected().size();
            }
            else
            {
                for(int i = 0; i < this.generatedCto.getNumComunidades(); ++i)
                {
                    conj += this.generatedCto.getCtoComunidades().get(i).getNumCategorias();
                }
            }
        }
        double salida = 100 * ((double) com / (double) conj);
        long factor = (long) Math.pow(10, 2);
        salida = salida * factor;
        long tmp = Math.round(salida);
        return (double) tmp / factor;
    }

    /**
     * Obtiene toda la información sobre un conjunto
     *
     * @param imported true si es importado
     * @return vector de datos sobre un conjunto
     */
    public int[] infoConjunto(boolean imported)
    {
        int[] info = new int[10];
        if(imported)
        {
            info[0] = this.importedCto.getNumComunidades();
            info[1] = this.importedCto.getAlgortimo();
            info[2] = this.importedCto.getP();
            info[3] = this.importedCto.getFiltros().getPname();
            info[4] = this.importedCto.getFiltros().getPcat();
            info[5] = this.importedCto.getFiltros().getPpag();
            info[6] = this.importedCto.getFiltros().getPfat();
            info[7] = this.importedCto.getFiltros().getPson();
            info[8] = this.importedCto.getSelections().getCategoriesSelected().size();
            info[9] = this.importedCto.getSelections().getPagesSelected().size();
        }
        else
        {
            info[0] = this.generatedCto.getNumComunidades();
            info[1] = this.generatedCto.getAlgortimo();
            info[2] = this.generatedCto.getP();
            info[3] = this.generatedCto.getFiltros().getPname();
            info[4] = this.generatedCto.getFiltros().getPcat();
            info[5] = this.generatedCto.getFiltros().getPpag();
            info[6] = this.generatedCto.getFiltros().getPfat();
            info[7] = this.generatedCto.getFiltros().getPson();
            info[8] = this.generatedCto.getSelections().getCategoriesSelected().size();
            info[9] = this.generatedCto.getSelections().getPagesSelected().size();
        }
        return info;
    }

    /**
     * Obtiene el tiempo de ejecución
     *
     * @param imported true si es importado
     * @return tiempo de ejecución
     */
    public double getTexec(boolean imported)
    {
        if(imported)
        {
            return this.importedCto.getTimeExecution();
        }
        return this.generatedCto.getTimeExecution();
    }

    /**
     * Verifica si se ha modificado un conjunto de comunidades
     *
     * @param imported true si es importado
     * @return true si hay modificacion
     */
    public boolean isCtoModified(boolean imported)
    {
        if(imported)
        {
            return this.importedCto.isModificado();
        }
        return this.generatedCto.isModificado();
    }

    /**
     * Calcula la pureza de todo un conjunto
     *
     * @param imported true si es importado
     * @return valor de pureza
     */
    public double getAllPurityOne(boolean imported)
    {
        if(imported)
        {
            return this.p.calcPurity(this.importedCto);
        }
        return this.p.calcPurity(this.generatedCto);
    }

    /**
     * Obtiene la pureza del conjunto generado como del conjunto importado
     *
     * @return pureza de los dos conjuntos
     */
    public double getAllPurityBoth()
    {
        return this.p.calcPurity(this.importedCto, this.generatedCto);
    }

    /**
     * Obtiene la pureza de un conjunto de comunidades bajo otro criterio
     *
     * @param imported true si es importado
     * @return valor de pureza
     */
    public double getPurityOne(boolean imported)
    {
        if(imported)
        {
            return this.p.calcPurityTwo(this.importedCto);
        }
        return this.p.calcPurityTwo(this.generatedCto);
    }

    /**
     * Obtiene la pureza del conjunto generado como del conjunto importado bajo
     * otro criterio
     *
     * @return pureza de los dos conjuntos
     */
    public double getPurityBoth()
    {
        return this.p.calcPurityTwo(this.importedCto, this.generatedCto);
    }

    /**
     * Verifica si existe un conjunto de comunidades
     *
     * @param imported true si es importado
     * @return
     */
    public boolean existsCjto(boolean imported)
    {
        if(imported)
        {
            if(this.importedCto != null)
            {
                return !this.importedCto.isEmpty();
            }
            return false;
        }
        if(this.generatedCto != null)
        {
            return !this.generatedCto.isEmpty();
        }
        return false;
    }

    /**
     * Verifica si existe una comunidad
     *
     * @param comunidad nombre de la comunidad
     * @param imported true si es importado
     * @return true si existe
     */
    public boolean existsComunidad(String comunidad, boolean imported)
    {
        if(imported)
        {
            for(Comunidad community : this.importedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(comunidad))
                {
                    return true;
                }
            }
        }
        else
        {
            for(Comunidad community : this.generatedCto.getCtoComunidades())
            {
                if(community.getNombre().equals(comunidad))
                {
                    return true;
                }
            }
        }
        return false;
    }
}