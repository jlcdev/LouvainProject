package domain.grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class GrafoEntrada implements Cloneable
{
    private Integer categoryId;
    private Integer pageId;
    private int edgeSize;
    private HashMap<Integer, Categoria> indexCategoria;
    private HashMap<Integer, Pagina> indexPagina;
    private HashMap<Categoria, Integer> categoriaIndex;
    private HashMap<Pagina, Integer> paginaIndex;
    
    private HashMap<Integer, ArrayList<Arch>> csupcEdges;
    private HashMap<Integer, ArrayList<Arch>> csubcEdges;
    private HashMap<Integer, ArrayList<Arch>> cpEdges;
    private HashMap<Integer, ArrayList<Arch>> pcEdges;
    
    public GrafoEntrada()
    {
        this.categoryId = 0;
        this.pageId = 0;
        this.edgeSize = 0;
        this.indexCategoria = new HashMap();
        this.indexPagina = new HashMap();
        this.categoriaIndex = new HashMap();
        this.paginaIndex = new HashMap();
        
        this.csubcEdges = new HashMap();
        this.csupcEdges = new HashMap();
        this.cpEdges = new HashMap();
        this.pcEdges = new HashMap();
    }
    
    public int getNumberEdges()
    {
        return this.edgeSize;
    }
    
    public int getCategorySize()
    {
        return this.indexCategoria.size();
    }
    
    public int getPageSize()
    {
        return this.indexPagina.size();
    }
    
    public void changePage(Integer page, String change)
    {
        Pagina pag = this.indexPagina.get(page);
        pag.setNombre(change);
        this.indexPagina.put(page, pag);
        this.paginaIndex.put(pag, page);
    }
    
    public void changeCategory(Integer category, String change)
    {
        Categoria cat = this.indexCategoria.get(category);
        cat.setNombre(change);
        this.indexCategoria.put(category, cat);
        this.categoriaIndex.put(cat, category);
    }
    
    public ArrayList<Integer> getCategories()
    {
        return new ArrayList(this.indexCategoria.keySet());
    }
    
    public ArrayList<Integer> getPages()
    {
        return new ArrayList(this.indexPagina.keySet());
    }
    
    public ArrayList<Integer> getCategoriesAdyacentCategories(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        if(this.csubcEdges.containsKey(node))
        {
            for(Arch arc : this.csubcEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                    response.add((Integer)temp);
            }
        }
        if(this.csupcEdges.containsKey(node))
        {
            for(Arch arc : this.csupcEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                    response.add((Integer)temp);
            }
        }
        return response;
    }
    public ArrayList<Integer> getPageAdyacentCategories(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        if(this.pcEdges.containsKey(node))
        {
            for(Arch arc : this.pcEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                    response.add((Integer)temp);
            }
        }
        return response;
    }
    
    public ArrayList<Integer> getCategoriesAdyacentPage(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        if(this.cpEdges.containsKey(node))
        {
            for(Arch arc : this.cpEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                    response.add((Integer)temp);
            }
        }
        return response;
    }
    
    public int getCategoryNumberAdyacent(Integer node)
    {
        int suma = 0;
        if(this.csubcEdges.containsKey(node))
        {
            suma += this.csubcEdges.get(node).size();
        }
        if(this.csupcEdges.containsKey(node))
        {
            suma += this.csupcEdges.get(node).size();
        }
        if(this.cpEdges.containsKey(node))
        {
            suma += this.cpEdges.get(node).size();
        }
        return suma;
    }
    
    public int getPageNumberAdyacent(Integer node)
    {
        int suma = 0;
        
        if(this.pcEdges.containsKey(node))
        {
            suma += this.pcEdges.get(node).size();
        }
        return suma;
    }
    
    public int getCsupCAdyacent(Integer node)
    {
        if(this.csupcEdges.containsKey(node))
            return this.csupcEdges.get(node).size();
        return 0;
    }
    
    public int getCsubCAdyacent(Integer node)
    {
        if(this.csubcEdges.containsKey(node))
            return this.csubcEdges.get(node).size();
        return 0;
    }
    
    public int getCsupCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.csupcEdges.containsKey(nodeA) && this.csupcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.csupcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.csupcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny()) ++suma;
                }
            }
        }
        return suma;
    }
    
    public int getCsubCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.csubcEdges.containsKey(nodeA) && this.csubcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.csubcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.csubcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny()) ++suma;
                }
            }
        }
        return suma;
    }
    
    public int getPCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.pcEdges.containsKey(nodeA) && this.pcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.pcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.pcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny()) ++suma;
                }
            }
        }
        return suma;
    }
    
    public int getCPCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.cpEdges.containsKey(nodeA) && this.cpEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.cpEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.cpEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny()) ++suma;
                }
            }
        }
        return suma;
    }
    
    public int getCategoriesCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        suma += this.getCsubCCommon(nodeA, nodeB);
        suma += this.getCsupCCommon(nodeA, nodeB);
        suma += this.getPCCommon(nodeA, nodeB);
        return suma;
    }
    
    public int getPagesCommon(Integer nodeA, Integer nodeB)
    {
        return this.getCPCommon(nodeA, nodeB);
    }
    
    public ArrayList<Arch> getCsubCArch(Integer nodeA)
    {
        if(this.csubcEdges.containsKey(nodeA))
            return this.csubcEdges.get(nodeA);
        return new ArrayList();
    }
    
    public ArrayList<Arch> getCsupCArch(Integer nodeA)
    {
        if(this.csupcEdges.containsKey(nodeA))
            return this.csupcEdges.get(nodeA);
        return new ArrayList();
    }
    
    public ArrayList<Arch> getCPArch(Integer nodeA)
    {
        if(this.cpEdges.containsKey(nodeA))
            return this.cpEdges.get(nodeA);
        return new ArrayList();
    }
    
    public ArrayList<Arch> getPCArch(Integer nodeA)
    {
        if(this.pcEdges.containsKey(nodeA))
            return this.pcEdges.get(nodeA);
        return new ArrayList();
    }
    
    public ArrayList<Arch> getCategoryArch(Integer nodeA)
    {
        ArrayList<Arch> response = new ArrayList();
        response.addAll(this.getCsupCArch(nodeA));
        response.addAll(this.getCsubCArch(nodeA));
        response.addAll(this.getCPArch(nodeA));
        return response;
    }
    
    public ArrayList<Arch> getPageArch(Integer nodeA)
    {
        ArrayList<Arch> response = new ArrayList();
        response.addAll(this.getPCArch(nodeA));
        return response;
    }
    
    public Integer getCategoryNumber(Categoria category)
    {
        if(this.categoriaIndex.containsKey(category))
        {
            return this.categoriaIndex.get(category);
        }
        return -1;
    }
    
    public Integer getPageNumber(Pagina page)
    {
        if(this.paginaIndex.containsKey(page))
        {
            return this.paginaIndex.get(page);
        }
        return -1;
    }
    
    public Categoria getNumberCategory(Integer nodeA)
    {
        if(this.indexCategoria.containsKey(nodeA))
        {
            return this.indexCategoria.get(nodeA);
        }
        return null;
    }
    
    public Pagina getNumberPage(Integer nodeA)
    {
        if(this.indexPagina.containsKey(nodeA))
        {
            return this.indexPagina.get(nodeA);
        }
        return null;
    }
    
    public String getNumberNameCategory(Integer nodeA)
    {
        Categoria category = this.getNumberCategory(nodeA);
        if(category != null) return category.getNombre();
        return "";
    }
    
    public String getNumberNamePage(Integer nodeA)
    {
        Pagina page = this.getNumberPage(nodeA);
        if(page != null) return page.getNombre();
        return "";
    }
    
    
    public void setData(String sA, String tA, String tArch, String sB, String tB)
    {
        Node A, B;
        Integer na, nb;
        Arch arc;
        if(tA.equals("cat"))
        {
            A = new Categoria(sA);
            int value = this.getCategoryNumber((Categoria) A);
            if(value == -1)
            {
                na = this.categoryId;
                this.indexCategoria.put(na, (Categoria)A);
                this.categoriaIndex.put((Categoria)A, na);
                ++this.categoryId;
            }
            else
            {
                na = value;
            }
        }
        else
        {
            A = new Pagina(sA);
            int value = this.getPageNumber((Pagina) A);
            if(value == -1)
            {
                na = this.pageId;
                this.indexPagina.put(na, (Pagina)A);
                this.paginaIndex.put((Pagina)A, na);
                ++this.pageId;
            }
            else
            {
                na = value;
            }
        }
        if(tB.equals("cat"))
        {
            B = new Categoria(sB);
            int value = this.getCategoryNumber((Categoria) B);
            if(value == -1)
            {
                nb = this.categoryId;
                this.indexCategoria.put(nb, (Categoria)B);
                this.categoriaIndex.put((Categoria)B, nb);
                ++this.categoryId;
            }
            else
            {
                nb = value;
            }
        }
        else
        {
            B = new Pagina(sB);
            int value = this.getPageNumber((Pagina) B);
            if(value == -1)
            {
                nb = this.pageId;
                this.indexPagina.put(nb, (Pagina)B);
                this.paginaIndex.put((Pagina)B, nb);
                ++this.pageId;
            }
            else
            {
                nb = value;
            }
        }
        
        switch(tArch)
        {
            case "CsubC":
                arc = new Arch(na, nb, sA, sB, Arch.typeArch.CsubC);
                if(this.csubcEdges.containsKey(na))
                {
                    ArrayList<Arch> arcs = this.csubcEdges.get(na);
                    arcs.add(arc);
                    this.csubcEdges.put(na, arcs);
                }
                else
                {
                    ArrayList<Arch> arcs = new ArrayList();
                    arcs.add(arc);
                    this.csubcEdges.put(na, arcs);
                }
                break;
            case "CsupC":
                arc = new Arch(na, nb, sA, sB, Arch.typeArch.CsupC);
                if(this.csupcEdges.containsKey(na))
                {
                    ArrayList<Arch> arcs = this.csupcEdges.get(na);
                    arcs.add(arc);
                    this.csupcEdges.put(na, arcs);
                }
                else
                {
                    ArrayList<Arch> arcs = new ArrayList();
                    arcs.add(arc);
                    this.csupcEdges.put(na, arcs);
                }
                break;
            case "CP":
                arc = new Arch(na, nb, sA, sB, Arch.typeArch.CP);
                if(this.cpEdges.containsKey(na))
                {
                    ArrayList<Arch> arcs = this.cpEdges.get(na);
                    arcs.add(arc);
                    this.cpEdges.put(na, arcs);
                }
                else
                {
                    ArrayList<Arch> arcs = new ArrayList();
                    arcs.add(arc);
                    this.cpEdges.put(na, arcs);
                }
                break;
            case "PC":
                arc = new Arch(na, nb, sA, sB, Arch.typeArch.PC);
                if(this.pcEdges.containsKey(na))
                {
                    ArrayList<Arch> arcs = this.pcEdges.get(na);
                    arcs.add(arc);
                    this.pcEdges.put(na, arcs);
                }
                else
                {
                    ArrayList<Arch> arcs = new ArrayList();
                    arcs.add(arc);
                    this.pcEdges.put(na, arcs);
                }
                break;
        }
        ++this.edgeSize;
    }
    
    public void loadFromFile(ArrayList<String> list)
    {
        for(String s : list)
        {
            String data[] = s.split("\\s+");
            this.setData(data[0], data[1], data[2], data[3], data[4]);
        }
    }
    
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> response = new ArrayList();
        Iterator<Entry<Integer, ArrayList<Arch>>> it;
        it = this.csubcEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                response.add(arc.toString());
            }
        }
        it = this.csupcEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                response.add(arc.toString());
            }
        }
        it = this.cpEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                response.add(arc.toString());
            }
        }
        it = this.pcEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                response.add(arc.toString());
            }
        }
        return response;
    }
    
    private void removeDestiny(Integer destiny, HashMap<Integer, ArrayList<Arch>> edges)
    {
        Iterator<Entry<Integer, ArrayList<Arch>>> it = edges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            Integer origin = entry.getKey();
            ArrayList<Arch> arcs = entry.getValue();
            Iterator<Arch> at = arcs.iterator();
            while(at.hasNext())
            {
                Arch arc = at.next();
                if(arc.getDestiny() == destiny)
                {
                    at.remove();
                    --this.edgeSize;
                }
            }
            edges.put(origin, arcs);
        }
    }
    
    public void removeCategoria(Categoria category)
    {
        Integer numCategory = this.categoriaIndex.remove(category);
        this.indexCategoria.remove(numCategory);
        this.csubcEdges.remove(numCategory);
        this.csupcEdges.remove(numCategory);
        this.cpEdges.remove(numCategory);
        this.removeDestiny(numCategory, this.pcEdges);
        this.removeDestiny(numCategory, this.csubcEdges);
        this.removeDestiny(numCategory, this.csupcEdges);
    }
    
    public void removePagina(Pagina page)
    {
        Integer numPage = this.paginaIndex.remove(page);
        this.indexPagina.remove(numPage);
        this.pcEdges.remove(numPage);
        this.removeDestiny(numPage, this.cpEdges);
    }
    
    public void removeArchPageCategory(Integer page, Integer category)
    {
        if(this.pcEdges.containsKey(page))
        {
            ArrayList<Arch> arcs = this.pcEdges.get(page);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == category)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }
    
    public void removeArchCategoryPage(Integer category, Integer page)
    {
        if(this.cpEdges.containsKey(category))
        {
            ArrayList<Arch> arcs = this.cpEdges.get(category);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == page)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }
    
    public void removeArchCategorySubCategory(Integer categoryA, Integer categoryB)
    {
        if(this.csubcEdges.containsKey(categoryA))
        {
            ArrayList<Arch> arcs = this.csubcEdges.get(categoryA);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == categoryB)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }
    
    public void removeArchCategorySupCategory(Integer categoryA, Integer categoryB)
    {
        if(this.csupcEdges.containsKey(categoryA))
        {
            ArrayList<Arch> arcs = this.csupcEdges.get(categoryA);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == categoryB)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }
    
    private void addArchPageCategory(Integer page, Integer category, Arch arc)
    {
        if(this.indexPagina.containsKey(page) && this.indexCategoria.containsKey(category))
        {
            if(this.pcEdges.get(page).contains(arc))
            {
                this.pcEdges.get(page).set(this.pcEdges.get(page).indexOf(arc), arc);
            }
            else
            {
                this.pcEdges.get(page).add(arc);
            }
            this.pcEdges.put(page, this.pcEdges.get(page));
        }
    }
    
    private void addArchCategoryPage(Integer category, Integer page, Arch arc)
    {
        if(this.indexPagina.containsKey(page) && this.indexCategoria.containsKey(category))
        {
            if(this.cpEdges.get(page).contains(arc))
            {
                this.cpEdges.get(page).set(this.cpEdges.get(page).indexOf(arc), arc);
            }
            else
            {
                this.cpEdges.get(page).add(arc);
            }
            this.cpEdges.put(page, this.cpEdges.get(page));
        }
    }
    
    private void addArchCategorySubCategory(Integer categoryA, Integer CategoryB, Arch arc)
    {
        if(this.indexCategoria.containsKey(categoryA) && this.indexCategoria.containsKey(CategoryB))
        {
            if(this.csubcEdges.get(categoryA).contains(arc))
            {
                this.csubcEdges.get(categoryA).set(this.csubcEdges.get(categoryA).indexOf(arc), arc);
            }
            else
            {
                this.csubcEdges.get(categoryA).add(arc);
            }
            this.csubcEdges.put(categoryA, this.csubcEdges.get(categoryA));
        }
    }
    
    private void addArchCategorySupCategory(Integer categoryA, Integer CategoryB, Arch arc)
    {
        if(this.indexCategoria.containsKey(categoryA) && this.indexCategoria.containsKey(CategoryB))
        {
            if(this.csupcEdges.get(categoryA).contains(arc))
            {
                this.csupcEdges.get(categoryA).set(this.csupcEdges.get(categoryA).indexOf(arc), arc);
            }
            else
            {
                this.csupcEdges.get(categoryA).add(arc);
            }
            this.csupcEdges.put(categoryA, this.csupcEdges.get(categoryA));
        }
    }
    
    public void addArch(Arch arc)
    {
        Integer origin = arc.getOrigin();
        Integer destiny = arc.getDestiny();
        Arch.typeArch tipo = arc.getTypeArch();
        switch(tipo)
        {
            case CsupC:
                this.addArchCategorySupCategory(origin, destiny, arc);
                break;
            case CsubC:
                this.addArchCategorySubCategory(origin, destiny, arc);
                break;
            case CP:
                this.addArchCategoryPage(origin, destiny, arc);
                break;
            case PC:
                this.addArchPageCategory(origin, destiny, arc);
                break;
        }
    }
    
    public void addCategoria(Categoria category)
    {
        int value = this.getCategoryNumber(category);
        if(value == -1)
        {
            this.indexCategoria.put(this.categoryId, category);
            this.categoriaIndex.put(category, this.categoryId);
            ++this.categoryId;
        }
    }
    
    public void addPagina(Pagina page)
    {
        int value = this.getPageNumber(page);
        if(value == -1)
        {
            this.indexPagina.put(this.pageId, page);
            this.paginaIndex.put(page, this.pageId);
            ++this.pageId;
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.categoryId);
        hash = 67 * hash + Objects.hashCode(this.pageId);
        hash = 67 * hash + this.edgeSize;
        hash = 67 * hash + Objects.hashCode(this.indexCategoria);
        hash = 67 * hash + Objects.hashCode(this.indexPagina);
        hash = 67 * hash + Objects.hashCode(this.categoriaIndex);
        hash = 67 * hash + Objects.hashCode(this.paginaIndex);
        hash = 67 * hash + Objects.hashCode(this.csupcEdges);
        hash = 67 * hash + Objects.hashCode(this.csubcEdges);
        hash = 67 * hash + Objects.hashCode(this.cpEdges);
        hash = 67 * hash + Objects.hashCode(this.pcEdges);
        return hash;
    }

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
        final GrafoEntrada other = (GrafoEntrada) obj;
        if(!Objects.equals(this.categoryId, other.categoryId))
        {
            return false;
        }
        if(!Objects.equals(this.pageId, other.pageId))
        {
            return false;
        }
        if(this.edgeSize != other.edgeSize)
        {
            return false;
        }
        if(!Objects.equals(this.indexCategoria, other.indexCategoria))
        {
            return false;
        }
        if(!Objects.equals(this.indexPagina, other.indexPagina))
        {
            return false;
        }
        if(!Objects.equals(this.categoriaIndex, other.categoriaIndex))
        {
            return false;
        }
        if(!Objects.equals(this.paginaIndex, other.paginaIndex))
        {
            return false;
        }
        if(!Objects.equals(this.csupcEdges, other.csupcEdges))
        {
            return false;
        }
        if(!Objects.equals(this.csubcEdges, other.csubcEdges))
        {
            return false;
        }
        if(!Objects.equals(this.cpEdges, other.cpEdges))
        {
            return false;
        }
        if(!Objects.equals(this.pcEdges, other.pcEdges))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public GrafoEntrada clone()
    {
        GrafoEntrada obj = null;
        try
        {
            obj = (GrafoEntrada)super.clone();
            obj.categoriaIndex = (HashMap<Categoria, Integer>) categoriaIndex.clone();
            obj.categoryId = new Integer(categoryId);
            obj.cpEdges = (HashMap<Integer, ArrayList<Arch>>) cpEdges.clone();
            obj.csubcEdges = (HashMap<Integer, ArrayList<Arch>>) csubcEdges.clone();
            obj.csupcEdges = (HashMap<Integer, ArrayList<Arch>>) csupcEdges.clone();
            obj.edgeSize = edgeSize;
            obj.indexCategoria = (HashMap<Integer, Categoria>) indexCategoria.clone();
            obj.indexPagina = (HashMap<Integer, Pagina>) indexPagina.clone();
            obj.pageId = new Integer(pageId);
            obj.paginaIndex = (HashMap<Pagina, Integer>) paginaIndex.clone();
            obj.pcEdges = (HashMap<Integer, ArrayList<Arch>>) pcEdges.clone();
        }
        catch(CloneNotSupportedException e){}
        return obj;
    }
}
