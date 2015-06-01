package domain.comunidades;

import domain.grafos.Filters;
import domain.grafos.GrafoEntrada;
import domain.grafos.Selections;
import java.util.ArrayList;

/**
 *
 * @author Javier López Calderón
 */
public class CtoComunidad
{
    private boolean modificado;
    private int algoritmo;
    private ArrayList<Comunidad> ctoComunidades;
    private Filters filtros;
    private Selections selections;
    private int p;
    private double texec;
    
    public CtoComunidad(ArrayList<ArrayList<Integer>> result, GrafoEntrada orig, int algorithm, Filters f, Selections selections, int p, double texec)
    {
        this.algoritmo = algorithm;
        this.filtros = f.clone();
        this.selections = selections.clone();
        this.p = p;
        this.texec = texec;
        int cont = 0;
        if(this.ctoComunidades == null) this.ctoComunidades = new ArrayList<>();
        for(ArrayList<Integer> communities : result)
        {
            Comunidad community = new Comunidad(cont);
            for(Integer category : communities)
            {
                community.addCategoria(orig.getNumberCategory(category));
            }
            this.ctoComunidades.add(community);
            ++cont;
        }
        System.out.println("TIEMPO: "+this.texec);
        System.out.println("Categorias: "+this.selections.getCategoriesSelected().size());
        
    }
    
    public CtoComunidad()
    {}
    
    public boolean isModificado()
    {
        return this.modificado;
    }
    
    public boolean isEmpty()
    {
        return this.ctoComunidades.isEmpty();
    }
    
    public ArrayList<Comunidad> getCtoComunidades()
    {
        return this.ctoComunidades;
    }
    
    public ArrayList<String> getNameComunidades()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Comunidad community : this.ctoComunidades)
        {
            response.add(community.getNombre());
        }
        return response;
    }
    
    public Integer getNumComunidades()
    {
        return this.ctoComunidades.size();        
    }
    
    public Selections getSelections()
    {
        return this.selections;
    }
    
    public Filters getFiltros()
    {
        return this.filtros;
    }
    
    public Comunidad getComunidad(String comunidad)
    {
        Comunidad response = null;
        for(Comunidad community : this.ctoComunidades)
        {
            if(community.getNombre().equals(comunidad))
            {
                response = community;
                break;
            }
        }
        return response;
    }
    
    public int getP()
    {
        return this.p;
    }
    
    public double getTimeExecution()
    {
        return this.texec;
    }
    
    public int getAlgortimo()
    {
        return this.algoritmo;
    }

    public void setCtoComunidades(ArrayList<Comunidad> ctoComunidades)
    {
        this.ctoComunidades = ctoComunidades;
    }

    public void addComunidades(Comunidad c)
    {
        if(!this.ctoComunidades.contains(c))
        {
            this.ctoComunidades.add(c);
        }
    }
    
    public void removeComunidades(Comunidad c)
    {
        if(this.ctoComunidades.contains(c))
        {
            this.ctoComunidades.remove(c);
        }
    }
    
    public void removeComunidades(String name)
    {
        int index = -1;
        for(Comunidad community : this.ctoComunidades)
        {
            if(community.getNombre().equals(name))
            {
                index = this.ctoComunidades.indexOf(community);
                break;
            }
        }
        if(index != -1) this.ctoComunidades.remove(index);
    }
    
    public void setModificado(boolean modificado)
    {
        this.modificado = modificado;
    }

    public void setAlgortimo(int algoritmo)
    {
        this.algoritmo = algoritmo;
    }
    
    public ArrayList<String> savetoFile()
    {
        ArrayList<String> response = new ArrayList<>();
        response.add("algoritmo:"+this.algoritmo);
        response.add("modificado:"+this.modificado);
        
        response.addAll(this.filtros.saveToFile());
        response.addAll(this.selections.saveToFile());
        
        for(Comunidad community : this.ctoComunidades)
        {
            response.addAll(community.saveToFile());
        }
        response.add("ENDCOMMUNITIES");
        return response;
    }
    
    public void loadFromFile(ArrayList<String> data)
    {
        this.ctoComunidades = new ArrayList();
        this.algoritmo = Integer.parseInt(data.get(0).replaceFirst("algoritmo:", ""));
        this.modificado = data.get(1).replaceFirst("modificado:", "").equals("true");
        int i = 2;
        ArrayList<String> load = new ArrayList<>();
        //LOAD FILTERS
        String search = data.get(i);
        while(!search.equals("ENDFILTER"))
        {
            load.add(search);
            ++i;
            search = data.get(i);
        }
        this.filtros = new Filters(load);
        load = new ArrayList<>();
        ++i;
        search = data.get(i);
        //LOAD SELECTION
        while(!search.equals("ENDSELECTION"))
        {
            load.add(search);
            ++i;
            search = data.get(i);
        }
        load.add(search);
        Selections selec = new Selections();
        selec.loadFromFile(load);
        this.selections = selec;
        ++i;
        search = data.get(i);
        //LOAD COMMUNITIES
        while(search.equals("COMMUNITY"))
        {
            load = new ArrayList<>();
            load.add(search);
            ++i;
            search = data.get(i);
            while(!search.equals("ENDCOMMUNITY"))
            {
                load.add(search);
                ++i;
                search = data.get(i);
            }
            Comunidad community = new Comunidad();
            community.loadFromFile(load);
            this.ctoComunidades.add(community);
            ++i;
            search = data.get(i);
        }
    }
    

    @Override
    public String toString()
    {
        return "CtoComunidad{" + ", modificado=" + this.modificado + ", algortimo=" + this.algoritmo + ", ctoComunidades=" + this.ctoComunidades.size() + ", filtros=" + this.filtros + "}'";
    }
}