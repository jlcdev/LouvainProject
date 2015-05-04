package domain.grafos;

import java.util.ArrayList;

/**
*
* @author Joan Rodas
*/


public class Selections {
	
	private ArrayList<String> selection;

	
	/**
     * Obtain selection 
     * @return selection, an ArrayList<Strings> containing the names of the nodes to be selected.
     */
	public ArrayList<String> getSelecion() {
		return selection;
	}

	/**
     * Set Selection 
     * @param selection, the ArrayList<Strings> containing the name of the nodes to be selected.
    */
	public void setSelection(ArrayList<String> selection) {
		this.selection = selection;
	}
	
	

}
