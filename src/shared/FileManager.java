package shared;

import data.CtrData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier López Calderón
 */
public class FileManager
{
    private File file = null;
    private boolean canRead = false;
    private boolean canWrite = false;
    private boolean isFile = false;
    
    public FileManager(){}
    public FileManager(String path)
    {
        file = new File(path);
        this.canRead = file.canRead();
        this.canWrite = file.canWrite();
        this.isFile = file.isFile();
    }
    
    public boolean writeFile(ArrayList<String> data)
    {
        boolean result = false;
        if(file != null && this.isFile && this.canWrite)
        {
            try
            {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.flush();
                for(String s : data)
                {
                    bw.write(s);
                    bw.newLine();
                }
                bw.close();
                fw.close();
                result = true;
            }
            catch(IOException ex)
            {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public ArrayList<String> readFile()
    {
        ArrayList<String> listString = new ArrayList();
        if(file != null && this.isFile && this.canRead)
        {
            try
            {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine()) != null)
                {
                    listString.add(line);
                }
                br.close();
                fr.close();
            }
            catch(IOException ex)
            {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listString;
    }
    
    public void setPath(String path)
    {
        file = new File(path);
        this.canRead = file.canRead();
        this.canWrite = file.canWrite();
        this.isFile = file.isFile();
    }
    
    public String getPath()
    {
        return null;
    }
    
    public void createFile(String path)
    {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(CtrData.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.close();
    }
    
    public boolean existFile(String path)
    {
        return new File(path).exists();
    }
    public boolean canWrite(String path)
    {
        return new File(path).canWrite();
    }
    public boolean canRead(String path)
    {
        return new File(path).canRead();
    }
}
