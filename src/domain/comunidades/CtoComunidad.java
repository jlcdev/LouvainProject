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
    private String nombre;
    private boolean modificado;
    private int algoritmo;
    private ArrayList<Comunidad> ctoComunidades;
    private Filters filtros;
    private Selections selections;
    
    public CtoComunidad(ArrayList<ArrayList<Integer>> result, GrafoEntrada orig, int algorithm, Filters f, Selections selections)
    {
        this.algoritmo = algorithm;
        this.filtros = f.clone();
        this.selections = selections.clone();
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
    }
    
    public CtoComunidad()
    {}
    
    public boolean isModificado()
    {
        return this.modificado;
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
    
    public String getNombre()
    {
        return this.nombre;
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
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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
        response.add("nombre:"+this.nombre);
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
        this.nombre = data.get(0);
        this.algoritmo = Integer.parseInt(data.get(1));
        this.modificado = data.get(2).equals("true");
        int i = 3;
        ArrayList<String> load = new ArrayList<>();
        //LOAD FILTERS
        String search = data.get(i);
        while(!search.equals("ENDFILTER"))
        {
            load.add(search);
            search = data.get(i);
            ++i;
        }
        this.filtros = new Filters(load);
        load = new ArrayList<>();
        //LOAD SELECTION
        while(!search.equals("ENDSELECTION"))
        {
            load.add(search);
            search = data.get(i);
            ++i;
        }
        Selections selec = new Selections();
        selec.loadFromFile(load);
        this.selections = selec;
        
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
        return "CtoComunidad{" + "nombre=" + this.nombre + ", modificado=" + this.modificado + ", algortimo=" + this.algoritmo + ", ctoComunidades=" + this.ctoComunidades.size() + ", filtros=" + this.filtros + "}'";
    }
}