/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Domain.Grafos.Grafo;
import Domain.Grafos.Transform;
import Optional.FileManager;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class CtrDominio
{
    private String filePath = null;
    private Grafo g = null;
    private FileManager fm = null;
    //private ctrDatos d;
    //private Graph<....> g2;
    
    public CtrDominio()
    {
        g = new Grafo();
        //d = new ctrDatos();
        //g2 = new GRaph();
    }
    
    public void verPagGeneral()
    {}
    
    public void verCatGeneral()
    {}
    
    public void verPag(int num)
    {}
    
    public void verCat(int num)
    {}
    
    public void leerFichero(String ruta)
    {
        this.filePath = ruta;
    }
    
    public void cargar()
    {
        if(this.filePath != null && this.filePath.isEmpty()) return;
        fm = new FileManager(this.filePath);
        this.g = Transform.multipleStringToGrafo(fm.readFile());
    }
    
    public void guardar()
    {}
    
    public void mostrarGrafo()
    {}
    
    public void mostrarComunidad()
    {}
    
    public void mostrarCtoComunidad()
    {}
    
    public void comparar(int com1, int com2)
    {}
    
    public void estadisticas()
    {}
    
    
}
