package data;

import domain.comunidades.Comunidad;
import domain.grafos.GrafoEntrada;
import java.util.ArrayList;
import shared.FileManager;

/**
 *
 * @author Javier López Calderón
 */
public class CtrData
{
    private String entryPath;
    private String algorithmPath;
    private FileManager fm;
    
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
}
