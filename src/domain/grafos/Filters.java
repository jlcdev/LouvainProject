package domain.grafos;

/**
*
* @author Joan Rodas
*/
	
public class Filters {

	private int[] priorities;

	
	/**
	 * Init Filters with all priorities = 0
	 */
	public Filters() {
		priorities = new int[]{0,0,0,0,0}; 
	}
	
	/**
	 * Initialize Filter with priorities
	 * @param pname
	 * @param pcat
	 * @param ppag
	 * @param pfat
	 * @param pson
	 */
	public Filters(int pname, int pcat, int ppag, int pfat, int pson) {
		priorities = new int[]{pname,pcat,ppag,pfat,pson}; 
	}
	
	
	/**
	 * Set all priorities
	 * @param pname
	 * @param pcat
	 * @param ppag
	 * @param pfat
	 * @param pson
	 */
	public void setAll(int pname, int pcat, int ppag, int pfat, int pson)
    {
    	priorities = new int[]{pname,pcat,ppag,pfat,pson};
    }
      
	/**
	 * @return String priorities
	 */
	public String getAll()
	{
		String result = "";
		int tam = this.priorities.length;
		int i;
		for(i=0;i<tam-1;++i) result += this.priorities[i] + ",";
		if(i < tam-1) result += this.priorities[i+1];
		return result;
	}
	
	
	/**
	 * @return name_priority
	 */
	public int getName_priority() {
		return priorities[0];
	}
	/**
	 * @param name_priority
	 */
	public void setName_priority(int name_priority) {
		this.priorities[0] = name_priority;
	}
	
	
	/**
	 * @return categories_priority
	 */
	public int getCategories_priority() {
		return priorities[1];
	}
	/**
	 * @param categories_priority
	 */
	public void setCategories_priority(int categories_priority) {
		this.priorities[1] = categories_priority;
	}
	
	
	/**
	 * @return pages_priority
	 */
	public int getPages_priority() {
		return priorities[2];
	}
	/**
	 * @param pages_priority
	 */
	public void setPages_priority(int pages_priority) {
		this.priorities[2] = pages_priority;
	}	
	
	/**
	 * @return father_priority
	 */
	public int getFather_priority() {
		return priorities[3];
	}
	/**
	 * @param father_priority
	 */
	public void setFather_priority(int father_priority) {
		this.priorities[3] = father_priority;
	}
	
	
	/**
	 * @return son_priority
	 */
	public int getSon_priority() {
		return priorities[4];
	}
	/**
	 * @param son_priority
	 */
	public void setSon_priority(int son_priority) {
		this.priorities[4] = son_priority;
	}
	
	/**
	 * @param priorities
	 */
	public void setFilters(int[] priorities)
	{
		this.priorities = priorities;
	}
	
	/**
	 * @return Array priorities
	 */
	public int[] getFilters() 
	{
		return priorities;
	}
		
	
}
