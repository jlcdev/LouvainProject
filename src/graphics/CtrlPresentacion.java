package graphics;

import domain.CtrDominio;
import domain.CtrAlgoritmo;
import java.util.*;
import shared.Graph;

/**
 * Clase encargada de gestionar todos los elementos de la vista y de realizar
 * las peticiones a los controladores inferiores si fuera necesario
 * 
 * @author Alfred Parellada Rodriguez
 * @version 1.0
 * @since 01/06/2015
 */
public class CtrlPresentacion
{
    private final CtrDominio ctrlDominio;
    private final CtrAlgoritmo ctrlAlgoritmo;
    private VistaPrincipal vistaPrincipal = null;    // (= null) innecesario
    private VistaAbout vistaAbout = null;  // (= null) innecesario
    private VistaError vistaError = null;
    private VistaFileChooser vistaFileChooser = null;
    private VistaManual vistaManual = null;

//////////////////////// Constructor y metodos de inicializacion
    /**
     * Constructor por defecto
     */
    public CtrlPresentacion()
    {
        this.ctrlDominio = new CtrDominio();
        this.ctrlAlgoritmo = new CtrAlgoritmo();
        if(this.vistaPrincipal == null)  // innecesario
        {
            this.vistaPrincipal = new VistaPrincipal(this);
        }
    }

    /**
     * Inicializa la vista principal
     */
    public void inicializarPresentacion()
    {
        //ctrlDominio.inicializarCtrDominio();
        this.vistaPrincipal.hacerVisible();
    }

//////////////////////// Metodos de sincronizacion entre vistas
    /**
     * Muestra el selector de ficheros y bloquea la vista principal
     *
     * @param importar
     * @param grafo
     * @param importado
     */
    public void sincronizacionVistaPrincipal_a_FileChooser(boolean importar, boolean grafo, boolean importado)
    {
        this.vistaPrincipal.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if(this.vistaFileChooser == null)
        {
            this.vistaFileChooser = new VistaFileChooser(this);
        }
        //tipus
        this.vistaFileChooser.hacerVisible(importar, grafo, importado);
    }

    /**
     * Muestra el manual y bloquea la vista principal
     */
    public void sincronizacionVistaPrincipal_a_Manual()
    {
        this.vistaPrincipal.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if(this.vistaManual == null)
        {
            this.vistaManual = new VistaManual(this);
        }
        this.vistaManual.hacerVisible();
    }

    /**
     * Muestra la vista About y bloquea la vista principal
     */
    public void sincronizacionVistaPrincipal_a_About()
    {
        this.vistaPrincipal.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if(this.vistaAbout == null)
        {
            this.vistaAbout = new VistaAbout(this);
        }
        this.vistaAbout.hacerVisible();
    }

    /**
     * Muestra la vista de errores y bloquea la vista principal
     *
     * @param msg mensaje a mostrar
     */
    public void sincronizacionVistaPrincipal_a_Error(String msg)
    {
        this.vistaPrincipal.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if(this.vistaError == null)
        {
            this.vistaError = new VistaError(this);
        }
        this.vistaError.setText(msg);
        this.vistaError.hacerVisible();
    }

    /**
     * Desbloquea la vista principal y hace invisible el filechooser
     */
    public void sincronizacionVistaFileChooser_a_Principal()
    {
        // Se hace invisible la vista secundaria (podria anularse)
        this.vistaFileChooser.hacerInvisible();
        this.vistaPrincipal.activar();
    }

    /**
     * Desbloquea la vista principal y hace invisible el manual
     */
    public void sincronizacionVistaManual_a_Principal()
    {
        // Se hace invisible la vista secundaria (podria anularse)
        this.vistaManual.hacerInvisible();
        this.vistaPrincipal.activar();
    }

    /**
     * Desbloquea la vista principal y hace invisible la vista About
     */
    public void sincronizacionVistaAbout_a_Principal()
    {
        // Se hace invisible la vista secundaria (podria anularse)
        this.vistaAbout.hacerInvisible();
        this.vistaPrincipal.activar();
    }

