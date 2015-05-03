package domain.comunidades;

import domain.grafos.Categoria;

import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;

/**
*
* @author Joan
*/

public class Community {

	private String name;
	private int id;
	private ArrayList<Categoria> categories;
	private HashMap<Integer, ArrayList<Integer> > links; 
	
	
	public Community(int id) {
		this.id = id;
		this.name = "Community"+id;
		categories = new ArrayList<Categoria>();
		links = new HashMap<Integer, ArrayList<Integer> >();		
	}	
	
	/**
	 * Modifica el nom de la comunitat
	 * @param name, el nou nom de la comunitat 
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Obté el nom de la comunitat
	 * @return name, el nom de la comunitat 
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Modifica l'identificador de la comunitat
	 * @param id, el nou identificador de la comunitat 
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Obté l'identificador de la comunitat
	 * @return id, l'identificador de la comunitat 
	 */
	public int getId() 
	{
		return id;
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
        final Community other = (Community) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }
	
	/**
	 * Afegeix node Categoria a la comunitat
	 * @param c, la nova categoria 
	 */
	public void addCategory(Categoria c)
	{
		if (!categories.contains(c)) categories.add(c);
		else ; //caca
	}
	
	/**
	 * Elimina node Categoria de la comunitat
	 * @param c, la categoria a eliminar 
	 */
	public void removeCategory(Categoria c)
	{
		categories.remove(c);
	}
	
	/**
	 * Mostra les categories (nodes) de la comunitat
	 *  
	 */
	public void showCategories()
	{
		for (int i = 0; i < categories.size(); ++i)
		{
			Categoria c = categories.get(i);
			String nom = c.getNombre();
			System.out.println(nom);
		}
		System.out.println(categories.size());
		
	}
	
	
	/**
	 * Afegeix enllaç entre 2 categories
	 * @param idCat1, idCat2 nou enllaç entre categories 
	 */
	public void addLink(Integer idCat1, Integer idCat2)
	{
		ArrayList<Integer> al = getLinks(idCat1); //Obtenim enllaços de Cat1
		if (al == null) al = new ArrayList<Integer>(); 
		al.add(idCat2); //Afegim enllaç a Cat2
		links.put(idCat1, al); //Actualitzem enllaços Cat1
		
		al = getLinks(idCat2); //Obtenim enllaços de Cat2
		if (al == null) al = new ArrayList<Integer>(); 
		al.add(idCat1); //Afegim enllaç a Cat1
		links.put(idCat2, al); //Actualitzem enllaços Cat2	
	}
	
	/**
	 * Elimina enllaç entre dues categories
	 * @param idCat1, idCat2, categories que deixaran de ser veines 
	 */
	public void removeLink(Integer idCat1, Integer idCat2)
	{
		ArrayList<Integer> al = getLinks(idCat1); //Obtenim enllaços de Cat1
		if (al == null) al = new ArrayList<Integer>(); 
		al.remove(idCat2);		
		links.put(idCat1, al);
		
		al = getLinks(idCat2); //Obtenim enllaços de Cat2
		if (al == null) al = new ArrayList<Integer>(); 
		al.remove(idCat1);		
		links.put(idCat2, al);
		
	}
	
	
	
	/**
	 * Mostra enllaços de la comunitat
	 * 
	 */
	public void showLinks() 
	{
		for(Integer i = 0; i < categories.size(); ++i)
		{
			String nm = categories.get(i).getNombre();
			ArrayList<Integer> ln = links.get(i+1); //id?
			if (ln != null) {
				System.out.print(nm+":");
				for(int j = 0; j < ln.size(); j++)
				{
					System.out.print(" "+(ln.get(j)));								
				}
				System.out.println();
			}
		}		
		
	}
	
	/**
	 * Obté enllaços d'una comunitat
	 * 
	 */
	public ArrayList<Integer> getLinks(int id)
	{
		return links.get(id);
	}
	
	
	/**
	 * Obté nombre de categories
	 * @return Nombre de categories a la comunitat
	 */
	public int getNumCategories() {
		return categories.size();
	}
	
	
	
	





}
