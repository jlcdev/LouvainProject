package domain.grafos;

/**
 *
 * @author Javier
 */
public class Pagina extends Node
{
    /**
     * Constructor por defecto
     */
    public Pagina()
    {
        super();
    }
    
    /**
     * Constructor con el nombre de la categoria
     * @param nombre 
     */
    public Pagina(String nombre)
    {
        super(nombre);
    }

    /**
     * Indica si los dos objetos son iguales
     * @param obj
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Pagina)) return false;
        return super.equals(obj);
    }

    /**
     * Devuelve el valor del hash code del objeto
     * @return valor del hash code del objeto
     */
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}