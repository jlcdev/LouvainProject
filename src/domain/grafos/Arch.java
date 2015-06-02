package domain.grafos;

import java.util.Objects;

/**
 * La clase Arch es la encargada de representar la relación entre dos nodos.
 * La información que almacena simplemente es nodo origen, nodo destino y el tipo
 * de arco que representa. Los tipo de arco posibles son: ·CsubC ·CsupC ·CP ·PC
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 1/06/2015
 */
public class Arch
{
    private int origin;
    private int destiny;
    private typeArch tipoArco;

    public static enum typeArch
    {
        CsubC, CsupC, CP, PC;
    }

    /**
     * Constructor de la clase
     *
     * @param origin Entero que representa al nodo que tiene como origen
     * @param destiny Entero que representa al nodo que tiene como destino
     * @param tipoArco
     */
    public Arch(int origin, int destiny, typeArch tipoArco)
    {
        this.origin = origin;
        this.destiny = destiny;
        this.tipoArco = tipoArco;
    }

    /**
     * Obtiene el id del nodo origen
     *
     * @return id nodo origen
     */
    public int getOrigin()
    {
        return this.origin;
    }

    /**
     * Obtiene el id del nodo destino
     *
     * @return id nodo destino
     */
    public int getDestiny()
    {
        return this.destiny;
    }

    /**
     * Obtiene el tipo arco
     *
     * @return tipo Arco (CsubC, CsupC, CP o PC)
     */
    public typeArch getTypeArch()
    {
        return this.tipoArco;
    }

    /**
     * Assigna el id del nodo origen
     *
     * @param origin
     */
    public void setOrigin(int origin)
    {
        this.origin = origin;
    }

    /**
     * Assigna el id del nodo destino
     *
     * @param destiny
     */
    public void setDestiny(int destiny)
    {
        this.destiny = destiny;
    }

    /**
     * Assigna el tipo de arco (CsubC, CsupC, CP o PC)
     *
     * @param tipoArco
     */
    public void setTypeArch(typeArch tipoArco)
    {
        this.tipoArco = tipoArco;
    }

    /**
     * Devuelve el valor del hash code del objeto
     *
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
     *
     * @param obj
     * @return true si los objetos son iguales, false en caso contrario
     */
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
        final Arch other = (Arch) obj;
        if(this.origin != other.origin)
        {
            return false;
        }
        if(this.destiny != other.destiny)
        {
            return false;
        }
        return this.tipoArco == other.tipoArco;
    }
}
