/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

/**
 *
 * @author Alfred Parellada Rodriguez
 */


import domain.CtrDominio;
import domain.CtrAlgoritmo;
import java.util.*;
import shared.Graph;

////////////////////////

public class CtrlPresentacion {

  private CtrDominio ctrlDominio;
  private CtrAlgoritmo ctrlAlgoritmo;
  private VistaPrincipal vistaPrincipal = null;    // (= null) innecesario
  private VistaAbout vistaAbout = null;  // (= null) innecesario
  private VistaError vistaError = null;
  private VistaFileChooser vistaFileChooser = null;
  private VistaManual vistaManual = null;


//////////////////////// Constructor y metodos de inicializacion


  public CtrlPresentacion() {
    this.ctrlDominio = new CtrDominio();
    this.ctrlAlgoritmo = new CtrAlgoritmo();
    if (this.vistaPrincipal == null)  // innecesario
      this.vistaPrincipal = new VistaPrincipal(this);
  }

  public void inicializarPresentacion() {
    //ctrlDominio.inicializarCtrDominio();
    this.vistaPrincipal.hacerVisible();
  }


//////////////////////// Metodos de sincronizacion entre vistas


  public void sincronizacionVistaPrincipal_a_FileChooser(boolean importar, boolean grafo, boolean importado)
  {
    this.vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (this.vistaFileChooser == null)
      this.vistaFileChooser = new VistaFileChooser(this);
    //tipus
    this.vistaFileChooser.hacerVisible(importar, grafo, importado);      
  }
  
  public void sincronizacionVistaPrincipal_a_Manual()
  {
    this.vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (this.vistaManual == null)
      this.vistaManual = new VistaManual(this);
    this.vistaManual.hacerVisible();     
  }
  
  public void sincronizacionVistaPrincipal_a_About()
  {
     this.vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (this.vistaAbout == null)
      this.vistaAbout = new VistaAbout(this);
    this.vistaAbout.hacerVisible();     
  }

  public void sincronizacionVistaPrincipal_a_Error(String msg)
  {
     this.vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (this.vistaError == null)
      this.vistaError = new VistaError(this);
    this.vistaError.hacerVisible(msg);     
  }
  
  public void sincronizacionVistaFileChooser_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    this.vistaFileChooser.hacerInvisible();
    this.vistaPrincipal.activar();    
  }
  
  public void sincronizacionVistaManual_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    this.vistaManual.hacerInvisible();
    this.vistaPrincipal.activar();      
  }
    
  public void sincronizacionVistaAbout_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    this.vistaAbout.hacerInvisible();
    this.vistaPrincipal.activar();     
  }
  
  public void sincronizacionVistaError_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    this.vistaError.hacerInvisible();
    this.vistaPrincipal.activar();     
  }


