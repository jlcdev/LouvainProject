package domain.grafos;

import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Node
{
    private String Nombre;

    public Node(){}
    public Node(String nombre)
    {
        this.Nombre = nombre;
    }
    
    public String getNombre()
    {
        return this.Nombre;
    }

    public void setNombre(String Nombre)
    {
        this.Nombre = Nombre;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.Nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final Node other = (Node) obj;
        if (!Objects.equals(this.Nombre, other.Nombre)) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Node{" + "Nombre=" + Nombre + '}';
    }
}
