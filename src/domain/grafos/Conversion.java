package domain.grafos;

import java.util.ArrayList;

import shared.Graph;

/**
*
* @author Joan Rodas
*/

public class Conversion
{
    private static Grafo removeSelection(Grafo g, Selections s, Selections s2)
    {
        for(int i = 0; i < g.getNumVertex(); i++)
        {
            String name = g.getTranslator(i);
            if(!s.getSelecion().contains(name) || !s2.getSelecion().contains(name))
            {
                System.out.println(name);
                g.remove(name);
            }
        }
        return g;
    }

    /**
     * @param grafo, Graph to be converted
     * @param f, Filters (priorities)
     * @param s, Selections
     * @return Weighted graph without pages
     */
    public static Graph<Integer, Double> toWeightedGraph(Grafo grafo, Filters f, Selections s, Selections s2)
    {
        int[] filters = f.getFilters();
        Grafo g = removeSelection(grafo, s, s2);

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
                    for(int j = 0; j < tam2; j++)
                    {
                        vei = g.getNodeNumber(veins.get(j));
                        veiname = vei.getNombre();
                        if(vei.getClass() == Categoria.class) //Si es una pàgina ens es igual l'enllaç
                        {
                            catComu = 0; pagComu = 0; fathersComu = 0; sonsComu = 0;
                            pesName = 0; pesCat = 0; pesPage = 0; pesCsupC = 0; pesCsubC = 0;
                            pagVei = 0; catVei = 0; fathersVei = 0; sonsVei = 0;

                            if(filters[0] > 0) pesName = DiceCoefficient.diceCoefficientOptimized(nname, veiname) * filters[0];
                            
                            if(filters[1] > 0)
                            {
                                catVei = g.getNumCatAdyacent(veins.get(j));
                                catComu = g.getNumCommonCatAdyacent(i, veins.get(j));
                                pesCat = ((catComu * 2) / (catNode + catVei)) * filters[1]; //Max = 10 en tot
                            }
                            if(filters[2] > 0)
                            {
                                pagVei = g.getNumPagAdyacent(veins.get(j));
                                pagComu = g.getNumCommonPagAdyacent(i, veins.get(j));
                                pesPage = ((pagComu * 2) / (pagNode + pagVei)) * filters[2];
                            }
                            if(filters[3] > 0)
                            {
                                fathersVei = g.getNumCsupCAdyacent(veins.get(j));
                                fathersComu = g.getNumCommonCsupCAdyacent(i, veins.get(j));
                                pesCsupC = ((fathersComu * 2) / (fathersNode + fathersVei)) * filters[3];
                            }
                            if(filters[4] > 0)
                            {
                                sonsVei = g.getNumCsubCAdyacent(veins.get(j));
                                sonsComu = g.getNumCommonCsubCAdyacent(i, veins.get(j));
                                pesCsubC = ((sonsComu * 2) / (sonsNode + sonsVei)) * filters[4];
                            }

                            double pes_total = (pesName + pesCat + pesPage + pesCsupC + pesCsubC) * 2; //*2 per fer max = 100
                            gr.addEdge(i, veins.get(j), pes_total);
                        }
                    }
                }
            }	
        }
        return gr;
    }
}
