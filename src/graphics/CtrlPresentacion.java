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


  public void sincronizacionVistaPrincipal_a_FileChooser(Boolean importar, Boolean grafo)
  {
    this.vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (this.vistaFileChooser == null)
      this.vistaFileChooser = new VistaFileChooser(this);
    //tipus
    this.vistaFileChooser.hacerVisible(importar, grafo);      
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
      this.vistaPrincipal.actualizarSeleccionCat();
      this.vistaPrincipal.actualizarSeleccionPag();
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
      Boolean error;
      error = this.ctrlDominio.saveEntryGraph(path);
      if(!error)sincronizacionVistaPrincipal_a_Error("El path esta vacío");
  }
  
  /**
   * Exporta un conjunto de comunidades cargado en el programa.
   * @param path
   */
  public void exportarConjunto(String path)
  {
      this.ctrlDominio.saveCtoComunidad(path);
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
     return this.ctrlDominio.getCatSelection(min, max); 
  }
  
  public ArrayList<Integer> getPagSelection(int min, int max)
  {
     return this.ctrlDominio.getPagSelection(min, max); 
  }
  
  /**
   * Añade una categoria al grafo.
   * @param categoria 
   * @return
   */
  public int addGrafoCat (String categoria)
  {
      return this.ctrlDominio.addGrafoCat(categoria);
  }
  
  /**
   * Añade una pagina al grafo.
   * @param pagina 
   * @return
   */
  public int addGrafoPag (String pagina)
  {
      return this.ctrlDominio.addGrafoPag(pagina);
  }
  
  /**
   * Añade un enlace al grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void addGrafoEnlace (String node1, String node2, String tipus)
  {
      this.ctrlDominio.addGrafoEnlace(node1,node2,tipus);
  }
    
  /**
   * Borra una categoria del grafo.
   * @param categoria 
   * @return
   */
  public int rmvGrafoCat (String categoria)
  {
      return this.ctrlDominio.rmvGrafoCat(categoria);
  }
  
  /**
   * Borra una pagina del grafo.
   * @param pagina 
     * @return  
   */
  public int rmvGrafoPag (String pagina)
  {
      return this.ctrlDominio.rmvGrafoPag(pagina);
  }
  
  /**
   * Borra un enlace del grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void rmvGrafoEnlace (String node1, String node2, String tipus)
  {
      this.ctrlDominio.rmvGrafoEnlace(node1,node2,tipus);
  }
  
  /**
   * Modifica el nombre de una pagina o categoria del grafo.
   * @param anterior
   * @param nuevo 
   * @param category 
   * @return  
   */
  public Integer modGrafoNombre (String anterior, String nuevo, Boolean category)
  {      
      int id = -1;
      if(category)
      {
          id = this.ctrlDominio.verNumCat(anterior);
          boolean error = this.ctrlDominio.modifyCategory(anterior,nuevo);
          if(!error)sincronizacionVistaPrincipal_a_Error("El nodo " + nuevo + " ya existe");
      }
      else
      {
          id = this.ctrlDominio.verNumPag(anterior);
          boolean error = this.ctrlDominio.modifyPage(anterior, nuevo);
          if(!error)sincronizacionVistaPrincipal_a_Error("El nodo " + nuevo + " ya existe");
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
  
  //pestaña algoritmo
  
  public Graph<Integer, Double> algorithmGraph()
  {
      if(!this.ctrlDominio.isAlgorithmGraph())
      {
      Graph<Integer, Double> grafo = this.ctrlAlgoritmo.generate(this.ctrlDominio.getGrafo());
      this.ctrlDominio.setAlgorithmGraph(grafo);
      return grafo;
      }
      return null;
  }
  
  public void ejecutar (int algoritmo, int p)
  {
      //HACER UN EQUALS CON EL GRAFO GENERADO
      System.out.println("SETEAR ALGORITMO");
      this.ctrlAlgoritmo.setAlgorithm(algoritmo);
      this.ctrlAlgoritmo.setP(p);
      this.ctrlDominio.setGeneratedCto(this.ctrlAlgoritmo.ejecutar(this.algorithmGraph(),this.ctrlDominio.getGrafo()));
      this.vistaPrincipal.activarTab(3); //CONJ
      this.vistaPrincipal.activarTab(4); //COMP
      this.vistaPrincipal.goToTab(3);
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
  
  public void addCtoCat (String categoria, String comunidad, Boolean importat)
  {
      Boolean error = this.ctrlDominio.addCtoCat(categoria, comunidad, importat);
      if(error)sincronizacionVistaPrincipal_a_Error("Comunidad no existente");
  }
  
  public void addCtoCom (String comunidad, Boolean importat)
  {
      this.ctrlDominio.addCtoCom(comunidad, importat);
  }
  
  public void rmvCtoCat (String categoria, String comunidad, Boolean importat)
  {
      this.ctrlDominio.rmvCtoCat(categoria,comunidad,importat);
  }
  
  public void rmvCtoCom (String comunidad, Boolean importat)
  {
      this.ctrlDominio.rmvCtoCom(comunidad, importat);
  }
  
  public void modCtoNombre (int tipus, String anterior, String nuevo, String comunidad, Boolean importat)
  {
      this.ctrlDominio.modCtoNombre(tipus, anterior, nuevo, comunidad, importat);
  }
  
  public ArrayList<String> mostrarCto (Boolean importat)
  {
      return this.ctrlDominio.mostrarCtoComunidad(importat);
  }
  
  public void visualizarCto (Boolean importat)
  {
      
  }
  
  public ArrayList<String> mostrarCom (String comunidad, Boolean importat)
  {
      return this.ctrlDominio.mostrarComunidad(comunidad, importat);
  }
  
  public void visualizarCom (String comunidad, Boolean importat)
  {
      
  }
  
  //COMPARACION
  
  public int numCatCom (String comunidad, Boolean imported)
  {
      return this.ctrlDominio.numCatCom(comunidad, imported);
  }
  
  public ArrayList<String> commonCategories(String com1, Boolean importado1, String com2, Boolean importado2)
  {
      return ctrlDominio.commonCategories(com1,importado1,com2,importado2);
  }
  
  public double getPorcentaje(String comunidad, Boolean importado)
  {
      return this.ctrlDominio.getPorcentaje(comunidad, importado);
  }
  
  public int[] infoConjunto(Boolean imported)
  {
      return this.ctrlDominio.infoConjunto(imported);
  }
  
  public double getTexec (Boolean imported)
  {
      return this.ctrlDominio.getTexec(imported);
  }
  
  public String getNombreConj(Boolean imported)
  {
      return this.ctrlDominio.getNombreConj(imported);
  }
}

