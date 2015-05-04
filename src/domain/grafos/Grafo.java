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
    private HashMap<String, Integer> translator2;
    private ArrayList<ArrayList<Arch>> aristas; 
    
    
    public Grafo()
    {
        this.vertexs = new ArrayList<>();
        this.correspondencia = new HashMap<>();
        this.correspondencia2 = new HashMap<>();
        this.translator = new HashMap<>();
        this.translator2 = new HashMap<>();
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
    public ArrayList<Integer> getAdyacents(Integer node)
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        for(Arch arc : this.aristas.get(node))
        {
            if(arc.getOrigin() == node) tmp.add(arc.getDestiny());
            else tmp.add(arc.getOrigin());
        }
        return tmp;
    }
    
    public int getNumTotalAdyacent(int node)
    {
        return this.aristas.get(node).size();
    }
    
    public int getNumPagAdyacent(int node)
    {
        int cont = 0;
        for(Arch arc : this.aristas.get(node))
        {
            if(arc.getOrigin() == node)
            {
                if(this.correspondencia.get(arc.getDestiny()).getClass() == Pagina.class) ++cont;
            }
            else
            {
                if(this.correspondencia.get(arc.getOrigin()).getClass() == Pagina.class) ++cont;
            }
        }
        return cont;
    }
    
    public int getNumCatAdyacent(int node)
    {
        int cont = 0;
        for(Arch arc : this.aristas.get(node))
        {
            if(arc.getOrigin() == node)
            {
                if(this.correspondencia.get(arc.getDestiny()).getClass() == Categoria.class) ++cont;
            }
            else
            {
                if(this.correspondencia.get(arc.getOrigin()).getClass() == Categoria.class) ++cont;
            }
        }
        return cont;
    }
    
    public int getNumCsupCAdyacent(int node)
    {
        int cont = 0;
        for(Arch arc : this.aristas.get(node))
        {
            if(arc.getTypeArch() == Arch.typeArch.CsupC) ++cont;
        }
        return cont;
    }
    
    public int getNumCsubCAdyacent(int node)
    {
        int cont = 0;
        for(Arch arc : this.aristas.get(node))
        {
            if(arc.getTypeArch() == Arch.typeArch.CsubC) ++cont;
        }
        return cont;
    }
    
    public int getNumCommonCsupCAdyacent(int ni, int nj)
    {
        int cont = 0;
        for(Arch arci : this.aristas.get(ni))
        {
            if(arci.getTypeArch() != Arch.typeArch.CsupC) continue;
            for(Arch arcj : this.aristas.get(nj))
            {
                if(arcj.getTypeArch() != Arch.typeArch.CsupC) continue;
                if(arci.getOrigin() == ni)
                {
                    if(arci.getDestiny() == arcj.getDestiny()) ++cont;
                }
                else
                {
                    if(arci.getOrigin() == arcj.getOrigin()) ++cont;
                }
            }
        }
        return cont;
    }
    
    public int getNumCommonCsubCAdyacent(int ni, int nj)
    {
        int cont = 0;
        for(Arch arci : this.aristas.get(ni))
        {
            if(arci.getTypeArch() != Arch.typeArch.CsubC) continue;
            for(Arch arcj : this.aristas.get(nj))
            {
                if(arcj.getTypeArch() != Arch.typeArch.CsubC) continue;
                if(arci.getOrigin() == ni)
                {
                    if(arci.getDestiny() == arcj.getDestiny()) ++cont;
                }
                else
                {
                    if(arci.getOrigin() == arcj.getOrigin()) ++cont;
                }
            }
        }
        return cont;
    }
    
    public int getNumCommonCatAdyacent(int ni, int nj)
    {
        int cont = 0;
        for(Arch arci : this.aristas.get(ni))
        {
            if(arci.getTypeArch() == Arch.typeArch.CP) continue;
            for(Arch arcj : this.aristas.get(nj))
            {
                if(arcj.getTypeArch() == Arch.typeArch.CP) continue;
                if(arci.getOrigin() == ni)
                {
                    if(this.translator.get(arci.getDestiny()).equals(this.translator.get(arcj.getDestiny()))) ++cont;
                }
                else
                {
                    if(this.translator.get(arci.getOrigin()).equals(this.translator.get(arcj.getOrigin()))) ++cont;
                }
            }
        }
        return cont;
    }
    
    public int getNumCommonPagAdyacent(int ni, int nj)
    {
        int cont = 0;
        for(Arch arci : this.aristas.get(ni))
        {
            if(arci.getTypeArch() != Arch.typeArch.CP) continue;
            for(Arch arcj : this.aristas.get(nj))
            {
                if(arcj.getTypeArch() != Arch.typeArch.CP) continue;
                if(arci.getOrigin() == ni)
                {
                    if(this.translator.get(arci.getDestiny()).equals(this.translator.get(arcj.getDestiny()))) ++cont;
                }
                else
                {
                    if(this.translator.get(arci.getOrigin()).equals(this.translator.get(arcj.getOrigin()))) ++cont;
                }
            }
        }
        return cont;
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
            ++this.numAristas;
        }
    }
    
    public int setNode(Node n)
    {
        if(this.correspondencia2.containsKey(n)) return this.getNumberNode(n);
        int cont = this.vertexs.size();
        this.vertexs.add(cont);
        this.correspondencia.put(cont, n);
        this.translator.put(cont, n.getNombre());
        this.translator2.put(n.getNombre(), cont);
        this.correspondencia2.put(n, cont);
        this.aristas.add(cont, new ArrayList<Arch>());
        ++this.numVertex;
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
    
    public int getNumVertex()
    {
        return this.numVertex;
    }
    
    public int getNumAristas()
    {
        return this.numAristas;
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
    
    public void remove(String name)
    {
        int num = this.translator2.get(name);
        this.translator2.remove(name);
        this.translator.remove(num);
        for(ArrayList<Arch> listArch : this.aristas)
        {
            for(Arch arc : listArch)
            {
                if(arc.getOrigin() == num || arc.getDestiny() == num)
                {
                    listArch.remove(arc);
                    --this.numAristas;
                }
            }
        }
        --this.numVertex;
        Node n = this.correspondencia.get(num);
        this.correspondencia2.remove(n);
        this.correspondencia.remove(num);
        this.vertexs.remove(num);
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
