package domain.comunidades;

import domain.grafos.Categoria;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Comunidad
{
    private int id;
    private String nombre;
    private ArrayList<Categoria> ctoCategorias;
    
    public Comunidad()
    {
        this.ctoCategorias = new ArrayList<>();
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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
    
    public void addCategoria(Categoria cat)
    {
        if(!ctoCategorias.contains(cat)) ctoCategorias.add(cat);
    }
    
    public void addCategoria(String name)
    {
        Categoria cat = new Categoria(name);
        if(!ctoCategorias.contains(cat)) ctoCategorias.add(cat);
    }
    
    public void removeCategoria(Categoria cat)
    {
        if(ctoCategorias.contains(cat))ctoCategorias.remove(cat);
    }
    
    public void removeCategoria(String name)
    {
        for(int i = 0;i < ctoCategorias.size();++i)
        {
            if(ctoCategorias.get(i).getNombre() == name)
                ctoCategorias.remove(ctoCategorias.get(i));
        }
    }
    
    public void modCategoria(Categoria cat,String nombre)
    {
        Integer index = ctoCategorias.indexOf(cat);
        if(index >= 0)ctoCategorias.get(index).setNombre(nombre);  
    }
    
    public void modCategoria(String cat, String nombre)
    {
        for(int i = 0;i < ctoCategorias.size(); ++i)
            if(ctoCategorias.get(i).getNombre() == cat)
                ctoCategorias.get(i).setNombre(nombre);
    }
    public Integer getNumCategorias()
    {
        return ctoCategorias.size();
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
    
    @Override
    public String toString()
    {
        return "Comunidad: "+this.id+"  Nombre: "+this.nombre+" NºCategorías: "+this.ctoCategorias.size();
    }
    
}

