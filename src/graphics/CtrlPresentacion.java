/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

/**
 *
 * @author alfred
 */


import domain.CtrDominio;
import domain.CtrAlgoritmo;
import java.util.*;

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
    if (vistaPrincipal == null)  // innecesario
      vistaPrincipal = new VistaPrincipal(this);
  }

  public void inicializarPresentacion() {
    //ctrlDominio.inicializarCtrDominio();
    vistaPrincipal.hacerVisible();
  }


//////////////////////// Metodos de sincronizacion entre vistas


  public void sincronizacionVistaPrincipal_a_Secundaria()
  {
      
  }
  
  public void sincronizacionVistaSecundaria_a_Principal()
  {
      
  }
  public void sincronizacionVistaPrincipal_a_FileChooser(Boolean tipus)
  {
    vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaFileChooser == null)
      vistaFileChooser = new VistaFileChooser(this);
    //tipus
    vistaFileChooser.hacerVisible();      
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


  /*public ArrayList<String> llamadaDominio1 (String selectedItem) {
    return ctrlDominio.llamadaDominio1(selectedItem);
  }

  public ArrayList<String> llamadaDominio2() {
    return ctrlDominio.llamadaDominio2();
  }*/

  public void crearGrafo ()
  {
      
  }
  
  /**
   * Importa un grafo a través de un fichero de texto.
   * @param path 
   */
  public void importarGrafo (String path)
  {
      ctrlDominio.readEntryGraphFile(path);
  }
  
  /**
   * Importa un conjunto de comunidades a través de un fichero de texto.
   * @param path 
   */
  public void importarConjunto (String path)
  {
      //ctrlDominio.cargarAlgorithmGraph(path);
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
      error = ctrlDominio.saveAlgorithmGraph(path);
      if(!error)sincronizacionVistaPrincipal_a_Error("El path esta vacío");
  }
  //PESTAÑA GRAFO
  
  /**
   * Añade una categoria al grafo.
   * @param categoria 
   */
  public void addGrafoCat (String categoria)
  {
      
  }
  
  /**
   * Añade una pagina al grafo.
   * @param pagina 
   */
  public void addGrafoPag (String pagina)
  {
      
  }
  
  /**
   * Añade un enlace al grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void addGrafoEnlace (String node1, String node2, String tipus)
  {
      
  }
    
  /**
   * Borra una categoria del grafo.
   * @param categoria 
   */
  public void rmvGrafoCat (String categoria)
  {
      
  }
  
  /**
   * Borra una pagina del grafo.
   * @param pagina 
   */
  public void rmvGrafoPag (String pagina)
  {
      
  }
  
  /**
   * Borra un enlace del grafo.
   * @param node1
   * @param node2
   * @param tipus 
   */
  public void rmvGrafoEnlace (String node1, String node2, String tipus)
  {
      
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
      int a = 0; //ELIMINAR
      Boolean error;
      if(tipus)error = ctrlDominio.modifyCategory(a,nuevo);
      else error = ctrlDominio.modifyPage(a, nuevo);
      if(!error)sincronizacionVistaPrincipal_a_Error("La categoria indicada no existe");
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
  
  public void ejecutar (int algoritmo, int p)
  {
      ctrlAlgoritmo.setAlgorithm(algoritmo);
      ctrlAlgoritmo.setP(p);
      //ctrlAlgoritmo.ejecutar(ctrlDominio.getGrafo());
  }
  
  public void aplicarSelPag (ArrayList<String> al)
  {
    //?????  
  }
  
  public void aplicarSelCat (ArrayList<String> al)
  {
      //?????
  }
  
  public void aplicarFiltros (String filtros)
  {
      ctrlAlgoritmo.setFilters(filtros);
  }
  
  //pestaña comunidades
  
  public void addCtoCat (String categoria, String comunidad, Boolean importat)
  {
      
  }
  
  public void addCtoCom (String comunidad, Boolean importat)
  {
      
  }
  
  public void rmvCtoCat (String categoria, String comunidad, Boolean importat)
  {
      
  }
  
  public void rmvCtoCom (String comunidad, Boolean importat)
  {
      
  }
  
  public void modCtoNombre (int tipus, String anterior, String nuevo, Boolean importat)
  {
      
  }
  
  public ArrayList<String> mostrarCto (Boolean importat)
  {
      return null;
  }
  
  public void visualizarCto (Boolean importat)
  {
      
  }
  
  public ArrayList<String> mostrarCom (String comunidad, Boolean importat)
  {
      return null;
  }
  
  public void visualizarCom (String comunidad, Boolean importat)
  {
      
  }
}

