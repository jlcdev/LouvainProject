package domain.grafos;

import java.util.ArrayList;

/**
 *
 * @author Javier López Calderón
 */
public class Filters implements Cloneable
{
    private int pname, pcat, ppag, pfat, pson;
    
    public Filters(int pname, int pcat, int ppag, int pfat, int pson)
    {
        this.pname = pname;
        this.pcat = pcat;
        this.ppag = ppag;
        this.pfat = pfat;
        this.pson = pson;
    }
    
    public Filters(ArrayList<String> filters)
    {
        this.loadFromFile(filters);
    }

    public int getPname()
    {
        return pname;
    }

    public int getPcat()
    {
        return pcat;
    }

    public int getPpag()
    {
        return ppag;
    }

    public int getPfat()
    {
        return pfat;
    }

    public int getPson()
    {
        return pson;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + this.pname;
        hash = 79 * hash + this.pcat;
        hash = 79 * hash + this.ppag;
        hash = 79 * hash + this.pfat;
        hash = 79 * hash + this.pson;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final Filters other = (Filters) obj;
        if(this.pname != other.pname)
        {
            return false;
        }
        if(this.pcat != other.pcat)
        {
            return false;
        }
        if(this.ppag != other.ppag)
        {
            return false;
        }
        if(this.pfat != other.pfat)
        {
            return false;
        }
        if(this.pson != other.pson)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Filters clone()
    {
        Object obj = null;
        try
        {
            obj = super.clone();
        }
        catch(CloneNotSupportedException e){}
        return (Filters) obj;
    }

    public ArrayList<String> saveToFile()
    {
        ArrayList<String> response = new ArrayList<>();
        response.add("FILTER");
        response.add("pname:"+this.pname);
        response.add("pcat:"+this.pcat);
        response.add("ppag:"+this.ppag);
        response.add("pfat:"+this.pfat);
        response.add("pson:"+this.pson);
        response.add("ENDFILTER");
        return response;
    }
    
    public void loadFromFile(ArrayList<String> data)
    {
        this.pname = Integer.parseInt(data.get(1).replaceFirst("pname:", ""));
        this.pcat = Integer.parseInt(data.get(2).replaceFirst("pcat:", ""));
        this.ppag = Integer.parseInt(data.get(3).replaceFirst("ppag:", ""));
        this.pfat = Integer.parseInt(data.get(4).replaceFirst("pfat:", ""));
        this.pson = Integer.parseInt(data.get(5).replaceFirst("pson:", ""));
    }
}
