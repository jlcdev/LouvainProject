package domain.grafos;

/**
 *
 * @author Javier López Calderón
 */
public class Filters implements Cloneable
{
    private final int pname, pcat, ppag, pfat, pson;
    
    public Filters(int pname, int pcat, int ppag, int pfat, int pson)
    {
        this.pname = pname;
        this.pcat = pcat;
        this.ppag = ppag;
        this.pfat = pfat;
        this.pson = pson;
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

    @Override
    public String toString()
    {
        return "name: "+this.pname+" category: "+this.pcat+" page: "+this.ppag+" father: "+this.pfat+" son: "+this.pson;
    }
}
