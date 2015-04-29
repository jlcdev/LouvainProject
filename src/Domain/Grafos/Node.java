package Domain.Grafos;

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
    public boolean equals(Object o)
    {
        if(!(o instanceof Node)) return false;
        Node n = (Node)o;
        return this.Nombre.equalsIgnoreCase(n.getNombre());
    }
}
