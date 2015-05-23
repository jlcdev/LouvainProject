package domain.grafos;

import java.util.ArrayList;

/**
 *
 * @author Joan Rodas
 */
public class Selections implements Cloneable
{
    private ArrayList<String> selection;
    private ArrayList<Integer> selectionInt;
    
    public Selections()
    {
        this.selection = new ArrayList<>();
        this.selectionInt = new ArrayList<>();
    }

    /**
     * Obtain selection
     *
     * @return selection, an ArrayList<Strings> containing the names of the
     * nodes to be selected.
     */
    public ArrayList<String> getSelecion()
    {
        return this.selection;
    }
    
    public void addToSelection(String name)
    {
        this.selection.add(name);
    }

    /**
     * Set Selection
     *
     * @param selection, the ArrayList<Strings> containing the name of the nodes
     * to be selected.
     */
    public void setSelection(ArrayList<String> selection)
    {
        this.selection.addAll(selection);
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
