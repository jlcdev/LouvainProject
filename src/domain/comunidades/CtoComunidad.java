package domain.comunidades;

import domain.grafos.Filters;
import domain.grafos.GrafoEntrada;
import domain.grafos.Selections;
import java.util.ArrayList;

/**
 * Clase que maneja y almacena todas la comunidades generadas
 *
 * @author Joan Rodas
 * @version 1.0
 * @since 01/06/2015
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

    /**
     * Crea un nuevo conjunto de comunidad con todos los datos
     *
     * @param result conjunto crudo de comunidades
     * @param orig grafo de entrada para realizar las traducciones
     * @param algorithm que algoritmo ha sido empleado
     * @param f que filtros de han seleccionado
     * @param selections criterios de seleccion empleados
     * @param p que factor de cohesion se ha empleado
     * @param texec cuando ha durado el calculo
     */
    public CtoComunidad(ArrayList<ArrayList<Integer>> result, GrafoEntrada orig, int algorithm, Filters f, Selections selections, int p, double texec)
    {
        this.algoritmo = algorithm;
        this.filtros = f.clone();
        this.selections = selections.clone();
        this.p = p;
        this.texec = texec;
        int cont = 0;
        if(this.ctoComunidades == null)
        {
            this.ctoComunidades = new ArrayList<>();
        }
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
        System.out.println("TIEMPO: " + this.texec);
        System.out.println("Categorias: " + this.selections.getCategoriesSelected().size());
    }

    /**
     * Constructor por defecto
     */
    public CtoComunidad()
    {
    }

    /**
     * Verifica se el conjunto de comunidades se ha modificado
     *
     * @return true si se ha modificado
     */
    public boolean isModificado()
    {
        return this.modificado;
    }

    /**
     * Verifica si el conjunto de comunidades está vacio
     *
     * @return true si está vacio
     */
    public boolean isEmpty()
    {
        return this.ctoComunidades.isEmpty();
    }

    /**
     * Obtiene todas las comunidades
     *
     * @return lista de comunidades
     */
    public ArrayList<Comunidad> getCtoComunidades()
    {
        return this.ctoComunidades;
    }

    /**
     * Obtiene la lista de los nombres de las comunidades
     *
     * @return lista de comunidades
     */
    public ArrayList<String> getNameComunidades()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Comunidad community : this.ctoComunidades)
        {
            response.add(community.getNombre());
        }
        return response;
    }

    /**
     * Obtiene el numero de comunidades que hay
     *
     * @return total de comunidades en este conjunto
     */
    public Integer getNumComunidades()
    {
        return this.ctoComunidades.size();
    }

    /**
     * Obtiene el criterio de seleccion empleado
     *
     * @return Criterio de seleccion
     */
    public Selections getSelections()
    {
        return this.selections;
    }

    /**
     * Obtiene los filtros empleados
     *
     * @return filtros
     */
    public Filters getFiltros()
    {
        return this.filtros;
    }

    /**
     * Devuelve la comunidad buscando por su nombre
     *
     * @param comunidad nombre de la comunidad
     * @return comunidad
     */
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

    /**
     * Obtiene el factor de cohesion empleado
     *
     * @return factor de cohesion
     */
    public int getP()
    {
        return this.p;
    }

    /**
     * Obtiene el tiempo que se ha tardado en procesar el conjunto de comunidad
     *
     * @return tiempo de procesado
     */
    public double getTimeExecution()
    {
        return this.texec;
    }

    /**
     * Devuelve el numero del algoritmo empleado
     *
     * @return numero del algoritmo
     */
    public int getAlgortimo()
    {
        return this.algoritmo;
    }

    /**
     * Sobreescribe la lista de comunidades por una nueva
     *
     * @param ctoComunidades lista de comunidades
     */
    public void setCtoComunidades(ArrayList<Comunidad> ctoComunidades)
    {
        this.ctoComunidades = ctoComunidades;
    }

    /**
     * Añade una nueva comunidad
     *
     * @param c comunidad a añadir
     * @return true si se ha podido añadir
     */
    public boolean addComunidades(Comunidad c)
    {
        if(!this.ctoComunidades.contains(c))
        {
            this.ctoComunidades.add(c);
            return true;
        }
        return false;
    }

    /**
     * Elimina una comunidad
     *
     * @param c comunidad a eliminar
     */
    public void removeComunidades(Comunidad c)
    {
        if(this.ctoComunidades.contains(c))
        {
            this.ctoComunidades.remove(c);
        }
    }

    /**
     * Elimina una comunidad buscandola por el nombre
     *
     * @param name nombre de la comunidad
     * @return numero de indice de la comunidad
     */
    public int removeComunidades(String name)
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
        if(index != -1)
        {
            this.ctoComunidades.remove(index);
            return index;
        }
        return index;
    }

    /**
     * Cambia el estado de modificado
     *
     * @param modificado true si hay modificacion
     */
    public void setModificado(boolean modificado)
    {
        this.modificado = modificado;
    }

    /**
     * Sobreescribe el numero de algoritmo empleado por uno nuevo
     *
     * @param algoritmo numero de algoritmo
     */
    public void setAlgortimo(int algoritmo)
    {
        this.algoritmo = algoritmo;
    }

    /**
     * Transforma la información del conjunto en una lista de Strings
     *
     * @return lista de Strings
     */
    public ArrayList<String> savetoFile()
    {
        ArrayList<String> response = new ArrayList<>();
        response.add("algoritmo:" + this.algoritmo);
        response.add("modificado:" + this.modificado);
        response.add("p:" + this.p);
        response.add("texec:" + this.texec);
        response.addAll(this.filtros.saveToFile());
        response.addAll(this.selections.saveToFile());
        for(Comunidad community : this.ctoComunidades)
        {
            response.addAll(community.saveToFile());
        }
        response.add("ENDCOMMUNITIES");
        return response;
    }

    /**
     * Llena la información de este conjunto de comunidades con elementos de una
     * lista de Strings
     *
     * @param data datos de entrada
     */
    public void loadFromFile(ArrayList<String> data)
    {
        this.ctoComunidades = new ArrayList();
        this.algoritmo = Integer.parseInt(data.get(0).replaceFirst("algoritmo:", ""));
        this.modificado = data.get(1).replaceFirst("modificado:", "").equals("true");
        this.p = Integer.parseInt(data.get(2).replaceFirst("p:", ""));
        this.texec = Double.parseDouble(data.get(3).replaceFirst("texec:", ""));
        int i = 4;
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

    /**
     * Muestra informacion acerca del conjunto de comunidad
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "CtoComunidad{" + ", modificado=" + this.modificado + ", algortimo=" + this.algoritmo + ", ctoComunidades=" + this.ctoComunidades.size() + ", filtros=" + this.filtros + "}'";
    }
}