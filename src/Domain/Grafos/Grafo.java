package Domain.Grafos;

import Domain.Grafos.Arch.typeArch;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Javier
 */
public class Grafo
{
    private ArrayList<Node> vertex;
    private HashMap<Node, ArrayList<Arch>> aristas;
    
    public Grafo()
    {
        this.vertex = new ArrayList();
        this.aristas = new HashMap();
    }
    public Grafo(Grafo toClone)
    {
        this.vertex = new ArrayList(toClone.getAllVertex());
        this.aristas = new HashMap(toClone.getAllAristas());
    }
    public ArrayList<Node> getAllVertex()
    {
        return this.vertex;
    }
    public HashMap<Node, ArrayList<Arch>> getAllAristas()
    {
        return this.aristas;
    }
    
    public ArrayList<Node> getAdyacentes(Node n)
    {
        ArrayList<Node> tmp = new ArrayList();
        for(Arch arc : this.aristas.get(n))
        {
            if(arc.getOrigin().equals(n)) tmp.add(arc.getDestiny());
            else tmp.add(arc.getOrigin());
        }
        return tmp;
    }
    private void setArista(Node origin, Node destiny, typeArch type)
    {
        if(!this.vertex.contains(origin) || !this.vertex.contains(destiny)) return;
        this.aristas.get(origin).add(new Arch(origin, destiny, type));
    }
    public void inverse(Node origin, Node destiny, typeArch type)
    {
        this.setArista(origin, destiny, type);
        switch(type)
        {
            case CsupC:
                this.setArista(destiny, origin, typeArch.CsubC);
                break;
            case CsubC:
                this.setArista(destiny, origin, typeArch.CsupC);
                break;
            case PC:
                this.setArista(destiny, origin, typeArch.CP);
                break;
            case CP:
                this.setArista(destiny, origin, typeArch.PC);
                break;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Grafo)) return false;
        Grafo g = (Grafo)o;
        return this.vertex.equals(g.getAllVertex()) && this.aristas.equals(g.getAllAristas());
    }
}
