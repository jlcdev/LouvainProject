/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import domain.grafos.Grafo;
import shared.FileManager;

/**
 *
 * @author potnox
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
    
    public Grafo readEntryGraphFile()
    {
        Grafo g = new Grafo();
        if(this.entryPath == null || this.entryPath.isEmpty()) return g;
        fm.setPath(this.entryPath);
        g.addMultipleEntry(fm.readFile());
        return g;
    }
    
    public boolean writeEntryGraphFile(Grafo g)
    {
        if(this.entryPath == null || this.entryPath.isEmpty()) return false;
        if(!fm.existFile(this.entryPath)) fm.createFile(this.entryPath);
        fm.setPath(this.entryPath);
        return fm.writeFile(g.getGraphInfo());
    }
}
