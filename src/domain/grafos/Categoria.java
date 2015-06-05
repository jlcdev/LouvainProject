package domain.grafos;

/**
 * La clase Categoria es una especialización de la clase Nodo y se emplea con
 * aquellos elementos que son creados como Categorias.
 *
 * @author Albert Campano
 * @version 1.0
 * @since 1/06/2015
 */
public class Categoria extends Node
{
    /**
     * Constructor por defecto
     */
    public Categoria()
    {
        super();
    }

    /**
     * Constructor con el nombre de la categoria
     *
     * @param nombre
     */
    public Categoria(String nombre)
    {
        super(nombre);
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
        if(!(obj instanceof Categoria))
        {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Devuelve el valor del hash code del objeto
     *
     * @return valor del hash code del objeto
     */
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}