package domain.grafos;

import java.util.ArrayList;
import java.util.Collections;

import shared.Graph;

/**
*
* @author Joan Rodas
*/

public class Conversion
{
    private static Grafo removeSelection(Grafo g, Selections s, Selections s2)
    {
        ArrayList<Integer> removeList = new ArrayList<>();
        for(int i = 0; i < g.getNumVertex(); i++)
        {
            String name = g.getTranslator(i);
            if(!s.getSelecion().contains(name) || !s2.getSelecion().contains(name))
            {
                if(!removeList.contains(i)) removeList.add(i);
            }
        }
        Collections.reverse(removeList);
        for(int i : removeList)
        {
            g.remove(i);
        }
        return g;
    }

    /**
     * @param grafo, Graph to be converted
     * @param f, Filters (priorities)
     * @param s, Category Selections
     * @param s2, Page Selections
     * @return Weighted graph without pages
     */
    public static Graph<Integer, Double> toWeightedGraph(Grafo grafo, Filters f, Selections s, Selections s2)
    {
        System.out.println("Vertex antes: "+grafo.getNumVertex());
        int[] filters = f.getFilters();
        Grafo g = removeSelection(grafo, s, s2);
        System.out.println("Vertex despues: "+grafo.getNumVertex());
        System.out.println("Aristas: "+g.getNumAristas());
        Graph<Integer, Double> gr = new Graph<>();
        double pagNode = 0, catNode = 0, fathersNode = 0, sonsNode = 0;
        double catComu = 0, pagComu = 0, fathersComu = 0, sonsComu = 0;
        double pesName = 0, pesCat = 0, pesPage = 0, pesCsupC = 0, pesCsubC = 0;
        double pagVei = 0, catVei = 0, fathersVei = 0, sonsVei = 0;
        Node n, vei;
        String nname, veiname;
        int tam = g.getNumVertex(), tam2;
        for(int i = 0; i < tam; i++)
        {
            pagNode = 0; catNode = 0; fathersNode = 0; sonsNode = 0;
            n = g.getNodeNumber(i);
            if(n != null)
            {
                nname = n.getNombre();
                if(n.getClass() == Categoria.class) //Nomes volem categories
                {
                    if(!gr.getVertexs().contains(i)) gr.addVertex(i);
                    catNode = g.getNumCatAdyacent(i);
                    pagNode = g.getNumPagAdyacent(i);
                    fathersNode = g.getNumCsupCAdyacent(i);
                    sonsNode = g.getNumCsubCAdyacent(i);
                    ArrayList<Integer> veins = g.getAdyacents(i);
                    tam2 = veins.size();
                    int neigh;
                    for(int j = 0; j < tam2; j++)
                    {
                        neigh = veins.get(j);
                        vei = g.getNodeNumber(neigh);
                        veiname = vei.getNombre();
                        if(vei.getClass() == Categoria.class) //Si es una pàgina ens es igual l'enllaç
                        {
                            catComu = 0; pagComu = 0; fathersComu = 0; sonsComu = 0;
                            pesName = 0; pesCat = 0; pesPage = 0; pesCsupC = 0; pesCsubC = 0;
                            pagVei = 0; catVei = 0; fathersVei = 0; sonsVei = 0;

                            if(filters[0] > 0) pesName = DiceCoefficient.diceCoefficientOptimized(nname, veiname) * filters[0];
                            
                            if(filters[1] > 0)
                            {
                                catVei = g.getNumCatAdyacent(neigh);
                                catComu = g.getNumCommonCatAdyacent(i, neigh);
                                if(catNode + catVei != 0)
                                    pesCat = ((catComu * 2) / (catNode + catVei)) * filters[1]; //Max = 10 en tot
                                else pesCat = 0.0;
                            }
                            if(filters[2] > 0)
                            {
                                pagVei = g.getNumPagAdyacent(neigh);
                                pagComu = g.getNumCommonPagAdyacent(i, neigh);
                                if(pagNode + pagVei != 0)
                                    pesPage = ((pagComu * 2) / (pagNode + pagVei)) * filters[2];
                                else pesPage = 0.0;
                            }
                            if(filters[3] > 0)
                            {
                                fathersVei = g.getNumCsupCAdyacent(veins.get(j));
                                fathersComu = g.getNumCommonCsupCAdyacent(i, neigh);
                                if(fathersNode + fathersVei != 0) 
                                    pesCsupC = ((fathersComu * 2) / (fathersNode + fathersVei)) * filters[3];
                                else pesCsupC = 0.0;
                            }
                            if(filters[4] > 0)
                            {
                                sonsVei = g.getNumCsubCAdyacent(neigh);
                                sonsComu = g.getNumCommonCsubCAdyacent(i, neigh);
                                if(sonsNode + sonsVei != 0)
                                    pesCsubC = ((sonsComu * 2) / (sonsNode + sonsVei)) * filters[4];
                                else pesCsubC = 0.0;
                            }

                            double pes_total = (pesName + pesCat + pesPage + pesCsupC + pesCsubC) * 2; //*2 per fer max = 100
                            gr.addEdge(i, neigh, pes_total);
                        }
                    }
                }
            }	
        }
        return gr;
    }
}
