package domain.grafos;

import java.util.ArrayList;

import shared.Graph;

/**
*
* @author Joan
*/

public class Conversion {
	
	
	private static Grafo removeSelection(Grafo g, Selections s)
	{
		for(int i = 0; i < g.getNumVertex(); i++)
		{
			String name = g.getTranslator(i);
			if(!s.getSelecion().contains(name)) //Si el node no es troba en la selecció, l'eliminem a ell i als seus enllaços
			{
				ArrayList<Integer> veins = g.getAdyacents(i);
				for(int j=0; j<veins.size(); j++)
				{
					//g.removeEdge(i,j);
				}
				//g.removeNode(i);
			}
		}
		return g;
	}
	
	public static void toWeightedGraph(Grafo grafo, Filters f, Selections s)
	{
		int[] filters = f.getFilters();
		Grafo g = removeSelection(grafo, s);
		Graph<Integer,Integer> gr = new Graph<Integer, Integer>();
		for(int i=0; i < g.getNumVertex(); i++) 
		{
			double pagNode=0,catNode=0,fathersNode=0,sonsNode=0;
			Node n = g.getNodeNumber(i);
			if(!gr.getVertexs().contains(i))
			{
				String nname = n.getNombre();
				if(n.getClass() == Categoria.class) //Nomes volem categories
				{
					gr.addVertex(i);
					//gr.addEdge(i, i, 0); //Enlace a si mismo(?)
					
					catNode = g.getNumCatAdyacent(i);
					pagNode = g.getNumPagAdyacent(i);
					fathersNode = g.getNumCsupCAdyacent(i);
					sonsNode = g.getNumCsubCAdyacent(i);
					ArrayList<Integer> veins = g.getAdyacents(i);
									
					for(int j=0;j<veins.size();j++)
					{
						
						
						if(!gr.getVertexs().contains(i))
						{
							if(n.getClass() == Categoria.class) //Si es una pàgina ens es igual l'enllaç
							{
								double catComu=0,pagComu=0,fathersComu=0,sonsComu=0;
								double pesName=0,pesCat=0,pesPage=0,pesCsupC=0,pesCsubC=0;
								double pagVei=0,catVei=0,fathersVei=0,sonsVei=0;
								
								gr.addVertex(j);
								
								catVei = g.getNumCatAdyacent(j);
								pagVei = g.getNumPagAdyacent(j);
								fathersVei = g.getNumCsupCAdyacent(j);
								sonsVei = g.getNumCsubCAdyacent(j);
								
								Node vei = g.getNodeNumber(j); 
								String veiname = vei.getNombre();
								
								if(filters[0] > 0) pesName = DiceCoefficient.diceCoefficientOptimized(nname, veiname)*filters[0]; 
								if(filters[1] > 0) catComu = g.getNumCommonCatAdyacent(i, j);
								if(filters[2] > 0) pagComu = g.getNumCommonPagAdyacent(i, j);
								if(filters[3] > 0) fathersComu = g.getNumCommonCsupCAdyacent(i, j);
								if(filters[4] > 0) sonsComu = g.getNumCommonCsubCAdyacent(i, j);
								
								pesCat = ((catComu*2)/(catNode + catVei))*filters[1]; //Max = 10 en tot
								pesPage = ((pagComu*2)/(pagNode + pagVei))*filters[2];
								pesCsupC = ((fathersComu*2)/(fathersNode + fathersVei))*filters[3];
								pesCsubC = ((sonsComu*2)/(sonsNode + sonsVei))*filters[4];								
								
								double pes_total = (pesName + pesCat + pesPage + pesCsupC + pesCsubC)*2; //*2 per fer max = 100
								int pt = (int)pes_total;
								gr.addEdge(i, j, pt);								
							}									
						}									
					}					
				}
			}		
		}
	}
	
}
