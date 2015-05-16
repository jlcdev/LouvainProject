
package domain.grafos;

import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Arch
{
    private int origin;
    private int destiny;
    private String sorigin;
    private String sdestiny;
    private typeArch tipoArco;
    
    public static enum typeArch
    {
        CsubC, CsupC, CP, PC;
    }

    public Arch(int origin, int destiny, String sorigin, String sdestiny, typeArch tipoArco)
    {
        this.origin = origin;
        this.destiny = destiny;
        this.sorigin = sorigin;
        this.sdestiny = sdestiny;
        this.tipoArco = tipoArco;
    }

    public int getOrigin()
    {
        return this.origin;
    }
    
    public int getDestiny()
    {
        return this.destiny;
    }
    
    public typeArch getTypeArch()
    {
        return this.tipoArco;
    }

    public void setOrigin(int origin)
    {
        this.origin = origin;
    }
    
    public void setDestiny(int destiny)
    {
        this.destiny = destiny;
    }
    
    public void setTypeArch(typeArch tipoArco)
    {
        this.tipoArco = tipoArco;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.origin;
        hash = 97 * hash + this.destiny;
        hash = 97 * hash + Objects.hashCode(this.tipoArco);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final Arch other = (Arch) obj;
        if(this.origin != other.origin) return false;
        if(this.destiny != other.destiny) return false;
        if(this.tipoArco != other.tipoArco) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Arch{" + "origin=" + origin + ", destiny=" + destiny + ", sorigin=" + sorigin + ", sdestiny=" + sdestiny + ", tipoArco=" + tipoArco + '}';
    }
}
