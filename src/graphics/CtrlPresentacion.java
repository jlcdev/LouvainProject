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
import java.util.*;

////////////////////////

public class CtrlPresentacion {

  private CtrDominio ctrlDominio;
  private VistaPrincipal vistaPrincipal = null;    // (= null) innecesario
  private VistaAbout vistaAbout = null;  // (= null) innecesario


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


  public void sincronizacionVistaPrincipal_a_Secundaria() {
    vistaPrincipal.desactivar();
    // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
    if (vistaAbout == null)
        vistaAbout = new VistaAbout(this);
    vistaAbout.hacerVisible();
  }

  public void sincronizacionVistaSecundaria_a_Principal() {
    // Se hace invisible la vista secundaria (podria anularse)
    vistaAbout.hacerInvisible();
    vistaPrincipal.activar();
  }


//////////////////////// Llamadas al controlador de dominio

 

  public void crearGrafo ()
  {
      
  }
  
  public void importarGrafo (String path)
  {
      
  }
  
  public void importarConjunto (String path)
  {
      
  }
  
  public void exportarGrafo(String path)
  {
      
  }
  
  public void exportarConjunto(String path)
  {
      
  }
  //PESTAÑA GRAFO
  public void addGrafoCat (String categoria)
  {
      
  }
  
  public void addGrafoPag (String pagina)
  {
      
  }
  
  public void addGrafoEnlace (String node1, String node2, String tipus)
  {
      
  }
    
  public void rmvGrafoCat (String categoria)
  {
      
  }
  
  public void rmvGrafoPag (String pagina)
  {
      
  }
  
  public void rmvGrafoEnlace (String node1, String node2, String tipus)
  {
      
  }
  
  public void modGrafoNombre (String anterior, String nuevo)
  {
      
  }
  
  public ArrayList<String> mostrarGrafoCat ()
  {
      return null;
  }
  
  public ArrayList<String> mostrarGrafoPag ()
  {
      return null;
  }
  
  public ArrayList<String> mostrarGrafoEnlaces ()
  {
      return null;
  }
  
  //pestaña algoritmo
  
  public void ejecutar (int algoritmo, int p)
  {
      
  }
  
  public void aplicarSelPag (ArrayList<String> as)
  {
    //?????  
  }
  
  public void aplicarSelCat (ArrayList<String> as)
  {
      //?????
  }
  
  public void aplicarFiltros (String filtros)
  {
      
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

////////////////////////
