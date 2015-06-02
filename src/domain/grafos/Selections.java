package domain.grafos;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Selections es la clase empleada para almacenar todas aquellas categorias y
 * páginas que el usuario selecciona.
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class Selections implements Cloneable
{
    private ArrayList<Integer> categoriesSelected;
    private ArrayList<Integer> pagesSelected;

    /**
     * Contructor de Selections
     */
    public Selections()
    {
        this.categoriesSelected = new ArrayList();
        this.pagesSelected = new ArrayList();
    }

    /**
     * Devuelve toda la lista de categorias que se han seleccionado
     *
     * @return ArrayList Lista de todas las categorias seleccionadas.
     */
    public ArrayList<Integer> getCategoriesSelected()
    {
        return this.categoriesSelected;
    }

    /**
     * Permite saber cuantas categorias se han seleccionado
     *
     * @return int Devuelve cuantas categorias hay seleccionadas
     */
    public int getCategoriesSelectedSize()
    {
        return this.categoriesSelected.size();
    }

    /**
     * Permite almacenar las categorias que se han seleccionado
     *
     * @param categoriesSelected lista de categorias que se han seleccionado
     */
    public void setCategoriesSelected(ArrayList<Integer> categoriesSelected)
    {
        this.categoriesSelected = categoriesSelected;
    }

    /**
     * Devuelve toda la lista de paginas que se han seleccionado
     *
     * @return ArrayList Lista de todas las paginas seleccionadas
     */
    public ArrayList<Integer> getPagesSelected()
    {
        return this.pagesSelected;
    }

    /**
     * Permite almacenar las paginas que se han seleccionado
     *
     * @param pagesSelected lista de paginas seleccionadas
     */
    public void setPagesSelected(ArrayList<Integer> pagesSelected)
    {
        this.pagesSelected = pagesSelected;
    }

    /**
     * Permite almacenar una pagina seleccionada en concreto
     *
     * @param page id de la pagina a almacenar
     */
    public void setPage(Integer page)
    {
        if(!this.pagesSelected.contains(page))
        {
            this.pagesSelected.add(page);
        }
    }

    /**
     * Permite almacenar una categoria seleccionada en concreto
     *
     * @param category id de la categoria a almacenar
     */
    public void setCategory(Integer category)
    {
        if(!this.categoriesSelected.contains(category))
        {
            this.categoriesSelected.add(category);
        }
    }

    /**
     * Permite generar un número de hash único que será empleado por las
     * estruscturas de tipo Map para ubicarlas empleando la clave devuelta.
     *
     * @return int hash
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.categoriesSelected);
        hash = 47 * hash + Objects.hashCode(this.pagesSelected);
        return hash;
    }

    /**
     * Permite comparar dos objetos con la finalidad de establecer si son
     * iguales.
     *
     * @param obj Objeto contra el que establecer la comparación de equidad
     * @return boolean true si son iguales false cualquier otra cosa
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
        final Selections other = (Selections) obj;
        if(!Objects.equals(this.categoriesSelected, other.categoriesSelected))
        {
            return false;
        }
        if(!Objects.equals(this.pagesSelected, other.pagesSelected))
        {
            return false;
        }
        return true;
    }

    /**
     * Se utiliza para convertir los datos de esta clase en un string sin
     * emplear clases serializadas. Es una manera sencilla y rápida establecer
     * un protocolo para la conversión de datos para que posteriormente puedan
     * ser trasladados a un fichero de texto
     *
     * @return ArrayList datos de esta clase en formato string
     */
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> response = new ArrayList<>();
        response.add("SELECTIONS");
        response.add("SELECTIONCATEGORY");
        for(Integer category : this.categoriesSelected)
        {
            response.add(category.toString());
        }
        response.add("SELECTIONPAGES");
        for(Integer page : this.pagesSelected)
        {
            response.add(page.toString());
        }
        response.add("ENDSELECTION");
        return response;
    }

    /**
     * permite rellenar de información la clase Selections a partir de los datos
     * que provienen del parámetro
     *
     * @param data lista de String que contiene la información para la clase
     */
    public void loadFromFile(ArrayList<String> data)
    {
        int i = 2;
        while(!data.get(i).equals("SELECTIONPAGES"))
        {
            this.categoriesSelected.add(Integer.parseInt(data.get(i)));
            ++i;
        }
        ++i;
        while(!data.get(i).equals("ENDSELECTION"))
        {
            this.pagesSelected.add(Integer.parseInt(data.get(i)));
            ++i;
        }
    }

    /**
     * Permite obtener un objeto nuevo e identico a este.
     *
     * @return Selections copia exacta de esta clase.
     */
    @Override
    public Selections clone()
    {
        Object obj = null;
        try
        {
            obj = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        return (Selections) obj;
    }
}