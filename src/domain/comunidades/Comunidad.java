package domain.comunidades;

import domain.grafos.Categoria;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Comunidad implements Cloneable
{
    private int id;
    private String nombre;
    private ArrayList<Categoria> ctoCategorias;
    
    public Comunidad()
    {
        this.ctoCategorias = new ArrayList<>();
    }
    
    public Comunidad(int i)
    {
        this.ctoCategorias = new ArrayList<>();
        this.nombre = "Comunidad"+i;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getNombre()
    {
        return this.nombre;
    }
    
    public Integer getNumCategorias()
    {
        return this.ctoCategorias.size();
    }
    
    public ArrayList<String> getNameCategories()
    {
        ArrayList<String> response = new ArrayList<>();
        for(Categoria c : this.ctoCategorias)
        {
            response.add(c.getNombre());
        }
        return response;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public void addCategoria(Categoria category)
    {
        if(!this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.add(category);
        }
    }
    
    public void addCategoria(String name)
    {
        this.addCategoria(new Categoria(name));
    }
    
    public void modCategoria(Categoria category, String newName)
    {
        if(this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.get(this.ctoCategorias.indexOf(category)).setNombre(newName);
        } 
    }
    
    public void modCategoria(String categoryName, String newName)
    {
        this.modCategoria(new Categoria(categoryName), newName);
    }
    
    public void removeCategoria(Categoria category)
    {
        if(this.ctoCategorias.contains(category))
        {
            this.ctoCategorias.remove(category);
        }
    }
    
    public void removeCategoria(String name)
    {
        this.removeCategoria(new Categoria(name));
    }
    
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("NEWCOMMUNITY");
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
    
    public void loadFromFile(ArrayList<String> data)
    {
        this.id = Integer.parseInt(data.get(1).replaceFirst("id:", ""));
        this.nombre = data.get(2).replaceFirst("name:", "");
        int size = Integer.parseInt(data.get(3).replaceFirst("size:", ""));
        for(int i=4; i < size;++i)
        {
            this.addCategoria(data.get(i));
        }
    }
    
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Comunidad other = (Comunidad) obj;
        if (this.id != other.id) return false;
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.nombre);
        hash = 61 * hash + Objects.hashCode(this.ctoCategorias);
        return hash;
    }
    
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
    
    @Override
    public String toString()
    {
        return "Comunidad: "+this.id+"  Nombre: "+this.nombre+" NºCategorías: "+this.ctoCategorias.size();
    }
}

