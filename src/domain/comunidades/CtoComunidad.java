package domain.comunidades;

import domain.grafos.Filters;
import domain.grafos.Grafo;
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
    private int algoritmo;
    private ArrayList<Comunidad> ctoComunidades;
    private Filters filtros;
    private Selections selectCategories;
    private Selections selectPages;
    
    public CtoComunidad(ArrayList<ArrayList<Integer>> result, Grafo orig, int algorithm, Filters f, Selections cat, Selections pag)
    {
        this.algoritmo = algorithm;
        this.filtros = f.clone();
        this.selectCategories = cat.clone();
        this.selectPages = pag.clone();
        int size = result.size();
        this.ctoComunidades = new ArrayList<>();
        Comunidad c;
        for(int i=0; i < size;++i)
        {
            c = new Comunidad();
            for(int num : result.get(i))
            {
                c.addCategoria(orig.getTranslator(num));
            }
            this.ctoComunidades.add(c);
        }
    }
    

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
    
    public int getAlgortimo()
    {
        return this.algoritmo;
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

    public void setAlgortimo(int algoritmo)
    {
        this.algoritmo = algoritmo;
    }

    @Override
    public String toString()
    {
        return "CtoComunidad{" + "nombre=" + nombre + ", modificado=" + modificado + ", algortimo=" + algoritmo + ", ctoComunidades=" + ctoComunidades + ", filtros=" + filtros + ", selectCategories=" + selectCategories + ", selectPages=" + selectPages + '}';
    }
}