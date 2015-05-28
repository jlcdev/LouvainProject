package data;

import domain.comunidades.CtoComunidad;
import domain.grafos.GrafoEntrada;
import shared.FileManager;

/**
 *
 * @author Javier López Calderón
 */
public class CtrData
{
    private String entryPath;
    private String algorithmPath;
    private final FileManager fm;
    
    public CtrData()
    {
        fm = new FileManager();
    }
    
    public void setEntryPath(String entryPath)
    {
        this.entryPath = entryPath;
    }
    
    public void setAlgorithmPath(String algorithmPath)
    {
        this.algorithmPath = algorithmPath;
    }
    
    public void readEntryGraphFile(GrafoEntrada g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return;
        fm.setPath(this.entryPath);
        g.loadFromFile(fm.readFile());
    }
    
    public boolean writeEntryGraphFile(GrafoEntrada g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return false;
        if(!fm.existFile(this.entryPath)) fm.createFile(this.entryPath);
        fm.setPath(this.entryPath);
        return fm.writeFile(g.saveToFile());
    }
    
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
