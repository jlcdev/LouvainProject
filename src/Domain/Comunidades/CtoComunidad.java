/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Comunidades;

import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class CtoComunidad
{
    private int id;
    private String nombre;
    private boolean modificado;
    private String algortimo;
    private ArrayList<Comunidad> ctoComunidades;
    private ArrayList<String> selecciones;
    private ArrayList<String> filtros;

    public int getId()
    {
        return id;
    }

    public ArrayList<Comunidad> getCtoComunidades()
    {
        return ctoComunidades;
    }

    public void setCtoComunidades(ArrayList<Comunidad> ctoComunidades)
    {
        this.ctoComunidades = ctoComunidades;
    }

    public ArrayList<String> getSelecciones()
    {
        return selecciones;
    }

    public void setSelecciones(ArrayList<String> selecciones)
    {
        this.selecciones = selecciones;
    }

    public ArrayList<String> getFiltros()
    {
        return filtros;
    }

    public void setFiltros(ArrayList<String> filtros)
    {
        this.filtros = filtros;
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

    public boolean isModificado()
    {
        return modificado;
    }

    public void setModificado(boolean modificado)
    {
        this.modificado = modificado;
    }

    public String getAlgortimo()
    {
        return algortimo;
    }

    public void setAlgortimo(String algortimo)
    {
        this.algortimo = algortimo;
    }
    
}
