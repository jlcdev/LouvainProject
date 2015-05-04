/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.comunidades;

import domain.grafos.Filters;
import domain.grafos.Selections;
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
    Filters filtros;
    Selections selecciones;

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

    public void addComunidades(Comunidad c)
    {
        if(!ctoComunidades.contains(c))ctoComunidades.add(c);
    }
    
    public void removeComunidades(Comunidad c)
    {
        if(ctoComunidades.contains(c))ctoComunidades.remove(c);
    }
    
    public Integer getNumComunidades()
    {
        return ctoComunidades.size();        
    }
    
    public Selections getSelecciones()
    {
        return selecciones;
    }

    public void setSelecciones(Selections selecciones)
    {
        this.selecciones = selecciones;
    }

    public Filters getFiltros()
    {
        return filtros;
    }

    public void setFiltros(Filters filtros)
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
    
    public String toString()
    {
        String salida;
        salida = "CtoComunidades{" + "Nombre=" + nombre + " Algoritmo=" + algortimo + " Selecciones=" + selecciones.getSelecion() + " Filtros=" + filtros.getAll() + " Comunidades=";
        for(int i = 0;i < ctoComunidades.size(); ++i){
            salida = salida + ctoComunidades.get(i).toString();
        }
        salida = salida +'}';
        return salida;
    }
}