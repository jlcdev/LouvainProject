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
 * @author Javier López Calderón
 */
public class CtoComunidad
{
    private String nombre;
    private boolean modificado;
    private String algortimo;
    private ArrayList<Comunidad> ctoComunidades;
    Filters filtros;
    Selections selectCategories;
    Selections selectPages;

    public boolean isModificado()
    {
        return this.modificado;
    }
    
    public ArrayList<Comunidad> getCtoComunidades()
    {
        return ctoComunidades;
    }
    
    public Integer getNumComunidades()
    {
        return ctoComunidades.size();        
    }
    
    public Selections getCatSelections()
    {
        return this.selectCategories;
    }
    
    public Selections getPagSelections()
    {
        return this.selectPages;
    }
    
    public Filters getFiltros()
    {
        return filtros;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getAlgortimo()
    {
        return this.algortimo;
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
    
    public void setCatSelections(Selections selectCategories)
    {
        this.selectCategories = selectCategories;
    }
    
    public void setPagSelections(Selections selectPages)
    {
        this.selectPages = selectPages;
    }

    public void setFiltros(Filters filtros)
    {
        this.filtros = filtros;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setModificado(boolean modificado)
    {
        this.modificado = modificado;
    }

    public void setAlgortimo(String algortimo)
    {
        this.algortimo = algortimo;
    }

    @Override
    public String toString()
    {
        return "CtoComunidad{" + "nombre=" + nombre + ", modificado=" + modificado + ", algortimo=" + algortimo + ", ctoComunidades=" + ctoComunidades + ", filtros=" + filtros + ", selectCategories=" + selectCategories + ", selectPages=" + selectPages + '}';
    }
}