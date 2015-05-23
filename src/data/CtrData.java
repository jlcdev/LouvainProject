package data;

import domain.comunidades.Comunidad;
import domain.grafos.Grafo;
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
    
    public void readEntryGraphFile(Grafo g)
    {
        fm.setPath(this.entryPath);
        g.addMultipleEntry(fm.readFile());
    }
    
    public boolean writeEntryGraphFile(Grafo g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return false;
        if(!fm.existFile(this.entryPath)) fm.createFile(this.entryPath);
        fm.setPath(this.entryPath);
        return fm.writeFile(g.getGraphInfo());
    }
    
    public void readCommunityGraphFile(Comunidad c)
    {
        fm.setPath(this.algorithmPath);
        ArrayList<String> dataRead = fm.readFile();
        c.setId(Integer.parseInt(dataRead.get(0)));
        c.setNombre(dataRead.get(1));
        dataRead.remove(0);
        dataRead.remove(1);
        for(String s : dataRead)
        {
            c.addCategoria(s);
        }
    }
    
    public boolean writeCommunityGraphFile(Comunidad c)
    {
        ArrayList<String> forSave = new ArrayList<>();
        ArrayList<String> list = c.getNameCategories();
        forSave.add(""+c.getId());
        forSave.add(""+c.getNombre());
        forSave.addAll(list);
        return fm.writeFile(forSave);
    }
}
