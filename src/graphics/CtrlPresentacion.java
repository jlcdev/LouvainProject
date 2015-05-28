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
import domain.comunidades.CtoComunidad;
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
    ctrlDominio = new CtrDominio();
    ctrlAlgoritmo = new CtrAlgoritmo();
    if (vistaPrincipal == null)  // innecesario
      vistaPrincipal = new VistaPrincipal(this);
  }

  public void inicializarPresentacion() {
    //ctrlDominio.inicializarCtrDominio();
    vistaPrincipal.hacerVisible();
  }


//////////////////////// Metodos de sincronizacion entre vistas


  public void sincronizacionVistaPrincipal_a_FileChooser(Boolean importar, Boolean grafo)
  {
    vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaFileChooser == null)
      vistaFileChooser = new VistaFileChooser(this);
    //tipus
    vistaFileChooser.hacerVisible(importar, grafo);      
  }
  
  public void sincronizacionVistaPrincipal_a_Manual()
  {
    vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaManual == null)
      vistaManual = new VistaManual(this);
    vistaManual.hacerVisible();     
  }
  
  public void sincronizacionVistaPrincipal_a_About()
  {
     vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaAbout == null)
      vistaAbout = new VistaAbout(this);
    vistaAbout.hacerVisible();     
  }

  public void sincronizacionVistaPrincipal_a_Error(String msg)
  {
     vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaError == null)
      vistaError = new VistaError(this);
    vistaError.hacerVisible(msg);     
  }
  
  public void sincronizacionVistaFileChooser_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    vistaFileChooser.hacerInvisible();
    vistaPrincipal.activar();    
  }
  
  public void sincronizacionVistaManual_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    vistaManual.hacerInvisible();
    vistaPrincipal.activar();      
  }
    
  public void sincronizacionVistaAbout_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    vistaAbout.hacerInvisible();
    vistaPrincipal.activar();     
  }
  
  public void sincronizacionVistaError_a_Principal()
  {
    // Se hace invisible la vista secundaria (podria anularse)
    vistaError.hacerInvisible();
    vistaPrincipal.activar();     
  }


