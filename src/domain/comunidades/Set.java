package domain.comunidades;

import java.util.ArrayList;
import java.util.HashMap;

import domain.grafos.Filters;
import domain.grafos.Selections;

/**
*
* @author Joan
*/

public class Set {

	private String name;
	private int id;
	private boolean modified;
	private String alg;
	private ArrayList<Community> communities;
	private HashMap<Integer, ArrayList<Integer> > links;
	//private ArrayList<String> filters;
	//private ArrayList<String> selections;
	Filters f;
	Selections s;
	
	
	public Set(int id) 
	{
		this.modified = false;
		this.id = id;
		this.name = "Set"+id;
		this.alg = "None";
		this.communities = new ArrayList<Community>();
		this.links = new HashMap<Integer, ArrayList<Integer> >();
		//this.filters = new ArrayList<String>();
		this.f = new Filters();
		this.s = new Selections();
	}

	/**
	 * Obté estat del conjunt
	 * @return modified, l'estat del conjunt de comunitats 
	 */
	public boolean isModified()
	{
		return modified;
	} 
	
	/**
	 * Modifica l'estat del conjunt
	 * @param bool, el valor de l'estat del conjunt (cert = modificat)
	 */
	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
	
	/**
	 * Modifica el nom del conjunt de comunitats
	 * @param name, el nou nom del conjunt 
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Obté el nom del conjunt
	 * @return name, el nom del conjunt 
	 */
	public String getName() 
	{
		return name;
	}
		
	
		/**
	 * Modifica l'identificador del conjunt de comunitats
	 * @param id, el nou identificador del conjunt 
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Obté l'identificador del conjunt
	 * @return id, l'identificador del conjunt 
	 */
	public int getId() 
	{
		return id;
	}
	
	
	/**
	 * Obté la comunitat a la posició pos
	 * @param pos, la posició de la comunitat
	 * @return Comunitat a la posició pos 
	 */	
	public Community getCommunity(int pos)
	{
		return communities.get(pos);
	}
	
	/**
	 * Modifica l'algorisme
	 * @param alg, l'algorisme 
	 */
	public void setAlgorithm(String alg) 
	{
		this.alg = alg;
	}
	
	/**
	 * Obté l'algorisme del conjunt
	 * @return alg, l'algorisme usat en el conjunt 
	 */
	public String getAlgorithm()
	{
		return alg;
	}
	
	/**
	 * Afegeix categoria a la comunitat
	 * @param c, categoria a afegir 
	 */	
	public void addCommunity(Community c)
	{
		if (!communities.contains(communities)) communities.add(c);
	}
	
	/**
	 * Elimina categoria del del conjunt
	 * @return c, categoria a eliminar 
	 */	
	public void removeCommunity(Community c)
	{
		communities.remove(c);
		//
	}	
	
	/**
	 * Obté conjunt de comunitats
	 * @return communities, conjunt de comunitats 
	 */	
	public ArrayList<Community> getCommunities()
    {
        return communities;
    }

	/**
	 * Estableix conjunt de comunitats
	 * @param communities, el conjunt de comunitats 
	 */
    public void setCommunities(ArrayList<Community> communities)
    {
        this.communities = communities;
    }
    
    /**
	 * Obté nombre de comunitats al conjunt
	 * @return nombre de comunitats del conjunt 
	 */	
	public int getNumCommunities()
	{
		return communities.size();
	}
    
    /**
	 * Obté filtres usats
	 * @return f, filtres usats 
	 */	
   // public ArrayList<String> getFilters()
	public Filters getFilters()
    {
        //return filters;
		return f;
    }

    /**
	 * Obté array filtres usats
	 * @return filtres usats 
	 */	
	public int[] getAllFilters()
	{
		return f.getFilters();
	}	
	
	/**
	 * Estableix filtres
	 * @param filters, conjunt de filtres usats 
	 */
    //public void setFilters(ArrayList<String> filters)
	public void setFilters(int[] i)
    {
        //this.filters = filters;
		f.setFilters(i);
    }	
	

    /**
	 * Obté seleccions
	 * @return selections, conjunt de seleccions 
	 */	
    public Selections getSelections()
    {
        return s;
    }

    /**
	 * Estableix seleccions
	 * @param selections, conjunt de seleccions 
	 */
    public void setSelections(Selections s)
    {
        this.s = s;
    }
    

	/**
	 * Obté links d'una comunitat
	 * @param idCom
	 * @return Links de la comunitat 
	 */
	public ArrayList<Integer> getLinks(int idCom)
	{
		return links.get(idCom);
	}	    

	
    	
	/**
	 * Afegeix enllaç
	 * @param id1 id2, identificadors de les comunitats a enllaçar 
	 */
	public void addLink(Integer idCom1, Integer idCom2)
	{
		ArrayList<Integer> al = getLinks(idCom1); //Obtenim enllaços de Com1
		if (al == null) al = new ArrayList<Integer>(); 
		al.add(idCom2); //Afegim enllaç a Com2
		links.put(idCom1, al); //Actualitzem enllaços Com1
				
		al = getLinks(idCom2); //Obtenim enllaços de Com2
		if (al == null) al = new ArrayList<Integer>(); 
		al.add(idCom1); //Afegim enllaç a Com1
		links.put(idCom2, al); //Actualitzem enllaços Com2		
	}
	
	/**
	 * Elimina enllaç
	 * @param id1 id2, identificadors de les comunitats a desenllaçar 
	 */
	public void removeLink(Integer idCom1, Integer idCom2)
	{
		ArrayList<Integer> al = getLinks(idCom1); //Obtenim enllaços de Com1
		if (al == null) al = new ArrayList<Integer>(); 
		al.remove(idCom2);		
		links.put(idCom1, al);
		
		al = getLinks(idCom2); //Obtenim enllaços de Com2
		if (al == null) al = new ArrayList<Integer>(); 
		al.remove(idCom1);		
		links.put(idCom2, al);		
	}	
	
			
	/**
	 * Llista comunitats del conjunt
	 *  
	 */
	public void listCommunities()
	{
		for(int i = 0; i < communities.size(); i++)
		{
			String nm = communities.get(i).getName();
			System.out.println(nm);
		}
		System.out.println(communities.size());
	}
	/* Ex:
	 * 
	 * Comunitat1
	 * Comunitat2 
	 * Comunitat3
	 * Comunitat4
	 * 
	 * #Communities = 4
	 */
	
	
	/**
	 * Llista comunitats del conjunt amb links
	 *  
	 */
	public void listLinks() 
	{
		for(int i = 0; i < communities.size(); ++i)
		{
			String nm = communities.get(i).getName();
			ArrayList<Integer> ln = links.get(communities.get(i).getId());
			if (ln != null) {
				System.out.print(nm+":");
				for(int j = 0; j < ln.size(); j++)
				{
					System.out.print(" ");
					System.out.print(getCommunity(ln.get(j)-1).getName());
				}
				System.out.println();
			}
		}				
	}
	
	/* Ex:
	 * 
	 * Comunitat1: 2,3
	 * Comunitat2: 1,4 
	 * Comunitat3: 1,4
	 * Comunitat4: 2,3
	 */
	
	
	
	
	
	
}
