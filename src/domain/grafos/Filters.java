package domain.grafos;

import java.util.ArrayList;

/**
 * Clase encargada de almacenar los filtros que se han empleado para generar el
 * conjunto de comunidades.
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public final class Filters implements Cloneable
{
    private int pname, pcat, ppag, pfat, pson;

    /**
     * Contructor por defecto.
     *
     * Todos los valores son: 0-10
     *
     * @param pname importancia de nombre parecido
     * @param pcat importancia de categorias comunes
     * @param ppag importancia de paginas comunes
     * @param pfat importancia de nodos padre comunes
     * @param pson importancia de nodos hijo comunes
     */
    public Filters(int pname, int pcat, int ppag, int pfat, int pson)
    {
        this.pname = pname;
        this.pcat = pcat;
        this.ppag = ppag;
        this.pfat = pfat;
        this.pson = pson;
    }

    /**
     * Constructor alternativo
     *
     * @param filters lista de filtros a añadir
     */
    public Filters(ArrayList<String> filters)
    {
        this.loadFromFile(filters);
    }

    /**
     * Devuelve el valor de la importancia de nombre commún
     *
     * @return int velor nombre común
     */
    public int getPname()
    {
        return pname;
    }

    /**
     * Devuelve el valor de la importancia de categoria común
     *
     * @return int valor de categoria común
     */
    public int getPcat()
    {
        return pcat;
    }

    /**
     * Devuelve el valor de la importancia de pagina común
     *
     * @return int valor de la pagina común
     */
    public int getPpag()
    {
        return ppag;
    }

    /**
     * Devuelve el valor de la importancia de padre común
     *
     * @return int valor de nodo padre común
     */
    public int getPfat()
    {
        return pfat;
    }

    /**
     * Devuelve el valor de la importancea de hijo común
     *
     * @return int valor de nodo hijo común
     */
    public int getPson()
    {
        return pson;
    }
    
    /**
     * Permite salvar la información del objeto en un array de strings
     * 
     * @return lista de datos de la clase
     */
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> response = new ArrayList<>();
        response.add("FILTER");
        response.add("pname:" + this.pname);
        response.add("pcat:" + this.pcat);
        response.add("ppag:" + this.ppag);
        response.add("pfat:" + this.pfat);
        response.add("pson:" + this.pson);
        response.add("ENDFILTER");
        return response;
    }
    
    /**
     * Permite setear la información de esta clase desde un array de Strings
     * con la información del mismo
     * 
     * @param data lista de datos para completar la clase
     */
    public void loadFromFile(ArrayList<String> data)
    {
        this.pname = Integer.parseInt(data.get(1).replaceFirst("pname:", ""));
        this.pcat = Integer.parseInt(data.get(2).replaceFirst("pcat:", ""));
        this.ppag = Integer.parseInt(data.get(3).replaceFirst("ppag:", ""));
        this.pfat = Integer.parseInt(data.get(4).replaceFirst("pfat:", ""));
        this.pson = Integer.parseInt(data.get(5).replaceFirst("pson:", ""));
    }

    /**
     * Genera el numero hash asociado a este objeto
     *
     * @return int hashcode
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + this.pname;
        hash = 79 * hash + this.pcat;
        hash = 79 * hash + this.ppag;
        hash = 79 * hash + this.pfat;
        hash = 79 * hash + this.pson;
        return hash;
    }

    /**
     * Verifica si el objeto pasado por parametro es igual al objeto implicito
     *
     * @param obj object
     * @return true if obj is equals to this object
     */
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
        final Filters other = (Filters) obj;
        if(this.pname != other.pname)
        {
            return false;
        }
        if(this.pcat != other.pcat)
        {
            return false;
        }
        if(this.ppag != other.ppag)
        {
            return false;
        }
        if(this.pfat != other.pfat)
        {
            return false;
        }
        if(this.pson != other.pson)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Clona el objeto
     * 
     * @return Nueva instancia de Filters
     */
    @Override
    public Filters clone()
    {
        Object obj = null;
        try
        {
            obj = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        return (Filters) obj;
    }
}