    /**
     * Desbloquea la vista principal y hace invisible la vista de errores
     */
    public void sincronizacionVistaError_a_Principal()
    {
        // Se hace invisible la vista secundaria (podria anularse)
        this.vistaError.hacerInvisible();
        this.vistaPrincipal.activar();
    }

//////////////////////// Llamadas al controlador de dominio
    /**
     * Crea un nuevo grafo vacío
     */
    public void crearGrafo()
    {
        this.ctrlDominio.newGrafo();
        this.vistaPrincipal.activarTab(1); //GRAFO
        this.vistaPrincipal.activarTab(2); //ALGORITMO
        this.vistaPrincipal.goToTab(1);
    }

    /**
     * Importa un grafo a través de un fichero de texto.
     *
     * @param path ruta absoluta al fichero
     */
    public void importarGrafo(String path)
    {
        try
        {
            this.ctrlDominio.readEntryGraphFile(path);
            this.vistaPrincipal.actualizarCat();
            this.vistaPrincipal.actualizarPag();
            this.vistaPrincipal.actualizarLinks();
            this.vistaPrincipal.activarTab(1); //GRAFO
            this.vistaPrincipal.activarTab(2); //ALGORITMO
            this.vistaPrincipal.goToTab(1);
        }
        catch(Exception e)
        {
            this.sincronizacionVistaPrincipal_a_Error("Fichero con formato incorrecto");
        }
    }

    /**
     * Importa un conjunto de comunidades a través de un fichero de texto.
     *
     * @param path ruta absoluta al fichero
     */
    public void importarConjunto(String path)
    {
        try
        {
            this.ctrlDominio.loadCtoComunidad(path);
            this.vistaPrincipal.actualizarSet(true);
            this.vistaPrincipal.activarTab(3); //CONJ
            this.vistaPrincipal.activarTab(4); //COMP
            this.vistaPrincipal.goToTab(3);
            this.vistaPrincipal.updateCjtos(true);
        }
        catch(Exception e)
        {
            this.sincronizacionVistaPrincipal_a_Error("Fichero con formato incorrecto");
        }
    }

    /**
     * Exporta el grafo cargado en el programa.
     *
     * @param path ruta absoluta al fichero
     */
    public void exportarGrafo(String path)
    {
        boolean error;
        error = this.ctrlDominio.saveEntryGraph(path);
        if(!error)
        {
            sincronizacionVistaPrincipal_a_Error("El path esta vacío");
        }
    }

    /**
     * Exporta un conjunto de comunidades cargado en el programa.
     *
     * @param path ruta absoluta al fichero
     * @param importado true si es importado
     */
    public void exportarConjunto(String path, boolean importado)
    {
        this.ctrlDominio.saveCtoComunidad(path, importado);
    }
    //PESTAÑA GRAFO

    /**
     * Mira si el grafo está vacío
     *
     * @return true si el grafo está vacio
     */
    public boolean isGraphEmpty()
    {
        return this.ctrlDominio.isGraphEmpty();
    }

    /**
     * 
     * @param pagina
     * @return
     */
    public int getPagNum(String pagina)
    {
        int r = this.ctrlDominio.verNumPag(pagina);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La pagina no existe");
        return r;
    }

