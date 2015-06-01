package domain.grafos;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Javier López Calderón
 */
public class Selections implements Cloneable
{
    private ArrayList<Integer> categoriesSelected;
    private ArrayList<Integer> pagesSelected;
    
    public Selections()
    {
        this.categoriesSelected = new ArrayList();
        this.pagesSelected = new ArrayList();
    }
    
    public ArrayList<Integer> getCategoriesSelected()
    {
        return this.categoriesSelected;
    }

    public void setCategoriesSelected(ArrayList<Integer> categoriesSelected)
    {
        this.categoriesSelected = categoriesSelected;
    }

    public ArrayList<Integer> getPagesSelected()
    {
        return this.pagesSelected;
    }

    public void setPagesSelected(ArrayList<Integer> pagesSelected)
    {
        this.pagesSelected = pagesSelected;
    }
    
    public void setPage(Integer page)
    {
        if(!this.pagesSelected.contains(page))
        {
            this.pagesSelected.add(page);
        }
    }
    
    public void setCategory(Integer category)
    {
        if(!this.categoriesSelected.contains(category))
        {
            this.categoriesSelected.add(category);
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.categoriesSelected);
        hash = 47 * hash + Objects.hashCode(this.pagesSelected);
        return hash;
    }

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
    
    @Override
    public Selections clone()
    {
        Object obj = null;
        try
        {
            obj = super.clone();
        }
        catch(CloneNotSupportedException e){}
        return (Selections) obj;
    }
}
