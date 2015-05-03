package Domain.Grafos;

/**
*
* @author Joan
*/
	
public class Filters {

	private int[] priorities;

	
	public Filters() {
		priorities = new int[]{0,0,0,0,0}; 
	}
	
	public Filters(int pname, int pcat, int ppag, int pfat, int pson) {
		priorities = new int[]{pname,pcat,ppag,pfat,pson}; 
	}
	
	
	public int getName_priority() {
		return priorities[0];
	}
	public void setName_priority(int name_priority) {
		this.priorities[0] = name_priority;
	}
	
	
	public int getCategories_priority() {
		return priorities[1];
	}
	public void setCategories_priority(int categories_priority) {
		this.priorities[1] = categories_priority;
	}
	
	
	public int getPages_priority() {
		return priorities[2];
	}
	public void setPages_priority(int pages_priority) {
		this.priorities[2] = pages_priority;
	}
	
	
	public int getFather_priority() {
		return priorities[3];
	}
	public void setFather_priority(int father_priority) {
		this.priorities[3] = father_priority;
	}
	
	
	public int getSon_priority() {
		return priorities[4];
	}
	public void setSon_priority(int son_priority) {
		this.priorities[4] = son_priority;
	}
	
	public void setFilters(int[] priorities)
	{
		this.priorities = priorities;
	}
	
	public int[] getFilters() 
	{
		return priorities;
	}
		
	
}