    /**
     *
     * @param categoria
     * @return
     */
    public int getCatNum(String categoria)
    {
        int r = this.ctrlDominio.verNumCat(categoria);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La categoria no existe");
        return r;
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public ArrayList<Integer> getCatSelection(int min, int max)
    {
        if(max < min)
        {
            sincronizacionVistaPrincipal_a_Error("max < min");
        }
        return this.ctrlDominio.getCatSelection(min, max);
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public ArrayList<Integer> getPagSelection(int min, int max)
    {
        if(max < min)
        {
            sincronizacionVistaPrincipal_a_Error("max < min");
        }
        return this.ctrlDominio.getPagSelection(min, max);
    }

    /**
     * Añade una categoria al grafo.
     *
     * @param categoria
     * @return
     */
    public int addGrafoCat(String categoria)
    {
        int r = this.ctrlDominio.addGrafoCat(categoria);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La cateogria introducida ya existe.");
        return r;
    }

    /**
     * Añade una pagina al grafo.
     *
     * @param pagina
     * @return
     */
    public int addGrafoPag(String pagina)
    {
        int r = this.ctrlDominio.addGrafoPag(pagina);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La pagina introducida ya existe.");
        return r;
    }

    /**
     * Añade un enlace al grafo.
     *
     * @param node1
     * @param node2
     * @param tipus
     */
    public void addGrafoEnlace(String node1, String node2, String tipus)
    {
        if(!this.ctrlDominio.addGrafoEnlace(node1, node2, tipus))
        {
            sincronizacionVistaPrincipal_a_Error("Datos mal introducidos.");
        }
    }

    /**
     * Borra una categoria del grafo.
     *
     * @param categoria
     * @return
     */
    public int rmvGrafoCat(String categoria)
    {
        int r = this.ctrlDominio.rmvGrafoCat(categoria);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La categoria introducida no existe.");
        return r;
    }

    /**
     * Borra una pagina del grafo.
     *
     * @param pagina
     * @return
     */
    public int rmvGrafoPag(String pagina)
    {
        int r = this.ctrlDominio.rmvGrafoPag(pagina);
        if(r != -1)
        {
            return r;
        }
        sincronizacionVistaPrincipal_a_Error("La pagina introducida no existe.");
        return r;
    }

    /**
     * Borra un enlace del grafo.
     *
     * @param node1
     * @param node2
     * @param tipus
     */
    public void rmvGrafoEnlace(String node1, String node2, String tipus)
    {
        if(!this.ctrlDominio.rmvGrafoEnlace(node1, node2, tipus))
        {
            sincronizacionVistaPrincipal_a_Error("Datos mal introducidos.");
        }
    }

    /**
     * Modifica el nombre de una pagina o categoria del grafo.
     *
     * @param anterior
     * @param nuevo
     * @param category
     * @return
     */
    public Integer modGrafoNombre(String anterior, String nuevo, boolean category)
    {
        int id;
        if(category)
        {
            id = this.ctrlDominio.verNumCat(anterior);
            if(id == -1)
            {
                sincronizacionVistaPrincipal_a_Error("El nodo " + anterior + " no existe");
                return -1;
            }
            boolean error = this.ctrlDominio.modifyCategory(anterior, nuevo);
            if(!error)
            {
                sincronizacionVistaPrincipal_a_Error("El nodo " + nuevo + " ya existe");
                return -1;
            }
        }
        else
        {
            id = this.ctrlDominio.verNumPag(anterior);
            if(id == -1)
            {
                sincronizacionVistaPrincipal_a_Error("El nodo " + anterior + " no existe");
                return -1;
            }
            boolean error = this.ctrlDominio.modifyPage(anterior, nuevo);
            if(!error)
            {
                sincronizacionVistaPrincipal_a_Error("El nodo " + nuevo + " ya existe");
                return -1;
            }
        }
        return id;
    }

    /**
     * Muestra las categorias del grafo.
     *
     * @return
     */
    public ArrayList<String> mostrarGrafoCat()
    {
        return this.ctrlDominio.verCatGeneral();
    }

    /**
     * Muestra las paginas del grafo.
     *
     * @return
     */
    public ArrayList<String> mostrarGrafoPag()
    {
        return this.ctrlDominio.verPagGeneral();
    }

    /**
     * Mostra els enllaços del graf.
     *
     * @return
     */
    public ArrayList<String> mostrarGrafoEnlaces()
    {
        return this.ctrlDominio.verEnlacesGeneral();
    }

    /**
     * Mostra els enllaços d'un node
     *
     * @param category
     * @param name
     * @return
     */
    public ArrayList<String> mostrarGrafoEnlaces(boolean category, String name)
    {
        if(category)
        {
            return this.ctrlDominio.verEnlacesGeneralNode(category, name);
        }
        return this.ctrlDominio.verEnlacesGeneralNode(category, name);
    }

    //pestaña algoritmo
    /**
     *
     * @return
     */
    public Graph<Integer, Double> algorithmGraph()
    {
        Graph<Integer, Double> grafo = this.ctrlAlgoritmo.generate(this.ctrlDominio.getGrafo());
        this.ctrlDominio.setAlgorithmGraph(grafo);
        System.out.println("CtrlPresentacion:Ejecutar:FINAL TRANSFORMACIÓN");
        return grafo;
    }

    /**
     *
     * @param algoritmo
     * @param p
     */
    public void ejecutar(int algoritmo, int p)
    {
        //HACER UN EQUALS CON EL GRAFO GENERADO
        System.out.println("CtrlPresentacion:Ejecutar:SETEAR ALGORITMO");
        this.ctrlAlgoritmo.setAlgorithm(algoritmo);
        this.ctrlAlgoritmo.setP(p);
        if(this.ctrlAlgoritmo.areCatSelections() && this.ctrlAlgoritmo.areFilters())
        {
            this.ctrlDominio.setGeneratedCto(this.ctrlAlgoritmo.ejecutar(this.algorithmGraph(), this.ctrlDominio.getGrafo()));
            this.vistaPrincipal.actualizarSet(false);
            this.vistaPrincipal.activarTab(3); //CONJ
            this.vistaPrincipal.activarTab(4); //COMP
            this.vistaPrincipal.goToTab(3);
            this.vistaPrincipal.updateCjtos(false);
        }
        else
        {
            String msg = "";
            if(!this.ctrlAlgoritmo.areCatSelections())
            {
                msg = msg + "Categorias ";
            }
            if(!this.ctrlAlgoritmo.areFilters())
            {
                msg = msg + "Filtros ";
            }
            msg = msg + "no seleccionad@s.";
            sincronizacionVistaPrincipal_a_Error(msg);
        }
    }

    /**
     *
     * @param al
     */
    public void aplicarSelPag(ArrayList<Integer> al)
    {
        this.ctrlAlgoritmo.setPageSelections(al);
    }

    /**
     *
     * @param al
     */
    public void aplicarSelCat(ArrayList<Integer> al)
    {
        this.ctrlAlgoritmo.setCatSelections(al);
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    public void aplicarFiltros(int a, int b, int c, int d, int e)
    {
        this.ctrlAlgoritmo.setFilters(a, b, c, d, e);
    }

    //pestaña comunidades
    /**
     *
     * @param categoria
     * @param comunidad
     * @param importat
     * @return
     */
    public boolean addCtoCat(String categoria, String comunidad, boolean importat)
    {
        if(!this.ctrlDominio.addCtoCat(categoria, comunidad, importat))
        {
            sincronizacionVistaPrincipal_a_Error("Comunidad no existente/Categoria ya existente.");
            return false;
        }
        return true;
    }

    /**
     *
     * @param comunidad
     * @param importat
     * @return
     */
    public boolean addCtoCom(String comunidad, boolean importat)
    {
        if(!this.ctrlDominio.addCtoCom(comunidad, importat))
        {
            sincronizacionVistaPrincipal_a_Error("Comunidad ya existente.");
            return false;
        }
        return true;
    }

    /**
     *
     * @param categoria
     * @param comunidad
     * @param importat
     */
    public void rmvCtoCat(String categoria, String comunidad, boolean importat)
    {
        if(!this.ctrlDominio.rmvCtoCat(categoria, comunidad, importat))
        {
            sincronizacionVistaPrincipal_a_Error("Comunidad no existente/Categoria no existente.");
        }
    }

    /**
     *
     * @param comunidad
     * @param importat
     * @return
     */
    public int rmvCtoCom(String comunidad, boolean importat)
    {
        int r = this.ctrlDominio.rmvCtoCom(comunidad, importat);
        if(r == -1)
        {
            sincronizacionVistaPrincipal_a_Error("Comunidad no existente.");
        }
        return r;
    }

    /**
     *
     * @param anterior
     * @param nuevo
     * @param importat
     */
    public void modCtoNombre(String anterior, String nuevo, boolean importat)
    {
        if(!this.ctrlDominio.modCtoNombre(anterior, nuevo, importat))
        {
            sincronizacionVistaPrincipal_a_Error("Nuevo nombre ya existente o anterior no existente.");
        }
    }

    /**
     *
     * @param importat
     * @return
     */
    public ArrayList<String> mostrarCto(boolean importat)
    {
        return this.ctrlDominio.mostrarCtoComunidad(importat);
    }

    /**
     *
     * @param importat
     */
    public void visualizarCto(boolean importat)
    {
    }

    /**
     *
     * @param comunidad
     * @param importat
     * @return
     */
    public ArrayList<String> mostrarCom(String comunidad, boolean importat)
    {
        ArrayList<String> s = this.ctrlDominio.mostrarComunidad(comunidad, importat);
        if(s == null)
        {
            sincronizacionVistaPrincipal_a_Error("Comunidad no existente.");
            s = new ArrayList<>();
        }
        return s;
    }

    /**
     *
     * @param comunidad
     * @param importat
     */
    public void visualizarCom(String comunidad, boolean importat)
    {
    }

    //COMPARACION
    /**
     *
     * @param comunidad
     * @param imported
     * @return
     */
    public boolean existsComunidad(String comunidad, boolean imported)
    {
        return this.ctrlDominio.existsComunidad(comunidad, imported);
    }

    /**
     *
     * @param comunidad
     * @param imported
     * @return
     */
    public int numCatCom(String comunidad, boolean imported)
    {
        return this.ctrlDominio.numCatCom(comunidad, imported);
    }

    /**
     *
     * @param com1
     * @param importado1
     * @param com2
     * @param importado2
     * @return
     */
    public ArrayList<String> commonCategories(String com1, boolean importado1, String com2, boolean importado2)
    {
        return ctrlDominio.commonCategories(com1, importado1, com2, importado2);
    }

    /**
     *
     * @param comunidad
     * @param importado
     * @return
     */
    public double getPorcentaje(String comunidad, boolean importado)
    {
        return this.ctrlDominio.getPorcentaje(comunidad, importado);
    }

    /**
     *
     * @param imported
     * @return
     */
    public int[] infoConjunto(boolean imported)
    {
        return this.ctrlDominio.infoConjunto(imported);
    }

    /**
     *
     * @param imported
     * @return
     */
    public double getTexec(boolean imported)
    {
        return this.ctrlDominio.getTexec(imported);
    }

    /**
     *
     * @param imported
     * @return
     */
    public String isCtoModified(boolean imported)
    {
        if(this.ctrlDominio.isCtoModified(imported))
        {
            return "si";
        }
        return "no";
    }

    /**
     *
     * @param imported
     * @return
     */
    public boolean existsSet(boolean imported)
    {
        return this.ctrlDominio.existsCjto(imported);
    }

    /**
     *
     * @param imported
     * @return
     */
    public double getPurityOne(boolean imported)
    {
        return this.ctrlDominio.getPurityOne(imported);
    }

    /**
     *
     * @return
     */
    public double getPurityBoth()
    {
        return this.ctrlDominio.getPurityBoth();
    }

    /**
     *
     * @param imported
     * @return
     */
    public double getAllPurityOne(boolean imported)
    {
        return this.ctrlDominio.getAllPurityOne(imported);
    }

    /**
     *
     * @return
     */
    public double getAllPurityBoth()
    {
        return this.ctrlDominio.getAllPurityBoth();
    }

    /**
     *
     * @param p
     */
    public void obtainCjto(int p)
    {
        this.ctrlDominio.setGeneratedCto(this.ctrlAlgoritmo.obtain(p, this.ctrlDominio.getGrafo()));
        //this.vistaPrincipal.actualizarSet(false);
    }

    /**
     *
     */
    public void visualizarGrafo()
    {
        this.ctrlDominio.windowGraphEntry();
    }

    /**
     *
     */
    public void visualizarGrafoGenerado()
    {
        this.ctrlDominio.windowGraphAlgorithm();
    }

    /**
     *
     * @return
     */
    public Double getAproxTime()
    {
        return this.ctrlAlgoritmo.timeAproximation();
    }
}
