package domain.comunidades;

import domain.grafos.Categoria;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase para almacenar todas las categorias que forman parte de una comunidad
 * 
 * @author Alfred Parellada
 * @version 1.0
 * @since 01/06/2015
 */
public class Comunidad implements Cloneable
{
    private int id;
    private String nombre;
    private ArrayList<Categoria> ctoCategorias;
    
    /**
     * Constructor por defecto
     */
    public Comunidad()
    {
        this.ctoCategorias = new ArrayList<>();
    }
    
    /**
     * Crea una comunidad y además le añade un nombre
     * 
     * @param i 
     */
    public Comunidad(int i)
    {
        this.ctoCategorias = new ArrayList<>();
        this.nombre = "Comunidad"+i;
    }
    
    /**
     * Obtiene el id de la comunidad
     * 
     * @return id de la comunidad
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * Obtiene el nombre de la comunidad
     * 
     * @return nombre de la comunidad
     */
    public String getNombre()
    {
        return this.nombre;
    }
    
    /**
     * Obtiene el numero total de categorias presentes en esta comunidad
     * 
     * @return numero de categorias
     */
    public Integer getNumCategorias()
    {
        return this.ctoCategorias.size();
    }
    
    /**
     * Obtiene los nombres de todos los elementos de esta comunidad
     * 
     * @return lista con los nombre de las categorias de esta comunidad
     */
    public ArrayList<String> getNameCategories()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Categoria c : this.ctoCategorias)
        {
            response.add(c.getNombre());
        }
        return response;
    }
    
    /**
     * Añade un identificador a esta comunidad
     * 
     * @param id identificador de esta comunidad
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * Añade un nombre a esta comunidad
     * 
     * @param nombre nombre para esta comunidad
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    /**
     * Añade una categoria al conjunto de comunidad
     * 
     * @param category categoria que se desea añadir
     */
    public void addCategoria(Categoria category)
    {
        if(!this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.add(category);
        }
    }
    
    /**
     * Añade una categoria a la comunidad
     * 
     * @param name nombre de la comunidad
     * @return 
     */
    public boolean addCategoria(String name)
    {
        Categoria category = new Categoria(name);
        if(!this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.add(category);
            return true;
        }
        return false;
    }
    
    /**
     * Modifica una categoria de la comunidad
     * 
     * @param category categoria a modificar
     * @param newName nombre nuevo
     */
    public void modCategoria(Categoria category, String newName)
    {
        if(this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.get(this.ctoCategorias.indexOf(category)).setNombre(newName);
        } 
    }
    
    /**
     * Modifica una categoria de la comunidad
     * 
     * @param categoryName nombre de la categoria
     * @param newName nombre nuevo
     */
    public void modCategoria(String categoryName, String newName)
    {
        this.modCategoria(new Categoria(categoryName), newName);
    }
    
    /**
     * Borra una categoria de la comunidad
     * 
     * @param category categoria a eliminar
     */
    public void removeCategoria(Categoria category)
    {
        if(this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.remove(category);
        }
    }
    
    /**
     * Borra una categoria de la comunidad
     * 
     * @param name nombre de la categoria a eliminar
     */
    public boolean removeCategoria(String name)
    {
        Categoria category = new Categoria(name);
        if(this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.remove(category);
            return true;
        }
        return false;
    }
    
    /**
     * Traduce los elementos de esta comunidad a una lista de Strings para 
     * poder almacenar en un fichero
     * 
     * @return lista de datos de la comunidad
     */
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("COMMUNITY");
        list.add("id:"+this.id);
        list.add("name:"+this.nombre);
        list.add("size:"+this.ctoCategorias.size());
        for(Categoria category : this.ctoCategorias)
        {
            list.add(category.getNombre());
        }
        list.add("ENDCOMMUNITY");
        return list;
    }
    
    /**
     * Traduce los datos de una lista de Strings a informacion de comunidades
     * @param data lista de datos
     */
    public void loadFromFile(ArrayList<String> data)
    {
        this.id = Integer.parseInt(data.get(1).replaceFirst("id:", ""));
        this.nombre = data.get(2).replaceFirst("name:", "");
        int size = Integer.parseInt(data.get(3).replaceFirst("size:", ""));
        size += 4;
        for(int i=4; i < size;++i)
        {
            this.addCategoria(data.get(i));
        }
    }
    
    /**
     * Permite comparar dos comunidades
     * 
     * @param obj conjunto 2
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Comunidad other = (Comunidad) obj;
        if (this.id != other.id) return false;
        return Objects.equals(this.nombre, other.nombre);
    }
    
    /**
     * Permite generar un hash propio para esta comunidad
     * 
     * @return hash
     */
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.nombre);
        hash = 61 * hash + Objects.hashCode(this.ctoCategorias);
        return hash;
    }
    
    /**
     * Clona la clase creando una instancia nueva con el mismo contenido
     * 
     * @return clon de la clase
     */
    @Override
    public Comunidad clone()
    {
        Comunidad obj = null;
        try
        {
            obj = (Comunidad) super.clone();
            obj.id = this.id;
            obj.nombre = this.nombre;
            obj.ctoCategorias = (ArrayList<Categoria>)this.ctoCategorias.clone();
        }catch(CloneNotSupportedException e)
        {
        }
        return obj;
    }
    
    /**
     * Representa parte de la información de esta clase en una linea de texto
     * @return String con informacion de la comunidad
     */
    @Override
    public String toString()
    {
        return "Comunidad: "+this.id+"  Nombre: "+this.nombre+" NºCategorías: "+this.ctoCategorias.size();
    }
}

