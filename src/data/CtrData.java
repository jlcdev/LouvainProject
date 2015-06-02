package data;

import domain.comunidades.CtoComunidad;
import domain.grafos.GrafoEntrada;
import shared.FileManager;

/**
 * Controlador de datos es la clase encargada de gestionar todo lo relacionado
 * con ficheros de esta aplicación
 * 
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class CtrData
{
    private String entryPath;
    private String algorithmPath;
    private final FileManager fm;
    
    /**
     * Constructor por defecto
     */
    public CtrData()
    {
        fm = new FileManager();
    }
    
    /**
     * Fija el path para los datos del grafo de entrada
     * 
     * @param entryPath String con el path al fichero
     */
    public void setEntryPath(String entryPath)
    {
        this.entryPath = entryPath;
    }
    
    /**
     * Fija el path para los datos de los conjuntos
     * 
     * @param algorithmPath String con el path al fichero
     */
    public void setAlgorithmPath(String algorithmPath)
    {
        this.algorithmPath = algorithmPath;
    }
    
    /**
     * Completa el grafo pasado por parámetro con la información leida de un fichero
     * 
     * @param g grafo de entrada
     */
    public void readEntryGraphFile(GrafoEntrada g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return;
        fm.setPath(this.entryPath);
        g.loadFromFile(fm.readFile());
    }
    
    /**
     * Guarda un grafo de entrada en un fichero
     * 
     * @param g Grafo de entrada
     * @return true si se ha guardado correctamente
     */
    public boolean writeEntryGraphFile(GrafoEntrada g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return false;
        if(!fm.existFile(this.entryPath)) fm.createFile(this.entryPath);
        fm.setPath(this.entryPath);
        return fm.writeFile(g.saveToFile());
    }
    
    /**
     * 
     * 
     * @param ctoComunidad 
     */
    public void readCtoComunidad(CtoComunidad ctoComunidad)
    {
        if(this.algorithmPath == null || this.algorithmPath.isEmpty()) return;
        fm.setPath(this.algorithmPath);
        ctoComunidad.loadFromFile(fm.readFile());
    }
    
    public boolean writeCtoComunidad(CtoComunidad ctoComunidad)
    {
        if(this.algorithmPath == null || this.algorithmPath.isEmpty()) return false;
        if(!fm.existFile(this.algorithmPath)) fm.createFile(this.algorithmPath);
        fm.setPath(this.algorithmPath);
        return fm.writeFile(ctoComunidad.savetoFile());
    }
}
