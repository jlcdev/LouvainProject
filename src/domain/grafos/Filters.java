package domain.grafos;

/**
 *
 * @author Joan Rodas
 */
public class Filters implements Cloneable
{
    private final int pname, pcat, ppag, pfat, pson;

    /**
     * Initialize Filter with priorities
     *
     * @param pname
     * @param pcat
     * @param ppag
     * @param pfat
     * @param pson
     */
    public Filters(int pname, int pcat, int ppag, int pfat, int pson)
    {
        this.pname = pname;
        this.pcat = pcat;
        this.ppag = ppag;
        this.pfat = pfat;
        this.pson = pson;
    }

    /**
     * @return String priorities
     */
    @Override
    public String toString()
    {
        return "name: "+this.pname+" cat: "+this.pcat+" pag: "+this.ppag+" padre: "+this.pfat+" hijo: "+this.pson;
    }

    public int[] getFilters()
    {
        int[] response = {this.pname, this.pcat, this.ppag, this.pfat, this.pson};
        return response;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + this.pname;
        hash = 59 * hash + this.pcat;
        hash = 59 * hash + this.ppag;
        hash = 59 * hash + this.pfat;
        hash = 59 * hash + this.pson;
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
        catch(CloneNotSupportedException e)
        {
        }
        return (Filters) obj;
    }
}
