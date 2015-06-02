package domain.grafos;

import java.util.Objects;

/**
 * Clase base del sistema, todos los elementos del sistema se basan en esta 
 * clase para operar
 * 
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class Node
{
    private String Nombre;

    /**
     * Constructor por defecto
     */
    public Node()
    {
    }

    /**
     * Constructor con el nombre de la categoria
     *
     * @param nombre
     */
    public Node(String nombre)
    {
        this.Nombre = nombre;
    }

    /**
     * Obtiene el nombre
     *
     * @return nombre
     */
    public String getNombre()
    {
        return this.Nombre;
    }

    /**
     * Assigna el nombre
     *
     * @param Nombre
     */
    public void setNombre(String Nombre)
    {
        this.Nombre = Nombre;
    }

    /**
     * Devuelve el valor del hash code del objeto
     *
     * @return valor del hash code del objeto
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.Nombre);
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
        if(this.getClass() != obj.getClass())
        {
            return false;
        }
        final Node other = (Node) obj;
        if(!Objects.equals(this.Nombre, other.Nombre))
        {
            return false;
        }
        return true;
    }

    /**
     * Devuelve una representacion del objeto
     *
     * @return una representacion del objeto en formato string
     */
    @Override
    public String toString()
    {
        return "Node{" + "Nombre=" + Nombre + '}';
    }
}