//////////////////////// Llamadas al controlador de dominio


  public void crearGrafo ()
  {
      this.ctrlDominio.newGrafo();
      this.vistaPrincipal.activarTab(1); //GRAFO
      this.vistaPrincipal.activarTab(2); //ALGORITMO
      this.vistaPrincipal.goToTab(1);
  }
  
  /**
   * Importa un grafo a través de un fichero de texto.
   * @param path 
   */
  public void importarGrafo (String path)
  {
      this.ctrlDominio.readEntryGraphFile(path);
      this.vistaPrincipal.actualizarCat();
      this.vistaPrincipal.actualizarPag();
      this.vistaPrincipal.actualizarLinks();
      this.vistaPrincipal.activarTab(1); //GRAFO
      this.vistaPrincipal.activarTab(2); //ALGORITMO
      this.vistaPrincipal.goToTab(1);
  }
  
  /**
   * Importa un conjunto de comunidades a través de un fichero de texto.
   * @param path 
   */
  public void importarConjunto (String path)
  {
      this.ctrlDominio.loadCtoComunidad(path);
      this.vistaPrincipal.actualizarSet(true);
      this.vistaPrincipal.activarTab(3); //CONJ
      this.vistaPrincipal.activarTab(4); //COMP
      this.vistaPrincipal.goToTab(3);
  }
  
  /**
   * Exporta el grafo cargado en el programa.
   * @param path
   */
  public void exportarGrafo(String path)
  {
      boolean error;
      error = this.ctrlDominio.saveEntryGraph(path);
      if(!error)sincronizacionVistaPrincipal_a_Error("El path esta vacío");
  }
  
  /**
   * Exporta un conjunto de comunidades cargado en el programa.
   * @param path
   * @param importado
   */
  public void exportarConjunto(String path, boolean importado)
  {
      this.ctrlDominio.saveCtoComunidad(path, importado);
  }
  //PESTAÑA GRAFO
  
  public int getPagNum(String pagina)
  {
      return this.ctrlDominio.verNumPag(pagina);
  }
  
  public int getCatNum(String categoria)
  {
      return this.ctrlDominio.verNumCat(categoria);
  }
  
  public ArrayList<Integer> getCatSelection(int min, int max)
  {
     if(max < min)sincronizacionVistaPrincipal_a_Error("max < min");
     return this.ctrlDominio.getCatSelection(min, max); 
  }
  
  public ArrayList<Integer> getPagSelection(int min, int max)
  {
     if(max < min)sincronizacionVistaPrincipal_a_Error("max < min");
     return this.ctrlDominio.getPagSelection(min, max); 
  }
  
  /**
   * Añade una categoria al grafo.
   * @param categoria 
   * @return
   */
  public int addGrafoCat (String categoria)
  {
      int r = this.ctrlDominio.addGrafoCat(categoria);
      if(r != -1)return r;
      sincronizacionVistaPrincipal_a_Error("La cateogria introducida ya existe.");
      return r;
  }
  
  /**
   * Añade una pagina al grafo.
   * @param pagina 
   * @return
   */
  public int addGrafoPag (String pagina)
  {
      int r = this.ctrlDominio.addGrafoPag(pagina);
      if(r != -1)return r;
      sincronizacionVistaPrincipal_a_Error("La pagina introducida ya existe.");
      return r;
  }
  
  /**
   * Añade un enlace al grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void addGrafoEnlace (String node1, String node2, String tipus)
  {
      if(! this.ctrlDominio.addGrafoEnlace(node1,node2,tipus))
            sincronizacionVistaPrincipal_a_Error("Datos mal introducidos.");
  }
    
  /**
   * Borra una categoria del grafo.
   * @param categoria 
   * @return
   */
  public int rmvGrafoCat (String categoria)
  {
      int r = this.ctrlDominio.rmvGrafoCat(categoria);
      if(r != -1)return r;
      sincronizacionVistaPrincipal_a_Error("La categoria introducida no existe.");
      return r;
  }
  
  /**
   * Borra una pagina del grafo.
   * @param pagina 
     * @return  
   */
  public int rmvGrafoPag (String pagina)
  {
      int r = this.ctrlDominio.rmvGrafoPag(pagina);
      if(r != -1)return r;
      sincronizacionVistaPrincipal_a_Error("La pagina introducida no existe.");
      return r;
  }
  
  /**
   * Borra un enlace del grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void rmvGrafoEnlace (String node1, String node2, String tipus)
  {
      if(! this.ctrlDominio.rmvGrafoEnlace(node1,node2,tipus))
            sincronizacionVistaPrincipal_a_Error("Datos mal introducidos.");
  }
  
  /**
   * Modifica el nombre de una pagina o categoria del grafo.
   * @param anterior
   * @param nuevo 
   * @param category 
   * @return  
   */
  public Integer modGrafoNombre (String anterior, String nuevo, boolean category)
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
          boolean error = this.ctrlDominio.modifyCategory(anterior,nuevo);
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
   * @return 
   */
  public ArrayList<String> mostrarGrafoCat ()
  {
      return this.ctrlDominio.verCatGeneral();
  }
  
  /**
   * Muestra las paginas del grafo.
   * @return 
   */
  public ArrayList<String> mostrarGrafoPag ()
  {
      return this.ctrlDominio.verPagGeneral();
  }
  
  /**
   * Mostra els enllaços del graf.
   * @return 
   */
  public ArrayList<String> mostrarGrafoEnlaces ()
  {
      return this.ctrlDominio.verEnlacesGeneral();
  }
  
  /**
   * Mostra els enllaços d'un node
   * @param category
   * @param name
   * @return 
   */
  public ArrayList<String> mostrarGrafoEnlaces(boolean category, String name)
  {
      if(category) return this.ctrlDominio.verEnlacesGeneralNode(category, name);
      return this.ctrlDominio.verEnlacesGeneralNode(category, name);
  }
  
  //pestaña algoritmo
  
  public Graph<Integer, Double> algorithmGraph()
  {
      Graph<Integer, Double> grafo = this.ctrlAlgoritmo.generate(this.ctrlDominio.getGrafo());
      this.ctrlDominio.setAlgorithmGraph(grafo);
      System.out.println("FINAL TRANSFORMACIÓN");
      return grafo;
  }
  
  public void ejecutar (int algoritmo, int p)
  {
      //HACER UN EQUALS CON EL GRAFO GENERADO
      System.out.println("SETEAR ALGORITMO");
      this.ctrlAlgoritmo.setAlgorithm(algoritmo);
      this.ctrlAlgoritmo.setP(p);
      if(this.ctrlAlgoritmo.areCatSelections() && this.ctrlAlgoritmo.arePagSelections() && this.ctrlAlgoritmo.areFilters())
      {
        this.ctrlDominio.setGeneratedCto(this.ctrlAlgoritmo.ejecutar(this.algorithmGraph(),this.ctrlDominio.getGrafo()));
        this.vistaPrincipal.actualizarSet(false);
        this.vistaPrincipal.activarTab(3); //CONJ
        this.vistaPrincipal.activarTab(4); //COMP
        this.vistaPrincipal.goToTab(3);
      }
      else sincronizacionVistaPrincipal_a_Error("Filtros/Cateogiras/Paginas no seleccionados");
  }
  
  public void aplicarSelPag (ArrayList<Integer> al)
  {
      this.ctrlAlgoritmo.setPageSelections(al);
  }
  
  public void aplicarSelCat (ArrayList<Integer> al)
  {
      this.ctrlAlgoritmo.setCatSelections(al);
  }
  
  public void aplicarFiltros (int a, int b, int c, int d, int e)
  {
      this.ctrlAlgoritmo.setFilters(a,b,c,d,e);
  }
  
  //pestaña comunidades
  
  public void addCtoCat (String categoria, String comunidad, boolean importat)
  {
      boolean error = this.ctrlDominio.addCtoCat(categoria, comunidad, importat);
      if(error)sincronizacionVistaPrincipal_a_Error("Comunidad no existente");
  }
  
  public void addCtoCom (String comunidad, boolean importat)
  {
      this.ctrlDominio.addCtoCom(comunidad, importat);
  }
  
  public void rmvCtoCat (String categoria, String comunidad, boolean importat)
  {
      this.ctrlDominio.rmvCtoCat(categoria,comunidad,importat);
  }
  
  public void rmvCtoCom (String comunidad, boolean importat)
  {
      this.ctrlDominio.rmvCtoCom(comunidad, importat);
  }
  
  public void modCtoNombre (String anterior, String nuevo, boolean importat)
  {
      this.ctrlDominio.modCtoNombre(anterior, nuevo, importat);
  }
  
  public ArrayList<String> mostrarCto (boolean importat)
  {
      return this.ctrlDominio.mostrarCtoComunidad(importat);
  }
  
  public void visualizarCto (boolean importat)
  {
      
  }
  
  public ArrayList<String> mostrarCom (String comunidad, boolean importat)
  {
      return this.ctrlDominio.mostrarComunidad(comunidad, importat);
  }
  
  public void visualizarCom (String comunidad, boolean importat)
  {
      
  }
  
  //COMPARACION
  
  public int numCatCom (String comunidad, boolean imported)
  {
      return this.ctrlDominio.numCatCom(comunidad, imported);
  }
  
  public ArrayList<String> commonCategories(String com1, boolean importado1, String com2, boolean importado2)
  {
      return ctrlDominio.commonCategories(com1,importado1,com2,importado2);
  }
  
  public double getPorcentaje(String comunidad, boolean importado)
  {
      return this.ctrlDominio.getPorcentaje(comunidad, importado);
  }
  
  public int[] infoConjunto(boolean imported)
  {
      return this.ctrlDominio.infoConjunto(imported);
  }
  
  public double getTexec (boolean imported)
  {
      return this.ctrlDominio.getTexec(imported);
  }
  
  
  public boolean existsSet(boolean imported)
  {
      return this.ctrlDominio.existsCjto(imported);
  }
  
  public double getPurity()
  {
      return this.ctrlDominio.getSimilarity();
  }
  
  public void obtainCjto(int p)
  {      
      this.ctrlDominio.setGeneratedCto(this.ctrlAlgoritmo.obtain(p, this.ctrlDominio.getGrafo()));
      //this.vistaPrincipal.actualizarSet(false);
  }
  
  public void visualizarGrafo()
  {
      this.ctrlDominio.windowGraphEntry();
  }
  
  public void visualizarGrafoGenerado()
  {
      this.ctrlDominio.windowGraphAlgorithm();
  }
}


