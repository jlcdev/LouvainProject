package domain.grafos;

import domain.grafos.Arch.typeArch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Grafo
{
    private int numVertex;
    private int numAristas;
    private ArrayList<Integer> vertexs;
    private HashMap<Integer, Node> correspondencia;
    private HashMap<Node, Integer> correspondencia2;
    private HashMap<Integer, String> translator;
    private ArrayList<ArrayList<Arch>> aristas; 
    
    
    public Grafo()
    {
        this.vertexs = new ArrayList<>();
        this.correspondencia = new HashMap<>();
        this.correspondencia2 = new HashMap<>();
        this.translator = new HashMap<>();
        this.aristas = new ArrayList<>();
        this.numVertex = 0;
        this.numAristas = 0;
    }
    
    public ArrayList<Integer> getAllVertex()
    {
        return this.vertexs;
    }
    public ArrayList<ArrayList<Arch>> getAllEdges()
    {
        return this.aristas;
    }
    public ArrayList<Integer> getAdyacents(Integer nodo)
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        for(Arch arc : this.aristas.get(nodo))
        {
            if(arc.getOrigin() == nodo) tmp.add(arc.getDestiny());
            else tmp.add(arc.getOrigin());
        }
        return tmp;
    }
    
    public ArrayList<Arch> getArcsFromNode(int node)
    {
        return this.aristas.get(node);
    }
    
    public int getNumberNode(Node n)
    {
        return this.correspondencia2.get(n);
    }
    
    public Node getNodeNumber(int i)
    {
        return this.correspondencia.get(i);
    }
    
    public String getTranslator(int i)
    {
        return this.translator.get(i);
    }
    
    public void setEdge(int origin, int destiny, String sna, String snb, typeArch type)
    {
        ArrayList<Arch> arcs = this.aristas.get(origin);
        Arch arc = new Arch(origin, destiny, sna, snb, type);
        if(!arcs.contains(arc))
        {
            arcs.add(arc);
            this.aristas.remove(origin);
            this.aristas.add(origin, arcs);
        }
    }
    
    public int setNode(Node n)
    {
        if(this.correspondencia2.containsKey(n)) return this.getNumberNode(n);
        int cont = this.vertexs.size();
        this.vertexs.add(cont);
        this.correspondencia.put(cont, n);
        this.translator.put(cont, n.getNombre());
        this.correspondencia2.put(n, cont);
        this.aristas.add(cont, new ArrayList<Arch>());
        return cont;
    }
    
    public void addNewEntry(String userInfo)
    {
        String[] data = userInfo.split("\\s+");
        Node a, b;
        if(data[1].equals("cat")) a = new Categoria(data[0]);
        else a = new Pagina(data[0]);
        if(data[4].equals("cat")) b = new Categoria(data[3]);
        else b = new Pagina(data[3]);

        int na = this.setNode(a);
        int nb = this.setNode(b);
            
        switch(data[2])
        {
            case "CsupC":
                this.setEdge(na, nb, data[0], data[3], Arch.typeArch.CsupC);
                break;
            case "CsubC":
                this.setEdge(na, nb, data[0], data[3], Arch.typeArch.CsubC);
                break;
            case "CP":
                this.setEdge(na, nb, data[0], data[3], Arch.typeArch.CP);
                break;
            case "PC":
                this.setEdge(na, nb, data[0], data[3], Arch.typeArch.PC);
                break;
        }
    }
    
    public void addMultipleEntry(ArrayList<String> list)
    {
        for(String s : list)
        {
            this.addNewEntry(s);
        }
    }
    
    public ArrayList<String> getGraphInfo()
    {
        ArrayList<String> listString = new ArrayList<>();
        String na, nb, typeArch, ta, tb;
        for(Integer n : this.getAllVertex())
        {
            for(Arch arc : this.getArcsFromNode(n))
            {
                typeArch = "CsupC";
                ta = "cat";
                tb = "cat";
                
                switch(arc.getTypeArch())
                {
                    case CsubC:
                        typeArch = "CsubC";
                        break;
                    case CP:
                        typeArch = "CP";
                        tb = "page";
                        break;
                    case PC:
                        typeArch = "PC";
                        ta = "page";
                        break;
                }
                listString.add(this.getTranslator(arc.getOrigin())+"  "+ta+"   "+typeArch+"   "+this.getTranslator(arc.getDestiny())+"   "+tb);
            }
        }
        return listString;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.numVertex;
        hash = 79 * hash + this.numAristas;
        hash = 79 * hash + Objects.hashCode(this.vertexs);
        hash = 79 * hash + Objects.hashCode(this.translator);
        hash = 79 * hash + Objects.hashCode(this.aristas);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final Grafo other = (Grafo) obj;
        if(this.numVertex != other.numVertex) return false;
        if(this.numAristas != other.numAristas) return false;
        if(!Objects.equals(this.vertexs, other.vertexs)) return false;
        if(!Objects.equals(this.translator, other.translator)) return false;
        if(!Objects.equals(this.aristas, other.aristas)) return false;
        return true;
    }
    
    
}
