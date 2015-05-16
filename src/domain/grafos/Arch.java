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
    
    /**
     * Constructor de la clase
     * @param origin
     * @param destiny
     * @param sorigin
     * @param sdestiny
     * @param tipoArco 
     */
    public Arch(int origin, int destiny, String sorigin, String sdestiny, typeArch tipoArco)
    {
        this.origin = origin;
        this.destiny = destiny;
        this.sorigin = sorigin;
        this.sdestiny = sdestiny;
        this.tipoArco = tipoArco;
    }

    /**
     * Obtiene el id del nodo origen
     * @return id nodo origen
     */
    public int getOrigin()
    {
        return this.origin;
    }
    
    /**
     * Obtiene el id del nodo destino
     * @return id nodo destino
     */
    public int getDestiny()
    {
        return this.destiny;
    }
    
    /**
     * Obtiene el tipo arco
     * @return tipo Arco (CsubC, CsupC, CP o PC)
     */
    public typeArch getTypeArch()
    {
        return this.tipoArco;
    }
    
    /**
     * Assigna el id del nodo origen
     * @param origin 
     */
    public void setOrigin(int origin)
    {
        this.origin = origin;
    }
    
    /**
     * Assigna el id del nodo destino
     * @param destiny 
     */
    public void setDestiny(int destiny)
    {
        this.destiny = destiny;
    }
    
    /**
     * Assigna el tipo de arco (CsubC, CsupC, CP o PC)
     * @param tipoArco 
     */
    public void setTypeArch(typeArch tipoArco)
    {
        this.tipoArco = tipoArco;
    }
    
    /**
     * Devuelve el valor del hash code del objeto
     * @return valor del hash code del objeto
     */
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.origin;
        hash = 97 * hash + this.destiny;
        hash = 97 * hash + Objects.hashCode(this.tipoArco);
        return hash;
    }

    /**
     * Indica si los dos objetos son iguales
     * @param obj
     * @return true si los objetos son iguales, false en caso contrario
     */
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

    /**
     * Devuelve una representacion del objeto
     * @return una representacion del objeto en formato string
     */
    @Override
    public String toString()
    {
        return "Arch{" + "origin=" + origin + ", destiny=" + destiny + ", sorigin=" + sorigin + ", sdestiny=" + sdestiny + ", tipoArco=" + tipoArco + '}';
    }
}
