/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Javier
 */
public class Comunidad
{
    private int id;
    private String nombre;
    private ArrayList<Categoria> ctoCategorias;

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
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Comunidad other = (Comunidad) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre))
        {
            return false;
        }
        return true;
    }
}