//////////////////////// Llamadas al controlador de dominio


  public void crearGrafo ()
  {
      ctrlDominio.newGrafo();
      vistaPrincipal.activarTab(1); //GRAFO
      vistaPrincipal.activarTab(2); //ALGORITMO
  }
  
  /**
   * Importa un grafo a través de un fichero de texto.
   * @param path 
   */
  public void importarGrafo (String path)
  {
      ctrlDominio.readEntryGraphFile(path);
      vistaPrincipal.actualizarSeleccionCat();
      vistaPrincipal.actualizarSeleccionPag();
      vistaPrincipal.activarTab(1); //GRAFO
      vistaPrincipal.activarTab(2); //ALGORITMO
      vistaPrincipal.goToTab(1);
  }
  
  /**
   * Importa un conjunto de comunidades a través de un fichero de texto.
   * @param path 
   */
  public void importarConjunto (String path)
  {
      //ctrlDominio.cargarAlgorithmGraph(path);
      vistaPrincipal.activarTab(3); //CONJ
      vistaPrincipal.activarTab(4); //COMP
      vistaPrincipal.goToTab(3);
  }
  
  /**
   * Exporta el grafo cargado en el programa.
   * @param path
   */
  public void exportarGrafo(String path)
  {
      Boolean error;
      error = ctrlDominio.saveEntryGraph(path);
      if(!error)sincronizacionVistaPrincipal_a_Error("El path esta vacío");
  }
  
  /**
   * Exporta un conjunto de comunidades cargado en el programa.
   * @param path
   */
  public void exportarConjunto(String path)
  {
      Boolean error;
      //error = ctrlDominio.saveAlgorithmGraph(path);
      //if(!error)sincronizacionVistaPrincipal_a_Error("El path esta vacío");
  }
  //PESTAÑA GRAFO
  
  public int getPagNum(String pagina)
  {
      return ctrlDominio.verNumPag(pagina);
  }
  
  public int getCatNum(String categoria)
  {
      return ctrlDominio.verNumCat(categoria);
  }
  
  public ArrayList<Integer> getCatSelection(int min, int max)
  {
     return ctrlDominio.getCatSelection(min, max); 
  }
  
  public ArrayList<Integer> getPagSelection(int min, int max)
  {
     return ctrlDominio.getPagSelection(min, max); 
  }
  
  /**
   * Añade una categoria al grafo.
   * @param categoria 
   */
  public void addGrafoCat (String categoria)
  {
      ctrlDominio.addGrafoCat(categoria);
  }
  
  /**
   * Añade una pagina al grafo.
   * @param pagina 
   */
  public void addGrafoPag (String pagina)
  {
      ctrlDominio.addGrafoPag(pagina);
  }
  
  /**
   * Añade un enlace al grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void addGrafoEnlace (String node1, String node2, String tipus)
  {
      ctrlDominio.addGrafoEnlace(node1,node2,tipus);
  }
    
  /**
   * Borra una categoria del grafo.
   * @param categoria 
   */
  public int rmvGrafoCat (String categoria)
  {
      return ctrlDominio.rmvGrafoCat(categoria);
  }
  
  /**
   * Borra una pagina del grafo.
   * @param pagina 
   */
  public int rmvGrafoPag (String pagina)
  {
      return ctrlDominio.rmvGrafoPag(pagina);
  }
  
  /**
   * Borra un enlace del grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void rmvGrafoEnlace (String node1, String node2, String tipus)
  {
      ctrlDominio.rmvGrafoEnlace(node1,node2,tipus);
  }
  
  /**
   * Modifica el nombre de una pagina o categoria del grafo.
   * @param anterior
   * @param nuevo 
   */
  public void modGrafoNombre (String anterior, String nuevo, Boolean tipus)
  {
      //***************************************************
      //***************************************************
      if(tipus)ctrlDominio.modifyCategory(anterior,nuevo);
      else ctrlDominio.modifyPage(anterior, nuevo);
  }
  
  /**
   * Muestra las categorias del grafo.
   * @return 
   */
  public ArrayList<String> mostrarGrafoCat ()
  {
      return ctrlDominio.verCatGeneral();
  }
  
  /**
   * Muestra las paginas del grafo.
   * @return 
   */
  public ArrayList<String> mostrarGrafoPag ()
  {
      return ctrlDominio.verPagGeneral();
  }
  
  /**
   * Mostra els enllaços del graf.
   * @return 
   */
  public ArrayList<String> mostrarGrafoEnlaces ()
  {
      return null;
  }
  
  //pestaña algoritmo
  
  public Graph<Integer, Double> algorithmGraph()
  {
      if(!ctrlDominio.isAlgorithmGraph())
      {
      Graph<Integer, Double> grafo = ctrlAlgoritmo.generate(ctrlDominio.getGrafo());
      ctrlDominio.setAlgorithmGraph(grafo);
      return grafo;
      }
      return null;
  }
  
  public void ejecutar (int algoritmo, int p)
  {
      //HACER UN EQUALS CON EL GRAFO GENERADO
      ctrlAlgoritmo.setAlgorithm(algoritmo);
      ctrlAlgoritmo.setP(p);
      ctrlDominio.setGeneratedCto(ctrlAlgoritmo.ejecutar(algorithmGraph(),ctrlDominio.getGrafo()));
      vistaPrincipal.activarTab(3); //CONJ
      vistaPrincipal.activarTab(4); //COMP
      vistaPrincipal.goToTab(3);
  }
  
  public void aplicarSelPag (ArrayList<Integer> al)
  {
      ctrlAlgoritmo.setPageSelections(al);
  }
  
  public void aplicarSelCat (ArrayList<Integer> al)
  {
      ctrlAlgoritmo.setCatSelections(al);
  }
  
  public void aplicarFiltros (int a, int b, int c, int d, int e)
  {
      ctrlAlgoritmo.setFilters(a,b,c,d,e);
  }
  
  //pestaña comunidades
  
  public void addCtoCat (String categoria, String comunidad, Boolean importat)
  {
      Boolean error = ctrlDominio.addCtoCat(categoria, comunidad, importat);
      if(error)sincronizacionVistaPrincipal_a_Error("Comunidad no existente");
  }
  
  public void addCtoCom (String comunidad, Boolean importat)
  {
      ctrlDominio.addCtoCom(comunidad, importat);
  }
  
  public void rmvCtoCat (String categoria, String comunidad, Boolean importat)
  {
      ctrlDominio.rmvCtoCat(categoria,comunidad,importat);
  }
  
  public void rmvCtoCom (String comunidad, Boolean importat)
  {
      ctrlDominio.rmvCtoCom(comunidad, importat);
  }
  
  public void modCtoNombre (int tipus, String anterior, String nuevo, String comunidad, Boolean importat)
  {
      ctrlDominio.modCtoNombre(tipus, anterior, nuevo, comunidad, importat);
  }
  
  public ArrayList<String> mostrarCto (Boolean importat)
  {
      return ctrlDominio.mostrarCtoComunidad(importat);
  }
  
  public void visualizarCto (Boolean importat)
  {
      
  }
  
  public ArrayList<String> mostrarCom (String comunidad, Boolean importat)
  {
      return ctrlDominio.mostrarComunidad(comunidad, importat);
  }
  
  public void visualizarCom (String comunidad, Boolean importat)
  {
      
  }
  
  //COMPARACION
  
  public ArrayList<String> compararComunidades(String com1, Boolean importado1, String com2, Boolean importado2)
  {
      return ctrlDominio.compararComunidades(com1,importado1,com2,importado2);
  }
  
  public void compararConjuntos()
  {
      ctrlDominio.compararConjuntos();
  }
}

